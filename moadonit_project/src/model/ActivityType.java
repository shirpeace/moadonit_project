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

	//bi-directional many-to-one association to Activity
	@OneToMany(mappedBy="tblActivityType")
	private List<Activity> tblActivities;

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

	public List<Activity> getTblActivities() {
		return this.tblActivities;
	}

	public void setTblActivities(List<Activity> tblActivities) {
		this.tblActivities = tblActivities;
	}

	public Activity addTblActivity(Activity tblActivity) {
		getTblActivities().add(tblActivity);
		tblActivity.setTblActivityType(this);

		return tblActivity;
	}

	public Activity removeTblActivity(Activity tblActivity) {
		getTblActivities().remove(tblActivity);
		tblActivity.setTblActivityType(null);

		return tblActivity;
	}

}