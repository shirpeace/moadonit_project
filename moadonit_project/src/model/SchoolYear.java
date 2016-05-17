package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_school_years database table.
 * 
 */
@Entity
@Table(name="tbl_school_years")
@NamedQuery(name="SchoolYear.findAll", query="SELECT s FROM SchoolYear s")
public class SchoolYear implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int yearID;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	private String yearName;

	//bi-directional many-to-one association to Activity
	@OneToMany(mappedBy="tblSchoolYear")
	private List<Activity> tblActivities;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="tblSchoolYear")
	private List<Attendance> tblAttendances;

	//bi-directional many-to-one association to GradeInYear
	@OneToMany(mappedBy="tblSchoolYear")
	private List<GradeInYear> tblGradeInYears;

	//bi-directional many-to-one association to OneTimeReg
	@OneToMany(mappedBy="tblSchoolYear")
	private List<OneTimeReg> tblOneTimeRegs;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblSchoolYear")
	private List<RegToMoadonit> tblRegToMoadonits;

	public SchoolYear() {
	}

	public int getYearID() {
		return this.yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getYearName() {
		return this.yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public List<Activity> getTblActivities() {
		return this.tblActivities;
	}

	public void setTblActivities(List<Activity> tblActivities) {
		this.tblActivities = tblActivities;
	}

	public Activity addTblActivity(Activity tblActivity) {
		getTblActivities().add(tblActivity);
		tblActivity.setTblSchoolYear(this);

		return tblActivity;
	}

	public Activity removeTblActivity(Activity tblActivity) {
		getTblActivities().remove(tblActivity);
		tblActivity.setTblSchoolYear(null);

		return tblActivity;
	}

	public List<Attendance> getTblAttendances() {
		return this.tblAttendances;
	}

	public void setTblAttendances(List<Attendance> tblAttendances) {
		this.tblAttendances = tblAttendances;
	}

	public Attendance addTblAttendance(Attendance tblAttendance) {
		getTblAttendances().add(tblAttendance);
		tblAttendance.setTblSchoolYear(this);

		return tblAttendance;
	}

	public Attendance removeTblAttendance(Attendance tblAttendance) {
		getTblAttendances().remove(tblAttendance);
		tblAttendance.setTblSchoolYear(null);

		return tblAttendance;
	}

	public List<GradeInYear> getTblGradeInYears() {
		return this.tblGradeInYears;
	}

	public void setTblGradeInYears(List<GradeInYear> tblGradeInYears) {
		this.tblGradeInYears = tblGradeInYears;
	}

	public GradeInYear addTblGradeInYear(GradeInYear tblGradeInYear) {
		getTblGradeInYears().add(tblGradeInYear);
		tblGradeInYear.setTblSchoolYear(this);

		return tblGradeInYear;
	}

	public GradeInYear removeTblGradeInYear(GradeInYear tblGradeInYear) {
		getTblGradeInYears().remove(tblGradeInYear);
		tblGradeInYear.setTblSchoolYear(null);

		return tblGradeInYear;
	}

	public List<OneTimeReg> getTblOneTimeRegs() {
		return this.tblOneTimeRegs;
	}

	public void setTblOneTimeRegs(List<OneTimeReg> tblOneTimeRegs) {
		this.tblOneTimeRegs = tblOneTimeRegs;
	}

	public OneTimeReg addTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().add(tblOneTimeReg);
		tblOneTimeReg.setTblSchoolYear(this);

		return tblOneTimeReg;
	}

	public OneTimeReg removeTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().remove(tblOneTimeReg);
		tblOneTimeReg.setTblSchoolYear(null);

		return tblOneTimeReg;
	}

	public List<RegToMoadonit> getTblRegToMoadonits() {
		return this.tblRegToMoadonits;
	}

	public void setTblRegToMoadonits(List<RegToMoadonit> tblRegToMoadonits) {
		this.tblRegToMoadonits = tblRegToMoadonits;
	}

	public RegToMoadonit addTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().add(tblRegToMoadonit);
		tblRegToMoadonit.setTblSchoolYear(this);

		return tblRegToMoadonit;
	}

	public RegToMoadonit removeTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().remove(tblRegToMoadonit);
		tblRegToMoadonit.setTblSchoolYear(null);

		return tblRegToMoadonit;
	}

}