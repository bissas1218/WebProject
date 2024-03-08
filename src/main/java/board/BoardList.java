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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;

/**
 * Servlet implementation class BoardList
 */
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBConnection dbconn = new DBConnection();
		Connection con = dbconn.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pageListSize = 10;
		
		int pageNum = 0;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		System.out.println("pageNum:"+pageNum);
		
		try {
			String sql = "select * from board order by reg_date desc limit ?, ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pageNum);
			pstmt.setInt(2, pageListSize);
			
			rs = pstmt.executeQuery();
			
			List<BoardVO> list = new ArrayList<BoardVO>();
			
			while (rs.next()) {
				//System.out.println(rs.getString(1)+", "+rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4));
				BoardVO boardVO = new BoardVO();
				boardVO.setSeq(rs.getString(1));
				boardVO.setTitle(rs.getString(2));
				boardVO.setContent(rs.getString(3));
				boardVO.setRegDate(rs.getString(4));
				boardVO.setRegId(rs.getString(5));
				list.add(boardVO);
			}

			request.setAttribute("boardList", list);
			
			pstmt.close();
			rs.close();
			
			sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			
			int totalCnt = rs.getInt(1);
			int pageCnt = totalCnt/pageListSize;
			if(totalCnt%pageListSize > 0) {
				pageCnt++;
			}
			
			request.setAttribute("pageListSize", pageListSize);
			request.setAttribute("pageCnt", pageCnt);
			
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
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/board/boardList.jsp");
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
