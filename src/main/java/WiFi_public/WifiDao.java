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
    public static void insertWifiInfo(String id, String district, String wifiName, String roadAddress, String detailAddress,
                                      String installPosition, String installType, String installOrg, String serviceType,
                                      String channelType, int installYear, String isIn, String accessEnv,
                                      float xPos, float yPos, Timestamp workDate) throws SQLException {
        String sql = "INSERT INTO wifi_info (id, district, wifi_name, road_address, detail_address, install_position, install_type, " +
                "install_org, service_type, channel_type, install_year, is_in, access_env, x_pos, y_pos, work_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id); // ✅ Ensure ID is stored as String
            pstmt.setString(2, district);
            pstmt.setString(3, wifiName);
            pstmt.setString(4, roadAddress);
            pstmt.setString(5, detailAddress);
            pstmt.setString(6, installPosition);
            pstmt.setString(7, installType);
            pstmt.setString(8, installOrg);
            pstmt.setString(9, serviceType);
            pstmt.setString(10, channelType);
            pstmt.setInt(11, installYear);
            pstmt.setString(12, isIn); // ✅ Keep as String (store "실내" or "실외")
            pstmt.setString(13, accessEnv);
            pstmt.setFloat(14, xPos); // 경도
            pstmt.setFloat(15, yPos); // 위도
            pstmt.setTimestamp(16, workDate); // ✅ Use Timestamp

            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB 삽입 실패", e);
            throw e;
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