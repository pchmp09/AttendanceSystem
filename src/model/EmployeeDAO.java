package kintai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO {
	private Connection con;

	public EmployeeDAO(Database database) {
		this.con = database.getCon();
	}

	public boolean employeeCertify(String id) throws SQLException {
		if(id == null )
			return false;

		String sql = "select emp_id from employee where emp_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, id);

		ResultSet rs = stmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = true;
		}else {
			result = false;
		}
		rs.close();
		stmt.close();
		return result;
	}

	public boolean isManeger(String id) throws SQLException {
		String sql = "select manager from employee where emp_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, id);

		ResultSet rs = stmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getBoolean("manager");
		}else {
			result = false;
		}

		rs.close();
		stmt.close();
		return result;
	}

	public List<Employee> getAllEmployee() throws SQLException{
		String sql = "select emp_id,nama,tell,mail,address from employee";

		PreparedStatement stmt = con.prepareStatement(sql);
		List<Employee> list = queryAndMakeList(stmt);
		stmt.close();
		return list;

	}


	private List<Employee> queryAndMakeList(PreparedStatement stmt) throws SQLException{
		ResultSet rs = stmt.executeQuery();
		List<Employee> list = new ArrayList<>();
		while(rs.next()){
			Employee result = makeInstanceFromRow(rs);
			list.add(result);
		}
		rs.close();
		return list;
	}

	private Employee makeInstanceFromRow(ResultSet rs) throws SQLException {
		Employee result = new Employee();
		result.setEmpId(rs.getString("emp_id"));
		result.setName(rs.getString("nama"));
		result.setTell(rs.getString("tell"));
		result.setMail(rs.getString("mail"));
		result.setAddress(rs.getString("address"));
		return result;
	}

	public boolean addEmployee(Employee emp) throws SQLException {
		if(emp.getEmpId() == null)
			return false;

		String sql = "insert into employee(emp_id, nama, tell, mail, address, manager) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, emp.getEmpId());
			stmt.setString(2, emp.getName());
			stmt.setString(3, emp.getTell());
			stmt.setString(4, emp.getMail());
			stmt.setString(5, emp.getAddress());
			stmt.setBoolean(6, emp.isManager());

			stmt.executeUpdate();

			return true;
		}catch(Exception e) {
			if(e instanceof SQLException  &&
	                 ((SQLException)e).getSQLState() != null &&
	                 ((SQLException)e).getSQLState().equals("23000") ) {
	                return false;
	            } else {
	                throw e;
	            }
	        } finally {
	            if(stmt != null) stmt.close();
	        }
	    }

	 public int deleteEmployee(String id) throws SQLException {
	        String sql = "delete from employee where emp_id = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1,id);
	        int i1 = stmt.executeUpdate();
	        stmt.close();
	        return i1;
	    }

	 public int updateEmployee(Employee emp,String id) throws SQLException {
		 String sql = "update employee set emp_id = ?, nama = ?, tell = ?,"
		 		+ " mail = ?, address = ?, manager = ? where emp_id = ?";

		 PreparedStatement stmt = con.prepareStatement(sql);
		 stmt.setString(1, emp.getEmpId());
		 stmt.setString(2, emp.getName());
		 stmt.setString(3, emp.getTell());
		 stmt.setString(4, emp.getMail());
		 stmt.setString(5, emp.getAddress());
		 stmt.setBoolean(6, emp.isManager());
		 stmt.setString(7, id);

		 int i1 = stmt.executeUpdate();
	        stmt.close();
	        return i1;
	 }

}
