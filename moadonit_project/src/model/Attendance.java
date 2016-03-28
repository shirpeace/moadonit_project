package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_attendance database table.
 * 
 */
@Entity
@Table(name="tbl_attendance")
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AttendancePK id;

	private int activityNum;

	@Column(name="`attended?`")
	private byte[] attended_;

	@Column(name="rec_type")
	private int recType;

	public Attendance() {
	}

	public AttendancePK getId() {
		return this.id;
	}

	public void setId(AttendancePK id) {
		this.id = id;
	}

	public int getActivityNum() {
		return this.activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public byte[] getAttended_() {
		return this.attended_;
	}

	public void setAttended_(byte[] attended_) {
		this.attended_ = attended_;
	}

	public int getRecType() {
		return this.recType;
	}

	public void setRecType(int recType) {
		this.recType = recType;
	}

}