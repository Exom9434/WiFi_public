<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.db.LocationHistoryDAO, com.example.model.LocationHistory, java.util.List" %>
<%
    // 위치 히스토리 조회
    List<LocationHistory> historyList = LocationHistoryDAO.getLocationHistory();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>위치 히스토리 목록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        function deleteLocation(id) {
            if (confirm("정말 삭제하시겠습니까?")) {
                window.location.href = "delete_location.jsp?id=" + id;
            }
        }
    </script>
</head>
<body>
<div class="container mt-3">
    <h2>위치 히스토리 목록</h2>
    <a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="#">Open API 와이파이 정보 가져오기</a>

    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
        <% for (LocationHistory history : historyList) { %>
        <tr>
            <td><%= history.getId() %></td>
            <td><%= history.getLat() %></td>
            <td><%= history.getLon() %></td>
            <td><%= history.getSearchedAt() %></td>
            <td><button class="btn btn-danger btn-sm" onclick="deleteLocation(<%= history.getId() %>)">삭제</button></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
