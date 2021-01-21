package servlet;

import kintai.ProcessErrorException;

public class AttendanceSession {
	private String id;
	private boolean manager = false;

	public AttendanceSession() {

	}

	public String getId() {
		return id;
	}

	public boolean isManager() {
		return manager;
	}

	public boolean isSignOn() {
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}

	public void setSignOff() {
		id = null;
		manager = false;
	}

	public void setSignOn(String id, boolean manager) throws ProcessErrorException {
		if (id == null) {
			throw new ProcessErrorException("error");
		} else {
			this.id = id;
			this.manager = manager;
		}
	}

}
