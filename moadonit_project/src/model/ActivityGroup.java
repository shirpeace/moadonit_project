package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_activity_group database table.
 * 
 */
@Entity
@Table(name="tbl_activity_group")
@NamedQuery(name="ActivityGroup.findAll", query="SELECT a FROM ActivityGroup a")
public class ActivityGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityGroupNum;

	private String actGroupName;

	private int activityType;

	//bi-directional many-to-one association to Activity
	@OneToMany(mappedBy="tblActivityGroup")
	private List<Activity> tblActivities;

	public ActivityGroup() {
	}

	public int getActivityGroupNum() {
		return this.activityGroupNum;
	}

	public void setActivityGroupNum(int activityGroupNum) {
		this.activityGroupNum = activityGroupNum;
	}

	public String getActGroupName() {
		return this.actGroupName;
	}

	public void setActGroupName(String actGroupName) {
		this.actGroupName = actGroupName;
	}

	public int getActivityType() {
		return this.activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public List<Activity> getTblActivities() {
		return this.tblActivities;
	}

	public void setTblActivities(List<Activity> tblActivities) {
		this.tblActivities = tblActivities;
	}

	public Activity addTblActivity(Activity tblActivity) {
		getTblActivities().add(tblActivity);
		tblActivity.setTblActivityGroup(this);

		return tblActivity;
	}

	public Activity removeTblActivity(Activity tblActivity) {
		getTblActivities().remove(tblActivity);
		tblActivity.setTblActivityGroup(null);

		return tblActivity;
	}

}