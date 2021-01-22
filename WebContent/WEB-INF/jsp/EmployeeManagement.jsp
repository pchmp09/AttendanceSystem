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
<title>従業員管理</title>
</head>
<body>
<%@ include file="header.jsp"%>

<h1>従業員一覧</h1>

<table border="1" >
	<tr>
		<th>I　　D</th>
		<th>名　　前</th>
		<th>電話番号</th>
		<th>メ  ー  ル</th>
		<th>住　　所</th>
	</tr>
<%for(int i = 0;i<employees.size();i++){%>
	<tr align="center">
		<td><%=employees.get(i).getEmpId() %></td>
		<td><%=employees.get(i).getName() %></td>
		<td><%=employees.get(i).getTell() %></td>
		<td><%=employees.get(i).getMail() %></td>
		<td><%=employees.get(i).getAddress() %></td>
		<td>
			<form action="./EmployeeUpdateServlet" method="post" >
			<input type="hidden" name="i" value="<%=i %>"/>
			<input type="submit" value="編集" />
			</form>
		</td>
		<td>
			<form action="./EmployeeDeleteServlet" method="post">
			<input type="hidden" name="i" value="<%=i %>"/>
			<input type="submit" value="削除" />
			</form>
		</td>
	</tr>
<%}%>
</table>
<br>
<form action="EmployeeEntryServlet" method ="post">
<input type="submit" value="新規登録">
</form>




</body>
</html>