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

	if(attendanceSession.isSignOn() == false){
		response.sendRedirect("./LoginServlet");
		return;
	}

%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common/css/style.css">


<title>管理者メニュー</title>
</head>
<body>
<%@ include file="header.jsp"%>
<main>
<%
	if(!(attendanceSession.isManager())){
		out.print("管理者権限がないと開けません");
	}else{%>
	<font size="5">
	<a href="./EmployeeManagementServlet?mode=list">従業員管理</a><br>
	<a href="./AttendanceManagementServlet">勤怠管理</a><br>
	</font>
	<%}%>

</main>

</body>
</html>