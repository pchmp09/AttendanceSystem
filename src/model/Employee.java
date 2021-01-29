package model;

//従業員情報を格納するクラス
public class Employee {
	private String empId;
	private String name;
	private String tell;
	private String mail;
	private String address;
	private boolean manager = false;

	public Employee() {

	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}



	@Override
	public String toString() {
		return "employee [empId=" + empId + ", name=" + name + ", tell=" + tell + ", mail=" + mail + "]";
	}



}
