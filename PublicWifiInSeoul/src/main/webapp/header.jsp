<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<header>
		<a href='<%=request.getContextPath()%>/'>홈</a> | 
	 	<a href='<%=request.getContextPath()%>/history.jsp'>위치 히스토리 목록</a> | 
		<a href='<%=request.getContextPath()%>/loadWifi.jsp'>Open API 와이파이 정보 가져오기</a>
	</header>
</body>
</html>