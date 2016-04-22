package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_grade_pupil database table.
 * 
 */
@Entity
@Table(name="tbl_grade_pupil")
@NamedQuery(name="GradePupil.findAll", query="SELECT g FROM GradePupil g")
public class GradePupil implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GradePupilPK id;

	//bi-directional many-to-one association to GradeCode
	@ManyToOne
	@JoinColumn(name="gradeID")
	private GradeCode tblGradeCode;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="yearID")
	private SchoolYear tblSchoolYear;

	public GradePupil() {
	}

	public GradePupilPK getId() {
		return this.id;
	}

	public void setId(GradePupilPK id) {
		this.id = id;
	}

	public GradeCode getTblGradeCode() {
		return this.tblGradeCode;
	}

	public void setTblGradeCode(GradeCode tblGradeCode) {
		this.tblGradeCode = tblGradeCode;
	}

	public Pupil getTblPupil() {
		return this.tblPupil;
	}

	public void setTblPupil(Pupil tblPupil) {
		this.tblPupil = tblPupil;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

}