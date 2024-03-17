package reserv;

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
 * Servlet implementation class ReservMng
 */
@WebServlet("/ReservMng")
public class ReservMng extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservMng() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/reserv/reservMng.jsp");

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println(request.getParameter("reserv_date"));
		
		DBConnection dbconn = new DBConnection();
		Connection con = dbconn.dbConn();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into reserv (reserv_date, reserv_time, name, tel) values (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("reserv_date"));
			pstmt.setString(2, request.getParameter("reserv_time"));
			pstmt.setString(3, request.getParameter("name"));
			pstmt.setString(4, request.getParameter("tel"));
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
		response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print("success");
	}

}
