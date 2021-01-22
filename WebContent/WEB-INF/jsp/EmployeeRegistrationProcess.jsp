<%@page import="kintai.Employee"%>
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
	boolean add_success = (Boolean)request.getAttribute("add_success");
	Employee employee = (Employee)request.getAttribute("employee");

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
<title>登録確認</title>
</head>
<body>
<%
if(add_success){
%>
登録完了<br>
<a href="./EmployeeManagementServlet">戻る</a>
<%}else{ %>
登録失敗<br>
<a href="./EmployeeManagementServlet">戻る</a>

<%} %>
</body>
</html>