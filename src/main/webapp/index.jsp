<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        function fetchWifiInfo(event) {
            event.preventDefault(); // 링크 클릭 시 페이지 이동 방지

            const apiKey = "YOUR_API_KEY"; // 여기에 본인의 API 키 입력
            const apiUrl = `http://openapi.seoul.go.kr:8088/${apiKey}/xml/TbPublicWifiInfo/1/5/`;

            fetch(apiUrl)
                .then(response => response.text()) // XML 데이터를 문자열로 받음
                .then(data => {
                    parseWifiXML(data); // XML 데이터 파싱 후 테이블에 추가
                })
                .catch(error => console.error("API 호출 오류:", error));
        }

        function parseWifiXML(xmlString) {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlString, "application/xml");
            const wifiList = xmlDoc.getElementsByTagName("row");

            let tableBody = document.getElementById("wifiTableBody");
            tableBody.innerHTML = ""; // 기존 데이터 초기화

            for (let i = 0; i < wifiList.length; i++) {
                let row = wifiList[i];

                let distance = row.getElementsByTagName("X_SWIFI_MGR_NO")[0].textContent || "-";
                let mgrNo = row.getElementsByTagName("X_SWIFI_WRDOFC")[0].textContent || "-";
                let gu = row.getElementsByTagName("X_SWIFI_MAIN_NM")[0].textContent || "-";
                let wifiName = row.getElementsByTagName("X_SWIFI_ADRES1")[0].textContent || "-";
                let roadAddress = row.getElementsByTagName("X_SWIFI_ADRES2")[0].textContent || "-";
                let detailAddress = row.getElementsByTagName("X_SWIFI_INSTL_FLOOR")[0].textContent || "-";
                let installFloor = row.getElementsByTagName("X_SWIFI_INSTL_TY")[0].textContent || "-";
                let installType = row.getElementsByTagName("X_SWIFI_INSTL_MBY")[0].textContent || "-";
                let installAgency = row.getElementsByTagName("X_SWIFI_SVC_SE")[0].textContent || "-";
                let serviceType = row.getElementsByTagName("X_SWIFI_CMCWR")[0].textContent || "-";
                let networkType = row.getElementsByTagName("X_SWIFI_CNSTC_YEAR")[0].textContent || "-";
                let year = row.getElementsByTagName("X_SWIFI_INOUT_DOOR")[0].textContent || "-";
                let indoorOutdoor = row.getElementsByTagName("X_SWIFI_REMARS3")[0].textContent || "-";
                let wifiEnv = row.getElementsByTagName("LAT")[0].textContent || "-";
                let xCoord = row.getElementsByTagName("LNT")[0].textContent || "-";
                let yCoord = row.getElementsByTagName("WORK_DTTM")[0].textContent || "-";

                let newRow = `
                    <tr>
                        <td>${distance}</td>
                        <td>${mgrNo}</td>
                        <td>${gu}</td>
                        <td>${wifiName}</td>
                        <td>${roadAddress}</td>
                        <td>${detailAddress}</td>
                        <td>${installFloor}</td>
                        <td>${installType}</td>
                        <td>${installAgency}</td>
                        <td>${serviceType}</td>
                        <td>${networkType}</td>
                        <td>${year}</td>
                        <td>${indoorOutdoor}</td>
                        <td>${wifiEnv}</td>
                        <td>${xCoord}</td>
                        <td>${yCoord}</td>
                        <td>${yCoord}</td>
                    </tr>
                `;
                tableBody.innerHTML += newRow;
            }
        }
        function getUserLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    function(position) {
                        document.getElementById("lat").value = position.coords.latitude.toFixed(6);
                        document.getElementById("lnt").value = position.coords.longitude.toFixed(6);
                    },
                    function(error) {
                        alert("위치 정보를 가져올 수 없습니다. 위치 서비스가 활성화되어 있는지 확인하세요.");
                    }
                );
            } else {
                alert("이 브라우저에서는 위치 정보를 지원하지 않습니다.");
            }
        }
        function saveLocation() {
            let lat = document.getElementById("lat").value;
            let lon = document.getElementById("lnt").value;

            if (!lat || !lon) {
                alert("위도와 경도를 입력하세요!");
                return;
            }
            window.location.href = "save_location.jsp?lat=" + lat + "&lon=" + lon;
        }
    </script>
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
    </style>
</head>
<body>

<div class="container">
    <div class="header">와이파이 정보 구하기</div>
    <a href="#">홈</a> | <a href="#">위치 히스토리 목록</a> |
    <a href="#" onclick="fetchWifiInfo(event)">Open API 와이파이 정보 가져오기</a>

    <div class="mt-3">
        <label for="lat">LAT: </label>
        <input type="text" id="lat" value="0.0">
        <label for="lnt">LNT: </label>
        <input type="text" id="lnt" value="0.0">
        <button class="btn btn-primary btn-sm" onclick="getUserLocation()">내 위치 가져오기</button>
        <button class="btn btn-success btn-sm" onclick="saveLocation()">근처 WIFI 정보 보기</button>
    </div>

    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
        </thead>
        <tbody id="wifiTableBody">
        <tr>
            <td colspan="17" class="text-center">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>
