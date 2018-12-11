
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
 * Servlet implementation class GetTopic
 */
@WebServlet("/GetTopic")
public class GetTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTopic() {
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

			if (jsonObject.getString("pageIndex") == null || jsonObject.getString("pageIndex").length() < 1) {
				throw new Exception("pageIndex not found!");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();
			String search_key = jsonObject.getString("search_key");
			String user_id = jsonObject.getString("user_id");
			String tieba_id = jsonObject.getString("tieba_id");
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
			sql = "SELECT topic.*,users.user_id,users.user_name,users.user_head_img,users.user_integration,users.user_pet_name"
					+ ",(select count(star.topic_id) from tieba_db.topic_star_table as star where star.topic_id=topic.topic_id) "
					+ "as star_count FROM tieba_db.tieba_topic_table as topic left join tieba_db.tieba_user_table as users "
					+ "on users.user_id=topic.user_id";

			if (search_key != null || user_id != null || tieba_id != null) {
				sql += " where ";
			}
			boolean insertAnd = false;
			if (search_key != null) {
				sql += "topic.topic_title like '%" + search_key + "%'";
				insertAnd = true;
			}

			if (user_id != null && tieba_id == null) {
				sql += (insertAnd ? " and " : "") + "topic.user_id=" + user_id;
				insertAnd = true;
			}

			if (tieba_id != null) {
				sql += (insertAnd ? " and " : "") + "topic.tieba_id=" + tieba_id;
			}

			sql += " limit " + (jsonObject.getIntValue("pageIndex") - 1) * jsonObject.getIntValue("pageCount") + ","
					+ jsonObject.getIntValue("pageCount") + ";";
			log(sql);
			rs = stmt.executeQuery(sql);

			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					jsonobj.put("topic_id", rs.getString("topic_id"));
					jsonobj.put("tieba_id", rs.getString("tieba_id"));
					jsonobj.put("user_id", rs.getString("user_id"));
					jsonobj.put("user_name", rs.getString("user_name"));
					jsonobj.put("user_head_img", rs.getString("user_head_img"));
					jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
					jsonobj.put("user_integration", rs.getString("user_integration"));
					jsonobj.put("topic_title", rs.getString("topic_title"));
					jsonobj.put("topic_content", rs.getString("topic_content"));
					jsonobj.put("topic_create_time", rs.getString("topic_create_time"));
					jsonobj.put("star_count", rs.getString("star_count"));
					for (int j = 1; j < 10; j++) {
						if (rs.getString("topic_img" + j) != null && rs.getString("topic_img" + j).length() > 0)
							jsonobj.put("topic_img" + j, rs.getString("topic_img" + j));
					}
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
						jsonobj.put("topic_id", rs.getString("topic_id"));
						jsonobj.put("tieba_id", rs.getString("tieba_id"));
						jsonobj.put("user_id", rs.getString("user_id"));
						jsonobj.put("user_name", rs.getString("user_name"));
						jsonobj.put("user_head_img", rs.getString("user_head_img"));
						jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
						jsonobj.put("user_integration", rs.getString("user_integration"));
						jsonobj.put("topic_title", rs.getString("topic_title"));
						jsonobj.put("topic_content", rs.getString("topic_content"));
						jsonobj.put("topic_create_time", rs.getString("topic_create_time"));
						jsonobj.put("star_count", rs.getString("star_count"));
						for (int j = 1; j < 10; j++) {
							if (rs.getString("topic_img" + j) != null && rs.getString("topic_img" + j).length() > 0)
								jsonobj.put("topic_img" + j, rs.getString("topic_img" + j));
						}
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
						+ (search_key == null ? (jsonObject.getInteger("pageIndex") > 1 ? "没有更多数据了" : "当前贴吧没有帖子。")
								: (user_id != null ? "当前用户没有查找到帖子" : "没有搜索到帖子。"))
						+ "\",\"token\":\"" + token + "\"}");
			} else {
				out.println("{\"token\":\"" + token + "\",\"topic\":" + jsonArray + "}");
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
