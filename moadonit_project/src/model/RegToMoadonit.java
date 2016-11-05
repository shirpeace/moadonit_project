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

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.DATE)
	private Date registerDate;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="activityNum")
	private Activity tblActivity;

	//bi-directional many-to-one association to RegisterPupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private RegisterPupil tblRegisterPupil;

	//bi-directional many-to-one association to RegSource
	@ManyToOne
	@JoinColumn(name="source")
	private RegSource tblRegSource;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="sunday_")
	private RegType tblRegType1;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="monday_")
	private RegType tblRegType2;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="tuesday_")
	private RegType tblRegType3;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="wednesday_")
	private RegType tblRegType4;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="thursday_")
	private RegType tblRegType5;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="activeYear")
	private SchoolYear tblSchoolYear;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="writenBy")
	private User tblUser;

	public RegToMoadonit() {
	}

	public RegToMoadonitPK getId() {
		return this.id;
	}

	public void setId(RegToMoadonitPK id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Activity getTblActivity() {
		return this.tblActivity;
	}

	public void setTblActivity(Activity tblActivity) {
		this.tblActivity = tblActivity;
	}

	public RegisterPupil getTblRegisterPupil() {
		return this.tblRegisterPupil;
	}

	public void setTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		this.tblRegisterPupil = tblRegisterPupil;
	}

	public RegSource getTblRegSource() {
		return this.tblRegSource;
	}

	public void setTblRegSource(RegSource tblRegSource) {
		this.tblRegSource = tblRegSource;
	}

	public RegType getTblRegType1() {
		return this.tblRegType1;
	}

	public void setTblRegType1(RegType tblRegType1) {
		this.tblRegType1 = tblRegType1;
	}

	public RegType getTblRegType2() {
		return this.tblRegType2;
	}

	public void setTblRegType2(RegType tblRegType2) {
		this.tblRegType2 = tblRegType2;
	}

	public RegType getTblRegType3() {
		return this.tblRegType3;
	}

	public void setTblRegType3(RegType tblRegType3) {
		this.tblRegType3 = tblRegType3;
	}

	public RegType getTblRegType4() {
		return this.tblRegType4;
	}

	public void setTblRegType4(RegType tblRegType4) {
		this.tblRegType4 = tblRegType4;
	}

	public RegType getTblRegType5() {
		return this.tblRegType5;
	}

	public void setTblRegType5(RegType tblRegType5) {
		this.tblRegType5 = tblRegType5;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

	public User getTblUser() {
		return this.tblUser;
	}

	public void setTblUser(User tblUser) {
		this.tblUser = tblUser;
	}

}