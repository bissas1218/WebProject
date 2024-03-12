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
				+ "			<a href=\"/\"><img src=\"/images/common/mylogo.png\" width=\"150\" height=\"32\"/></a>\r\n"
				+ "		</div>\r\n"
				+ "		<div class=\"menu\">\r\n"
				+ "			<ul>"
				+ "				<a href=\"/BoardList\"><li>공지사항</li></a>\r\n"
				+ "				<a href=\"/\"><li>예약하기</li></a>\r\n"
				+ "			</ul>\r\n"
				+ "		</div>\r\n"
				+ "		<div class=\"login\">"
				+ "			<ul>"
				+ "				<li class=\"search_icon\"><a href=\"javascript:openLayer('layerPop1');\"><img src=\"/images/search_icon.png\" width=\"29\" height=\"31\"/></a></li>\r\n"
				+ "				<li class=\"menu_icon\"><a href=\"javascript:openLayer('layerPop1');\"><img src=\"/images/common/menu_icon.png\" width=\"32\" height=\"28\"/></a></li>\r\n"
				+ "				<li class=\"text\"><a href=\"/BoardList\">로그인</a></li>\r\n"
				+ "				<li class=\"text\"><a href=\"/\">회원가입</a></li>\r\n"
				+ "			</ul>\r\n"	
				+ "		</div>\r\n"
				+ "	</div>\r\n"
				+ ""
				+ "<div id=\"wrapper_popup\">\r\n"
				+ "	<div id=\"layerPop1\" class=\"layer-shadow\">\r\n"
				+ "			<h2>메뉴</h2>\r\n"
				+ "			<ul>\r\n"
				+ "				<li><a href=\"/BoardList\">공지사항</a></li>\r\n"
				+ "				<li><a href=\"#\">통계보기</a></li>\r\n"
				+ "			</ul>\r\n"
				+ "		<div class=\"b-area\"><input type=\"text\" name=\"search_txt\" id=\"search_txt\" value=\"\" /></div>\r\n"
				+ "			<h2>최근검색어</h2>\r\n"
				+ "			<ul>\r\n"
				+ "				<li><a href=\"/BoardList\">표시할내용이없습니다.</a></li>\r\n"
				+ "			</ul>\r\n"
				+ "			<h2>추전검색어</h2>\r\n"
				+ "			<ul>\r\n"
				+ "				<li><a href=\"/BoardList\">공지사항</a></li>\r\n"
				+ "				<li><a href=\"#\">통계보기</a></li>\r\n"
				+ "			</ul>\r\n"
				+ "		<a href=\"#\" onclick=\"closeLayer('layerPop1')\"><img class=\"cancel\" src=\"/images/plus_icon.png\" alt=\"X\"></a>\r\n"
				+ "	</div>\r\n"
				+ "</div>"
				+ "</header>"
				+ ""
				+ "<script>\r\n"
				+ "		//팝업 닫기\r\n"
				+ "		function closeLayer(IdName){\r\n"
				+ "		//	console.log('closeLayer : '+IdName);\r\n"
				+ "			$(\"#\"+IdName).css(\"display\",\"none\");\r\n"
				+ "		}\r\n"
				+ "		//팝업열기\r\n"
				+ "		function openLayer(IdName){\r\n"
				+ "		//	console.log('openLayer : ' + IdName);\r\n"
				+ "         if($(\"#\"+IdName).css(\"display\")=='none'){\r\n"
				+ "               $(\"#\"+IdName).css(\"display\",\"block\");        \r\n"
				+ "         }else{\r\n"
				+ "               $(\"#\"+IdName).css(\"display\",\"none\");        \r\n"
				+ "         }\r\n"
				+ "			\r\n"
				+ "		}\r\n"
				+ " "
				+ "		/* 외부영역 클릭 시 팝업 닫기								\r\n"
				+ "		$(document).mouseup(function (e){					\r\n"
				+ "  		var LayerPopup = $('#wrapper_popup');				\r\n"
				+ "         var menuIcon = $('.menu_icon');                 \r\n"
				+ "         var searchTxt = $('#search_txt');                 \r\n"
				+ " 		console.log(searchTxt.has(e.focus).length); 	\r\n"
				+ " \r\n"
				+ "  		if(LayerPopup.has(e.target).length === 0 && menuIcon.has(e.target).length === 0 && searchTxt.has(e.target).length === 0){ 		\r\n"
				+ "    			$('#layerPop1').css('display','none'); 		\r\n"
				+ "  		}												\r\n"
				+ "		});  */ \r\n"
				+ "		/* 스크롤 시 팝업 무조건 닫기									\r\n"
				+ "		$(document).scroll(function (e){					\r\n"
				+ "  		var LayerPopup = $('#wrapper_popup');				\r\n"
				+ "         var menuIcon = $('.menu_icon');                 \r\n"
				+ "         var searchTxt = $('#search_txt');                 \r\n"
				+ " 		console.log(searchTxt.has(e.target).length); 	\r\n"
				+ "  		if(LayerPopup.has(e.target).length === 0 && menuIcon.has(e.target).length === 0 && searchTxt.has(e.target).length === 0){ 		\r\n"
				+ "    			$('#layerPop1').css('display','none'); 			\r\n"
				+ "         }                                                \r\n"
				+ "		}); */ \r\n"				
				+ ""
				+ " 	$('body').click(function(e){ \r\n"
				+ " 		if($('#wrapper_popup').has(e.target).length === 0 && $('.menu_icon').has(e.target).length === 0 && $('.search_icon').has(e.target).length === 0){	\r\n"
				+ " 		//	console.log($('#wrapper_popup').has(e.target).length); 	\r\n"
				+ "    			$('#layerPop1').css('display','none'); 			\r\n"
				+ "			} \r\n"
				+ " 	}) \r\n"
				+ " \r\n"
				+ "</script>"
				+ ""
				+ "").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
