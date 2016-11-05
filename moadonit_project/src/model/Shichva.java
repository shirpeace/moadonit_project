package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_shichva database table.
 * 
 */
@Entity
@Table(name="tbl_shichva")
@NamedQuery(name="Shichva.findAll", query="SELECT s FROM Shichva s")
public class Shichva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int shichvaValue;

	private String shicvaName;

	//bi-directional many-to-one association to GradeCode
	@OneToMany(mappedBy="tblShichva")
	private List<GradeCode> tblGradeCodes;

	public Shichva() {
	}

	public int getShichvaValue() {
		return this.shichvaValue;
	}

	public void setShichvaValue(int shichvaValue) {
		this.shichvaValue = shichvaValue;
	}

	public String getShicvaName() {
		return this.shicvaName;
	}

	public void setShicvaName(String shicvaName) {
		this.shicvaName = shicvaName;
	}

	public List<GradeCode> getTblGradeCodes() {
		return this.tblGradeCodes;
	}

	public void setTblGradeCodes(List<GradeCode> tblGradeCodes) {
		this.tblGradeCodes = tblGradeCodes;
	}

	public GradeCode addTblGradeCode(GradeCode tblGradeCode) {
		getTblGradeCodes().add(tblGradeCode);
		tblGradeCode.setTblShichva(this);

		return tblGradeCode;
	}

	public GradeCode removeTblGradeCode(GradeCode tblGradeCode) {
		getTblGradeCodes().remove(tblGradeCode);
		tblGradeCode.setTblShichva(null);

		return tblGradeCode;
	}

}