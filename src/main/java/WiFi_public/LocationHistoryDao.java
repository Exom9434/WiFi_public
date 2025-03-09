package WiFi_public;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryDao {

    // 위치 저장 메서드
    public static void saveLocation(float lat, float lon) {
        String sql = "INSERT INTO search_wifi (lat, lon) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, lat);
            pstmt.setFloat(2, lon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 최근 조회한 위치 목록 조회 메서드
    public static List<LocationHistory> getLocationHistory() {
        List<LocationHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM search_wifi ORDER BY search_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
            e.printStackTrace();
        }
        return historyList;
    }

    // 위치 삭제 메서드
    public static void deleteLocation(int id) {
        String sql = "DELETE FROM search_wifi WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
