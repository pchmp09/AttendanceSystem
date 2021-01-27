package model;

//ログインに関わる情報を格納するクラス
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

	//ログイン状態か否かを判定して結果を返す処理
	public boolean isSignOn() {
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}

	//未ログイン状態にする処理
	public void setSignOff() {
		id = null;
		manager = false;
	}

	//IDと管理者権限情報をセットし、ログイン状態にする処理
	public void setSignOn(String id, boolean manager) throws ProcessErrorException {
			this.id = id;
			this.manager = manager;
		}
	}