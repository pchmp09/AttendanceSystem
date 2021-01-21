package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/EmployeeManagementServlet")
public class EmployeeManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeManagementServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "EmployeeManagement.jsp";

		HttpSession session = request.getSession();
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
		
		request.setCharacterEncoding("UTF-8");

		String mode = (String)request.getParameter("mode");






		try (Database database = new Database();) {
			EmployeeDAO employeeDAO = database.getEmployeeDAO();
			if(mode == null || mode.equals("list")) {


			}else if(mode.equals("delete")) {
				int i = (Integer)session.getAttribute("i");
				int i1 = employeeDAO.deleteEmployee(employees.get(i).getEmpId());


			}else if(mode.equals("update")) {
				int i = (Integer)session.getAttribute("i");
				Employee employee = new Employee();
				employee.setEmpId((String)request.getParameter("empId"));
				employee.setName((String)request.getParameter("name"));
				employee.setTell((String)request.getParameter("tell"));
				employee.setMail((String)request.getParameter("mail"));
				employee.setAddress((String)request.getParameter("address"));
				employee.setManager(Boolean.parseBoolean(request.getParameter("manager")));
				int i1 = employeeDAO.updateEmployee(employee, employees.get(i).getEmpId());
			}

			employees = employeeDAO.getAllEmployee();
			session.setAttribute("employees", employees);




		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
