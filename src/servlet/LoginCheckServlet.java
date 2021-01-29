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
import kintai.Database;
import kintai.EmployeeDAO;
import kintai.ProcessErrorException;

//ログインの可否を判定し、結果を送るクラス
@WebServlet("/LoginCheckServlet")
public class LoginCheckServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LoginCheckServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/LoginCheck.jsp";

		//sessionオブジェクトを取得
		HttpSession session = request.getSession();

		//ログインに関わる情報を取得、無ければインスタンスを作成してsessionオブジェクトに設定する
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");
		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		//ログイン判別対象のIDを取得する
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");

		//データベース接続
		try (Database database = new Database();) {
			EmployeeDAO employeeDAO = database.getEmployeeDAO();

			boolean signOn = false;		//ログイン判定用の変数

			//データベースに引数で与えたIDと同一のものがあればtrue
			signOn = employeeDAO.employeeCertify(id);

			if (signOn) {
				//IDと管理者権限の有無をsessionオブジェクトに設定し、ログイン状態を保持する
				attendanceSession.setSignOn(id,employeeDAO.isManeger(id));
			} else {
				attendanceSession.setSignOff();
			}

		} catch (ProcessErrorException e) {
			request.setAttribute("exception", e);
		} catch (Exception e) {
			request.setAttribute("exception", e);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);


	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
