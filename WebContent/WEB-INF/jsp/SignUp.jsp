<%@page import="servlet.AttendanceSession"%>
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
<title>ログイン確認</title>
</head>
<body>
<%@ include file="header.jsp"%>


<%
	if(attendanceSession.isSignOn() == true){
%>
ログイン成功
<a href="./MainServlet">topへ</a>
<%
} else{
%>
ログイン失敗
<a href="./MainServlet">topへ</a>
<%
}
%>

</body>
</html>