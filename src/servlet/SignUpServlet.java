package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kintai.Database;
import kintai.EmployeeDAO;
import kintai.ProcessErrorException;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public SignUpServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = "/ErrorPage.jsp";

		HttpSession session = request.getSession();
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");

		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();

			session.setAttribute("attendanceSession", attendanceSession);
		}

		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");

		boolean signOn = false;

		try (Database database = new Database();) {
			EmployeeDAO employeeDAO = database.getEmployeeDAO();

			signOn = employeeDAO.employeeCertify(id);

			if (signOn) {
				attendanceSession.setSignOn(id,employeeDAO.isManeger(id));
			} else {
				attendanceSession.setSignOff();
			}

			resultPage = "SignUp.jsp";

		} catch (ProcessErrorException e) {
			request.setAttribute("exception", e);
            resultPage = "/ErrorPage.jsp";
		} catch (Exception e) {
			request.setAttribute("exception", e);
            resultPage = "/ErrorPage.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);


	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
