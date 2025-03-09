package WiFi_public;

import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;

///정보 가져오는 함수
@WebServlet("/fetch_api_data")
public class WiFiFetchServlet extends HttpServlet {
    private static final String API_KEY = "575843755373696c3130306c4b624976";
    private static final int TOTAL_COUNT = 25521; // 총 데이터 개수 하드 코딩...
    private static final int BATCH_SIZE = 1000;   // 한 번에 가져올 데이터 개수(최대치가 1000)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        try {
            // 기존 Wi-Fi 데이터는 삭제(중복KEY 에러 발생문제 해결)
            WifiDao.clearWifiTable();

            int totalInserted = 0;
            for (int startIndex = 1; startIndex <= TOTAL_COUNT; startIndex += BATCH_SIZE) {
                int endIndex = Math.min(startIndex + BATCH_SIZE - 1, TOTAL_COUNT);
                String jsonData = fetchWifiDataFromAPI(startIndex, endIndex);

                if (jsonData != null) {
                    totalInserted += parseAndSaveWifiData(jsonData);
                }
            }

            // 응답 전송
            if (totalInserted > 0) {
                sendSuccessResponse(response, "Wi-Fi 데이터 " + totalInserted + "개 저장");
            } else {
                sendErrorResponse(response, "저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "서버 오류 발생");
        }
    }

    /**
     * API에서 Wi-Fi 데이터를 가져오는 메서드 (배치 범위 추가)
     */
    private String fetchWifiDataFromAPI(int startIndex, int endIndex) throws IOException {
        String apiUrl = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo/" + startIndex + "/" + endIndex + "/";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            return jsonString.toString();
        }
    }

    /**
     * JSON 파싱해서 저장
     * 개선점-> 파싱과 저장 처리 따로 진행해야 함
     */
    private int parseAndSaveWifiData(String jsonData) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONObject wifiData = jsonObj.getJSONObject("TbPublicWifiInfo");
            JSONArray wifiList = wifiData.getJSONArray("row");

            int insertedCount = 0;
            for (int i = 0; i < wifiList.length(); i++) {
                JSONObject wifi = wifiList.getJSONObject(i);

                String id = wifi.getString("X_SWIFI_MGR_NO");
                String district = wifi.getString("X_SWIFI_WRDOFC");
                String wifiName = wifi.getString("X_SWIFI_MAIN_NM");
                String roadAddress = wifi.getString("X_SWIFI_ADRES1");
                String detailAddress = wifi.optString("X_SWIFI_ADRES2", "-");
                String installPosition = wifi.optString("X_SWIFI_INSTL_FLOOR", "-");
                String installType = wifi.getString("X_SWIFI_INSTL_TY");
                String installOrg = wifi.getString("X_SWIFI_INSTL_MBY");
                String serviceType = wifi.getString("X_SWIFI_SVC_SE");
                String channelType = wifi.getString("X_SWIFI_CMCWR");
                int installYear = wifi.optInt("X_SWIFI_CNSTC_YEAR", 0);
                String isIn = wifi.optString("X_SWIFI_INOUT_DOOR", "-").equals("실내") ? "Y" : "N";
                String accessEnv = wifi.optString("X_SWIFI_REMARS3", "-");
                float xCoord = wifi.getFloat("LNT");
                float yCoord = wifi.getFloat("LAT");
                Timestamp workDate = Timestamp.valueOf(wifi.getString("WORK_DTTM").replace("T", " "));

                // DB 저장
                WifiDao.insertOrUpdateWifiInfo(id, district, wifiName, roadAddress, detailAddress, installPosition, installType,
                        installOrg, serviceType, channelType, installYear, isIn, accessEnv, xCoord, yCoord, workDate);
                insertedCount++;
            }
            return insertedCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 성공 응답 전송
     */
    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", "success");
        responseJson.put("message", message);
        response.getWriter().print(responseJson.toString());
    }

    /**
     * 에러 응답 전송
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        JSONObject errorJson = new JSONObject();
        errorJson.put("status", "error");
        errorJson.put("message", message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().print(errorJson.toString());
    }
}
