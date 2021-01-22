<%@page import="kintai.Attendance"%>
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

if(attendanceSession.isSignOn() == false){
	response.sendRedirect("./LoginServlet");
	return;
}

Attendance attendance = (Attendance) session.getAttribute("attendance");
if(attendance == null){
	attendance = new Attendance();
	session.setAttribute("attendance", attendance);
}

boolean success = (boolean)session.getAttribute("success");
int i1 = (Integer)session.getAttribute("i1");


%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common/css/style.css">
<title>勤怠登録</title>
</head>
<body>
<%@ include file="header.jsp"%>

<table>
	<tr>
		<th>日付</th>
		<th>出勤</th>
		<th>退勤</th>
		<th>休憩開始</th>
		<th>休憩終了</th>
	</tr>
	<tr>
		<td><%out.print(attendance.getWorkDate() 	== null ? "---" : attendance.getWorkDateToString());%></td>
		<td><%out.print(attendance.getArrive() 			== null ? "---" : attendance.eachTimeToString(attendance.getArrive()));%></td>
		<td><%out.print(attendance.getLeave() 			== null ? "---" : attendance.eachTimeToString(attendance.getLeave()));%></td>
		<td><%out.print(attendance.getStartBreak() 	== null ? "---" : attendance.eachTimeToString(attendance.getStartBreak()));%></td>
		<td><%out.print(attendance.getEndBreak() 		== null ? "---" : attendance.eachTimeToString(attendance.getEndBreak()));%></td>
	</tr>

</table>



</body>
</html>