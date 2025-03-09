<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="WiFi_public.LocationHistoryDao, WiFi_public.LocationHistory, java.util.List" %>
<%
    // 위치 히스토리 조회
    List<LocationHistory> historyList = LocationHistoryDao.getLocationHistory();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>위치 히스토리 목록</title>
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

    <script>
        function deleteLocation(id) {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            const projectName = window.location.pathname.split('/')[1];
            const url = `${window.location.origin}/${projectName}/delete_location?id=${id}`;

            fetch(url, { method: "DELETE" })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("삭제 실패!");
                    }
                    return response.json();
                })
                .then(data => {
                    alert(data.message);
                    location.reload(); // 삭제 후 페이지 새로고침
                })
                .catch(error => {
                    console.error("❌ 삭제 실패:", error);
                    alert("❌ 삭제에 실패했습니다.");
                });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="header">위치 히스토리 목록</div>
    <a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="#">공공 API 와이파이 정보 가져오기</a>

    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th class="text-center">ID</th>
            <th class="text-center">X좌표</th>
            <th class="text-center">Y좌표</th>
            <th class="text-center">조회일자</th>
            <th class="text-center">비고</th>
        </tr>
        </thead>
        <tbody>
        <% if (historyList != null && !historyList.isEmpty()) {
            for (LocationHistory history : historyList) { %>
        <tr>
            <td class="text-center"><%= history.getId() %></td>
            <td class="text-center"><%= history.getxPos() %></td>
            <td class="text-center"><%= history.getyPos() %></td>
            <td class="text-center"><%= history.getSearchedAt() %></td>
            <td class="text-center">
                <button class="btn btn-danger btn-sm" onclick="deleteLocation('<%= history.getId() %>')">삭제</button>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="5" class="text-center">최근 검색한 위치가 없습니다.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
