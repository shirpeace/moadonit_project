package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_course_teacher database table.
 * 
 */
@Entity
@Table(name="tbl_course_teacher")
@NamedQuery(name="CourseTeacher.findAll", query="SELECT c FROM CourseTeacher c")
public class CourseTeacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int staffID;

	private float hourlySalary;

	//bi-directional one-to-one association to Staff
	@OneToOne
	@JoinColumn(name="staffID")
	private Staff tblStaff;

	public CourseTeacher() {
	}

	public int getStaffID() {
		return this.staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public float getHourlySalary() {
		return this.hourlySalary;
	}

	public void setHourlySalary(float hourlySalary) {
		this.hourlySalary = hourlySalary;
	}

	public Staff getTblStaff() {
		return this.tblStaff;
	}

	public void setTblStaff(Staff tblStaff) {
		this.tblStaff = tblStaff;
	}

}