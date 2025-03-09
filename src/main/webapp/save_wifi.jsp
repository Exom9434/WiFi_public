<!-- 백엔드에서 직접 처리하기로 해서 삭제 필요
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.net.HttpURLConnection, java.net.URL, java.sql.Timestamp, java.sql.SQLException" %>
<%@ page import="WiFi_public.WifiDao, WiFi_public.WiFiFetchServlet" %>
<%@ page import="org.json.JSONObject, org.json.JSONArray" %><%@ page import="WiFi_public.WifiServlet"%>

<%
    String apiKey = "575843755373696c3130306c4b624976";
    String apiUrl = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo/1/5/";

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    try {
        // API 호출
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
        }
        br.close();

        // JSON 파싱
        JSONObject jsonObj = new JSONObject(jsonString.toString());
        JSONObject wifiData = jsonObj.getJSONObject("TbPublicWifiInfo");
        JSONArray wifiList = wifiData.getJSONArray("row");

        for (int i = 0; i < wifiList.length(); i++) {
            JSONObject wifi = wifiList.getJSONObject(i);

            // API 데이터 매핑
            String id = wifi.getString("X_SWIFI_MGR_NO"); // 관리번호 (PK)
            String district = wifi.getString("X_SWIFI_WRDOFC"); // 자치구
            String wifiName = wifi.getString("X_SWIFI_MAIN_NM"); // 와이파이명
            String roadAddress = wifi.getString("X_SWIFI_ADRES1"); // 도로명주소
            String detailAddress = wifi.optString("X_SWIFI_ADRES2", "-"); // 상세주소 (NULL 가능)
            String installPosition = wifi.optString("X_SWIFI_INSTL_FLOOR", "-"); // 설치 위치
            String installType = wifi.getString("X_SWIFI_INSTL_TY"); // 설치유형
            String installOrg = wifi.getString("X_SWIFI_INSTL_MBY"); // 설치기관
            String serviceType = wifi.getString("X_SWIFI_SVC_SE"); // 서비스구분
            String channelType = wifi.getString("X_SWIFI_CMCWR"); // 망종류
            int installYear = wifi.optInt("X_SWIFI_CNSTC_YEAR", 0); // 설치년도 (기본값 0)
            String isIn = wifi.optString("X_SWIFI_INOUT_DOOR", "-").equals("실내") ? "Y" : "N"; // 실내/실외 구분
            String accessEnv = wifi.optString("X_SWIFI_REMARS3", "-"); // 접속환경
            float xCoord = wifi.getFloat("LNT"); // X 좌표 (경도)
            float yCoord = wifi.getFloat("LAT"); // Y 좌표 (위도)
            Timestamp workDate = Timestamp.valueOf(wifi.getString("WORK_DTTM").replace("T", " ")); // 작업일자

            // DB 저장
            WifiServlet.insertWifiInfo(id, district, wifiName, roadAddress, detailAddress, installPosition, installType,
                    installOrg, serviceType, channelType, installYear, isIn, accessEnv, xCoord, yCoord, workDate);
        }

        // JSON 응답 반환
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", "success");
        responseJson.put("message", "✅ Wi-Fi 데이터 저장 완료!");
        out.print(responseJson.toString());

    } catch (SQLException e) {
        e.printStackTrace();
        JSONObject errorJson = new JSONObject();
        errorJson.put("status", "error");
        errorJson.put("message", "❌ 데이터베이스 오류 발생!");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print(errorJson.toString());
    } catch (Exception e) {
        e.printStackTrace();
        JSONObject errorJson = new JSONObject();
        errorJson.put("status", "error");
        errorJson.put("message", "❌ API 처리 중 오류 발생!");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print(errorJson.toString());
    }
%> -->
