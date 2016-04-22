package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_grade_pupil database table.
 * 
 */
@Embeddable
public class GradePupilPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int pupilNum;

	@Column(insertable=false, updatable=false)
	private int gradeID;

	@Column(insertable=false, updatable=false)
	private int yearID;

	public GradePupilPK() {
	}
	public int getPupilNum() {
		return this.pupilNum;
	}
	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}
	public int getGradeID() {
		return this.gradeID;
	}
	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
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
		if (!(other instanceof GradePupilPK)) {
			return false;
		}
		GradePupilPK castOther = (GradePupilPK)other;
		return 
			(this.pupilNum == castOther.pupilNum)
			&& (this.gradeID == castOther.gradeID)
			&& (this.yearID == castOther.yearID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pupilNum;
		hash = hash * prime + this.gradeID;
		hash = hash * prime + this.yearID;
		
		return hash;
	}
}