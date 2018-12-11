
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class CreateSubComment
 */
@WebServlet("/CreateSubComment")
public class CreateSubComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateSubComment() {
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
			if (jsonObject.getString("comment_content").length() == 0
					|| jsonObject.getString("comment_id").length() == 0
					|| jsonObject.getString("user_id").length() == 0) {
				throw new Exception("comment_content or comment_id or tieba_id or user_id failed");
			}

			jsonObject.put("comment_content",
					SensitiveUtils.start(URLDecoder.decode(jsonObject.getString("comment_content"), "UTF-8")));

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();
			String sql;
			sql = "insert into tieba_db.topic_sub_comment_table values(null," + jsonObject.getString("comment_id") + ","
					+ jsonObject.getString("user_id") + ",'" + jsonObject.getString("comment_content") + "',now()"
					+ (jsonObject.getString("call_user_id") == null ? ",null"
							: "," + jsonObject.getString("call_user_id"))
					+ ");";
			log(sql);
			int isSucess = stmt.executeUpdate(sql);

			out = response.getWriter();
			out.println("{\"result\":\"" + (isSucess > 0 ? "回复成功" : "failed") + "\"}");
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
