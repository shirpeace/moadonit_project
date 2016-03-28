package model;

import java.io.Serializable;
import javax.persistence.*;


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

}