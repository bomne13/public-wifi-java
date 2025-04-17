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
<title>Insert title here</title>
</head>
<body>
	<%
		WifiController controller = new WifiController();
		String resultString = controller.insertWiFiList();
	%>
	<h2 class='center-text'><%= resultString %></h2>
	<p class='center-text'><a href='<%=request.getContextPath()%>/'>홈으로 돌아가기</a></p>
</body>
</html>