package WiFi_public;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WifiDao {
    private static final Logger LOGGER = Logger.getLogger(WifiDao.class.getName());

    /**
     * x_pos(경도)와 y_pos(위도)를 기준으로 가까운 Wi-Fi 정보를 가져오는 메서드
     */
    public static List<WifiInfo> getNearestWifi(double xPos, double yPos) throws SQLException {
        List<WifiInfo> wifiList = new ArrayList<>();

        String sql = "SELECT id, district, wifi_name, road_address, detail_address, install_position, " +
                "install_type, install_org, service_type, channel_type, install_year, is_in, access_env, " +
                "x_pos, y_pos, work_date, " +
                "(6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(y_pos)) * " +
                "COS(RADIANS(x_pos) - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(y_pos)))) AS distance " +
                "FROM wifi_info " +
                "ORDER BY distance ASC " +
                "LIMIT 20";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, yPos); // 위도 (y_pos)
            pstmt.setDouble(2, xPos); // 경도 (x_pos)
            pstmt.setDouble(3, yPos); // 위도 (y_pos)

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiInfo wifi = new WifiInfo(
                            rs.getString("id"), // ✅ `id` remains as String
                            rs.getString("district"),
                            rs.getString("wifi_name"),
                            rs.getString("road_address"),
                            rs.getString("detail_address"),
                            rs.getString("install_position"),
                            rs.getString("install_type"),
                            rs.getString("install_org"),
                            rs.getString("service_type"),
                            rs.getString("channel_type"),
                            rs.getInt("install_year"),
                            rs.getString("is_in"), // ✅ No conversion, store as String
                            rs.getString("access_env"),
                            rs.getFloat("y_pos"), // 위도
                            rs.getFloat("x_pos"), // 경도
                            rs.getTimestamp("work_date")
                    );
                    wifiList.add(wifi);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB 조회 실패", e);
            throw e;
        }
        return wifiList;
    }

    /**
     * Wi-Fi 정보를 DB에 저장하는 메서드
     */
    public static void insertOrUpdateWifiInfo(String id, String district, String wifiName, String roadAddress,
                                              String detailAddress, String installPosition, String installType,
                                              String installOrg, String serviceType, String channelType,
                                              int installYear, String isIn, String accessEnv,
                                              float xPos, float yPos, Timestamp workDate) throws SQLException {

        String checkSql = "SELECT COUNT(*) FROM wifi_info WHERE id = ?";
        String insertSql = "INSERT INTO wifi_info (id, district, wifi_name, road_address, detail_address, " +
                "install_position, install_type, install_org, service_type, channel_type, install_year, " +
                "is_in, access_env, x_pos, y_pos, work_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE wifi_info SET district=?, wifi_name=?, road_address=?, detail_address=?, " +
                "install_position=?, install_type=?, install_org=?, service_type=?, channel_type=?, " +
                "install_year=?, is_in=?, access_env=?, x_pos=?, y_pos=?, work_date=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) { // 이미 존재하면 UPDATE
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, district);
                    updateStmt.setString(2, wifiName);
                    updateStmt.setString(3, roadAddress);
                    updateStmt.setString(4, detailAddress);
                    updateStmt.setString(5, installPosition);
                    updateStmt.setString(6, installType);
                    updateStmt.setString(7, installOrg);
                    updateStmt.setString(8, serviceType);
                    updateStmt.setString(9, channelType);
                    updateStmt.setInt(10, installYear);
                    updateStmt.setString(11, isIn);
                    updateStmt.setString(12, accessEnv);
                    updateStmt.setFloat(13, xPos);
                    updateStmt.setFloat(14, yPos);
                    updateStmt.setTimestamp(15, workDate);
                    updateStmt.setString(16, id);
                    updateStmt.executeUpdate();
                }
            } else { // 존재하지 않으면 INSERT
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, id);
                    insertStmt.setString(2, district);
                    insertStmt.setString(3, wifiName);
                    insertStmt.setString(4, roadAddress);
                    insertStmt.setString(5, detailAddress);
                    insertStmt.setString(6, installPosition);
                    insertStmt.setString(7, installType);
                    insertStmt.setString(8, installOrg);
                    insertStmt.setString(9, serviceType);
                    insertStmt.setString(10, channelType);
                    insertStmt.setInt(11, installYear);
                    insertStmt.setString(12, isIn);
                    insertStmt.setString(13, accessEnv);
                    insertStmt.setFloat(14, xPos);
                    insertStmt.setFloat(15, yPos);
                    insertStmt.setTimestamp(16, workDate);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    public static void clearWifiTable() throws SQLException {
        String sql = "DELETE FROM wifi_info";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    /**
     * 특정 ID의 Wi-Fi 정보를 DB에서 삭제하는 메서드
     */
    public static void deleteLocation(String id) throws SQLException {
        String sql = "DELETE FROM wifi_info WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id); // ✅ Ensure ID is stored as String
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB 삭제 실패", e);
            throw e;
        }
    }
}