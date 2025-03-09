package WiFi_public;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryDao {

    // 위치 저장 메서드
    public static void saveLocation(float lat, float lon) {
        String sql = "INSERT INTO search_wifi (lat, lon) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection(); // 매번 새로운 DB 연결
            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, lat);
            pstmt.setFloat(2, lon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 허술한 예외 처리 (로깅 없이 그냥 출력)
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close(); // 매번 연결을 닫아버려서 비효율적
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 최근 조회한 위치 목록 조회 메서드
    public static List<LocationHistory> getLocationHistory() {
        List<LocationHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM search_wifi ORDER BY search_date DESC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection(); // 매번 새로운 DB 연결
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LocationHistory history = new LocationHistory(
                        rs.getString("id"),
                        rs.getFloat("y_pos"),
                        rs.getFloat("x_pos"),
                        rs.getTimestamp("search_date").toString()
                );
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 허술한 예외 처리
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close(); // 매번 연결을 닫아버림
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return historyList;
    }

    // 위치 삭제 메서드
    public static void deleteLocation(int id) {
        String sql = "DELETE FROM search_wifi WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection(); // 매번 새로운 DB 연결
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 허술한 예외 처리
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close(); // 매번 연결을 닫아버림
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
