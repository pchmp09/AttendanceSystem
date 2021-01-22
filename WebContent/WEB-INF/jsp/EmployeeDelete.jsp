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
<title>従業員削除確認</title>
</head>
<body>
<%@ include file="header.jsp"%>

<%=employees.get(i).getName() %> を本当に従業員一覧から削除しますか？<br>
<br>
<form action="./EmployeeManagementServlet?mode=delete" method="post">
<input type="submit" value="する" />
</form>

<form action="./EmployeeManagementServlet?mode=list" method="post">
<input type="submit" value="削除せず戻る" />
</form>




</body>
</html>