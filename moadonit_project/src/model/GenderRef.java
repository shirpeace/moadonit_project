package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gender_ref database table.
 * 
 */
@Entity
@Table(name="gender_ref")
@NamedQuery(name="GenderRef.findAll", query="SELECT g FROM GenderRef g")
public class GenderRef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte gender;

	private String genderName;

	//bi-directional many-to-one association to Pupil
	@OneToMany(mappedBy="genderRef")
	private List<Pupil> tblPupils;

	public GenderRef() {
	}

	public byte getGender() {
		return this.gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public String getGenderName() {
		return this.genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public List<Pupil> getTblPupils() {
		return this.tblPupils;
	}

	public void setTblPupils(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

	public Pupil addTblPupil(Pupil tblPupil) {
		getTblPupils().add(tblPupil);
		tblPupil.setGenderRef(this);

		return tblPupil;
	}

	public Pupil removeTblPupil(Pupil tblPupil) {
		getTblPupils().remove(tblPupil);
		tblPupil.setGenderRef(null);

		return tblPupil;
	}

}