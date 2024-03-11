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
import java.util.List;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
		
		String os = System.getProperty("os.name").toLowerCase();
		//String path = System.getProperty("user.dir");
        //System.out.println("os: " + os); 
        String uploadPath = "";
        
        if (os.contains("win")) {
            System.out.println("Windows");
            uploadPath = "C:\\editor_img\\";
        } else if (os.contains("mac")) {
            System.out.println("Mac");

        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            System.out.println("Unix");
            uploadPath = "/bissas2/editor_img/";
        } else if (os.contains("linux")) {
            System.out.println("Linux");
            uploadPath = "/bissas2/editor_img/";
        } else if (os.contains("sunos")) {
            System.out.println("Solaris");
        }
        
		String fileNm = request.getParameter("fileNm");
		String fileStr = uploadPath; 
		
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
	    
	    /* 이미지 리사이즈 graphics start 
		try {
			
			// 파일에서 이미지 불러오기
			Image oldImg = ImageIO.read(new File(fileStr, fileNm));
			
			// 이미지 사이즈 체크
			int oldWidth = oldImg.getWidth(null);
			int oldHeight = oldImg.getHeight(null);
			System.out.println("image width:"+oldWidth);
			System.out.println("image height:"+oldHeight);
			
			// 이미지 너비가 1300을 넘을경우 리사이징
			if(oldWidth > 1300) {
				
				//확장자
				String format = fileNm.substring(fileNm.lastIndexOf(".") + 1);
				System.out.println("format:"+format);
				
				int new_width = 0;
				int new_height = 0;
				
				// 너비기준
				double ratio = (double)1300/(double)oldWidth;
				new_width = (int)(oldWidth * ratio);
				new_height = (int)(oldHeight * ratio);
	
				// 높이기준
		//		double ratio = (double)1300/(double)oldHeight;
		//		new_width = (int)(oldWidth * ratio);
		//		new_height = (int)(oldHeight * ratio);
				
				System.out.println(new_width+", "+new_height);
				// 이미지 사이즈 수정
				// 이미지 리사이즈
				// Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
				// Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
				// Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
				// Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
				// Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
				Image resizeImage = oldImg.getScaledInstance(new_width, new_height, Image.SCALE_SMOOTH);
				
				// 새 이미지  저장하기
				// 새 이미지를 저장하기 위해 변경할 width와 height을 가진 이미지를 생성한다.
				BufferedImage newImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
				Graphics g = newImage.getGraphics();
				//새 이미지에 원본이미지를 그려준다.
				g.drawImage(resizeImage, 0, 0, null);
				//연결 끊기
				g.dispose();
				//그려진 이미지를 파일로 만들기
				ImageIO.write(newImage, format, new File(uploadPath + fileNm));
			}
		
			ro2(90, uploadPath, fileNm);
			
		}catch (Exception e){
			e.printStackTrace();
		}*/
		/* 이미지 리사이즈 graphics end */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private static void ro2(int rotate, String fileStr, String fileNm) throws Exception {
		
	//	String fileStr = "C:\\editor_img\\";
	//	String fileNm = "118840_KakaoTalk_20240311_131842112.jpg";
		
//	    byte[] imgbuf2 = getDecript("원본 이미지 경로", "");
//	    ByteArrayInputStream bais2 = new ByteArrayInputStream(imgbuf2);
//	    BufferedImage oldImage = ImageIO.read(bais2);
	 
	    File orgFile = new File(fileStr + fileNm);
	    BufferedImage oldImage = ImageIO.read(orgFile);
	 
	    BufferedImage newImage = null;
	 
	    if(180 == rotate) {
	        newImage = new BufferedImage(oldImage.getWidth(),oldImage.getHeight(), oldImage.getType());
	    }
	    else {
	        newImage = new BufferedImage(oldImage.getHeight(),oldImage.getWidth(), oldImage.getType());
	    }
	 
	    Graphics2D graphics = (Graphics2D) newImage.getGraphics();
	 
	    graphics.rotate(Math.toRadians(rotate), newImage.getWidth() / 2, newImage.getHeight() / 2);
	 
	    if(180 != rotate) {
	        graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);        // 90, 270도일때만 사용
	    }
	 
	    graphics.drawImage(oldImage, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);
	 
	    ImageIO.write(newImage, "JPG", new File(fileStr + fileNm));
	}
}
