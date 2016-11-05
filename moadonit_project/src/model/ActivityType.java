package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_activity_type database table.
 * 
 */
@Entity
@Table(name="tbl_activity_type")
@NamedQuery(name="ActivityType.findAll", query="SELECT a FROM ActivityType a")
public class ActivityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int typeID;

	private String type;

	public ActivityType() {
	}

	public int getTypeID() {
		return this.typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}