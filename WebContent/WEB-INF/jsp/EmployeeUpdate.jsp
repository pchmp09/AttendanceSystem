<%@page import="java.util.ArrayList"%>
<%@page import="kintai.Employee"%>
<%@page import="java.util.List"%>
<%@page import="kintai.AttendanceSession"%>
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
<title>従業員修正</title>
</head>
<body>
<%@ include file="header.jsp"%>

<form action="./EmployeeManagementServlet?mode=update" method="post">
　 I　　　  D　<input type="text" name="id" size="20" maxlength="18" value="<%=employees.get(i).getEmpId() %>"/><br>
　名　　　前　<input type="text" name="name" size="20" maxlength="18" value="<%=employees.get(i).getName() %>"/><br>
電　話　番　号<input type="text" name="tell" size="20" maxlength="18" value="<%=employees.get(i).getTell() %>"/><br>
メールアドレス<input type="text" name="mail" size="20" maxlength="18" value="<%=employees.get(i).getMail() %>"/><br>
　住　　　所　<input type="text" name="address" size="20" maxlength="100" value="<%=employees.get(i).getAddress() %>"/><br>
管理者として登録する<input type="checkbox" name ="manager" value="true"><br>
<br>
<input type="submit" value="完了">
</form>

</body>
</html>