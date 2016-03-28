package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_attendance database table.
 * 
 */
@Embeddable
public class AttendancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int pupilID;

	@Temporal(TemporalType.DATE)
	private java.util.Date specifficDate;

	public AttendancePK() {
	}
	public int getPupilID() {
		return this.pupilID;
	}
	public void setPupilID(int pupilID) {
		this.pupilID = pupilID;
	}
	public java.util.Date getSpecifficDate() {
		return this.specifficDate;
	}
	public void setSpecifficDate(java.util.Date specifficDate) {
		this.specifficDate = specifficDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AttendancePK)) {
			return false;
		}
		AttendancePK castOther = (AttendancePK)other;
		return 
			(this.pupilID == castOther.pupilID)
			&& this.specifficDate.equals(castOther.specifficDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pupilID;
		hash = hash * prime + this.specifficDate.hashCode();
		
		return hash;
	}
}