package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_reg_source database table.
 * 
 */
@Entity
@Table(name="tbl_reg_source")
@NamedQuery(name="RegSource.findAll", query="SELECT r FROM RegSource r")
public class RegSource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sourceNum;

	private String sourceName;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegSource")
	private List<RegToMoadonit> tblRegToMoadonits;

	public RegSource() {
	}

	public int getSourceNum() {
		return this.sourceNum;
	}

	public void setSourceNum(int sourceNum) {
		this.sourceNum = sourceNum;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public List<RegToMoadonit> getTblRegToMoadonits() {
		return this.tblRegToMoadonits;
	}

	public void setTblRegToMoadonits(List<RegToMoadonit> tblRegToMoadonits) {
		this.tblRegToMoadonits = tblRegToMoadonits;
	}

	public RegToMoadonit addTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().add(tblRegToMoadonit);
		tblRegToMoadonit.setTblRegSource(this);

		return tblRegToMoadonit;
	}

	public RegToMoadonit removeTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().remove(tblRegToMoadonit);
		tblRegToMoadonit.setTblRegSource(null);

		return tblRegToMoadonit;
	}

}