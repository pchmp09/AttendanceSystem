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
import kintai.Employee;
import kintai.EmployeeDAO;

@WebServlet("/EmployeeRegistrationProcessServlet")
public class EmployeeRegistrationProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeRegistrationProcessServlet() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp//EmployeeRegistrationProcess.jsp";

		HttpSession session = request.getSession();
		AttendanceSession attendanceSession =
				(AttendanceSession) session.getAttribute("attendanceSession");

		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String tell = request.getParameter("tell");
		String mail = request.getParameter("mail");
		String address = request.getParameter("address");
		boolean manager = Boolean.parseBoolean(request.getParameter("manager"));

		boolean add_success = false;
		Employee employee = null;
		try(Database database = new Database();){
			EmployeeDAO employeeDAO = database.getEmployeeDAO();

			employee = new Employee();
			employee.setEmpId(id);
			employee.setName(name);
			employee.setTell(tell);
			employee.setMail(mail);
			employee.setAddress(address);
			employee.setManager(manager);

			add_success = employeeDAO.addEmployee(employee);


			request.setAttribute("employee", employee);
			request.setAttribute("add_success", add_success);
			resultPage = "WEB-INF/jsp/EmployeeRegistrationProcess.jsp";

		 } catch (Exception e) {
	            request.setAttribute("exception", e);
	            resultPage = "WEB-INF/jsp/ErrorPage.jsp";
	        }

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
