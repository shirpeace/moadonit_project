package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_reg_to_moadonit database table.
 * 
 */
@Entity
@Table(name="tbl_reg_to_moadonit")
@NamedQuery(name="RegToMoadonit.findAll", query="SELECT r FROM RegToMoadonit r")
public class RegToMoadonit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RegToMoadonitPK id;

	@Column(name="`monday?`")
	private String monday_;

	@Temporal(TemporalType.DATE)
	private Date registerDate;

	private String source;

	@Column(name="`sunday?`")
	private String sunday_;

	@Column(name="`thursday?`")
	private String thursday_;

	@Column(name="`tuesday?`")
	private String tuesday_;

	@Column(name="`wednesday?`")
	private String wednesday_;

	private int writenBy;

	public RegToMoadonit() {
	}

	public RegToMoadonitPK getId() {
		return this.id;
	}

	public void setId(RegToMoadonitPK id) {
		this.id = id;
	}

	public String getMonday_() {
		return this.monday_;
	}

	public void setMonday_(String monday_) {
		this.monday_ = monday_;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSunday_() {
		return this.sunday_;
	}

	public void setSunday_(String sunday_) {
		this.sunday_ = sunday_;
	}

	public String getThursday_() {
		return this.thursday_;
	}

	public void setThursday_(String thursday_) {
		this.thursday_ = thursday_;
	}

	public String getTuesday_() {
		return this.tuesday_;
	}

	public void setTuesday_(String tuesday_) {
		this.tuesday_ = tuesday_;
	}

	public String getWednesday_() {
		return this.wednesday_;
	}

	public void setWednesday_(String wednesday_) {
		this.wednesday_ = wednesday_;
	}

	public int getWritenBy() {
		return this.writenBy;
	}

	public void setWritenBy(int writenBy) {
		this.writenBy = writenBy;
	}

}