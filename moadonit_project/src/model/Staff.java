package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_staff database table.
 * 
 */
@Entity
@Table(name="tbl_staff")
@NamedQuery(name="Staff.findAll", query="SELECT s FROM Staff s")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int staffID;

	private String cellphone;

	private String email;

	private String firstName;

	private String lastName;

	//bi-directional many-to-one association to Activity
	@OneToMany(mappedBy="tblStaff")
	private List<Activity> tblActivities;

	//bi-directional one-to-one association to Teacher
	@OneToOne(mappedBy="tblStaff")
	private Teacher tblTeacher;

	public Staff() {
	}

	public int getStaffID() {
		return this.staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Activity> getTblActivities() {
		return this.tblActivities;
	}

	public void setTblActivities(List<Activity> tblActivities) {
		this.tblActivities = tblActivities;
	}

	public Activity addTblActivity(Activity tblActivity) {
		getTblActivities().add(tblActivity);
		tblActivity.setTblStaff(this);

		return tblActivity;
	}

	public Activity removeTblActivity(Activity tblActivity) {
		getTblActivities().remove(tblActivity);
		tblActivity.setTblStaff(null);

		return tblActivity;
	}

	public Teacher getTblTeacher() {
		return this.tblTeacher;
	}

	public void setTblTeacher(Teacher tblTeacher) {
		this.tblTeacher = tblTeacher;
	}

}