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

import model.AttendanceSession;
import model.Database;
import model.Employee;
import model.EmployeeDAO;

//データベースから従業員情報を取得して画面に送るクラス
@WebServlet("/EmployeeManagementServlet")
public class EmployeeManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeManagementServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/EmployeeManagement.jsp";

		//sessionオブジェクトを取得する
		HttpSession session = request.getSession();

		//ログインに関わる情報を取得、無ければ作成してsessionオブジェクトに格納する
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");
		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		//従業員リストを取得し、sessionオブジェクトに格納する
		List<Employee> employees = (List<Employee>)session.getAttribute("employees");
		if (employees == null) {
			employees = new ArrayList<Employee>();
			session.setAttribute("employees", employees);
		}

		request.setCharacterEncoding("UTF-8");
		//パラメータによって処理を変えてデータを送る
		String mode = (String)request.getParameter("mode");

		//データベース接続
		try (Database database = new Database();) {
			EmployeeDAO employeeDAO = database.getEmployeeDAO();

			//パラメータがNULLの時には何も処理せず、従業員一覧を表示させる
			if(mode == null) {

				//パラメータがdeleteのときは従業員情報を削除する
			}else if(mode.equals("delete")) {
				int i = (Integer)session.getAttribute("i");		//従業員リストの削除対象のindex番号

				//従業員削除処理
				int i1 = employeeDAO.deleteEmployee(employees.get(i).getEmpId());

				//パラメータがupdateのとき、従業員情報を更新する
			}else if(mode.equals("update")) {
				int i = (Integer)session.getAttribute("i");		//従業員リストの削除対象のindex番号

				//インスタンスを生成して更新情報を格納する
				Employee employee = new Employee();
				employee.setEmpId((String)request.getParameter("id"));
				employee.setName((String)request.getParameter("name"));
				employee.setTell((String)request.getParameter("tell"));
				employee.setMail((String)request.getParameter("mail"));
				employee.setAddress((String)request.getParameter("address"));
				employee.setManager(Boolean.parseBoolean(request.getParameter("manager")));

				//従業員情報更新処理
				int i1 = employeeDAO.updateEmployee(employee, employees.get(i).getEmpId());
			}

			//全従業員情報を取得し、sessionオブジェクトに格納する
			employees = employeeDAO.getAllEmployee();
			session.setAttribute("employees", employees);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
