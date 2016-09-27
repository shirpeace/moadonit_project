package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_moadonit_groups database table.
 * 
 */
@Embeddable
public class MoadonitGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int gradeID;

	@Column(insertable=false, updatable=false)
	private int yearID;

	@Column(insertable=false, updatable=false)
	private int activityNum;

	@Temporal(TemporalType.DATE)
	private java.util.Date startMonth;

	public MoadonitGroupPK() {
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
	public int getActivityNum() {
		return this.activityNum;
	}
	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}
	public java.util.Date getStartMonth() {
		return this.startMonth;
	}
	public void setStartMonth(java.util.Date startMonth) {
		this.startMonth = startMonth;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MoadonitGroupPK)) {
			return false;
		}
		MoadonitGroupPK castOther = (MoadonitGroupPK)other;
		return 
			(this.gradeID == castOther.gradeID)
			&& (this.yearID == castOther.yearID)
			&& (this.activityNum == castOther.activityNum)
			&& this.startMonth.equals(castOther.startMonth);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.gradeID;
		hash = hash * prime + this.yearID;
		hash = hash * prime + this.activityNum;
		hash = hash * prime + this.startMonth.hashCode();
		
		return hash;
	}
}