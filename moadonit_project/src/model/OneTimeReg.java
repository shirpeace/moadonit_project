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

	private String regType;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	public OneTimeReg() {
	}

	public OneTimeRegPK getId() {
		return this.id;
	}

	public void setId(OneTimeRegPK id) {
		this.id = id;
	}

	public String getRegType() {
		return this.regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public Pupil getTblPupil() {
		return this.tblPupil;
	}

	public void setTblPupil(Pupil tblPupil) {
		this.tblPupil = tblPupil;
	}

}