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
import kintai.Database;

@WebServlet("/StampingServlet")
public class StampingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public StampingServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = "Stamping.jsp";

		HttpSession session = request.getSession();
		AttendanceSession attendanceSession =
				(AttendanceSession) session.getAttribute("attendanceSession");

		if (attendanceSession == null) {
			attendanceSession = new AttendanceSession();
			session.setAttribute("attendanceSession", attendanceSession);
		}

		Attendance attendance = (Attendance) session.getAttribute("attendance");
		if(attendance == null){
			attendance = new Attendance();
			session.setAttribute("attendance", attendance);
		}

		request.setCharacterEncoding("UTF-8");
		String stamp = request.getParameter("stamp");

		boolean success = false;
		int i1 = 0;

		try(Database database = new Database();){
			AttendanceDAO attendanceDAO = database.getAttendanceDAO();

			if(stamp.equals("arrival_time")) {
				success = attendanceDAO.setArrival(attendanceSession);
				attendance = attendanceDAO.getMostRecentAttendance(attendanceSession.getId());
				session.setAttribute("attendance", attendance);
			}else {
				i1 = attendanceDAO.setTime(attendanceSession, stamp);
				attendance = attendanceDAO.getMostRecentAttendance(attendanceSession.getId());
				session.setAttribute("attendance", attendance);
			}

		} catch (Exception e) {
			System.out.println(e);
		}


		session.setAttribute("success", success);
		session.setAttribute("i1", i1);

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
