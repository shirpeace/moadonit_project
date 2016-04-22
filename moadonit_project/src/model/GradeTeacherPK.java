package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_grade_teacher database table.
 * 
 */
@Embeddable
public class GradeTeacherPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int gradeID;

	private String teacherName;

	@Column(insertable=false, updatable=false)
	private int yearID;

	public GradeTeacherPK() {
	}
	public int getGradeID() {
		return this.gradeID;
	}
	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}
	public String getTeacherName() {
		return this.teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getYearID() {
		return this.yearID;
	}
	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GradeTeacherPK)) {
			return false;
		}
		GradeTeacherPK castOther = (GradeTeacherPK)other;
		return 
			(this.gradeID == castOther.gradeID)
			&& this.teacherName.equals(castOther.teacherName)
			&& (this.yearID == castOther.yearID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.gradeID;
		hash = hash * prime + this.teacherName.hashCode();
		hash = hash * prime + this.yearID;
		
		return hash;
	}
}