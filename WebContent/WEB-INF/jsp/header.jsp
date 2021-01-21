<%@page import="servlet.AttendanceSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<header>
    <a href ="MainServlet"><img src="common/img/kintaisisutemu.png"></a>
    <nav class="gnav">
        <ul class="menu">
            <li>
            <br>
            <%if(attendanceSession.isSignOn()){
            	out.print("ログイン中：" + attendanceSession.getId());
          	%>
          	<form action="MainServlet" method="post">
          		<input type="submit" name="logout" value="ログアウト">
          	</form>
            <%}else{ %>
            	<form action="SignUpServlet" method="post">
					id入力<input type="text" name="id" value="00000000" size=8 maxlength="8" />
					<input type="submit"  value="ログイン" />
				</form>
				<%} %>
			</li>
            <li>
            	<table>
            		<tr>
            				<td>
            					<a href="StampingServlet?stamp=arrival_time">出勤</a><br>
            					<br>
            					<a href="StampingServlet?stamp=leaving_time">退勤</a><br>
            				<td>
            		</tr>
            	</table>
            </li>
            <li>
            	<table>
            		<tr>
            				<td>
            					<a href="StampingServlet?stamp=start_break">休憩開始</a><br>
            					<br>
            					<a href="StampingServlet?stamp=end_break">休憩終了</a><br>
            				<td>
            		</tr>
            	</table>
            </li>
            <li>
            	<br>
            	<a href="ManagerMainServlet">管理者用メニュー</a>
            </li>
        </ul>
    </nav>
</header>
