
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

/**
 * Servlet implementation class GetTieba
 */
@WebServlet("/GetTieba")
public class GetTieba extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTieba() {
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

			if (jsonObject.getString("pageIndex").length() < 1) {
				throw new Exception("pageIndex  not found!");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();
			String search_key = jsonObject.getString("search_key");

			String sql = "SELECT tieba.*,(select count(topic.tieba_id) from tieba_db.tieba_topic_table "
					+ "as topic where topic.tieba_id=tieba.tieba_id) as topic_count,"
					+ "(select count(attention.tieba_id) from tieba_db.tieba_attention_table "
					+ "as attention where attention.tieba_id=tieba.tieba_id) as attention_count "
					+ "FROM  tieba_db.tieba_table as tieba "
					+ (search_key == null ? "" : " where tieba.tieba_name like '%" + search_key + "%' ")
					+ " order by topic_count desc, attention_count desc limit "
					+ (jsonObject.getIntValue("pageIndex") - 1) * jsonObject.getIntValue("pageCount") + ","
					+ jsonObject.getIntValue("pageCount") + ";";
			log(sql);
			ResultSet rs = stmt.executeQuery(sql);

			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					jsonobj.put("tieba_id", rs.getString("tieba_id"));
					jsonobj.put("tieba_name", rs.getString("tieba_name"));
					jsonobj.put("tieba_description", rs.getString("tieba_description"));
					jsonobj.put("tieba_cover", rs.getString("tieba_cover"));
					jsonobj.put("attention_count", rs.getString("attention_count"));
					jsonobj.put("topic_count", rs.getString("topic_count"));
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
						jsonobj.put("tieba_id", rs.getString("tieba_id"));
						jsonobj.put("tieba_name", rs.getString("tieba_name"));
						jsonobj.put("tieba_description", rs.getString("tieba_description"));
						jsonobj.put("tieba_cover", rs.getString("tieba_cover"));
						jsonobj.put("attention_count", rs.getString("attention_count"));
						jsonobj.put("topic_count", rs.getString("topic_count"));
					}
					jsonArray.add(jsonobj);
				}
			}

			out = response.getWriter();

			if (jsonArray.size() == 0) {
				out.println("{\"result\":\""
						+ (search_key == null ? (jsonObject.getInteger("pageIndex") > 1 ? "没有更多贴吧了" : "网络错误")
								: "没有搜索到帖吧。")
						+ "\"}");
			} else {
				out.println("{\"tieba\":" + jsonArray + "}");
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
