package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.json.JSONObject;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

@WebServlet(
        name = "ckeditor5Upload",
        urlPatterns = {"/ckeditor5Upload"}
)

/* maxFileSize 10MB, maxRequestSize 50MB */
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 10,
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
		String os = System.getProperty("os.name").toLowerCase();
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
	
        File tmpDir = new File(uploadPath);
	    if(!tmpDir.exists()) {
	        tmpDir.mkdirs();
	    }
	    
		Part part = request.getPart("upload");
		String fileName = (int)(Math.random() * 1000000) + "_" + getFilename(part);
		
		if (!fileName.isEmpty()) {
            part.write(uploadPath + fileName);
        }
		
		JSONObject outData = new JSONObject();
		outData.put("uploaded", true);
		System.out.println(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/getImageForContents?fileNm=" + fileName);
		outData.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/getImageForContents?fileNm=" + fileName);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(outData.toString());
		
		/* 이미지 리사이징 */
	
		//확장자
		//imageResize(uploadPath, fileName);
		//copyResizeImageRatioCriteria(300, "width", uploadPath, fileName, uploadPath, "new_"+fileName, "normal");
		/*
		try {
			makeThumbnail(uploadPath, fileName, "jpg");
		}catch(Exception e) {
			System.out.println(e);
		}
		*/
		
		int orientation = getOrientation(uploadPath, fileName);
		System.out.println("orientation:"+orientation);
		
		imageResize(uploadPath, fileName);
		
		if(orientation==6) {
			try {
				ro2(90, uploadPath, fileName);
			}catch(Exception e) {
				System.out.println(e);
			}
		}
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
	
	private int getOrientation(String filePath, String fileName) {
		
		int orientation = 0;
		
		try {
			
			
			// 1. 원본 파일을 읽는다.
			File imageFile = new File(filePath+fileName);
			Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
			
			Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			System.out.println("directory:"+directory);
			if(directory != null){
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION); // 회전정보
			}
			
			
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (MetadataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return orientation;
		
	}
	
	private void makeThumbnail(String filePath, String fileName, String fileExt) throws Exception {
		// 1. 원본 파일을 읽는다.
		// 2. 원본 파일의 Orientation 정보를 읽는다.
		// 3. 변경할 값들을 설정한다.
		// 4. 회전하여 생성할 파일을 만든다.
		// 5. 원본파일을 회전하여 파일을 저장한다.

		// 1. 원본 파일을 읽는다.
		File imageFile = new File(filePath+fileName);

		// 2. 원본 파일의 Orientation 정보를 읽는다.
		int orientation = 1; // 회전정보, 1. 0도, 3. 180도, 6. 270도, 8. 90도 회전한 정보
		int width = 0; // 이미지의 가로폭
		int height = 0; // 이미지의 세로높이
		int tempWidth = 0; // 이미지 가로, 세로 교차를 위한 임의 변수

		Metadata metadata; // 이미지 메타 데이터 객체
		Directory directory; // 이미지의 Exif 데이터를 읽기 위한 객체
		JpegDirectory jpegDirectory; // JPG 이미지 정보를 읽기 위한 객체

		try {
			metadata = ImageMetadataReader.readMetadata(imageFile);
			directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			System.out.println("directory:"+directory);
			if(directory != null){
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION); // 회전정보
				width = jpegDirectory.getImageWidth(); // 가로
				height = jpegDirectory.getImageHeight(); // 세로
			}
			
			// 3. 변경할 값들을 설정한다.
		    AffineTransform atf = new AffineTransform();
		    System.out.println("orientation:"+orientation);
		    switch (orientation) {
		    case 1:
		        break;
		    case 2: // Flip X
		    	atf.scale(-1.0, 1.0);
		    	atf.translate(-width, 0);
		        break;
		    case 3: // PI rotation 
		    	atf.translate(width, height);
		    	atf.rotate(Math.PI);
		        break;
		    case 4: // Flip Y
		    	atf.scale(1.0, -1.0);
		    	atf.translate(0, -height);
		        break;
		    case 5: // - PI/2 and Flip X
		    	atf.rotate(-Math.PI / 2);
		    	atf.scale(-1.0, 1.0);
		        break;
		    case 6: // -PI/2 and -width
		    	atf.translate(height, 0);
		    	atf.rotate(Math.PI / 2);
		        break;
		    case 7: // PI/2 and Flip
		    	atf.scale(-1.0, 1.0);
		    	atf.translate(-height, 0);
		    	atf.translate(0, width);
		    	atf.rotate(  3 * Math.PI / 2);
		        break;
		    case 8: // PI / 2
		    	atf.translate(0, width);
		    	atf.rotate(  3 * Math.PI / 2);
		        break;
		    }
		    
		    switch (orientation) {
			case 5:
			case 6:
			case 7:
			case 8:
		        tempWidth = width;
		        width = height;
		        height = tempWidth;
				break;
			}
		    
			BufferedImage image = ImageIO.read(imageFile);
			final BufferedImage afterImage = new BufferedImage(width, height, image.getType());
			final AffineTransformOp rotateOp = new AffineTransformOp(atf, AffineTransformOp.TYPE_BILINEAR);
			final BufferedImage rotatedImage = rotateOp.filter(image, afterImage);
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = iter.next();
		    ImageWriteParam iwp = writer.getDefaultWriteParam();
		    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		    iwp.setCompressionQuality(1.0f);

		    // 4. 회전하여 생성할 파일을 만든다.
		    File outFile = new File(filePath+fileName);
		    FileImageOutputStream fios = new FileImageOutputStream(outFile);
		    
		    // 5. 원본파일을 회전하여 파일을 저장한다.
		    writer.setOutput(fios);
		    writer.write(null, new IIOImage(rotatedImage ,null,null),iwp);
		    fios.close();
		    writer.dispose();
			
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (MetadataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		
	}
	
	/**
	 * 원본 이미지를 활용하여 리사이징된 이미지를 생성한다.
	 *
	 * @author mitw.tistory.com
	 * @see <pre>
	 *  Modification Information
	 *
	 *     수정일      / 수정자    / 수정내용
	 *     ------------------------------------------
	 *     2021-09-12 / mitw   / 최초 생성
	 * </pre>
	 *
	 * @since 2021-09-12
	 * @param maxPixel String : 최대사이즈
	 * @param criteria String : 리사이징 시 최대사이즈의 기준 (가로:width,세로:그 외)
	 * @param orgFullPath String : 원본 이미지 파일 경로
	 * @param orgFileName String : 원본 이미지 파일명
	 * @param resizeFullPath String : 리사이징된 이미지 파일 저장 경로
	 * @param resizeFileName String : 리사이징된 이미지 파일명
	 * @param resizeType String : 리사이징 타입 (crop, normal)
	 * @exception IOException : 원본 이미지에 대한 문제가 발생한 경우
	 * */
	public static void copyResizeImageRatioCriteria(int maxPixel, String criteria, String orgFullPath, String orgFileName, String resizeFullPath, String resizeFileName, String resizeType) throws IOException {
	    //이미지 캐시를 사용하지 않는다.
	    //사용을 원하면 tmp경로를 완벽하게 구성하거나, VM option에 -Djava.io.tmpdir=/path/to/tmpdir를 추가한다.
	    ImageIO.setUseCache(false);
	    //원본 파일을 불러온다.
	    BufferedImage originalImg = ImageIO.read(new File(orgFullPath + File.separator + orgFileName));
	    //원본 이미지의 가로와 세로 사이즈를 가져온다.
	    int originWidth = originalImg.getWidth(), width;
	    int originHeight = originalImg.getHeight(), height;

	    boolean isWidth = criteria.toLowerCase(Locale.ROOT).equals("width");

	    //리사이징할 이미지의 기준을 정한다. 가로 기준인지 세로 기준인지
	    //기준의 최고 픽셀보다 원본이 큰지 작은지
	    if(isWidth) {
	        if (originWidth > maxPixel) {
	            height = (originHeight * maxPixel) / originWidth;
	            width = maxPixel;
	        } else {
	            height = originHeight;
	            width = originWidth;
	        }
	    } else {
	        if (originHeight > maxPixel) {
	            height = maxPixel;
	            width = (originWidth * maxPixel) / originHeight;
	        } else {
	            height = originHeight;
	            width = originWidth;
	        }
	    }
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    //중앙을 중심으로 CROP 형태로 저장할 지, 일반형태로 저장할 지
	    if(resizeType.toLowerCase(Locale.ROOT).equals("crop")) {
	        int[] centerPoint = {originalImg.getWidth() / 2, originalImg.getHeight() / 2};
	        Graphics2D graphics2D = newImage.createGraphics();
	        graphics2D.setBackground(Color.WHITE);
	        graphics2D.setPaint(Color.WHITE);
	        graphics2D.fillRect(0, 0, width, height);
	        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics2D.drawImage(originalImg, 0, 0, width, height, 
	                centerPoint[0] - (int) (width / 2), centerPoint[1] - (int) (height / 2), 
	                centerPoint[0] + (int) (width / 2), centerPoint[1] + (int) (height / 2), null);
	    } else {
	        Image resizeImage = originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        Graphics graphics = newImage.getGraphics();
	        graphics.drawImage(resizeImage, 0, 0, null);
	        graphics.dispose();
	    }
	    // 이미지 저장
	    String[] fileNameArr = resizeFileName.split("\\.");
	    File newImgFile = new File(resizeFullPath + resizeFileName);
	    FileOutputStream fileOutputStream = new FileOutputStream(newImgFile);
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ImageIO.write(newImage, fileNameArr[fileNameArr.length - 1], output);
	    fileOutputStream.write(output.toByteArray());
	}
	
	private static void imageResize(String uploadPath, String fileName) {
		
		/* 이미지 리사이즈 graphics start */
		try {
			
			// 파일에서 이미지 불러오기
			Image oldImg = ImageIO.read(new File(uploadPath + fileName));

			// 이미지 사이즈 체크
			int oldWidth = oldImg.getWidth(null);
			int oldHeight = oldImg.getHeight(null);
			System.out.println("image width:"+oldWidth);
			System.out.println("image height:"+oldHeight);
			
			 
			// 이미지 너비가 1300을 넘을경우 리사이징
			if(oldWidth > 1300) {
				
				//확장자
				String format = fileName.substring(fileName.lastIndexOf(".") + 1);
				System.out.println("format:"+format);
				
				int new_width = 0;
				int new_height = 0;
				
				double ratio = (double)1300/(double)oldWidth;
				new_width = (int)(oldWidth * ratio);
				new_height = (int)(oldHeight * ratio);
	
				System.out.println(new_width+", "+new_height);
				// 이미지 사이즈 수정
				// 이미지 리사이즈
				// Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
				// Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
				// Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
				// Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
				// Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
				Image resizeImage = oldImg.getScaledInstance(new_width, new_height, Image.SCALE_REPLICATE);
				
				// 새 이미지  저장하기
				// 새 이미지를 저장하기 위해 변경할 width와 height을 가진 이미지를 생성한다.
				BufferedImage newImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
				Graphics g = newImage.getGraphics();
				//새 이미지에 원본이미지를 그려준다.
				g.drawImage(resizeImage, 0, 0, null);
				//연결 끊기
				g.dispose();
				//그려진 이미지를 파일로 만들기
				ImageIO.write(newImage, format, new File(uploadPath + fileName));
			}
		
			// 이미지회전
		//	ro2(90, uploadPath, fileName);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		/* 이미지 리사이즈 graphics end */

		
		/*
		String mainPosition = "Width";
	//	BufferedImage newImg;
		//확장자
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		try{
			// 이미지 읽어 오기
	        BufferedImage inputImage = ImageIO.read(new File(uploadPath + fileName));
			// 이미지 세로 가로 측정
	        int originWidth = inputImage.getWidth();
	        int originHeight = inputImage.getHeight();
	        System.out.println("originWidth:"+originWidth);
	        System.out.println("originHeight:"+originHeight);
	        // 변경할 가로 길이
	        int newWidth = 1300;

	        if (originWidth > newWidth) {
	            // 기존 이미지 비율을 유지하여 세로 길이 설정
	            int newHeight = (originHeight * newWidth) / originWidth;
	   // 이미지 품질 설정         
	// Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
	// Image.SCALE_FAST : 이미지 부드러움보다 속도 우선
	// Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
	// Image.SCALE_SMOOTH : 속도보다 이미지 부드러움을 우선
	// Image.SCALE_AREA_AVERAGING : 평균 알고리즘 사용
	            Image resizeImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
	            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	            Graphics graphics = newImage.getGraphics();
	            graphics.drawImage(resizeImage, 0, 0, null);
	            graphics.dispose();
	          // 이미지 저장
	            File newFile = new File(uploadPath + fileName);
	            ImageIO.write(newImage, formatName, newFile);
	        } else { 
	             //   files.transferTo(new java.io.File(filePath));
	        }
			
		}catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	private static void ro2(int rotate, String fileStr, String fileNm) throws Exception {
		
	//	String fileStr = "C:\\editor_img\\";
	//	String fileNm = "118840_KakaoTalk_20240311_131842112.jpg";
		
//		    byte[] imgbuf2 = getDecript("원본 이미지 경로", "");
//		    ByteArrayInputStream bais2 = new ByteArrayInputStream(imgbuf2);
//		    BufferedImage oldImage = ImageIO.read(bais2);
	 
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
