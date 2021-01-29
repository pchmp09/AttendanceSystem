package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttendanceSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LoginServlet() {

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/Login.jsp";



		HttpSession session = request.getSession();
		AttendanceSession attendanceSession =
				(AttendanceSession)session.getAttribute("attendanceSession");

		if(attendanceSession == null) {
			attendanceSession = new AttendanceSession();

			session.setAttribute("attendanceSession", attendanceSession);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
    }

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


	}

