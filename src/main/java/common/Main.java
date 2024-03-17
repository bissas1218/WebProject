package common;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.BoardVO;

/**
 * Servlet implementation class Main
 */
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 방문자 저장
		new SaveAccessor(request, "Main");
	    
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.dbConn();
	//	System.out.println(con);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select seq, title, content, date_format(reg_date, '%Y-%m-%d'), reg_id from board order by reg_date desc limit 0, 9";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
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
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
