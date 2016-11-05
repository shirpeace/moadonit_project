package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_grade_in_year database table.
 * 
 */
@Entity
@Table(name="tbl_grade_in_year")
@NamedQuery(name="GradeInYear.findAll", query="SELECT g FROM GradeInYear g")
public class GradeInYear implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int gradeYearID;

	private String teacherName;

	//bi-directional many-to-one association to GradeCode
	@ManyToOne
	@JoinColumn(name="gradeID")
	private GradeCode tblGradeCode;

	//bi-directional many-to-many association to Pupil
	@ManyToMany
	@JoinTable(
		name="tbl_grade_pupil"
		, joinColumns={
			@JoinColumn(name="gradeID", referencedColumnName="gradeID"),
			@JoinColumn(name="yearID", referencedColumnName="yearID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pupilNum")
			}
		)
	private List<Pupil> tblPupils;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="yearID")
	private SchoolYear tblSchoolYear;

	//bi-directional many-to-one association to MoadonitGroup
	@OneToMany(mappedBy="tblGradeInYear")
	private List<MoadonitGroup> tblMoadonitGroups;


	public GradeInYear() {
	}

	public int getGradeYearID() {
		return this.gradeYearID;
	}

	public void setGradeYearID(int gradeYearID) {
		this.gradeYearID = gradeYearID;
	}

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public GradeCode getTblGradeCode() {
		return this.tblGradeCode;
	}

	public void setTblGradeCode(GradeCode tblGradeCode) {
		this.tblGradeCode = tblGradeCode;
	}

	public List<Pupil> getTblPupils1() {
		return this.tblPupils;
	}

	public void setTblPupils1(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

	public List<MoadonitGroup> getTblMoadonitGroups() {
		return this.tblMoadonitGroups;
	}

	public void setTblMoadonitGroups(List<MoadonitGroup> tblMoadonitGroups) {
		this.tblMoadonitGroups = tblMoadonitGroups;
	}

	public MoadonitGroup addTblMoadonitGroup(MoadonitGroup tblMoadonitGroup) {
		getTblMoadonitGroups().add(tblMoadonitGroup);
		tblMoadonitGroup.setTblGradeInYear(this);

		return tblMoadonitGroup;
	}

	public MoadonitGroup removeTblMoadonitGroup(MoadonitGroup tblMoadonitGroup) {
		getTblMoadonitGroups().remove(tblMoadonitGroup);
		tblMoadonitGroup.setTblGradeInYear(null);

		return tblMoadonitGroup;
	}


}