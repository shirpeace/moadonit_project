package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_gender_ref database table.
 * 
 */
@Entity
@Table(name="tbl_gender_ref")
@NamedQuery(name="GenderRef.findAll", query="SELECT g FROM GenderRef g")
public class GenderRef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int gender; 

	private String genderName;

	//bi-directional many-to-one association to Pupil
	@OneToMany(mappedBy="tblGenderRef")
	private List<Pupil> tblPupils;

	public GenderRef() {
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
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
		tblPupil.setTblGenderRef(this);

		return tblPupil;
	}

	public Pupil removeTblPupil(Pupil tblPupil) {
		getTblPupils().remove(tblPupil);
		tblPupil.setTblGenderRef(null);

		return tblPupil;
	}

}