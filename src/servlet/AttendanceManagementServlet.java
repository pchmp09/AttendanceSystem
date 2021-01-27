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

import model.Attendance;
import model.AttendanceDAO;
import model.AttendanceSession;
import model.AttendanceTable;
import model.Database;
import model.Employee;
import model.EmployeeDAO;


//データベースから勤怠情報を取得して画面に送るクラス
@WebServlet("/AttendanceManagementServlet")
public class AttendanceManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AttendanceManagementServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/AttendanceManagement.jsp";

		//sessionオブジェクトを取得する
		HttpSession session = request.getSession();

		//ログインに関わる情報を取得、無ければ作成してsessionオブジェクトに設定する
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");
		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		//従業員リストを取得する
		List<Employee> employees = (List<Employee>) session.getAttribute("employees");
		if (employees == null) {
			employees = new ArrayList<>();
		}

		//勤怠実績リストを取得する
		List<Attendance> attendances = (List<Attendance>) session.getAttribute("attendances");
		if (attendances == null) {
			attendances = new ArrayList<>();
		}

		//勤怠実績リストの指定従業員、指定年月情報を取得する
		AttendanceTable attendanceTable = (AttendanceTable) session.getAttribute("attendanceTable");
		if (attendanceTable == null) {
			attendanceTable = new AttendanceTable();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		request.setCharacterEncoding("UTF-8");

		String mode = (String) request.getParameter("mode");

		//パラメータによって処理を変えてデータを送る
		session.setAttribute("mode", mode);

		//データベース接続
		try (Database database = new Database();) {
			AttendanceDAO attendanceDAO = database.getAttendanceDAO();

			//パラメータがNULLのとき、従業員指定のための処理のみをする
			if (mode == null) {

				//データベースから全従業員のデータを取得し、sessionオブジェクトに設定する
				EmployeeDAO employeeDAO = database.getEmployeeDAO();
				employees = employeeDAO.getAllEmployee();
				session.setAttribute("employees", employees);

				//パラメータがlistのとき、指定された従業員の指定年、指定月の勤怠実績を取得し表示させる
			} else if (mode.equals("list")) {
				int empNum 	= Integer.parseInt(request.getParameter("empNum"));	//従業員の識別ID
				String year 		= (String) request.getParameter("year");		//指定年
				String month 	= String.format("%02d", Integer.parseInt(request.getParameter("month")));	//指定月

				//sessionオブジェクトに指定情報を設定する
				attendanceTable.setEmpNum(empNum);
				attendanceTable.setYear(year);
				attendanceTable.setMonth(month);
				session.setAttribute("attendanceTable", attendanceTable);

				//月別勤怠実績を取得しリスト化する
				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

				//パラメータがdeleteのとき、指定された勤怠実績を削除し、削除後の勤怠実績を表示させる
			} else if (mode.equals("delete")) {
				int i = (Integer) session.getAttribute("i");	//削除対象の勤怠実績リストのindex番号

				//勤怠実績の削除処理
				int i1 = attendanceDAO.deleteAttendance(attendances.get(i).getAttendanceId());

				//削除後改めて月別勤怠実績を取得しリスト化する
				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

				//パラメータがupdateのとき、指定された勤怠実績を修正し、修正後の勤怠実績を表示させる
			} else if (mode.equals("update")) {
				int i = (Integer) session.getAttribute("i");	//修正対象の勤怠実績リストのindex番号

				String[] stamp = { "workDate", "arrive", "leave", "startBreak", "endBreak" };

				//修正された勤怠実績のデータを取得
				String[] time = {
						(String) request.getParameter("workDate"),
						(String) request.getParameter("arrive"),
						(String) request.getParameter("leave"),
						(String) request.getParameter("startBreak"),
						(String) request.getParameter("endBreak") };

				//修正データを修正元に格納する※
				for (int j = 0; j < stamp.length; j++) {
					switch (stamp[j]) {
					case "workDate":
						if (!(time[j].equals(""))) {
							attendances.get(i).setWorkDate(time[j]);
						}
						break;

					case "arrive":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setArrive(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
						}
						break;

					case "leave":
						if (!(time[j].equals(""))) {
							attendances.get(i).setLeave(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
						}
						break;

					case "startBreak":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setStartBreak(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
						}
						break;

					case "endBreak":
						if (!(time[j].equals(""))) {
							attendances.get(i)
									.setEndBreak(LocalTime.parse(time[j], DateTimeFormatter.ofPattern("HH:mm")));
						}
						break;

					default:
						break;
					}

				}

				//勤怠実績の修正処理
				int i1 = attendanceDAO.updateAttendance(attendances.get(i));

				//修正後改めて月別勤怠実績を取得しリスト化する
				attendances = attendanceDAO.getAttendanceOfMonth(employees.get(attendanceTable.getEmpNum()).getEmpId(),
						attendanceTable.getYear(), attendanceTable.getMonth());

			}

		//sessionオブジェクトに勤怠実績リストを設定する
		session.setAttribute("attendances", attendances);

	}catch(ClassNotFoundException e){
		e.printStackTrace();
	}catch(SQLException e){
		e.printStackTrace();
	}

	RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);dispatcher.forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
