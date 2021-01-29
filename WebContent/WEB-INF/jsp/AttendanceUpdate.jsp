<%@page import="model.AttendanceTable"%>
<%@page import="model.Attendance"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Employee"%>
<%@page import="java.util.List"%>
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
<title>勤怠修正</title>
</head>
<body>
<%@ include file="header.jsp"%>

<form action="./AttendanceManagementServlet?mode=update" method="post">
<table border="1">
	<tr>
		<th>日　　付</th>
		<th>出　　勤</th>
		<th>退　　勤</th>
		<th>休憩開始</th>
		<th>休憩終了</th>
	</tr>
		<td><input type="date" name="workDate" value ="<%=attendances.get(i).getWorkDate() == null ? "" : attendances.get(i).getWorkDateToString()%>"></td>
		<td><input type="time" name="arrive" value ="<%=attendances.get(i).getArrive() == null ? "" : attendances.get(i).eachTimeToString(attendances.get(i).getArrive())%>"></td>
		<td><input type="time" name="leave" value ="<%=attendances.get(i).getLeave() == null ? "" : attendances.get(i).eachTimeToString(attendances.get(i).getLeave())%>"></td>
		<td><input type="time" name="startBreak" value ="<%=attendances.get(i).getStartBreak() == null ? "" : attendances.get(i).eachTimeToString(attendances.get(i).getStartBreak())%>"></td>
		<td><input type="time" name="endBreak" value ="<%=attendances.get(i).getEndBreak() == null ? "" : attendances.get(i).eachTimeToString(attendances.get(i).getEndBreak())%>"></td>
	</tr>
</table>
<br>
<input type="submit" value="完了">
</form>
</body>
</html>