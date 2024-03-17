package statistics;

import jakarta.servlet.RequestDispatcher;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONObject;

import common.DBConnection;
import common.Human;
import common.SaveAccessor;

/**
 * Servlet implementation class StatsView
 */
@WebServlet("/StatsView")
public class StatsView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatsView() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 방문자 저장
		new SaveAccessor(request, "StatsView");
				
		int[] arr = {1,2,3};
		
		// 방문자 조회
		request.setAttribute("array", "[1,2,3,4,5,6,7,13]");
		request.setAttribute("array2", "[1,2,3,4,5,6,7,13]");
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/stats/statsView.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int prevDay = -30;
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Calendar day = Calendar.getInstance();   
		String nowDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime());
		day.add(Calendar.DATE , prevDay); 
		JSONObject jObj = new JSONObject();
		
		try {
			
	        ArrayList<String> menuList = new ArrayList<String>();
			String sql = "select name from accessor group by name";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				menuList.add(rs.getString(1));
			}
			
			pstmt.close();
			rs.close();
			String sql2 = "select\r\n"
					+ "	name,\r\n"
					+ "	count(name) cnt,\r\n"
					+ "	date_format(reg_date, '%Y-%m-%d') reg_date\r\n"
					+ "from accessor\r\n"
					+ "where\r\n"
					+ "	date_format(reg_date, '%Y-%m-%d') >= '"+new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime())+"'\r\n"
					+ "	and date_format(reg_date, '%Y-%m-%d') <= '"+nowDay+"'\r\n"
					+ "group by\r\n"
					+ "	name,\r\n"
					+ "	date_format(reg_date, '%Y-%M-%d')\r\n"
					+ "order by\r\n"
					+ "	reg_date";
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			List<HashMap<String, String>> list2 = new ArrayList<HashMap<String, String>>();
			
			while(rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("name", rs.getString(1));
				map.put("cnt", rs.getString(2));
				map.put("reg_date", rs.getString(3));
				
				list2.add(map);
				
			}
			
			String beforeDate = "";
			String[] labelsList = new String[(prevDay*-1) + 1];// {1,2,3,4,5,6,7,8,9}; 
	        int a=0;
	        
	        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	        String[][] array = new String[menuList.size()][(prevDay*-1) + 1];
	        
			for(int i=prevDay; i<=0; i++) {
				
				beforeDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime());    
			//	System.out.println("==============="+beforeDate+"============================== "+beforeDate.substring(8,10));
				
				// 레이블 넣기
				labelsList[a] = beforeDate.substring(8,10);
				
					// 메뉴반복
					for(int k=0; k<menuList.size(); k++) {
						
						String test = "";
						String cnt = "";
						for(int j=0; j<list2.size(); j++) {
							if(list2.get(j).get("name").equals(menuList.get(k)) && list2.get(j).get("reg_date").equals(beforeDate)) {
								test = menuList.get(k)+", "+list2.get(j).get("cnt")+", "+beforeDate;
								cnt = list2.get(j).get("cnt");
							}
						}
						
						// 데이터넣기
						if(!test.equals("")) {
							array[k][a] = cnt;
						}else {
							array[k][a] = "0";
						}
					}
				
				day.add(Calendar.DATE , 1);  
				
				a++;
			} // 반복문 종료
			
			jObj.put("labels", labelsList);
			
			// 메뉴반복
			for(int k=0; k<menuList.size(); k++) {
				HashMap<String, Object> map4 = new HashMap<String, Object>();
				map4.put("label", menuList.get(k));
				map4.put("data", array[k]);
				int ran1 = (int)(Math.random()*256);
				int ran2 = (int)(Math.random()*256);
				int ran3 = (int)(Math.random()*256);
				map4.put("borderColor", "rgb("+ran1+", "+ran2+", "+ran3+")");
		        map4.put("backgroundColor", "rgba("+ran1+", "+ran2+", "+ran3+", 0.5)");
		        
		        list.add(map4);
			}
	        
	        jObj.put("list", list);
	        
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
		
		
		
	
		
		
        
        
        
        response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print(jObj);
	}

	public void catherine(){    
		
		//오늘    
		Date today = new Date();    
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");    
		String toDay = date.format(today);    
		System.out.println(toDay);    
		
		// 1시간전    
		Calendar cal = Calendar.getInstance();    
		cal.setTime(today);    
		cal.add(Calendar.HOUR, -1);    
		
		// 포맷변경 ( 년월일 시분초)    
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");    
		sdformat.setTimeZone(TimeZone.getTimeZone("UTC"));    
		String beforeHour = sdformat.format(cal.getTime());    
		System.out.println("1시간 전 : " + beforeHour);    
		
		//하루 전    
		Calendar day = Calendar.getInstance();    
		day.add(Calendar.DATE , -1);    
		String beforeDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime());    
		System.out.println(beforeDate);    
		
		//한주 전    
		Calendar week = Calendar.getInstance();    
		week.add(Calendar.DATE , -7);    
		String beforeWeek = new java.text.SimpleDateFormat("yyyy-MM-dd").format(week.getTime());    
		System.out.println(beforeWeek);    
		
		//한달 전    
		Calendar mon = Calendar.getInstance();   
		mon.add(Calendar.MONTH , -1);    
		String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime());    
		System.out.println(beforeMonth);
	
	}

}
