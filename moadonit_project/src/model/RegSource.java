package model;

import java.io.Serializable;
import javax.persistence.*;


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

}