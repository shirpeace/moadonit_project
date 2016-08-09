package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-one association to ActivityGroup
	@OneToMany(mappedBy="tblActivityType")
	private List<ActivityGroup> tblActivityGroups;

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

	public List<ActivityGroup> getTblActivityGroups() {
		return this.tblActivityGroups;
	}

	public void setTblActivityGroups(List<ActivityGroup> tblActivityGroups) {
		this.tblActivityGroups = tblActivityGroups;
	}

	public ActivityGroup addTblActivityGroup(ActivityGroup tblActivityGroup) {
		getTblActivityGroups().add(tblActivityGroup);
		tblActivityGroup.setTblActivityType(this);

		return tblActivityGroup;
	}

	public ActivityGroup removeTblActivityGroup(ActivityGroup tblActivityGroup) {
		getTblActivityGroups().remove(tblActivityGroup);
		tblActivityGroup.setTblActivityType(null);

		return tblActivityGroup;
	}

}