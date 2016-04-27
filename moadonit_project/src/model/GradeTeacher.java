package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_grade_teacher database table.
 * 
 */
/*@Entity not in use - sould be deleted*/
@Table(name="tbl_grade_teacher")
@NamedQuery(name="GradeTeacher.findAll", query="SELECT g FROM GradeTeacher g")
public class GradeTeacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GradeTeacherPK id;

	//bi-directional many-to-one association to GradeCode
	@ManyToOne
	@JoinColumn(name="gradeID")
	private GradeCode tblGradeCode;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="yearID")
	private SchoolYear tblSchoolYear;

	public GradeTeacher() {
	}

	public GradeTeacherPK getId() {
		return this.id;
	}

	public void setId(GradeTeacherPK id) {
		this.id = id;
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

}