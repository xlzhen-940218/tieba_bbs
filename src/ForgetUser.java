
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class ForgetUser
 */
@WebServlet("/ForgetUser")
public class ForgetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgetUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection connection = null;
		try {
			BufferedReader reader = request.getReader();
			String json = reader.readLine();
			reader.close();
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (jsonObject.getString("user_name").length() == 0 || jsonObject.getString("user_email").length() == 0) {
				throw new Exception("user_name or user_email failed");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();
			String pwd = getRandomString(6);
			String sql;
			sql = "update tieba_db.tieba_user_table set user_pwd='" + MD5SignUtils.MD5(pwd) + "' where user_name='"
					+ jsonObject.getString("user_name") + "'&&user_email='" + jsonObject.getString("user_email") + "';";

			int isSucess = stmt.executeUpdate(sql);
			out = response.getWriter();
			if (isSucess > 0) {
				sendEmail(pwd, jsonObject.getString("user_email"));
				out.println("{\"result\":\"邮件发送成功，请去邮箱获取您的新密码\"}");
			} else {
				out.println("{\"result\":\"用户名或邮箱错误！\"}");
			}

			stmt.close();
			connection.close();
		} catch (Exception e) {
			out.print("{\"result\":\"" + e.getMessage() + "\"}");
			e.printStackTrace();
		}

	}

	public static String getRandomString(int length) {
		// 定义一个字符串（A-Z，a-z，0-9）即62位；
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		// 由Random生成随机数
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		// 长度为几就循环几次
		for (int i = 0; i < length; ++i) {
			// 产生0-61的数字
			int number = random.nextInt(62);
			// 将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		// 将承载的字符转换成字符串
		return sb.toString();
	}

	private void sendEmail(String newpwd, String email) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.ym.163.com"); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", "smtp.ym.163.com"); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 使用JSSE的SSL
																						// socketfactory来取代默认的socketfactory
		props.put("mail.smtp.socketFactory.fallback", "false"); // 只处理SSL的连接,对于非SSL的连接不做处理
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
																// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true); // 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session); // 加载发件人地址
		try {
			message.setFrom(new InternetAddress("system@tieba.group"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 加载收件人地址
			message.setSubject("密码找回"); // 加载标题
			Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
			String text = "您的新密码是：" + newpwd + "，请使用新密码登录！";
			contentPart.setContent(text, "text/html;charset=utf-8");
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			message.saveChanges(); // 保存变化
			Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
			transport.connect("smtp.ym.163.com", "system@tieba.group", "3iJzvoE5cs"); // 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
