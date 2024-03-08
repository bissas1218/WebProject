package common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

/**
 * Servlet implementation class AjaxTest
 */
@WebServlet("/hello")
public class AjaxTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/x-json; charset=UTF-8");
       // PrintWriter out = response.getWriter();
        String name = request.getParameter("no");
        String age = request.getParameter("age");    
        
        System.out.println("doget :"+name);
        System.out.println("deget :"+age);
            
        String str = "안녕하세요";
        
        JSONObject jObj = new JSONObject();
        jObj.put("str", str); // key, value
        
        response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print(jObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Human human = new Human("홀길동", 24);
        JSONObject jObj = new JSONObject();
        jObj.put("human", human);
        System.out.println(human.getName());
        response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print(jObj);
	}

}
