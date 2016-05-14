package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_family database table.
 * 
 */
@Entity
@Table(name="tbl_family")
@NamedQuery(name="Family.findAll", query="SELECT f FROM Family f")
public class Family implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int familyID;

	private int areDivorced;

	private String homeAddress;

	private String homePhoneNum;

	//bi-directional many-to-one association to Parent
	@ManyToOne
	@JoinColumn(name="parentID1")
	private Parent tblParent1;

	//bi-directional many-to-one association to Parent
	@ManyToOne
	@JoinColumn(name="parentID2")
	private Parent tblParent2;

	//bi-directional many-to-one association to Pupil
	@OneToMany(mappedBy="tblFamily")
	private List<Pupil> tblPupils;

	public Family() {
	}

	public int getFamilyID() {
		return this.familyID;
	}

	public void setFamilyID(int familyID) {
		this.familyID = familyID;
	}

	public int getAreDivorced() {
		return this.areDivorced;
	}

	public void setAreDivorced(int areDivorced) {
		this.areDivorced = areDivorced;
	}

	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePhoneNum() {
		return this.homePhoneNum;
	}

	public void setHomePhoneNum(String homePhoneNum) {
		this.homePhoneNum = homePhoneNum;
	}

	public Parent getTblParent1() {
		return this.tblParent1;
	}

	public void setTblParent1(Parent tblParent1) {
		this.tblParent1 = tblParent1;
	}

	public Parent getTblParent2() {
		return this.tblParent2;
	}

	public void setTblParent2(Parent tblParent2) {
		this.tblParent2 = tblParent2;
	}

	public List<Pupil> getTblPupils() {
		return this.tblPupils;
	}

	public void setTblPupils(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

	public Pupil addTblPupil(Pupil tblPupil) {
		getTblPupils().add(tblPupil);
		tblPupil.setTblFamily(this);

		return tblPupil;
	}

	public Pupil removeTblPupil(Pupil tblPupil) {
		getTblPupils().remove(tblPupil);
		tblPupil.setTblFamily(null);

		return tblPupil;
	}

}