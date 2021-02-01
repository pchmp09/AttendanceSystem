<%@page import="model.AttendanceSession"%>
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
	}else if(attendanceSession.isManager() == false){
		response.sendRedirect("./ManagerMainServlet");
		return;
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
</head>
<body>
<h1>新規登録</h1>
<form action="./EmployeeRegistrationProcessServlet" method="post">
　 I　　　  D　<input type="text" name="id" size="20" maxlength="18"/><br>
　名　　　前　<input type="text" name="name" size="20" maxlength="18"/><br>
電　話　番　号<input type="text" name="tell" size="20" maxlength="18"/><br>
メールアドレス<input type="text" name="mail" size="20" maxlength="18"/><br>
　住　　　所　<input type="text" name="address" size="20" maxlength="100"/><br>
管理者として登録する<input type="checkbox" name ="manager" value="true"><br>
<input type="submit" value="登録"/>
</form>

</body>
</html>