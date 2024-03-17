package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.DBConnection;

/**
 * Servlet implementation class BoardInsert
 */
@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/board/boardInsert.jsp");
		request.setAttribute("aaa", "bbb");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//System.out.println(request.getParameter("content"));
		
		DBConnection dbconn = new DBConnection();
		Connection con = dbconn.dbConn();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into board (seq, title, content, reg_date, reg_id) values ((select ifnull(max(A.seq), 0)+1 from board A), ?, ?, now(), 'admin')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("title"));
			pstmt.setString(2, request.getParameter("content"));
			pstmt.executeQuery();
			
		}catch(SQLException e) {
			System.out.println("error: " + e);
		}finally {
			try {

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
		response.sendRedirect("/BoardList");
	}

}
