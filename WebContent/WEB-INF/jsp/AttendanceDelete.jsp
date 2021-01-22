<%@page import="kintai.AttendanceTable"%>
<%@page import="kintai.Attendance"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kintai.Employee"%>
<%@page import="java.util.List"%>
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

List<Employee> employees = (List<Employee>)session.getAttribute("employees");
if (employees == null) {
	employees = new ArrayList<Employee>();
	session.setAttribute("employees", employees);
}

List<Attendance> attendances = (List<Attendance>)session.getAttribute("attendances");
if(attendances == null) {
	attendances = new ArrayList<>();
	session.setAttribute("attendances", attendances);
}

AttendanceTable attendanceTable = (AttendanceTable)session.getAttribute("attendanceTable");
if (attendanceTable == null) {
	attendanceTable = new AttendanceTable();
	session.setAttribute("attendanceSession", attendanceSession);
}

int i = (Integer)session.getAttribute("i");
session.setAttribute("i", i);

if(attendanceSession.isSignOn() == false){
	response.sendRedirect("./LoginServlet");
	return;
}else if(attendanceSession.isManager() == false){
	response.sendRedirect("./ManagerMainServlet");
	return;
}

%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common/css/style.css">
<title>勤怠削除確認</title>
</head>
<body>
<%@ include file="header.jsp"%>

本当に削除しますか？<br>
<br>
<form action="./AttendanceManagementServlet?mode=delete" method="post">
<input type="submit" value="する" />
</form>

<form action="./AttendanceManagementServlet?mode=list" method="post" >
<button type="button"  onclick="history.back()">削除せず戻る</button>
</form>

</body>
</html>