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
int i = Integer.parseInt(request.getParameter("i"));
session.setAttribute("i", i);

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
<title>従業員修正</title>
</head>
<body>
<%@ include file="header.jsp"%>

<form action="EmployeeManagementServlet?mode=update" method="post">
<table>
	<tr>
		<th>ID</th>
		<th>名前</th>
		<th>電話番号</th>
		<th>メール</th>
		<th>住所</th>
		<th>管理者</th>
	</tr>
	<tr>
		<th><input type="text" name="empId" size="20" maxlength="18" value="<%=employees.get(i).getEmpId() %>"/></th>
		<th><input type="text" name="name" size="20" maxlength="18" value="<%=employees.get(i).getName() %>"/></th>
		<th><input type="text" name="tell" size="20" maxlength="18" value="<%=employees.get(i).getTell() %>"/></th>
		<th><input type="text" name="mail" size="20" maxlength="18" value="<%=employees.get(i).getMail() %>"/></th>
		<th><input type="text" name="address" size="20" maxlength="100" value="<%=employees.get(i).getAddress() %>"/></th>
		<th><input type="radio" name ="manager" value="true"></th>
	</tr>

</table>
<br>
<input type="submit" value="完了">
</form>

</body>
</html>