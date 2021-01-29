package kintai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//勤怠データベースと繋ぐDAOクラス
public class AttendanceDAO {
	private Connection con;

	public AttendanceDAO(Database database) {
		this.con = database.getCon();
	}

	//勤怠情報のリストを作成する処理
	private List<Attendance> queryAndMakeList(PreparedStatement stmt) throws SQLException {
		ResultSet rs 		= stmt.executeQuery();
		List<Attendance> list 	= new ArrayList<>();

		while (rs.next()) {
			Attendance result = makeInstanceFromRow(rs);
			list.add(result);
		}
		rs.close();
		return list;
	}

	//データベースから勤怠情報を取得する処理
	private Attendance makeInstanceFromRow(ResultSet rs) throws SQLException {
		Attendance result = new Attendance();

		String[] stamp = new String[] { "leaving_time", "start_break", "end_break" };

		result.setAttendanceId(rs.getInt("attendance_id"));
		result.setEmpId(rs.getString("emp_id"));
		result.setWorkDate(dateToString(rs.getDate("work_date")));
		result.setArrive(timestampToString(rs.getTimestamp("arrival_time")));

		//退勤、休憩開始、休憩終了でNULLがあっても処理を中断しないようにcatch節をループの中に入れる
		for (int i = 0; i < stamp.length; i++) {
			try {
				switch (stamp[i]) {
				case "leaving_time":
					result.setLeave(timestampToString(rs.getTimestamp(stamp[i])));
					break;

				case "start_break":
					result.setStartBreak(timestampToString(rs.getTimestamp(stamp[i])));
					break;

				case "end_break":
					result.setEndBreak(timestampToString(rs.getTimestamp(stamp[i])));
					break;
				}
			} catch (NullPointerException e) {
			}
		}

		result.calcBreakTime();
		result.calcWorkTime();
		result.calcMidnightWorkTime();

		return result;
	}

	//Date型をString型に変換する処理
	private String dateToString(Date date) {
		return date.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	//TimestampをLocalDateTime型に合わせたString型に変換する処理
	private String timestampToString(Timestamp ts) {
		return ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	//出勤日と出勤時刻をデータベースに格納する処理
	public boolean setArrival(AttendanceSession session) throws SQLException {
		if (session.getId() == null)
			return false;

		String sql = "insert into attendance (emp_id,work_date,arrival_time) values (?,?,?)";
		PreparedStatement stmt = null;

		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, session.getId());
			stmt.setString(2, now);
			stmt.setString(3, now);

			stmt.executeUpdate();

			return true;

		} catch (Exception e) {
			if (e instanceof SQLException &&
					((SQLException) e).getSQLState() != null &&
					((SQLException) e).getSQLState().equals("23000")) {
				return false;
			} else {
				throw e;
			}
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	//退勤時刻、休憩時刻をデータベースに格納する処理
	public int setTime(AttendanceSession attendanceSession, String stamp) throws SQLException {
		if (attendanceSession.getId() == null)
			return 0;

		//勤怠管理テーブル内に格納された直近のレコードにアップデートする
		String sql = "update attendance set " + stamp + "= ? where emp_id = ? order by work_date desc limit 1";

		//現在時刻を取得しフォーマットする
		LocalDateTime nowDateTime 	= LocalDateTime.now();
		DateTimeFormatter formatter 	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = nowDateTime.format(formatter);

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, now);
		stmt.setString(2, attendanceSession.getId());

		int i1 = stmt.executeUpdate();
		stmt.close();
		return i1;

	}

	//勤怠管理テーブル内に格納された直近のレコードを取得する処理
	public Attendance getMostRecentAttendance(String id) throws SQLException {
		Attendance result = null;

		String sql = "select * from attendance where emp_id = ? order by work_date desc limit 1";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, id);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			result = makeInstanceFromRow(rs);
		}
		rs.close();
		stmt.close();
		return result;

	}

	public List<Attendance> getAllAttendance() throws SQLException{
		String sql = "select * from attendance";

		PreparedStatement stmt = con.prepareStatement(sql);
		List<Attendance> list = queryAndMakeList(stmt);
		stmt.close();
		return list;

	}

	//月ごとの勤怠実績を取得する処理
	public List<Attendance> getAttendanceOfMonth(String id, String year, String month) throws SQLException{
		String sql = "select * from attendance where emp_id = ? && work_date like ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, id);
		stmt.setString(2, year + "-" + month + "%");

		List<Attendance> list = queryAndMakeList(stmt);

		stmt.close();
		return list;

	}


	//勤怠実績を削除する処理
	public int deleteAttendance(int attendanceId) throws SQLException {
		String sql = "delete from attendance where attendance_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1,attendanceId);
		int i1 = stmt.executeUpdate();
		stmt.close();
       	 	return i1;
    	}

	//勤怠実績を修正する処理
	public int updateAttendance(Attendance attendance) throws SQLException {
	 String sql = "update attendance set work_date = ?, arrival_time = ?, leaving_time = ?,"
	 		+ " start_break = ?, end_break = ? where attendance_Id = ?" ;

	 PreparedStatement stmt = con.prepareStatement(sql);
	 stmt.setString(1, attendance.getWorkDateToString());
	 stmt.setString(2, attendance.eachDateTimeToString(attendance.getArrive()));
	 stmt.setString(3, attendance.eachDateTimeToString(attendance.getLeave()));
	 stmt.setString(4, attendance.eachDateTimeToString(attendance.getStartBreak()));
	 stmt.setString(5, attendance.eachDateTimeToString(attendance.getEndBreak()));
	 stmt.setInt(6, attendance.getAttendanceId());


	 int i1 = stmt.executeUpdate();
        stmt.close();
        return i1;
 }

}