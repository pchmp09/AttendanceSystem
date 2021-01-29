package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kintai.Attendance;
import kintai.AttendanceDAO;
import kintai.AttendanceSession;
import kintai.Database;

//打刻処理を行い、結果を送るクラス
@WebServlet("/StampingServlet")
public class StampingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public StampingServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "WEB-INF/jsp/Stamping.jsp";

		//sessionオブジェクトを取得する
		HttpSession session = request.getSession();

		//ログインに関わる情報を取得、無ければインスタンスを作成してsessionオブジェクトに設定する
		AttendanceSession attendanceSession = (AttendanceSession) session.getAttribute("attendanceSession");
		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		//attendanceオブジェクトを取得し、sessionオブジェクトに設定する
		Attendance attendance = (Attendance) session.getAttribute("attendance");
		if(attendance == null){
			attendance = new Attendance();
			session.setAttribute("attendance", attendance);
		}

		//打刻する分類のパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String stamp = request.getParameter("stamp");

		boolean success = false;


		try(Database database = new Database();){
			AttendanceDAO attendanceDAO = database.getAttendanceDAO();

			//パラメータがarrival_timeのとき、出勤日と出勤時刻をデータベースに新しく格納する
			if(stamp.equals("arrival_time")) {
				success = attendanceDAO.setArrival(attendanceSession);

				//パラメータがarrival_time以外のとき、対応する分類の時刻をデータベースに格納する
			}else {
				int i1 = attendanceDAO.setTime(attendanceSession, stamp);
			}

			//打刻した勤怠情報を取得し、sessionオブジェクトに設定する
			attendance = attendanceDAO.getMostRecentAttendance(attendanceSession.getId());
			session.setAttribute("attendance", attendance);

		} catch (Exception e) {
			System.out.println(e);
		}


		session.setAttribute("success", success);

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
