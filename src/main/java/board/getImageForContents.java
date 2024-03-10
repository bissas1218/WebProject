package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Servlet implementation class getImageForContents
 */
public class getImageForContents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getImageForContents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("========================== getImageForContents ================================");
		String fileNm = request.getParameter("fileNm");
		System.out.println(fileNm);
		String fileStr = "C:\\uploadTest";
		
		File tmpDir = new File(fileStr);
	    if(!tmpDir.exists()) {
	        tmpDir.mkdirs();
	    }
	    
	    FileInputStream fis = null;
	    BufferedInputStream in = null;
	    ByteArrayOutputStream bStream = null;

	    try {

	        fis = new FileInputStream(new File(fileStr, fileNm));
	        in = new BufferedInputStream(fis);
	        bStream = new ByteArrayOutputStream();

	        int imgByte;
	        while ((imgByte = in.read()) != -1) {
	            bStream.write(imgByte);
	        }

	        String type = "";
	        String ext = fileNm.substring(fileNm.lastIndexOf(".") + 1).toLowerCase();

	        if ("jpg".equals(ext)) {
	            type = "image/jpeg";
	        } else {
	            type = "image/" + ext;
	        }

	        response.setHeader("Content-Type", type);
	        response.setContentLength(bStream.size());

	        bStream.writeTo(response.getOutputStream());

	        response.getOutputStream().flush();
	        response.getOutputStream().close();

	    } finally {
	    	bStream.close();
	    	in.close();
	    	fis.close();
	        //EgovResourceCloseHelper.close(bStream, in, fis);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
