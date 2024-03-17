package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;
import common.PageNumberVO;
import common.PageNumbering;
import common.SaveAccessor;

/**
 * Servlet implementation class BoardList
 */
@WebServlet("/BoardList")
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
		
		// 방문자 저장
		new SaveAccessor(request, "BoardList");
				
		DBConnection dbconn = new DBConnection();
		Connection con = dbconn.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 페이징처리
		PageNumberVO pageNumVO = new PageNumberVO(10, 10, request.getParameter("pageNum"));

		try {
			
			String sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			
			pageNumVO.setTotalCnt(rs.getInt(1));
			
			pstmt.close();
			rs.close();
			
			/* 페이징처리 start */
			PageNumbering pageNumbering = new PageNumbering();
			pageNumVO = pageNumbering.pagingProcess(pageNumVO);
			
			request.setAttribute("currentPageBlock", pageNumVO.getCurrentPageBlock());
			request.setAttribute("totalPageBlock", pageNumVO.getTotalPageBlock());
			request.setAttribute("totalPageCnt", pageNumVO.getTotalPageCnt()); 
			request.setAttribute("startPageNum", pageNumVO.getStartPageNum()); 
			request.setAttribute("endPageNum", pageNumVO.getEndPageNum()); 
			request.setAttribute("currentPageNum", pageNumVO.getCurrentPageNum());
			/* 페이징처리 end */
			
			
			sql = "select seq, title, content, date_format(reg_date, '%Y-%m-%d'), reg_id from board order by reg_date desc limit ?, ?";

			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, pageNumVO.getPageNum());
			pstmt.setInt(2, pageNumVO.getPageListSize());
			
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
