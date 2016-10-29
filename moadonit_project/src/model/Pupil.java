package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_pupil database table.
 * 
 */
@Entity
@Table(name="tbl_pupil")
@NamedQuery(name="Pupil.findAll", query="SELECT p FROM Pupil p")
public class Pupil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pupilNum;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String cellphone;

	private String firstName;

	private String lastName;

	private String photoPath;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="tblPupil")
	private List<Attendance> tblAttendances;

	//bi-directional many-to-many association to GradeInYear
	@ManyToMany(mappedBy="tblPupils1")
	private List<GradeInYear> tblGradeInYears1;

	//bi-directional many-to-one association to OneTimeReg
	@OneToMany(mappedBy="tblPupil")
	private List<OneTimeReg> tblOneTimeRegs;

	//bi-directional many-to-one association to Family
	@ManyToOne
	@JoinColumn(name="familyID")
	private Family tblFamily;

	//bi-directional many-to-one association to GenderRef
	@ManyToOne
	@JoinColumn(name="gender")
	private GenderRef tblGenderRef;

	//bi-directional many-to-many association to GradeInYear
	@ManyToMany
	@JoinTable(
		name="tbl_grade_pupil"
		, joinColumns={
			@JoinColumn(name="pupilNum")
			}
		, inverseJoinColumns={
			@JoinColumn(name="gradeID", referencedColumnName="gradeID"),
			@JoinColumn(name="yearID", referencedColumnName="yearID")
			}
		)
	private List<GradeInYear> tblGradeInYears2;

	//bi-directional many-to-one association to PupilState
	@ManyToOne
	@JoinColumn(name="isReg")
	private PupilState tblPupilState;

	//bi-directional many-to-one association to PupilActivity
	@OneToMany(mappedBy="tblPupil")
	private List<PupilActivity> tblPupilActivities;

	//bi-directional one-to-one association to RegisterPupil
	@OneToOne(mappedBy="tblPupil")
	private RegisterPupil tblRegisterPupil;

	public Pupil() {
	}

	public int getPupilNum() {
		return this.pupilNum;
	}

	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public List<Attendance> getTblAttendances() {
		return this.tblAttendances;
	}

	public void setTblAttendances(List<Attendance> tblAttendances) {
		this.tblAttendances = tblAttendances;
	}

	public Attendance addTblAttendance(Attendance tblAttendance) {
		getTblAttendances().add(tblAttendance);
		tblAttendance.setTblPupil(this);

		return tblAttendance;
	}

	public Attendance removeTblAttendance(Attendance tblAttendance) {
		getTblAttendances().remove(tblAttendance);
		tblAttendance.setTblPupil(null);

		return tblAttendance;
	}

	public List<GradeInYear> getTblGradeInYears1() {
		return this.tblGradeInYears1;
	}

	public void setTblGradeInYears1(List<GradeInYear> tblGradeInYears1) {
		this.tblGradeInYears1 = tblGradeInYears1;
	}

	public List<OneTimeReg> getTblOneTimeRegs() {
		return this.tblOneTimeRegs;
	}

	public void setTblOneTimeRegs(List<OneTimeReg> tblOneTimeRegs) {
		this.tblOneTimeRegs = tblOneTimeRegs;
	}

	public OneTimeReg addTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().add(tblOneTimeReg);
		tblOneTimeReg.setTblPupil(this);

		return tblOneTimeReg;
	}

	public OneTimeReg removeTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().remove(tblOneTimeReg);
		tblOneTimeReg.setTblPupil(null);

		return tblOneTimeReg;
	}

	public Family getTblFamily() {
		return this.tblFamily;
	}

	public void setTblFamily(Family tblFamily) {
		this.tblFamily = tblFamily;
	}

	public GenderRef getTblGenderRef() {
		return this.tblGenderRef;
	}

	public void setTblGenderRef(GenderRef tblGenderRef) {
		this.tblGenderRef = tblGenderRef;
	}

	public List<GradeInYear> getTblGradeInYears2() {
		return this.tblGradeInYears2;
	}

	public void setTblGradeInYears2(List<GradeInYear> tblGradeInYears2) {
		this.tblGradeInYears2 = tblGradeInYears2;
	}

	public PupilState getTblPupilState() {
		return this.tblPupilState;
	}

	public void setTblPupilState(PupilState tblPupilState) {
		this.tblPupilState = tblPupilState;
	}

	public List<PupilActivity> getTblPupilActivities() {
		return this.tblPupilActivities;
	}

	public void setTblPupilActivities(List<PupilActivity> tblPupilActivities) {
		this.tblPupilActivities = tblPupilActivities;
	}

	public PupilActivity addTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().add(tblPupilActivity);
		tblPupilActivity.setTblPupil(this);

		return tblPupilActivity;
	}

	public PupilActivity removeTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().remove(tblPupilActivity);
		tblPupilActivity.setTblPupil(null);

		return tblPupilActivity;
	}

	public RegisterPupil getTblRegisterPupil() {
		return this.tblRegisterPupil;
	}

	public void setTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		this.tblRegisterPupil = tblRegisterPupil;
	}
	
	//bi-directional many-to-one association to GradePupil
	@OneToMany(mappedBy="tblPupil")
	private List<GradePupil> tblGradePupils;

		public List<GradePupil> getTblGradePupils() {
		return tblGradePupils;
	}

	public void setTblGradePupils(List<GradePupil> tblGradePupils) {
		this.tblGradePupils = tblGradePupils;
	}

}