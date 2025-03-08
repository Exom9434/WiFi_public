<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        .container { margin-top: 20px; }
        .header { font-size: 24px; font-weight: bold; margin-bottom: 10px; }
        .table thead th { background-color: green; color: white; text-align: center; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">와이파이 정보 구하기</div>
    <a href="#" onclick="goHome(); return false;">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="#" id="fetchWifiData">공공 API 정보 가져오기</a>

    <div class="mt-3">
        <label for="x_pos">X 좌표 (경도): </label>
        <input type="text" id="x_pos" value="0.0">
        <label for="y_pos">Y 좌표 (위도): </label>
        <input type="text" id="y_pos" value="0.0">
        <button class="btn btn-primary btn-sm" id="getUserLocation">내 위치 가져오기</button>
        <button class="btn btn-success btn-sm" id="fetchNearbyWifi">근처 WIFI 정보 보기</button>
    </div>

    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>설치기관</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>Y 좌표</th>
            <th>X 좌표</th>
            <th>거리(Km)</th>
        </tr>
        </thead>
        <tbody id="wifiTableBody">
        <tr>
            <td colspan="10" class="text-center">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("fetchNearbyWifi").addEventListener("click", fetchNearbyWifi);
        document.getElementById("getUserLocation").addEventListener("click", getUserLocation);
        document.getElementById("fetchWifiData").addEventListener("click", fetchWifiData);
    });

    function getUserLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                position => {
                    document.getElementById("x_pos").value = position.coords.longitude.toFixed(6);
                    document.getElementById("y_pos").value = position.coords.latitude.toFixed(6);
                },
                error => alert("위치 정보를 가져올 수 없습니다. 위치 서비스가 활성화되어 있는지 확인하세요.")
            );
        } else {
            alert("이 브라우저에서는 위치 정보를 지원하지 않습니다.");
        }
    }

    function fetchNearbyWifi() {
        const xPos = document.getElementById("x_pos").value.trim();
        const yPos = document.getElementById("y_pos").value.trim();

        if (!xPos || !yPos || xPos === "0.0" || yPos === "0.0") {
            alert("위치 정보를 가져온 후 실행하세요!");
            return;
        }

        const projectName = window.location.pathname.split('/')[1];
        const url = `${window.location.origin}/${projectName}/wifi?x_pos=${xPos}&y_pos=${yPos}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                updateWifiTable(data);
                saveSearchHistory(xPos, yPos);  // DB에 검색 기록 저장
            })
            .catch(error => console.error("❌ Fetch 오류:", error));
    }

    function updateWifiTable(data) {
        const tableBody = document.getElementById("wifiTableBody");
        tableBody.innerHTML = "";

        if (!data.wifi_list || data.wifi_list.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="10" class="text-center">근처 Wi-Fi 정보를 찾을 수 없습니다.</td></tr>`;
            return;
        }

        data.wifi_list.forEach(wifi => {
            const row = document.createElement("tr");
            row.innerHTML = `
            <td>${wifi.id || "-"}</td>
            <td>${wifi.district || "-"}</td>
            <td>${wifi.wifi_name || "-"}</td>
            <td>${wifi.road_address || "-"}</td>
            <td>${wifi.install_org || "-"}</td>
            <td>${wifi.install_year || "-"}</td>
            <td>${wifi.is_in || "-"}</td>
            <td>${wifi.y_pos || "-"}</td>
            <td>${wifi.x_pos || "-"}</td>
            <td>${wifi.distance ? wifi.distance.toFixed(2) + " km" : "- km"}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    function saveSearchHistory(xPos, yPos) {
        const projectName = window.location.pathname.split('/')[1];
        const url = `${window.location.origin}/${projectName}/save_location?x_pos=${xPos}&y_pos=${yPos}`;

        fetch(url, { method: "POST" })
            .then(response => response.json())
            .then(data => console.log("✅ 검색 기록 저장 완료:", data))
            .catch(error => console.error("❌ 검색 기록 저장 실패:", error));
    }

    function fetchWifiData() {
        const projectName = window.location.pathname.split('/')[1];
        const url = `${window.location.origin}/${projectName}/fetch_api_data`;

        fetch(url)
            .then(response => response.json())
            .then(data => alert(data.message))
            .catch(error => console.error("❌ API 데이터 가져오기 오류:", error));
    }
    function goHome() {
        const projectName = window.location.pathname.split('/')[1];
        window.location.href = `/${projectName}/index.jsp`; // 홈 화면 경로로 이동
    }
</script>

</body>
</html>
