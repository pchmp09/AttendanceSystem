package kintai;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
	private int attendanceId;
	private String empId;
	private LocalDate workDate = null;
	private LocalDateTime arrive = null;
	private LocalDateTime leave = null;
	private LocalDateTime startBreak = null;
	private LocalDateTime endBreak = null;
	private Duration breakTime;
	private Duration workTime;
	private Duration midnightWorkTime;

	public Attendance() {

	}

	public Attendance(String empId) {
		this.empId = empId;
	}

	public int getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public LocalDate getWorkDate() {
		return workDate;
	}

	public String getWorkDateToString() {
		return workDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public void setWorkDate(String workDate) {
		this.workDate = LocalDate.parse(workDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public LocalDateTime getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = LocalDateTime.parse(arrive, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setArrive(LocalTime arrive) {
		this.arrive = this.arrive.with(arrive);
	}

	public LocalDateTime getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = LocalDateTime.parse(leave, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setLeave(LocalTime leave) {
		this.leave = this.leave.with(leave);
	}

	public LocalDateTime getStartBreak() {
		return startBreak;
	}

	public void setStartBreak(String startBreak) {
		this.startBreak = LocalDateTime.parse(startBreak, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setStartBreak(LocalTime startBreak) {
		this.startBreak = this.startBreak.with(startBreak);
	}

	public LocalDateTime getEndBreak() {
		return endBreak;
	}

	public void setEndBreak(String endBreak) {
		this.endBreak = LocalDateTime.parse(endBreak, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setEndBreak(LocalTime endBreak) {
		this.endBreak = this.endBreak.with(endBreak);
	}

	public String eachTimeToString(LocalDateTime ldt) {
		if(ldt == null)
			return null;
		return ldt.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
	
	public String eachDateTimeToString(LocalDateTime ldt) {
		if(ldt == null)
			return null;
		return ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public Duration getBreakTime() {
		return breakTime;
	}

	public Duration getWorkTime() {
		return workTime;
	}

	public Duration getMidnightWorkTime() {
		return midnightWorkTime;
	}
	
	public String eachDurationToString(Duration duration) {
		return DateTimeFormatter.ofPattern("HH時間mm分").format(LocalTime.MIDNIGHT.plus(duration));
	}

	public void calcBreakTime() {
		if (startBreak != null && endBreak != null) {
			breakTime = Duration.between(startBreak, endBreak);
		}
	}

	public void calcWorkTime() {
		if (arrive != null && leave != null) {
			workTime = Duration.between(arrive, leave);
			if (breakTime != null) {
				workTime = workTime.minus(breakTime);
			}
		}
	}

	public void calcMidnightWorkTime() {
		if (arrive != null && leave != null && breakTime != null) {

			if(workTime == null)
				calcWorkTime();


			LocalDateTime startMidnight = arrive.with(LocalTime.of(23, 0));
			midnightWorkTime = workTime.minus(Duration.between(arrive, startMidnight));

			LocalDateTime endMidnight = leave.with(LocalTime.of(5, 0));
			midnightWorkTime = midnightWorkTime.minus(Duration.between(endMidnight, leave));

		}

	}

	@Override
	public String toString() {
		return "Attendance [empId=" + empId + ", workDate=" + workDate + ", arrive=" + arrive + ", leave=" + leave
				+ ", startBreak=" + startBreak + ", endBreak=" + endBreak + ", breakTime=" + breakTime + ", workTime="
				+ workTime + ", midnightWorkTime=" + midnightWorkTime + "]";
	}

}
