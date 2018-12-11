
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

/**
 * Servlet implementation class StarComment
 */
@WebServlet("/StarComment")
public class StarComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StarComment() {
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
			if (jsonObject.getString("comment_id").length() == 0 || jsonObject.getString("user_id").length() == 0) {
				throw new Exception("comment_id or user_id failed");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();
			String sql;
			sql = "insert into tieba_db.topic_comment_star_table values(" + jsonObject.getString("comment_id") + ","
					+ jsonObject.getString("user_id") + ",false,now()) ON DUPLICATE KEY UPDATE "
					+ (jsonObject.getString("star_type") == null ? ""
							: "star_type=" + jsonObject.getString("star_type") + ",")
					+ "last_star_time=now();";

			int isSuccess = stmt.executeUpdate(sql);

			sql = "select * from tieba_db.topic_comment_star_table where comment_id="
					+ jsonObject.getString("comment_id") + " and user_id=" + jsonObject.getString("user_id") + ";";

			ResultSet rs = stmt.executeQuery(sql);
			JSONObject jsonobj = new JSONObject();
			while (rs.next()) {
				jsonobj.put("star_type", rs.getString("star_type"));
				jsonobj.put("last_star_time", rs.getString("last_star_time"));
			}
			out = response.getWriter();
			out.println(jsonobj);
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
