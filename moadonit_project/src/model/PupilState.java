package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_pupil_state database table.
 * 
 */
@Entity
@Table(name="tbl_pupil_state")
@NamedQuery(name="PupilState.findAll", query="SELECT p FROM PupilState p")
public class PupilState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stateNum;

	private String state;

	//bi-directional many-to-one association to Pupil
	@OneToMany(mappedBy="tblPupilState")
	private List<Pupil> tblPupils;

	public PupilState() {
	}

	public int getStateNum() {
		return this.stateNum;
	}

	public void setStateNum(int stateNum) {
		this.stateNum = stateNum;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Pupil> getTblPupils() {
		return this.tblPupils;
	}

	public void setTblPupils(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

	public Pupil addTblPupil(Pupil tblPupil) {
		getTblPupils().add(tblPupil);
		tblPupil.setTblPupilState(this);

		return tblPupil;
	}

	public Pupil removeTblPupil(Pupil tblPupil) {
		getTblPupils().remove(tblPupil);
		tblPupil.setTblPupilState(null);

		return tblPupil;
	}

}