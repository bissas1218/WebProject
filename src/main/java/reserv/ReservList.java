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
import java.util.Calendar;
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
@WebServlet("/ReservList")
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
		//System.out.println("reserv list : "+request.getParameter("curDate"));
		
		String curDate = request.getParameter("curDate");
		String curYear = curDate.substring(0,4);
		String curMonth = curDate.substring(5,7);
		Calendar cal = Calendar.getInstance(); 
		cal.set(Integer.parseInt(curYear), Integer.parseInt(curMonth)-1, 1); 

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        
			int maxReservCnt = 0;
			String sql2 = "select count(*) from reserv where reserv_date = '9999-12-31'";
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				maxReservCnt = rs.getInt(1);
			}
			
			pstmt.close();
			rs.close();
			String sql3 = "select reserv_date from reserv where reserv_date between '"+curDate+"-01' and '"+curDate+"-"+lastDay+"'";
			pstmt = con.prepareStatement(sql3);
			rs = pstmt.executeQuery();
			List<String> list2 = new ArrayList<String>();
			
			while(rs.next()) {
				list2.add(rs.getString(1));
			}
			
			for(int i=1; i<=lastDay; i++) {
				
				String day = Integer.toString(i);
				if(day.length() == 1) {
					day = "0" + day;
				}

				String curDate2 = curYear + "-" + curMonth + "-" + day;
				
				int chkDate = 0;
				for(int j=0; j<list2.size(); j++) {
					if(curDate2.equals(list2.get(j))){
						chkDate++;
					}
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("reserv_date", curYear + "-" + curMonth + "-" + day);
				map.put("no", Integer.toString(chkDate));
				map.put("yes", Integer.toString(maxReservCnt-chkDate));
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
