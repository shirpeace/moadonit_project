package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_activity database table.
 * 
 */
@Entity
@Table(name="tbl_activity")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityNum;

	private String activityName;

	private int activityType;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="tblActivity")
	private List<Attendance> tblAttendances;

	public Activity() {
	}

	public int getActivityNum() {
		return this.activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getActivityType() {
		return this.activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public List<Attendance> getTblAttendances() {
		return this.tblAttendances;
	}

	public void setTblAttendances(List<Attendance> tblAttendances) {
		this.tblAttendances = tblAttendances;
	}

	public Attendance addTblAttendance(Attendance tblAttendance) {
		getTblAttendances().add(tblAttendance);
		tblAttendance.setTblActivity(this);

		return tblAttendance;
	}

	public Attendance removeTblAttendance(Attendance tblAttendance) {
		getTblAttendances().remove(tblAttendance);
		tblAttendance.setTblActivity(null);

		return tblAttendance;
	}

}