package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kintai.Attendance;
import kintai.AttendanceDAO;
import kintai.AttendanceTable;
import kintai.Database;
import kintai.Employee;
import kintai.EmployeeDAO;

@WebServlet("/AttendanceManagementServlet")
public class AttendanceManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AttendanceManagementServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/AttendanceManagement.jsp";

		HttpSession session = request.getSession();
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");

		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		List<Employee> employees = (List<Employee>) session.getAttribute("employees");
		if (employees == null) {
			employees = new ArrayList<>();
			session.setAttribute("employees", employees);
		}

		List<Attendance> attendances = (List<Attendance>) session.getAttribute("attendances");
		if (attendances == null) {
			attendances = new ArrayList<>();
			session.setAttribute("attendances", attendances);
		}

		AttendanceTable attendanceTable = (AttendanceTable) session.getAttribute("attendanceTable");
		if (attendanceTable == null) {
			attendanceTable = new AttendanceTable();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		request.setCharacterEncoding("UTF-8");

		String mode = (String) request.getParameter("mode");
		session.setAttribute("mode", mode);

		try (Database database = new Database();) {
			EmployeeDAO employeeDAO = database.getEmployeeDAO();
			AttendanceDAO attendanceDAO = database.getAttendanceDAO();
			if (mode == null) {

			} else if (mode.equals("list")) {
				int empNum = Integer.parseInt(request.getParameter("empNum"));
				String year = (String) request.getParameter("year");
				String month = String.format("%02d", Integer.parseInt(request.getParameter("month")));

				attendanceTable.setEmpNum(empNum);
				attendanceTable.setYear(year);
				attendanceTable.setMonth(month);

				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

			} else if (mode.equals("delete")) {
				int i = (Integer) session.getAttribute("i");
				int i1 = attendanceDAO.deleteAttendance(attendances.get(i).getAttendanceId());

				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

			} else if (mode.equals("update")) {
				int i = (Integer) session.getAttribute("i");

				String[] stamp = { "workDate", "arrive", "leave", "startBreak", "endBreak" };

				String[] time = {
						(String) request.getParameter("workDate"),
						(String) request.getParameter("arrive"),
						(String) request.getParameter("leave"),
						(String) request.getParameter("startBreak"),
						(String) request.getParameter("endBreak") };

				for (int j = 0; j < stamp.length; j++) {
					switch (stamp[j]) {
					case "workDate":
						if (!(time[j].equals(""))) {
							attendances.get(i).setWorkDate(time[j]);
							break;
						}
						break;

					case "arrive":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setArrive(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
							break;
						}
						break;

					case "leave":
						if (!(time[j].equals(""))) {
							attendances.get(i).setLeave(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
							break;
						}
						break;

					case "startBreak":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setStartBreak(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
							break;
						}
						break;

					case "endBreak":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setEndBreak(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
							break;
						}
						break;

					default:
						break;
					}

				}

				int i1 = attendanceDAO.updateAttendance(attendances.get(i));

				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

			}



		employees = employeeDAO.getAllEmployee();
		session.setAttribute("employees", employees);
		session.setAttribute("attendances", attendances);
		session.setAttribute("attendanceTable", attendanceTable);

	}catch(

	ClassNotFoundException e)
	{
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}catch(
	SQLException e)
	{
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}

	RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);dispatcher.forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
