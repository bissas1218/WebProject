package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;

public class SaveAccessor {

	public SaveAccessor(HttpServletRequest request, String name) {
        super();
        // TODO Auto-generated constructor stub
        String ip = getClientIP(request);
        //System.out.println(name+", "+ip);
        
        DBConnection dbconn = new DBConnection();
		Connection con = dbconn.dbConn();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into accessor (name, ip, reg_date) values (?, ?, now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, ip);
			pstmt.executeQuery();
			
		}catch(SQLException e) {
			System.out.println("error: " + e);
		}finally {
			try {

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
		
    }
	
	public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
       // System.out.println("> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            //System.out.println("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            //System.out.println(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            //System.out.println("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            //System.out.println("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            //System.out.println("> getRemoteAddr : "+ip);
        }
       // System.out.println("> Result : IP Address : "+ip);

        return ip;
    }
}
