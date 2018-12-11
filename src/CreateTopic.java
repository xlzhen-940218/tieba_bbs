
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
 * Servlet implementation class CreateTopic
 */
@WebServlet("/CreateTopic")
public class CreateTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTopic() {
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
			log(jsonObject.toJSONString());
			if (jsonObject.getString("topic_title").length() == 0 || jsonObject.getString("topic_content").length() == 0
					|| jsonObject.getString("tieba_id").length() == 0
					|| jsonObject.getString("user_id").length() == 0) {
				throw new Exception("topic_title or topic_content or tieba_id or user_id failed");
			}
			jsonObject.put("topic_title", SensitiveUtils.start(jsonObject.getString("topic_title")));
			jsonObject.put("topic_content", SensitiveUtils.start(jsonObject.getString("topic_content")));

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
			sql = "insert into tieba_db.tieba_topic_table values(null," + jsonObject.getString("tieba_id") + ","
					+ jsonObject.getString("user_id") + ",'" + jsonObject.getString("topic_title") + "','"
					+ jsonObject.getString("topic_content") + "',now()";
			for (int i = 1; i < 10; i++)
				if (jsonObject.getString("topic_img" + i) != null) {
					sql += ",'" + jsonObject.getString("topic_img" + i) + "'";
				} else {
					sql += ",null";
				}

			sql += ");";
			log(sql);
			int isSucess = stmt.executeUpdate(sql);

			String token = MD5SignUtils.MD5Encode(jsonObject.getString("user_id") + System.currentTimeMillis());
			sql = "insert into tieba_db.user_token_table values(" + jsonObject.getString("user_id") + ",'" + token
					+ "',now()) ON DUPLICATE KEY UPDATE login_token='" + token + "',login_time=now();";

			stmt.executeUpdate(sql);

			out = response.getWriter();
			out.println("{\"result\":\"" + (isSucess > 0 ? "帖子已发布，请返回并刷新页面查看！" : "failed") + "\",\"token\":\"" + token
					+ "\"}");
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
