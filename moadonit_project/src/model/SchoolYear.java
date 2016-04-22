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

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="tblSchoolYear")
	private List<Attendance> tblAttendances;

	//bi-directional many-to-one association to GradePupil
	@OneToMany(mappedBy="tblSchoolYear")
	private List<GradePupil> tblGradePupils;

	//bi-directional many-to-one association to GradeTeacher
	@OneToMany(mappedBy="tblSchoolYear")
	private List<GradeTeacher> tblGradeTeachers;

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

	public List<GradePupil> getTblGradePupils() {
		return this.tblGradePupils;
	}

	public void setTblGradePupils(List<GradePupil> tblGradePupils) {
		this.tblGradePupils = tblGradePupils;
	}

	public GradePupil addTblGradePupil(GradePupil tblGradePupil) {
		getTblGradePupils().add(tblGradePupil);
		tblGradePupil.setTblSchoolYear(this);

		return tblGradePupil;
	}

	public GradePupil removeTblGradePupil(GradePupil tblGradePupil) {
		getTblGradePupils().remove(tblGradePupil);
		tblGradePupil.setTblSchoolYear(null);

		return tblGradePupil;
	}

	public List<GradeTeacher> getTblGradeTeachers() {
		return this.tblGradeTeachers;
	}

	public void setTblGradeTeachers(List<GradeTeacher> tblGradeTeachers) {
		this.tblGradeTeachers = tblGradeTeachers;
	}

	public GradeTeacher addTblGradeTeacher(GradeTeacher tblGradeTeacher) {
		getTblGradeTeachers().add(tblGradeTeacher);
		tblGradeTeacher.setTblSchoolYear(this);

		return tblGradeTeacher;
	}

	public GradeTeacher removeTblGradeTeacher(GradeTeacher tblGradeTeacher) {
		getTblGradeTeachers().remove(tblGradeTeacher);
		tblGradeTeacher.setTblSchoolYear(null);

		return tblGradeTeacher;
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