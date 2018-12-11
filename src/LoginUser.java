
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tieba.GetIP;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class LoginUser
 */
@WebServlet("/LoginUser")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUser() {
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
			if (jsonObject.getString("user_name").length() == 0 || jsonObject.getString("user_pwd").length() == 0) {
				throw new Exception("user_name or user_pwd failed");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);
			jsonObject.put("user_name", SensitiveUtils.start(jsonObject.getString("user_name")));
			Statement stmt = connection.createStatement();
			String sql;
			sql = "SELECT * FROM tieba_db.login_lock_table where login_ip='" + GetIP.getIpAddr(request) + "';";
			ResultSet rs = stmt.executeQuery(sql);
			boolean isLock = false;
			Date datetime = null;
			while (rs.next()) {
				isLock = rs.getBoolean("login_lock");
				datetime = rs.getDate("login_last_time");
			}
			if (isLock && datetime != null && datetime.getTime() + 24 * 60 * 60 * 1000 > System.currentTimeMillis()) {
				rs.close();
				stmt.close();
				connection.close();
				throw new Exception("您的IP已被锁定，24小时后自动解锁");
			}

			sql = "SELECT users.*,(select count(topic.topic_id) from tieba_db.tieba_topic_table as topic "
					+ "where topic.user_id=users.user_id) as topic_count,(select count(comm.comment_id) "
					+ "from tieba_db.tieba_topic_comment_table as comm where comm.user_id=users.user_id) "
					+ "as comment_count FROM tieba_db.tieba_user_table as users where user_name='"
					+ jsonObject.getString("user_name") + "'&&user_pwd='" + jsonObject.getString("user_pwd") + "';";

			rs = stmt.executeQuery(sql);
			JSONObject jsonobj = new JSONObject();
			while (rs.next()) {
				jsonobj.put("user_id", rs.getString("user_id"));
				jsonobj.put("user_name", rs.getString("user_name"));
				jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
				jsonobj.put("user_pwd", rs.getString("user_pwd"));
				jsonobj.put("user_email", rs.getString("user_email"));
				jsonobj.put("user_integration", rs.getString("user_integration"));
				jsonobj.put("user_head_img", rs.getString("user_head_img"));
				jsonobj.put("topic_count", rs.getString("topic_count"));
				jsonobj.put("comment_count", rs.getString("comment_count"));
			}

			if (!jsonobj.containsKey("user_id")) {
				sql = isLock
						? "update tieba_db.login_lock_table set login_lock=false,login_failed_count=0 where login_ip='"
								+ GetIP.getIpAddr(request) + "';"
						: "insert into tieba_db.login_lock_table values('" + GetIP.getIpAddr(request)
								+ "',now(),false,1) "
								+ "ON DUPLICATE KEY UPDATE login_last_time=now(),login_failed_count=login_failed_count+1"
								+ ",login_lock=login_failed_count>4;";
				stmt.executeUpdate(sql);
				rs.close();
				stmt.close();
				connection.close();
				throw new Exception("用户名或密码不正确。");
			} else {
				sql = "SELECT count(message.message_id) as message_count  FROM tieba_db.chat_friend_table as chat "
						+ "left join tieba_db.chat_message_table as message on message.chat_id=chat.chat_id "
						+ "where user_id=" + jsonobj.getString("user_id") + " or friend_id="
						+ jsonobj.getString("user_id") + ";";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					jsonobj.put("message_count", rs.getString("message_count"));
				}
			}
			String token = MD5SignUtils.MD5Encode(jsonobj.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonobj.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);
			out = response.getWriter();
			out.println("{\"token\":\"" + token + "\",\"user\":" + jsonobj + "}");
			rs.close();
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

}
