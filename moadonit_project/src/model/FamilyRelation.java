package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_family_relation database table.
 * 
 */
@Entity
@Table(name="tbl_family_relation")
@NamedQuery(name="FamilyRelation.findAll", query="SELECT f FROM FamilyRelation f")
public class FamilyRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_family_relation")
	private int idFamilyRelation;

	private String relation;

	//bi-directional many-to-one association to Parent
	@OneToMany(mappedBy="tblFamilyRelation")
	private List<Parent> tblParents;

	public FamilyRelation() {
	}

	public int getIdFamilyRelation() {
		return this.idFamilyRelation;
	}

	public void setIdFamilyRelation(int idFamilyRelation) {
		this.idFamilyRelation = idFamilyRelation;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public List<Parent> getTblParents() {
		return this.tblParents;
	}

	public void setTblParents(List<Parent> tblParents) {
		this.tblParents = tblParents;
	}

	public Parent addTblParent(Parent tblParent) {
		getTblParents().add(tblParent);
		tblParent.setTblFamilyRelation(this);

		return tblParent;
	}

	public Parent removeTblParent(Parent tblParent) {
		getTblParents().remove(tblParent);
		tblParent.setTblFamilyRelation(null);

		return tblParent;
	}

}