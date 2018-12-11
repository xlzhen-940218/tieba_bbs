
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
 * Servlet implementation class GetCommentList
 */
@WebServlet("/GetCommentList")
public class GetCommentList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCommentList() {
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

			String search_key = jsonObject.getString("search_key");
			String user_id = jsonObject.getString("user_id");
			String topic_id = jsonObject.getString("topic_id");

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

			sql = "SELECT comm.*,users.user_id,users.user_name,users.user_head_img"
					+ ",users.user_pet_name,users.user_integration,"
					+ "(select count(star.comment_id) from tieba_db.topic_comment_star_table "
					+ "as star where star.comment_id=comm.comment_id) as star_count "
					+ "FROM tieba_db.tieba_topic_comment_table as comm left join tieba_db.tieba_user_table "
					+ "as users on users.user_id=comm.user_id ";

			if (search_key != null || user_id != null || topic_id != null) {
				sql += " where ";
			}
			boolean insertAnd = false;
			if (search_key != null) {
				sql += "comm.comment_content like '%" + search_key + "%'";
				insertAnd = true;
			}

			if (user_id != null && topic_id == null) {
				sql += (insertAnd ? " and " : "") + "comm.user_id=" + user_id;
				insertAnd = true;
			}

			if (topic_id != null) {
				sql += (insertAnd ? " and " : "") + "comm.topic_id=" + topic_id;
			}

			sql += " order by comment_create_time asc limit "
					+ (jsonObject.getIntValue("pageIndex") - 1) * jsonObject.getIntValue("pageCount") + ","
					+ jsonObject.getIntValue("pageCount") + ";";

			rs = stmt.executeQuery(sql);

			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					jsonobj.put("comment_id", rs.getString("comment_id"));
					jsonobj.put("topic_id", rs.getString("topic_id"));
					jsonobj.put("user_id", rs.getString("user_id"));
					jsonobj.put("user_name", rs.getString("user_name"));
					jsonobj.put("user_head_img", rs.getString("user_head_img"));
					jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
					jsonobj.put("user_integration", rs.getString("user_integration"));
					jsonobj.put("comment_content", rs.getString("comment_content"));
					jsonobj.put("comment_create_time", rs.getString("comment_create_time"));
					jsonobj.put("star_count", rs.getString("star_count"));
					for (int j = 1; j < 10; j++) {
						if (rs.getString("comment_img" + j) != null && rs.getString("comment_img" + j).length() > 0)
							jsonobj.put("comment_img" + j, rs.getString("comment_img" + j));
					}
				}
				jsonArray.add(jsonobj);
			}

			if (jsonArray.size() == 0 && jsonObject.getIntValue("pageIndex") == 1) {
				sql = sql.replaceAll("," + jsonObject.getIntValue("pageCount") + ";", "");
				rs = stmt.executeQuery(sql);

				while (rs.next()) {

					JSONObject jsonobj = new JSONObject();
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						jsonobj.put("comment_id", rs.getString("comment_id"));
						jsonobj.put("topic_id", rs.getString("topic_id"));
						jsonobj.put("user_id", rs.getString("user_id"));
						jsonobj.put("user_name", rs.getString("user_name"));
						jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
						jsonobj.put("user_integration", rs.getString("user_integration"));
						jsonobj.put("user_head_img", rs.getString("user_head_img"));
						jsonobj.put("comment_content", rs.getString("comment_content"));
						jsonobj.put("comment_create_time", rs.getString("comment_create_time"));
						jsonobj.put("star_count", rs.getString("star_count"));
						for (int j = 1; j < 10; j++) {
							if (rs.getString("comment_img" + j) != null && rs.getString("comment_img" + j).length() > 0)
								jsonobj.put("comment_img" + j, rs.getString("comment_img" + j));
						}
					}
					jsonArray.add(jsonobj);
				}
			}
			if (jsonArray.size() > 0) {
				sql = "SELECT sub.*,users.user_id,users.user_name,users.user_pet_name,users.user_integration,users.user_head_img"
						+ ",call_users.user_id as call_user_id,call_users.user_name as call_user_name"
						+ ",call_users.user_pet_name as call_user_pet_name,call_users.user_integration as call_user_integration"
						+ ",call_users.user_head_img as call_user_head_img "
						+ "FROM tieba_db.topic_sub_comment_table as sub "
						+ "left join tieba_db.tieba_user_table as users on users.user_id=sub.user_id "
						+ "left join tieba_db.tieba_user_table as call_users on call_users.user_id=sub.call_user_id "
						+ "where sub.comment_id in (";

				for (int i = 0; i < jsonArray.size(); i++) {
					if (jsonArray.size() - 1 == i)
						sql += jsonArray.getJSONObject(i).getString("comment_id") + ")";
					else
						sql += jsonArray.getJSONObject(i).getString("comment_id") + ",";
				}

				log(sql);

				rs = stmt.executeQuery(sql);
				while (rs.next()) {

					JSONObject jsonobj = new JSONObject();
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						jsonobj.put("sub_comment_id", rs.getString("sub_comment_id"));
						jsonobj.put("comment_id", rs.getString("comment_id"));
						jsonobj.put("user_id", rs.getString("user_id"));
						jsonobj.put("user_name", rs.getString("user_name"));
						jsonobj.put("user_pet_name", rs.getString("user_pet_name"));
						jsonobj.put("user_integration", rs.getString("user_integration"));
						jsonobj.put("user_head_img", rs.getString("user_head_img"));
						jsonobj.put("call_user_id", rs.getString("call_user_id"));
						jsonobj.put("call_user_name", rs.getString("call_user_name"));
						jsonobj.put("call_user_pet_name", rs.getString("call_user_pet_name"));
						jsonobj.put("call_user_integration", rs.getString("call_user_integration"));
						jsonobj.put("call_user_head_img", rs.getString("call_user_head_img"));
						jsonobj.put("comment_content", rs.getString("comment_content"));
						jsonobj.put("comment_create_time", rs.getString("comment_create_time"));
					}
					for (int j = 0; j < jsonArray.size(); j++) {
						if (jsonArray.getJSONObject(j).getString("comment_id")
								.equals(jsonobj.getString("comment_id"))) {
							if (jsonArray.getJSONObject(j).getJSONArray("sub_comment") == null) {
								jsonArray.getJSONObject(j).put("sub_comment", new JSONArray());
							}
							jsonArray.getJSONObject(j).getJSONArray("sub_comment").add(jsonobj);

						}

					}

				}

			}

			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);

			if (jsonArray.size() == 0) {
				out.println("{\"result\":\"" + (jsonObject.getInteger("pageIndex") > 1 ? "没有更多数据了" : "当前还没有评论。")
						+ "\",\"token\":\"" + token + "\"}");
			} else {
				out.println("{\"token\":\"" + token + "\",\"comment\":" + jsonArray + "}");
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (

		Exception e) {
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
