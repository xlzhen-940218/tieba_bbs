
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class SignTieba
 */
@WebServlet("/SignTieba")
public class SignTieba extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignTieba() {
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
			if (jsonObject.getString("tieba_id").length() == 0 || jsonObject.getString("user_id").length() == 0) {
				throw new Exception("tieba_id or user_id failed");
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
			sql = "select * from tieba_db.tieba_sign_table where tieba_id=" + jsonObject.getString("tieba_id")
					+ " and user_id=" + jsonObject.getString("user_id") + ";";
			rs = stmt.executeQuery(sql);
			JSONObject jsonobj = new JSONObject();
			while (rs.next()) {
				jsonobj.put("today_sign_type", rs.getString("today_sign_type"));
				jsonobj.put("last_sign_time", rs.getString("last_sign_time"));
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(jsonobj.getDate("last_sign_time") != null ? jsonobj.getDate("last_sign_time")
					: new Date(System.currentTimeMillis()));

			calendar.add(Calendar.DAY_OF_YEAR, 1);

			if (jsonobj.getDate("last_sign_time") != null && jsonobj.containsKey("today_sign_type")
					&& calendar.getTime().getTime() > System.currentTimeMillis()) {
				// 已经签到过了
			} else {
				// 没有签到过或当天没签到
				sql = "insert into tieba_db.tieba_sign_table values(" + jsonObject.getString("tieba_id") + ","
						+ jsonObject.getString("user_id")
						+ ",true,now()) ON DUPLICATE KEY UPDATE today_sign_type=true, last_sign_time=now();";
				stmt.executeUpdate(sql);
				jsonobj.put("today_sign_type", "1");
				jsonobj.put("last_sign_time", new Date(System.currentTimeMillis()));

				sql = "update tieba_db.tieba_user_table set user_integration=user_integration+'5' where user_id="
						+ jsonObject.getString("user_id") + ";";
				stmt.executeUpdate(sql);
			}
			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);
			out = response.getWriter();
			out.println("{\"token\":\"" + token + "\",\"sign\":" + jsonobj + "}");
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
