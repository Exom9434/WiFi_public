package WiFi_public;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/wifi")
public class WifiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 요청 파라미터 가져오기 (x_pos: 경도, y_pos: 위도)
        String xPosParam = request.getParameter("x_pos");
        String yPosParam = request.getParameter("y_pos");

        if (xPosParam == null || yPosParam == null) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"x_pos 및 y_pos 값이 필요합니다.\"}");
            return;
        }

        double xPos, yPos;
        try {
            xPos = Double.parseDouble(xPosParam); // 경도
            yPos = Double.parseDouble(yPosParam); // 위도
        } catch (NumberFormatException e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"x_pos 또는 y_pos 값이 올바르지 않습니다.\"}");
            return;
        }

        try {
            // 데이터 조회 (가까운 Wi-Fi 정보 가져오기)
            List<WifiInfo> wifiList = WifiDao.getNearestWifi(xPos, yPos);

            // JSON 변환
            JSONObject jsonResponse = new JSONObject();
            JSONArray wifiArray = new JSONArray();

            for (WifiInfo wifi : wifiList) {
                JSONObject wifiJson = new JSONObject();
                wifiJson.put("id", wifi.getId());
                wifiJson.put("district", wifi.getDistrict());
                wifiJson.put("wifi_name", wifi.getWifiName());
                wifiJson.put("road_address", wifi.getRoadAddress());
                wifiJson.put("detail_address", wifi.getDetailAddress());
                wifiJson.put("install_position", wifi.getInstallPosition());
                wifiJson.put("install_type", wifi.getInstallType());
                wifiJson.put("install_org", wifi.getInstallOrg());
                wifiJson.put("service_type", wifi.getServiceType());
                wifiJson.put("channel_type", wifi.getChannelType());
                wifiJson.put("install_year", wifi.getInstallYear());
                wifiJson.put("is_in", wifi.getIsIn());
                wifiJson.put("access_env", wifi.getAccessEnv());
                wifiJson.put("x_pos", wifi.getXPos());
                wifiJson.put("y_pos", wifi.getYPos());
                wifiJson.put("work_date", wifi.getWorkDate());

                wifiArray.put(wifiJson);
            }

            jsonResponse.put("wifi_list", wifiArray);

            // JSON 응답 전송
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"데이터베이스 오류 발생\"}");
        }
    }
}
