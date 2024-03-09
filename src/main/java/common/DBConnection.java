package common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public Connection dbConn() {
		
		Connection con = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String url = "jdbc:mariadb://localhost:3306/bissas2?serverTimezone=UTC";
            String user = "bissas2";
            String pwd = "qusehd!82";

            con = DriverManager.getConnection(url, user, pwd);
            if(con != null) {
                //System.out.println("연결 성공");
            }
        } catch(Exception e) {
            System.out.println("연결 오류 발생!");
            e.printStackTrace();
        }
        
		return con;
	}
}
