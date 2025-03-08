<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="WiFi_public.WifiInfo, java.util.List" %>
<%
  List<WifiInfo> wifiList = (List<WifiInfo>) request.getAttribute("wifiList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>근처 와이파이 정보</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-3">
  <h2>가장 가까운 와이파이 20개</h2>
  <table class="table table-bordered">
    <thead>
    <tr>
      <th>자치구</th>
      <th>와이파이명</th>
      <th>도로명 주소</th>
      <th>상세 주소</th>
      <th>설치 위치</th>
      <th>설치 유형</th>
      <th>설치 기관</th>
      <th>서비스 구분</th>
      <th>망 종류</th>
      <th>설치 년도</th>
      <th>실내/외</th>
      <th>WIFI 환경</th>
      <th>X좌표</th>
      <th>Y좌표</th>
      <th>작업일자</th>
    </tr>
    </thead>
    <tbody>
    <% if (wifiList != null) {
      for (WifiInfo wifi : wifiList) { %>
    <tr>
      <td><%= wifi.getDistrict() %></td>
      <td><%= wifi.getWifiName() %></td>
      <td><%= wifi.getRoadAddress() %></td>
      <td><%= wifi.getDetailAddress() %></td>
      <td><%= wifi.getInstallPosition() %></td>
      <td><%= wifi.getInstallType() %></td>
      <td><%= wifi.getInstallOrg() %></td>
      <td><%= wifi.getServiceType() %></td>
      <td><%= wifi.getChannelType() %></td>
      <td><%= wifi.getInstallYear() %></td>
      <td><%= wifi.getIsIn() ? "실내" : "실외" %></td>
      <td><%= wifi.getAccessEnv() %></td>
      <td><%= wifi.getYPos() %></td>
      <td><%= wifi.getXPos() %></td>
      <td><%= wifi.getWorkDate() %></td>
    </tr>
    <% }
    } else { %>
    <tr>
      <td colspan="16" class="text-center">와이파이 정보를 불러올 수 없습니다.</td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
