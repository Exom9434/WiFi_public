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
  <style>
    .container {
      margin-top: 20px;
    }
    .header {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 10px;
    }
    .table thead th {
      background-color: green;
      color: white;
      text-align: center;
    }
    .text-center {
      text-align: center;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="header">가장 가까운 와이파이 20개</div>
  <table class="table table-bordered mt-3">
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
    <% if (wifiList != null && !wifiList.isEmpty()) {
      for (WifiInfo wifi : wifiList) { %>
    <tr>
      <td class="text-center"><%= wifi.getDistrict() %></td>
      <td class="text-center"><%= wifi.getWifiName() %></td>
      <td class="text-center"><%= wifi.getRoadAddress() %></td>
      <td class="text-center"><%= wifi.getDetailAddress() %></td>
      <td class="text-center"><%= wifi.getInstallPosition() %></td>
      <td class="text-center"><%= wifi.getInstallType() %></td>
      <td class="text-center"><%= wifi.getInstallOrg() %></td>
      <td class="text-center"><%= wifi.getServiceType() %></td>
      <td class="text-center"><%= wifi.getChannelType() %></td>
      <td class="text-center"><%= wifi.getInstallYear() %></td>
      <td class="text-center"><%= wifi.getIsIn() ? "실내" : "실외" %></td>
      <td class="text-center"><%= wifi.getAccessEnv() %></td>
      <td class="text-center"><%= wifi.getYPos() %></td>
      <td class="text-center"><%= wifi.getXPos() %></td>
      <td class="text-center"><%= wifi.getWorkDate() %></td>
    </tr>
    <% }
    } else { %>
    <tr>
      <td colspan="15" class="text-center">와이파이 정보를 불러올 수 없습니다.</td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
