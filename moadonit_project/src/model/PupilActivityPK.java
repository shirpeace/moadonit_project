package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_pupil_activities database table.
 * 
 */
@Embeddable
public class PupilActivityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int pupilNum;

	@Column(insertable=false, updatable=false)
	private int activityNum;

	public PupilActivityPK() {
	}
	public int getPupilNum() {
		return this.pupilNum;
	}
	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}
	public int getActivityNum() {
		return this.activityNum;
	}
	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PupilActivityPK)) {
			return false;
		}
		PupilActivityPK castOther = (PupilActivityPK)other;
		return 
			(this.pupilNum == castOther.pupilNum)
			&& (this.activityNum == castOther.activityNum);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pupilNum;
		hash = hash * prime + this.activityNum;
		
		return hash;
	}
}