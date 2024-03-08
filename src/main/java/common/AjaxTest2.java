package common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * Servlet implementation class AjaxTest2
 */
public class AjaxTest2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTest2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list
        
        List<Human> list = new ArrayList<Human>();
        list.add(new Human("홍길동", 24));
        list.add(new Human("성춘향", 16));
        list.add(new Human("홍두께", 22));
        
        JSONObject jObj = new JSONObject();
        jObj.put("list", list);
        
        response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print(jObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Map
        
        String str = "안녕하세요";
        
        List<Human> list = new ArrayList<Human>();
        list.add(new Human("홍길동", 24));
        list.add(new Human("성춘향", 16));
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", str);
        map.put("mylist", list);
        
        JSONObject jObj = new JSONObject();
        jObj.put("map", map);
        
        response.setContentType("application/x-json; charset=utf-8");
        response.getWriter().print(jObj);
	}

}
