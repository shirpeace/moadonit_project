package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_course database table.
 * 
 */
@Entity
@Table(name="tbl_course")
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityNum;

	private int category;

	private float extraPrice;

	private float pricePerMonth;

	private int pupilCapacity;

	//bi-directional many-to-one association to CourseType
	@ManyToOne
	@JoinColumn(name="courseTypeID")
	private CourseType tblCourseType;

	//bi-directional one-to-one association to Activity
	@OneToOne
	@JoinColumn(name="activityNum")
	private Activity tblActivity;

	public Course() {
	}

	public int getActivityNum() {
		return this.activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public float getExtraPrice() {
		return this.extraPrice;
	}

	public void setExtraPrice(float extraPrice) {
		this.extraPrice = extraPrice;
	}

	public float getPricePerMonth() {
		return this.pricePerMonth;
	}

	public void setPricePerMonth(float pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

	public int getPupilCapacity() {
		return this.pupilCapacity;
	}

	public void setPupilCapacity(int pupilCapacity) {
		this.pupilCapacity = pupilCapacity;
	}

	public CourseType getTblCourseType() {
		return this.tblCourseType;
	}

	public void setTblCourseType(CourseType tblCourseType) {
		this.tblCourseType = tblCourseType;
	}

	public Activity getTblActivity() {
		return this.tblActivity;
	}

	public void setTblActivity(Activity tblActivity) {
		this.tblActivity = tblActivity;
	}

}