package kintai;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable{
	private Connection con;
	private EmployeeDAO employeeDAO;
	private AttendanceDAO attendanceDAO;

	public Database() throws ClassNotFoundException, SQLException {
		super();


		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_33b15a10d15b2e9?characterEncoding=UTF-8" + 
						"&serverTimezone=JST&zeroDateTimeBehavior=convertToNull" ,
				"b25b85cdf3eb64","6ce92d7e");
	}

	public EmployeeDAO getEmployeeDAO() {
		if(employeeDAO == null) {
			employeeDAO = new EmployeeDAO(this);
		}
		return employeeDAO;
	}

	public AttendanceDAO getAttendanceDAO() {
		if(attendanceDAO == null) {
			attendanceDAO = new AttendanceDAO(this);
		}
		return attendanceDAO;
	}

	public Connection getCon() {
		return con;
	}

	@Override
	public void close() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}

}
