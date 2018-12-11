
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class ChangeUserInfo
 */
@WebServlet("/ChangeUserInfo")
public class ChangeUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeUserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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

			if (jsonObject.getString("user_id") == null || jsonObject.getString("user_pwd") == null) {
				throw new Exception("user_id or user_pwd not found!");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

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
			sql = "update tieba_db.tieba_user_table set ";

			if (jsonObject.getString("user_name") != null) {
				jsonObject.put("user_name", SensitiveUtils.start(jsonObject.getString("user_name")));
				sql += "user_name='" + jsonObject.getString("user_name") + "',";
			}
			if (jsonObject.getString("user_pet_name") != null) {
				jsonObject.put("user_pet_name", SensitiveUtils.start(jsonObject.getString("user_pet_name")));
				sql += "user_pet_name='" + jsonObject.getString("user_pet_name") + "',";
			}
			if (jsonObject.getString("user_email") != null)
				sql += "user_email='" + jsonObject.getString("user_email") + "',";

			if (jsonObject.getString("user_head_img") != null)
				sql += "user_head_img='" + jsonObject.getString("user_head_img") + "',";

			if (jsonObject.getString("user_name") != null || jsonObject.getString("user_pet_name") != null
					|| jsonObject.getString("user_email") != null || jsonObject.getString("user_head_img") != null)
				sql = sql.substring(0, sql.length() - 1);

			sql += " where user_id=" + jsonObject.getString("user_id") + " and user_pwd='"
					+ jsonObject.getString("user_pwd") + "';";
			log(sql);
			int isSuccess = stmt.executeUpdate(sql);

			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);

			out = response.getWriter();
			out.println("{\"token\":\"" + token + "\",\"result\":\""
					+ (isSuccess > 0 ? "用户信息更新成功，回到首页自动登录后可见信息变更" : "failed") + "\"}");
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
