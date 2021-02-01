<%@page import="java.awt.print.Printable"%>
<%@page import="model.EmployeeDAO"%>
<%@page import="model.AttendanceSession"%>
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
<h1>ログインページ</h1>
<div>
	<form action="./LoginCheckServlet" method="post">
		id入力<input type="text" name="id" size=20 maxlength="18" autocomplete="off"/>
		<input type="submit" name="Logout" value="ログイン" />
	</form>
<br>
<br>
<a href="./MainServlet">トップページに戻る</a>

</div>
</body>
</html>