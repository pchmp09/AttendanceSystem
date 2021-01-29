package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DAOクラスとデータベースを繋ぐクラス
public class Database implements AutoCloseable{
	private Connection con;
	private EmployeeDAO employeeDAO;
	private AttendanceDAO attendanceDAO;

	public Database() throws ClassNotFoundException, SQLException {
		super();


		Class.forName("com.mysql.cj.jdbc.Driver");

		//Timestamp型のカラムでのSQLExceptionを回避するためzeroDateTimeBehavior=convertToNullを記述
		con = DriverManager.getConnection(
				"jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_33b15a10d15b2e9?characterEncoding=UTF-8" +
						"&serverTimezone=JST&zeroDateTimeBehavior=convertToNull" ,
				"b25b85cdf3eb64","6ce92d7e");
	}

	//EmployeeDAOのインスタンスを生成しデータベースと繋ぐ
	public EmployeeDAO getEmployeeDAO() {
		if(employeeDAO == null) {
			employeeDAO = new EmployeeDAO(this);
		}
		return employeeDAO;
	}

	//AttendanceDAOのインスタンスを生成しデータベースと繋ぐ
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
