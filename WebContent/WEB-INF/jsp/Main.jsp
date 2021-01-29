<%@page import="model.AttendanceSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	AttendanceSession attendanceSession =
	(AttendanceSession) session.getAttribute("attendanceSession");

	if (attendanceSession == null) {
		attendanceSession = new AttendanceSession();
		session.setAttribute("attendanceSession", attendanceSession);
	}


%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common/css/style.css">


<title>トップページ</title>
</head>
<body>
<%@ include file="header.jsp"%>
<main>
   <h1>トップページ</h1>
</main>

</body>
</html>