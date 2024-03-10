package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@WebServlet(
        name = "ckeditor5Upload",
        urlPatterns = {"/ckeditor5Upload"}
)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)

/**
 * Servlet implementation class ckeditor5Upload
 */
public class ckeditor5Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ckeditor5Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("======================= ckeditor5Upload ===========================");
		
		Part part = request.getPart("upload");
		String fileName = getFilename(part);
		System.out.println(part);
		System.out.println(fileName);
		
		if (!fileName.isEmpty()) {
            part.write("C:\\uploadTest\\"+fileName);
        }
		
		/* 응답 작성
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        writer.print("작성자: aaa<br>");
        writer.print("파일명:<a href='FileDownloadTest?fileName=" + fileName + "'> " + fileName + "</a href><br>"); 
        writer.print("파일설명: bbb<br>"); // 다운로드 추가
        writer.print("파일크기: " + part.getSize() + " bytes" + "<br>");
        */
		
        JSONObject outData = new JSONObject();
		outData.put("uploaded", true);
		System.out.println(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/getImageForContents?fileNm=" + fileName);
		outData.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/getImageForContents?fileNm=" + fileName);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(outData.toString());

	}

	private String getFilename(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] split = contentDisp.split(";");
        for (int i = 0; i < split.length; i++) {
            String temp = split[i];
            if (temp.trim().startsWith("filename")) {
                return temp.substring(temp.indexOf("=") + 2, temp.length() - 1);
            }
        }
        return "";
    }
}
