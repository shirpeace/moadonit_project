package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_grade_code database table.
 * 
 */
@Entity
@Table(name="tbl_grade_code")
@NamedQuery(name="GradeCode.findAll", query="SELECT g FROM GradeCode g")
public class GradeCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int gradeID;

	private String gradeName;

	//bi-directional many-to-one association to GradePupil
	@OneToMany(mappedBy="tblGradeCode")
	private List<GradePupil> tblGradePupils;

	//bi-directional many-to-one association to GradeInYear
	@OneToMany(mappedBy="tblGradeCode")
	private List<GradeInYear> tblGradeInYears;

	public GradeCode() {
	}

	public int getGradeID() {
		return this.gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public List<GradePupil> getTblGradePupils() {
		return this.tblGradePupils;
	}

	public void setTblGradePupils(List<GradePupil> tblGradePupils) {
		this.tblGradePupils = tblGradePupils;
	}

	public GradePupil addTblGradePupil(GradePupil tblGradePupil) {
		getTblGradePupils().add(tblGradePupil);
		tblGradePupil.setTblGradeCode(this);

		return tblGradePupil;
	}

	public GradePupil removeTblGradePupil(GradePupil tblGradePupil) {
		getTblGradePupils().remove(tblGradePupil);
		tblGradePupil.setTblGradeCode(null);

		return tblGradePupil;
	}

	public List<GradeInYear> getTblGradeInYears() {
		return this.tblGradeInYears;
	}

	public void setTblGradeInYears(List<GradeInYear> tblGradeInYears) {
		this.tblGradeInYears = tblGradeInYears;
	}

	public GradeInYear addTblGradeInYear(GradeInYear tblGradeInYear) {
		getTblGradeInYears().add(tblGradeInYear);
		tblGradeInYear.setTblGradeCode(this);

		return tblGradeInYear;
	}

	public GradeInYear removeTblGradeInYear(GradeInYear tblGradeInYear) {
		getTblGradeInYears().remove(tblGradeInYear);
		tblGradeInYear.setTblGradeCode(null);

		return tblGradeInYear;
	}

}