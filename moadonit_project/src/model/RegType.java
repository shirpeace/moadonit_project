package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_reg_types database table.
 * 
 */
@Entity
@Table(name="tbl_reg_types")
@NamedQuery(name="RegType.findAll", query="SELECT r FROM RegType r")
public class RegType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int typeNum;

	private String type;

	//bi-directional many-to-one association to OneTimeReg
	@OneToMany(mappedBy="tblRegType")
	private List<OneTimeReg> tblOneTimeRegs;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegType1")
	private List<RegToMoadonit> tblRegToMoadonits1;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegType2")
	private List<RegToMoadonit> tblRegToMoadonits2;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegType3")
	private List<RegToMoadonit> tblRegToMoadonits3;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegType4")
	private List<RegToMoadonit> tblRegToMoadonits4;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegType5")
	private List<RegToMoadonit> tblRegToMoadonits5;

	public RegType() {
	}

	public int getTypeNum() {
		return this.typeNum;
	}

	public void setTypeNum(int typeNum) {
		this.typeNum = typeNum;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OneTimeReg> getTblOneTimeRegs() {
		return this.tblOneTimeRegs;
	}

	public void setTblOneTimeRegs(List<OneTimeReg> tblOneTimeRegs) {
		this.tblOneTimeRegs = tblOneTimeRegs;
	}

	public OneTimeReg addTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().add(tblOneTimeReg);
		tblOneTimeReg.setTblRegType(this);

		return tblOneTimeReg;
	}

	public OneTimeReg removeTblOneTimeReg(OneTimeReg tblOneTimeReg) {
		getTblOneTimeRegs().remove(tblOneTimeReg);
		tblOneTimeReg.setTblRegType(null);

		return tblOneTimeReg;
	}

	public List<RegToMoadonit> getTblRegToMoadonits1() {
		return this.tblRegToMoadonits1;
	}

	public void setTblRegToMoadonits1(List<RegToMoadonit> tblRegToMoadonits1) {
		this.tblRegToMoadonits1 = tblRegToMoadonits1;
	}

	public RegToMoadonit addTblRegToMoadonits1(RegToMoadonit tblRegToMoadonits1) {
		getTblRegToMoadonits1().add(tblRegToMoadonits1);
		tblRegToMoadonits1.setTblRegType1(this);

		return tblRegToMoadonits1;
	}

	public RegToMoadonit removeTblRegToMoadonits1(RegToMoadonit tblRegToMoadonits1) {
		getTblRegToMoadonits1().remove(tblRegToMoadonits1);
		tblRegToMoadonits1.setTblRegType1(null);

		return tblRegToMoadonits1;
	}

	public List<RegToMoadonit> getTblRegToMoadonits2() {
		return this.tblRegToMoadonits2;
	}

	public void setTblRegToMoadonits2(List<RegToMoadonit> tblRegToMoadonits2) {
		this.tblRegToMoadonits2 = tblRegToMoadonits2;
	}

	public RegToMoadonit addTblRegToMoadonits2(RegToMoadonit tblRegToMoadonits2) {
		getTblRegToMoadonits2().add(tblRegToMoadonits2);
		tblRegToMoadonits2.setTblRegType2(this);

		return tblRegToMoadonits2;
	}

	public RegToMoadonit removeTblRegToMoadonits2(RegToMoadonit tblRegToMoadonits2) {
		getTblRegToMoadonits2().remove(tblRegToMoadonits2);
		tblRegToMoadonits2.setTblRegType2(null);

		return tblRegToMoadonits2;
	}

	public List<RegToMoadonit> getTblRegToMoadonits3() {
		return this.tblRegToMoadonits3;
	}

	public void setTblRegToMoadonits3(List<RegToMoadonit> tblRegToMoadonits3) {
		this.tblRegToMoadonits3 = tblRegToMoadonits3;
	}

	public RegToMoadonit addTblRegToMoadonits3(RegToMoadonit tblRegToMoadonits3) {
		getTblRegToMoadonits3().add(tblRegToMoadonits3);
		tblRegToMoadonits3.setTblRegType3(this);

		return tblRegToMoadonits3;
	}

	public RegToMoadonit removeTblRegToMoadonits3(RegToMoadonit tblRegToMoadonits3) {
		getTblRegToMoadonits3().remove(tblRegToMoadonits3);
		tblRegToMoadonits3.setTblRegType3(null);

		return tblRegToMoadonits3;
	}

	public List<RegToMoadonit> getTblRegToMoadonits4() {
		return this.tblRegToMoadonits4;
	}

	public void setTblRegToMoadonits4(List<RegToMoadonit> tblRegToMoadonits4) {
		this.tblRegToMoadonits4 = tblRegToMoadonits4;
	}

	public RegToMoadonit addTblRegToMoadonits4(RegToMoadonit tblRegToMoadonits4) {
		getTblRegToMoadonits4().add(tblRegToMoadonits4);
		tblRegToMoadonits4.setTblRegType4(this);

		return tblRegToMoadonits4;
	}

	public RegToMoadonit removeTblRegToMoadonits4(RegToMoadonit tblRegToMoadonits4) {
		getTblRegToMoadonits4().remove(tblRegToMoadonits4);
		tblRegToMoadonits4.setTblRegType4(null);

		return tblRegToMoadonits4;
	}

	public List<RegToMoadonit> getTblRegToMoadonits5() {
		return this.tblRegToMoadonits5;
	}

	public void setTblRegToMoadonits5(List<RegToMoadonit> tblRegToMoadonits5) {
		this.tblRegToMoadonits5 = tblRegToMoadonits5;
	}

	public RegToMoadonit addTblRegToMoadonits5(RegToMoadonit tblRegToMoadonits5) {
		getTblRegToMoadonits5().add(tblRegToMoadonits5);
		tblRegToMoadonits5.setTblRegType5(this);

		return tblRegToMoadonits5;
	}

	public RegToMoadonit removeTblRegToMoadonits5(RegToMoadonit tblRegToMoadonits5) {
		getTblRegToMoadonits5().remove(tblRegToMoadonits5);
		tblRegToMoadonits5.setTblRegType5(null);

		return tblRegToMoadonits5;
	}

}