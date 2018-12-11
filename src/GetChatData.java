
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class GetChatData
 */
@WebServlet("/GetChatData")
public class GetChatData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetChatData() {
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
			if (jsonObject.getString("user_id").length() == 0 || jsonObject.getString("to_user_id").length() == 0) {
				throw new Exception("user_id or to_user_id failed");
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Config.server_sql_path, Config.server_sql_user,
					Config.server_sql_pwd);

			Statement stmt = connection.createStatement();

			if (jsonObject.getString("chat_id") == null) {
				findOrInsertChatId(jsonObject, stmt, out);
			}

			String sql = "SELECT chat.defriend,chat.chat_id,message.* FROM tieba_db.chat_friend_table as chat "
					+ "left join tieba_db.chat_message_table as message on message.chat_id=chat.chat_id "
					+ (jsonObject.getDate("last_create_time") != null
							? "and message.create_time>\"" + jsonObject.getString("last_create_time") + "\""
							: "")
					+ " where "
					+ (jsonObject.getString("chat_id") != null ? "chat.chat_id=" + jsonObject.getString("chat_id")
							: " user_id=" + jsonObject.getString("user_id") + " and friend_id="
									+ jsonObject.getString("to_user_id"))

					+ " order by create_time asc;";// 因为是一条一条从上往下加的，所以是按时间最早排序
			log(sql);
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					jsonobj.put("defriend", rs.getString("defriend"));// 是否已拉黑
					jsonobj.put("from_user_id", rs.getString("from_user_id"));
					jsonobj.put("to_user_id", rs.getString("to_user_id"));
					jsonobj.put("message_content", rs.getString("message_content"));
					jsonobj.put("message_type", rs.getString("message_type"));
					jsonobj.put("create_time", rs.getString("create_time"));
					jsonobj.put("chat_id", rs.getString("chat_id"));// 必须要这个，否则聊天的信息无法插入指定的chatId。
				}
				jsonArray.add(jsonobj);
			}
			out = response.getWriter();
			out.println(jsonArray.size() == 0 ? "{\"result\":\"10秒内对方没有回复消息\"}" : "{\"chat\":" + jsonArray + "}");
			stmt.close();
			connection.close();
		} catch (Exception e) {
			out.print("{\"result\":\"" + e.getMessage() + "\"}");
			e.printStackTrace();
		}
	}

	private void findOrInsertChatId(JSONObject jsonObject, Statement stmt, PrintWriter out) {

		try {
			// 如果反向查聊天关系可以查到ID，则不新增chatID
			String sql = "SELECT * FROM tieba_db.chat_friend_table where user_id=" + jsonObject.getString("to_user_id")
					+ " and friend_id=" + jsonObject.getString("user_id") + ";";

			boolean size = false;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					size = rs.getString("chat_id") != null;
				}
			}
			if (size) {
				// 将查询关系对调，不然关系肯定会错乱
				String tempId = jsonObject.getString("user_id");
				jsonObject.put("user_id", jsonObject.getString("to_user_id"));
				jsonObject.put("to_user_id", tempId);

				if (jsonObject.getString("defriend") != null) {
					sql = "update tieba_db.tieba_friend_table set defriend=" + jsonObject.getString("defriend")
							+ " where user_id=" + jsonObject.getString("user_id") + " and friend_id="
							+ jsonObject.getString("to_user_id");

					stmt.executeUpdate(sql);
				}
			} else {
				// 如果反向查聊天关系无记录，则可以新增聊天关系的chatId，存在则判断是否需要更新拉黑关系
				sql = "insert into tieba_db.chat_friend_table values(null," + jsonObject.getString("user_id") + ","
						+ jsonObject.getString("to_user_id") + ",false,now()) ON DUPLICATE KEY UPDATE create_time=now()"
						+ (jsonObject.getString("defriend") == null ? ""
								: ",defriend=" + jsonObject.getString("defriend"));
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
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
