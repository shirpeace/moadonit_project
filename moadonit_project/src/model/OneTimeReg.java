package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_one_time_reg database table.
 * 
 */
@Entity
@Table(name="tbl_one_time_reg")
@NamedQuery(name="OneTimeReg.findAll", query="SELECT o FROM OneTimeReg o")
public class OneTimeReg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OneTimeRegPK id;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	//bi-directional many-to-one association to RegType
	@ManyToOne
	@JoinColumn(name="regType")
	private RegType tblRegType;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="schoolYear")
	private SchoolYear tblSchoolYear;

	public OneTimeReg() {
	}

	public OneTimeRegPK getId() {
		return this.id;
	}

	public void setId(OneTimeRegPK id) {
		this.id = id;
	}

	public Pupil getTblPupil() {
		return this.tblPupil;
	}

	public void setTblPupil(Pupil tblPupil) {
		this.tblPupil = tblPupil;
	}

	public RegType getTblRegType() {
		return this.tblRegType;
	}

	public void setTblRegType(RegType tblRegType) {
		this.tblRegType = tblRegType;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

}