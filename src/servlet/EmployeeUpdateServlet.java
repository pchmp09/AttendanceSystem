package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kintai.AttendanceSession;

@WebServlet("/EmployeeUpdateServlet")
public class EmployeeUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/EmployeeUpdate.jsp";

		HttpSession session = request.getSession();
		AttendanceSession attendanceSession =
				(AttendanceSession) session.getAttribute("attendanceSession");

		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		request.setCharacterEncoding("UTF-8");
		int i = Integer.parseInt(request.getParameter("i"));
		session.setAttribute("i", i);


		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}






}
