
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tieba.MD5SignUtils;

/**
 * Servlet implementation class AddChat 寻找所有可聊天的对象
 */
@WebServlet("/AddChat")
public class AddChat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddChat() {
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

			if (jsonObject.getString("pageIndex") == null || jsonObject.getString("pageIndex").length() < 1
					|| jsonObject.getString("user_id").length() < 1 || jsonObject.getString("token").length() < 1) {
				throw new Exception("pageIndex or user_id or token not found!");
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

			String search_key = jsonObject.getString("search_key");

			sql = "SELECT * FROM tieba_db.tieba_user_table "
					+ (search_key == null ? ""
							: " where user_name like '%" + search_key + "%' " + " or user_pet_name like '%" + search_key
									+ "%'")
					+ " limit " + (jsonObject.getIntValue("pageIndex") - 1) * jsonObject.getIntValue("pageCount") + ","
					+ jsonObject.getIntValue("pageCount") + ";";
			log(sql);
			rs = stmt.executeQuery(sql);

			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					jsonobj.put("user_id", rs.getString("user_id"));
					jsonobj.put("user_name", rs.getString("user_name"));
					jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
					jsonobj.put("user_head_img", rs.getString("user_head_img"));
				}
				jsonArray.add(jsonobj);
			}

			if (jsonArray.size() == 0 && jsonObject.getIntValue("pageIndex") == 1) {
				sql = sql.replaceAll("," + jsonObject.getIntValue("pageCount") + ";", "");
				rs = stmt.executeQuery(sql);
				log(sql);
				while (rs.next()) {

					JSONObject jsonobj = new JSONObject();
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						jsonobj.put("user_id", rs.getString("user_id"));
						jsonobj.put("user_name", rs.getString("user_name"));
						jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
						jsonobj.put("user_head_img", rs.getString("user_head_img"));
					}
					jsonArray.add(jsonobj);
				}
			}

			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);

			out = response.getWriter();

			if (jsonArray.size() == 0) {
				out.println("{\"result\":\""
						+ (search_key == null ? (jsonObject.getInteger("pageIndex") > 1 ? "没有更多数据了" : "找不到用户")
								: "没有搜索到用户。")
						+ "\",\"token\":\"" + token + "\"}");
			} else {
				out.println("{\"token\":\"" + token + "\",\"users\":" + jsonArray + "}");
			}
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
