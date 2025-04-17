<%@page import="controller.HistoryController"%>
<%@page import="model.History"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	
	<script>
		function deleteHistory(deleteNo) {
			if (confirm("'" + deleteNo + "' 기록을 삭제하시겠습니까?")) {
				window.location.assign("<%= request.getContextPath() %>/deleteHistory?historyNo=" + deleteNo);
			}
		}
	</script>
</head>
<body>
	<h2>위치 히스토리 목록</h2>
	<%@ include file="header.jsp" %><br>

	<%
		String deleteResult = (String) request.getAttribute("deleteResult");
		if (deleteResult == "FAILL") {
	%>
		<script>
			alert("데이터 삭제에 실패했습니다. 잠시후 다시 시도해주세요.");
		</script>
	<%
		}
	%>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X 좌표</th>
				<th>Y 좌표</th>
				<th>조회 일자</th>
				<th>비고</th>
			</tr>
		</thead>
		
		<tbody>
			<% 
				HistoryController controller = new HistoryController();
				List<History> historyList = controller.getHistoryList();
				
				if (historyList == null) {
			%>
				<tr>
					<td colspan='5'>히스토리가 없습니다.</td>
				</tr>
			<%
				} else {
					for (History history: historyList) {
			%>
				<tr>
					<td><%= history.getHistoryNo()%></td>
					<td><%= history.getLongitude()%></td>
					<td><%= history.getLatitude()%></td>
					<td><%= history.getSerchDate()%></td>
					<td><button onclick="deleteHistory(<%= history.getHistoryNo()%>)">삭제</button></td>
				</tr>
			<%
					}
				}
			%>
		</tbody>
	</table>
</body>
</html>