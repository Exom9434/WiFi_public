package WiFi_public;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/public_wifi?serverTimezone=UTC";
    private static final String USER = "root";  //ID
    private static final String PASSWORD = "zerobase";  // MySQL 비밀번호

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

