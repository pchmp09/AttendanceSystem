<%@page import="java.time.LocalDateTime"%>
<%@page import="kintai.Attendance"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
Attendance attendance = new Attendance("0001");
LocalDateTime arrive = LocalDateTime.of(2020,12,01,22,00);
LocalDateTime leave = LocalDateTime.of(2020,12,02,6,30);
LocalDateTime startBreak = LocalDateTime.of(2020,12,02,2,00);
LocalDateTime endBreak = LocalDateTime.of(2020,12,02,3,00);



attendance.setArrive(arrive);
attendance.setLeave(leave);
attendance.setStartBreak(startBreak);
attendance.setEndBreak(endBreak);

attendance.calcBreakTime();
attendance.calcWorkTime();
attendance.calcMidnightWorkTime();


%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% out.println(attendance);%>
</body>
</html>