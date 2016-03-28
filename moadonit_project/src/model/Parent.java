package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_parent database table.
 * 
 */
@Entity
@Table(name="tbl_parent")
@NamedQuery(name="Parent.findAll", query="SELECT p FROM Parent p")
public class Parent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int parentID;

	private String cellphone;

	private String firstName;

	private String lastName;

	private String parentEmail;

	//bi-directional many-to-one association to Family
	@OneToMany(mappedBy="tblParent1")
	private List<Family> tblFamilies1;

	//bi-directional many-to-one association to Family
	@OneToMany(mappedBy="tblParent2")
	private List<Family> tblFamilies2;

	//bi-directional many-to-one association to FamilyRelation
	@ManyToOne
	@JoinColumn(name="relationToPupil")
	private FamilyRelation tblFamilyRelation;

	public Parent() {
	}

	public int getParentID() {
		return this.parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public List<Family> getTblFamilies1() {
		return this.tblFamilies1;
	}

	public void setTblFamilies1(List<Family> tblFamilies1) {
		this.tblFamilies1 = tblFamilies1;
	}

	public Family addTblFamilies1(Family tblFamilies1) {
		getTblFamilies1().add(tblFamilies1);
		tblFamilies1.setTblParent1(this);

		return tblFamilies1;
	}

	public Family removeTblFamilies1(Family tblFamilies1) {
		getTblFamilies1().remove(tblFamilies1);
		tblFamilies1.setTblParent1(null);

		return tblFamilies1;
	}

	public List<Family> getTblFamilies2() {
		return this.tblFamilies2;
	}

	public void setTblFamilies2(List<Family> tblFamilies2) {
		this.tblFamilies2 = tblFamilies2;
	}

	public Family addTblFamilies2(Family tblFamilies2) {
		getTblFamilies2().add(tblFamilies2);
		tblFamilies2.setTblParent2(this);

		return tblFamilies2;
	}

	public Family removeTblFamilies2(Family tblFamilies2) {
		getTblFamilies2().remove(tblFamilies2);
		tblFamilies2.setTblParent2(null);

		return tblFamilies2;
	}

	public FamilyRelation getTblFamilyRelation() {
		return this.tblFamilyRelation;
	}

	public void setTblFamilyRelation(FamilyRelation tblFamilyRelation) {
		this.tblFamilyRelation = tblFamilyRelation;
	}

}