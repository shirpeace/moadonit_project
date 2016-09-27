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

	@EmbeddedId
	private GradeInYearPK id;

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
	private List<Pupil> tblPupils1;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="yearID")
	private SchoolYear tblSchoolYear;

	//bi-directional many-to-many association to Pupil
	@ManyToMany(mappedBy="tblGradeInYears2")
	private List<Pupil> tblPupils2;

	//bi-directional many-to-one association to MoadonitGroup
	@OneToMany(mappedBy="tblGradeInYear")
	private List<MoadonitGroup> tblMoadonitGroups;

	public GradeInYear() {
	}

	public GradeInYearPK getId() {
		return this.id;
	}

	public void setId(GradeInYearPK id) {
		this.id = id;
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
		return this.tblPupils1;
	}

	public void setTblPupils1(List<Pupil> tblPupils1) {
		this.tblPupils1 = tblPupils1;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

	public List<Pupil> getTblPupils2() {
		return this.tblPupils2;
	}

	public void setTblPupils2(List<Pupil> tblPupils2) {
		this.tblPupils2 = tblPupils2;
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