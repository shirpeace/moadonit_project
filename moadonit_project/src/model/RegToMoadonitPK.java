package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_reg_to_moadonit database table.
 * 
 */
@Embeddable
public class RegToMoadonitPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int pupilNum;

	@Temporal(TemporalType.DATE)
	private java.util.Date startDate;

	public RegToMoadonitPK() {
	}
	public int getPupilNum() {
		return this.pupilNum;
	}
	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RegToMoadonitPK)) {
			return false;
		}
		RegToMoadonitPK castOther = (RegToMoadonitPK)other;
		return 
			(this.pupilNum == castOther.pupilNum)
			&& this.startDate.equals(castOther.startDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pupilNum;
		hash = hash * prime + this.startDate.hashCode();
		
		return hash;
	}
}