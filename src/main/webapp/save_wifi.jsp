<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.net.HttpURLConnection, java.net.URL, java.sql.Connection, java.sql.PreparedStatement" %>
<%@ page import="com.example.db.WifiDAO" %>
<%@ page import="org.w3c.dom.*, javax.xml.parsers.DocumentBuilder, javax.xml.parsers.DocumentBuilderFactory" %>

<%
    String apiKey = "YOUR_API_KEY"; // Open API 키 입력
    String apiUrl = "http://openapi.seoul.go.kr:8088/" + apiKey + "/xml/TbPublicWifiInfo/1/5/";

    try {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/xml");

        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder xmlString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            xmlString.append(line);
        }
        br.close();

        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlString.toString().getBytes("UTF-8")));

        NodeList wifiList = doc.getElementsByTagName("row");

        for (int i = 0; i < wifiList.getLength(); i++) {
            Node row = wifiList.item(i);
            Element element = (Element) row;

            String district = element.getElementsByTagName("X_SWIFI_WRDOFC").item(0).getTextContent();
            String wifiName = element.getElementsByTagName("X_SWIFI_MAIN_NM").item(0).getTextContent();
            String roadAddress = element.getElementsByTagName("X_SWIFI_ADRES1").item(0).getTextContent();
            String detailAddress = element.getElementsByTagName("X_SWIFI_ADRES2").item(0).getTextContent();
            String installPosition = element.getElementsByTagName("X_SWIFI_INSTL_FLOOR").item(0).getTextContent();
            String installType = element.getElementsByTagName("X_SWIFI_INSTL_TY").item(0).getTextContent();
            String installOrg = element.getElementsByTagName("X_SWIFI_INSTL_MBY").item(0).getTextContent();
            String serviceType = element.getElementsByTagName("X_SWIFI_SVC_SE").item(0).getTextContent();
            String channelType = element.getElementsByTagName("X_SWIFI_CMCWR").item(0).getTextContent();
            int installYear = Integer.parseInt(element.getElementsByTagName("X_SWIFI_CNSTC_YEAR").item(0).getTextContent());
            boolean isIn = "실내".equals(element.getElementsByTagName("X_SWIFI_INOUT_DOOR").item(0).getTextContent());
            String accessEnv = element.getElementsByTagName("X_SWIFI_REMARS3").item(0).getTextContent();
            float lati = Float.parseFloat(element.getElementsByTagName("LAT").item(0).getTextContent());
            float longi = Float.parseFloat(element.getElementsByTagName("LNT").item(0).getTextContent());
            String workDate = element.getElementsByTagName("WORK_DTTM").item(0).getTextContent();

            WifiDAO.insertWifiInfo(district, wifiName, roadAddress, detailAddress, installPosition, installType, installOrg, serviceType, channelType, installYear, isIn, accessEnv, lati, longi, workDate);
        }

        out.println("✅ 데이터 저장 완료!");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("❌ 오류 발생!");
    }
%>
