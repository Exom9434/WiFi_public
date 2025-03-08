package WiFi_public;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchHistory {

    // 위치 저장 메서드
    public static void saveLocation(float xPos, float yPos) {
        String sql = "INSERT INTO location_history (id, x_pos, y_pos, search_date) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String id = generateUniqueId();  // ID를 생성하는 함수 추가
            pstmt.setString(1, id);
            pstmt.setFloat(2, xPos);
            pstmt.setFloat(3, yPos);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 최근 조회한 위치 목록 조회 메서드
    public static List<LocationHistory> getLocationHistory() {
        List<LocationHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM location_history ORDER BY search_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocationHistory history = new LocationHistory(
                        rs.getString("id"),        // ID는 String으로 변경
                        rs.getFloat("x_pos"),      // x_pos 필드
                        rs.getFloat("y_pos"),      // y_pos 필드
                        rs.getTimestamp("search_date").toString() // search_date 필드
                );
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    // 위치 삭제 메서드
    public static void deleteLocation(String id) {
        String sql = "DELETE FROM location_history WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 유니크한 ID 생성 메서드 (예: UUID 기반)
    private static String generateUniqueId() {
        return "HIS" + System.currentTimeMillis();
    }
}
