package common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class header
 */
public class header extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public header() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("<header>\r\n"
				+ "	<div id=\"wrap\">\r\n"
				+ "		<div class=\"logo\">\r\n"
				+ "			<a href=\"/\"><img src=\"/images/mylogo.png\" width=\"148\" height=\"35\"/></a>\r\n"
				+ "		</div>\r\n"
				+ "		<div class=\"menu\">\r\n"
				+ "			공지사항 예약하기 통계보기 지도찾기\r\n"
				+ "		</div>\r\n"
				+ "		<div class=\"search\"></div>\r\n"
				+ "		<div class=\"login\"></div>\r\n"
				+ "	</div>\r\n"
				+ "</header>").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
