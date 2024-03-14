package reserv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import board.BoardVO;
import common.DBConnection;
import common.Human;

/**
 * Servlet implementation class ReservList
 */
public class ReservList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("reserv list "+request.getParameter("curDate"));
		
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "with recursive T as (\r\n"
					+ "    select last_day(str_to_date('"+request.getParameter("curDate")+"-01', '%Y-%m-%d') - interval 1 month) + interval 1 day as startDate\r\n"
					+ "    union all\r\n"
					+ "    select startDate + interval 1 day from T where startDate < last_day(str_to_date('"+request.getParameter("curDate")+"-01', '%Y-%m-%d'))\r\n"
					+ ")\r\n"
					+ "select startDate reserv_date\r\n"
				//	+ " , (select count(*) from reserv where reserv_date = startDate and (name = '' or name is null)) no\r\n"
					+ " , (select count(*) from reserv where reserv_date = startDate and (name != '' and name is not null)) yes\r\n"
					+ " , (select count(*) from reserv where reserv_date = '9999-12-31') - (select count(*) from reserv where reserv_date = startDate and (name != '' and name is not null)) no\r\n"
					+ "from T order by startDate asc";
			System.out.println(sql);

			pstmt = con.prepareStatement(sql);
		//	pstmt.setString(1, request.getParameter("curDate"));
			rs = pstmt.executeQuery();
			//System.out.println(rs.next());
			
			
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        
			while (rs.next()) {
				
				Map<String, String> map = new HashMap<String, String>();
				
			//	System.out.println(rs.getString(1));
				map.put("reserv_date", rs.getString(1));
				map.put("no", rs.getString(2));
				map.put("yes", rs.getString(3));
				list.add( map );
			}
			
			
	        
	        JSONObject jObj = new JSONObject();
	        jObj.put("list", list);
	        
	        response.setContentType("application/x-json; charset=utf-8");
	        response.getWriter().print(jObj);
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("selDate"));
		
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// 이전날짜일 경우 - 예약된 건수만 조회
			
			// 오늘이후일 경우 - 예약된 건수와 예약가능건수 조인해서 조회
			
			String sql = "select ? reserv_date, a.reserv_time, ifnull(b.name,'') name, ifnull(b.tel,'') tel from (select reserv_time from reserv where reserv_date = '9999-12-31') a left join\r\n"
					+ "(select reserv_time, name, tel from reserv where reserv_date = ?) b\r\n"
					+ "on a.reserv_time = b.reserv_time order by a.reserv_time asc";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("selDate"));
			pstmt.setString(2, request.getParameter("selDate"));
			rs = pstmt.executeQuery();
			
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        
			while (rs.next()) {
				
				Map<String, String> map = new HashMap<String, String>();
				
			//	System.out.println(rs.getString(1));
				map.put("reserv_date", rs.getString(1));
				map.put("reserv_time", rs.getString(2));
				map.put("name", rs.getString(3));
				map.put("tel", rs.getString(4));
				list.add( map );
			}
			
	        JSONObject jObj = new JSONObject();
	        jObj.put("list", list);
	        
	        response.setContentType("application/x-json; charset=utf-8");
	        response.getWriter().print(jObj);
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
