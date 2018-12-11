
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class CashPrizeIntegration
 */
@WebServlet("/CashPrizeIntegration")
public class CashPrizeIntegration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashPrizeIntegration() {
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
			if (jsonObject.getString("money").length() == 0 || jsonObject.getString("month").length() == 0
					|| jsonObject.getString("user_id").length() == 0) {
				throw new Exception("money or month or user_id failed");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);
			String award_code = getRandomString(16);
			Statement stmt = connection.createStatement();
			String sql = "SELECT * FROM tieba_db.user_token_table where user_id=" + jsonObject.getString("user_id")
					+ " and login_token='" + jsonObject.getString("token") + "';";

			ResultSet rs = stmt.executeQuery(sql);
			String userId = null;
			while (rs.next()) {
				userId = rs.getString("user_id");
			}
			if (userId == null) {
				rs.close();
				stmt.close();
				connection.close();
				throw new Exception("token无效，请检查登录状态！");
			}

			sql = "SELECT * FROM tieba_db.tieba_user_table where user_id=" + jsonObject.getString("user_id") + ";";
			rs = stmt.executeQuery(sql);

			int integration = 0;
			while (rs.next()) {
				integration = rs.getInt("user_integration");
			}
			log(sql);

			if (jsonObject.getInteger("money") * 200 > integration)
				throw new Exception("积分余额不足");

			sql = "update tieba_db.tieba_user_table set user_integration=user_integration-'"
					+ jsonObject.getInteger("money") * 200 + "' where user_id=" + jsonObject.getString("user_id") + ";";

			int isSucess = stmt.executeUpdate(sql);
			if (isSucess == 0)
				throw new Exception("积分余额不足");

			sql = "insert into tieba_db.integration_cash_prize_table values(null,'" + jsonObject.getString("user_id")
					+ "','" + jsonObject.getString("money") + "','" + jsonObject.getString("month") + "','"
					+ award_code.toUpperCase() + "',false,now());";
			;
			log(sql);
			isSucess = stmt.executeUpdate(sql);

			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);

			out = response.getWriter();
			out.println("{\"result\":\"" + (isSucess > 0 ? award_code.toUpperCase() : "积分余额不足") + "\",\"token\":\""
					+ token + "\"}");
			stmt.close();
			connection.close();
		} catch (Exception e) {
			out.print("{\"result\":\"" + e.getMessage() + "\"}");
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

}
