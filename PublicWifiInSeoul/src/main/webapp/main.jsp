<%@page import="model.WifiInfo"%>
<%@page import="java.util.List"%>
<%@page import="controller.WifiController"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	
	<script>
		// 입력값 초기화
		function resetLocation() {
			document.getElementById('latitude').value = '';
			document.getElementById('longitude').value = '';
		}
		
		// 현재 위치 가져오기
		function getLocation() {
			const getSuccess = (position) => {
				const result = {
					latitude: position.coords.latitude,
					longitude: position.coords.longitude
				}
				document.getElementById('latitude').value = result.latitude;
				document.getElementById('longitude').value = result.longitude;
			};
			
			const getError = (error) => {
				var errorMessage = '';
				switch(error.code) {
					case error.PERMISSION_DENIED:
						errorMessage = "위치 권한을 허용 후 다시 시도해주세요."
				        break;
					default:
						errorMessage = "위치 정보를 가져올 수 없습니다."
						break;
				}
				alert(errorMessage);
			};
			
			navigator.geolocation.getCurrentPosition(getSuccess, getError);
		}
		
		// 근처 WiFi 리스트 가져오기
		function getNearWifiList() {
			let latitude = document.getElementById('latitude').value;
			let longitude = document.getElementById('longitude').value;
			
			if (latitude == "" || longitude == "") {
				alert("위치 정보를 먼저 입력해 주세요.");
			} else {
				window.location.assign("<%= request.getContextPath() %>/getNearWifi?latitude=" + latitude + "&longitude=" + longitude);
			}
		}
	</script>
</head>
<body>
	<h2>와이파이 정보 구하기</h2>
	<%@ include file="header.jsp" %>

	<p>
		LAT: <input type='text' id='latitude' placeholder='0.0' value = '${latitude}'>
		LNT: <input type='text' id='longitude' placeholder='0.0' value = '${longitude}'>
		<button type='button' onclick='resetLocation()'>입력 초기화</button>
		<button type='button' id ='location' onclick='getLocation()'>내 위치 가져오기</button>
		<button type='button' onclick='getNearWifiList()'>근처 WiFi 정보 보기</button>
	</p>
	
	<table>
		<thead>
			<tr>
				<th>거리(km)</th>
				<th>관리 번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명 주소</th>
				<th>상세 주소</th>
				<th>설치 위치(층)</th>
				<th>설치 유형</th>
				<th>설치 기관</th>
				<th>서비스 구분</th>
				<th>망 종류</th>
				<th>설치 연도</th>
				<th>실내외 구분</th>
				<th>WIFI 접속 환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업 일자</th>	
			</tr>
		</thead>
		
		<tbody>
			<%
				String latitude = (String) request.getAttribute("latitude");
				String longitude = (String) request.getAttribute("longitude");
				
				List<WifiInfo> wifiList = (List<WifiInfo>) request.getAttribute("wifiList");
				if (latitude == null || longitude == null) {
			%>
				<tr>
					<td colspan='17' style ='font-weight: bold;'>위치 정보를 먼저 입력해 주세요.</td>
				</tr>
			<%
				} else if (wifiList == null) {
			%>
				<tr>
					<td colspan='17' style ='font-weight: bold;'>저장된 와이파이 정보가 없습니다.</td>
				</tr>
			<%
				} else {
					for(WifiInfo wifiInfo: wifiList) {
			%>
				<tr>
		            <td><%= wifiInfo.getDistance()%></td>
		            <td><%= wifiInfo.getWifiNo() %></td>
		            <td><%= wifiInfo.getDistrict() %></td>
		            <td><%= wifiInfo.getWifiName() %></td>
		            <td><%= wifiInfo.getAddress1() %></td>
		            <td><%= wifiInfo.getAddress2() %></td>
		            <td><%= wifiInfo.getInstallFloor() %></td>
		            <td><%= wifiInfo.getInstallType() %></td>
		            <td><%= wifiInfo.getInstallMby() %></td>
		            
		            <td><%= wifiInfo.getServiceType() %></td>
		            <td><%= wifiInfo.getNetworkType() %></td>
		            <td><%= wifiInfo.getInstallYear() %></td>
		            <td><%= wifiInfo.getInoutDoor() %></td>
		            <td><%= wifiInfo.getWifiConnection() %></td>
		            <td><%= wifiInfo.getLongitude() %></td>
		            <td><%= wifiInfo.getLatitude() %></td>
		            <td><%= wifiInfo.getWorkDate() %></td>
	           </tr>
			<%
					}
				}
			%>
		</tbody>
	</table>
</body>
</html>