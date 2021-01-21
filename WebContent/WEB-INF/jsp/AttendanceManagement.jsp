<%@page import="kintai.AttendanceTable"%>
<%@page import="java.time.LocalDate"%>
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

if(attendanceSession.isSignOn() == false){
	response.sendRedirect("LoginServlet");
	return;
}else if(attendanceSession.isManager() == false){
	response.sendRedirect("ManagerMainServlet");
	return;
}

%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="common/css/style.css">
<title>勤怠管理</title>
</head>
<body>
<%@ include file="header.jsp"%>

<form action="AttendanceManagementServlet?mode=list" method="post">
<select name ="empNum">
<%
for(int i = 0; i < employees.size(); i++){ %>
	<option value="<%= i %>">
	<%=employees.get(i).getName() %>
	</option>
<%} %>
</select>　　　　　　　　

<select name ="year">
<%
LocalDate ld = LocalDate.now();
for(int i = 2019; i <= ld.getYear(); i++){%>
	<option value="<%= i %>">
	<%= i %>
	</option>
<%} %>
</select>年

<select name ="month">
<%
for(int i = 1; i <= 12; i++){%>
	<option value="<%= i %>">
	<%= i %>
	</option>
<%} %>
</select>月

<input type="submit" value="勤怠情報表示"/>

</form>
<br>
<br>
<%if(session.getAttribute("mode") != null){ %>
<h2><%=employees.get(attendanceTable.getEmpNum()).getName() %>：<%=attendanceTable.getYear() %>年　<%=attendanceTable.getMonth() %>月度勤務実績</h2>
	<table border="1">
	<tr>
		<th>日　　付</th>
		<th>出　　勤</th>
		<th>退　　勤</th>
		<th>休憩開始</th>
		<th>休憩終了</th>
		<th>休憩時間</th>
		<th>勤務時間</th>
		<th>深夜時間</th>
	</tr>

	<%for(int i = 0;i<attendances.size();i++){%>
		<tr align="center">
			<td><%out.print(attendances.get(i).getWorkDate()					== null ? "---" : attendances.get(i).getWorkDateToString()); %></td>
			<td><%out.print(attendances.get(i).getArrive()						== null ? "---" : attendances.get(i).eachTimeToString(attendances.get(i).getArrive())); %></td>
			<td><%out.print(attendances.get(i).getLeave()						== null ? "---" : attendances.get(i).eachTimeToString(attendances.get(i).getLeave())); %></td>
			<td><%out.print(attendances.get(i).getStartBreak()				== null ? "---" : attendances.get(i).eachTimeToString(attendances.get(i).getStartBreak())); %></td>
			<td><%out.print(attendances.get(i).getEndBreak()					== null ? "---" : attendances.get(i).eachTimeToString(attendances.get(i).getEndBreak())); %></td>
			<td><%out.print(attendances.get(i).getBreakTime()				== null ? "---" : attendances.get(i).eachDurationToString(attendances.get(i).getBreakTime()));%></td>
			<td><%out.print(attendances.get(i).getWorkTime()					== null ? "---" : attendances.get(i).eachDurationToString(attendances.get(i).getWorkTime()));%></td>
			<td><%out.print(attendances.get(i).getMidnightWorkTime()	== null ? "---" : attendances.get(i).eachDurationToString(attendances.get(i).getMidnightWorkTime()));%></td>
			<td>
				<form action="AttendanceUpdate.jsp" method="post" >
				<input type="hidden" name="i" value="<%=i %>"/>
				<input type="submit" value="編集" />
				</form>
			</td>
			<td>
				<form action="AttendanceDelete.jsp" method="post">
				<input type="hidden" name="i" value="<%=i %>"/>
				<input type="submit" value="削除" />
				</form>
			</td>
		</tr>
	<%}%>

<%} %>


</table>
</body>
</html>