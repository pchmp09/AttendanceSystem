<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.sendRedirect("MainServlet");
	return;
	%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TestApp</title>
</head>
<body>
    <div>
        <h1>ようこそ ゲスト さん</h1>
        <a href="./LoginServlet">ログインページへ＞＞</a>
    </div>
</body>
</html>