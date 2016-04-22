package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_one_time_reg database table.
 * 
 */
@Embeddable
public class OneTimeRegPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int pupilNum;

	@Temporal(TemporalType.DATE)
	private java.util.Date specificDate;

	public OneTimeRegPK() {
	}
	public int getPupilNum() {
		return this.pupilNum;
	}
	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}
	public java.util.Date getSpecificDate() {
		return this.specificDate;
	}
	public void setSpecificDate(java.util.Date specificDate) {
		this.specificDate = specificDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OneTimeRegPK)) {
			return false;
		}
		OneTimeRegPK castOther = (OneTimeRegPK)other;
		return 
			(this.pupilNum == castOther.pupilNum)
			&& this.specificDate.equals(castOther.specificDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pupilNum;
		hash = hash * prime + this.specificDate.hashCode();
		
		return hash;
	}
}