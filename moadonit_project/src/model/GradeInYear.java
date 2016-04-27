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

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="yearID")
	private SchoolYear tblSchoolYear;

	//bi-directional many-to-many association to Pupil
	@ManyToMany(mappedBy="tblGradeInYears")
	private List<Pupil> tblPupils;

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

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

	public List<Pupil> getTblPupils() {
		return this.tblPupils;
	}

	public void setTblPupils(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

}