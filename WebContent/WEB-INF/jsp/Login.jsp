<%@page import="java.awt.print.Printable"%>
<%@page import="kintai.EmployeeDAO"%>
<%@page import="kintai.AttendanceSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");

	if (attendanceSession == null) {
		attendanceSession = new AttendanceSession();

		session.setAttribute("attendanceSession", attendanceSession);
	}
%>

<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
<div>
	<form action="./LoginCheckServlet" method="post">
		id入力<input type="text" name="id" value="00000000" size=20 maxlength="18" />
		<input type="submit" name="Logout" value="ログイン" />
	</form>
<br>
<br>
<a href="./MainServlet">メインページに戻る</a>

</div>
</body>
</html>