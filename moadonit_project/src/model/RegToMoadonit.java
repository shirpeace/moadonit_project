package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_reg_to_moadonit database table.
 * 
 */
@Entity
@Table(name = "tbl_reg_to_moadonit")
@NamedQuery(name = "RegToMoadonit.findAll", query = "SELECT r FROM RegToMoadonit r")
public class RegToMoadonit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RegToMoadonitPK id;

	@Column(name = "`monday?`")
	private int monday_;

	@Temporal(TemporalType.DATE)
	private Date registerDate;

	private String source;

	@Column(name = "`sunday?`")
	private int sunday_;

	@Column(name = "`thursday?`")
	private int thursday_;

	@Column(name = "`tuesday?`")
	private int tuesday_;

	@Column(name = "`wednesday?`")
	private int wednesday_;

	private int writenBy;

	public RegToMoadonit() {
	}

	public RegToMoadonitPK getId() {
		return this.id;
	}

	public void setId(RegToMoadonitPK id) {
		this.id = id;
	}

	public int getMonday_() {
		return this.monday_;
	}

	public void setMonday_(int monday_) {
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

	public int getSunday_() {
		return this.sunday_;
	}

	public void setSunday_(int sunday_) {
		this.sunday_ = sunday_;
	}

	public int getThursday_() {
		return this.thursday_;
	}

	public void setThursday_(int thursday_) {
		this.thursday_ = thursday_;
	}

	public int getTuesday_() {
		return this.tuesday_;
	}

	public void setTuesday_(int tuesday_) {
		this.tuesday_ = tuesday_;
	}

	public int getWednesday_() {
		return this.wednesday_;
	}

	public void setWednesday_(int wednesday_) {
		this.wednesday_ = wednesday_;
	}

	public int getWritenBy() {
		return this.writenBy;
	}

	public void setWritenBy(int writenBy) {
		this.writenBy = writenBy;
	}

}