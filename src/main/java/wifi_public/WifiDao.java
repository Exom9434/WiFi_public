package wifi_public;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WifiDao {
    private static final Logger LOGGER = Logger.getLogger(WifiDao.class.getName());

    public static List<WifiInfo> getNearestWifi(double lat, double lon) throws SQLException {
        List<WifiInfo> wifiList = new ArrayList<>();
        String sql = "SELECT *, " +
                "(6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(lati)) * " +
                "COS(RADIANS(longi) - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(lati)))) AS distance " +
                "FROM wifi_info " +
                "ORDER BY distance ASC " +
                "LIMIT 20";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lon);
            pstmt.setDouble(3, lat);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiInfo wifi = new WifiInfo(
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
                            rs.getBoolean("is_in"),
                            rs.getString("access_env"),
                            rs.getFloat("lati"),
                            rs.getFloat("longi"), // 컬럼명 확인 필요
                            rs.getString("work_date"),
                            rs.getDouble("distance")
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
}

