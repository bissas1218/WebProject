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
		System.out.println("reserv list "+request.getParameter("curDate"));
		
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
					+ " , (select count(*) from reserv where reserv_date = startDate and (name = '' or name is null)) no\r\n"
					+ " , (select count(*) from reserv where reserv_date = startDate and (name != '' and name is not null)) yes\r\n"
					+ "from T order by startDate asc";

			pstmt = con.prepareStatement(sql);
		//	pstmt.setString(1, request.getParameter("curDate"));
			rs = pstmt.executeQuery();
			//System.out.println(rs.next());
			
			
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        
			while (rs.next()) {
				
				Map<String, String> map = new HashMap<String, String>();
				
				System.out.println(rs.getString(1));
				map.put("reserv_date", rs.getString(1));
				map.put("no", rs.getString(2));
				map.put("yes", rs.getString(3));
				list.add( map );
			}
			
			
	        
	        JSONObject jObj = new JSONObject();
	        jObj.put("list", list);
	        
	        response.setContentType("application/x-json; charset=utf-8");
	        response.getWriter().print(jObj);
			/*
			List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			while (rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setSeq(rs.getString(1));
				boardVO.setTitle(rs.getString(2));
				boardVO.setContent(rs.getString(3));
				boardVO.setRegDate(rs.getString(4));
				boardVO.setRegId(rs.getString(5));
				boardList.add(boardVO);
			}

			request.setAttribute("boardList", boardList);
			*/
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
