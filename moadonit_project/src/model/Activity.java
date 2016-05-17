package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the tbl_activity database table.
 * 
 */
@Entity
@Table(name="tbl_activity")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityNum;

	private String activityName;

	private Time endTime;

	private Time startTime;

	private String weekDay;

	//bi-directional many-to-one association to ActivityType
	@ManyToOne
	@JoinColumn(name="activityType")
	private ActivityType tblActivityType;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="schoolYear")
	private SchoolYear tblSchoolYear;

	//bi-directional many-to-one association to Staff
	@ManyToOne
	@JoinColumn(name="responsibleStaff")
	private Staff tblStaff;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="tblActivity")
	private List<Attendance> tblAttendances;

	//bi-directional one-to-one association to Course
	@OneToOne(mappedBy="tblActivity")
	private Course tblCourse;

	//bi-directional many-to-one association to PupilActivity
	@OneToMany(mappedBy="tblActivity")
	private List<PupilActivity> tblPupilActivities;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblActivity")
	private List<RegToMoadonit> tblRegToMoadonits;

	public Activity() {
	}

	public int getActivityNum() {
		return this.activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public String getWeekDay() {
		return this.weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public ActivityType getTblActivityType() {
		return this.tblActivityType;
	}

	public void setTblActivityType(ActivityType tblActivityType) {
		this.tblActivityType = tblActivityType;
	}

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

	public Staff getTblStaff() {
		return this.tblStaff;
	}

	public void setTblStaff(Staff tblStaff) {
		this.tblStaff = tblStaff;
	}

	public List<Attendance> getTblAttendances() {
		return this.tblAttendances;
	}

	public void setTblAttendances(List<Attendance> tblAttendances) {
		this.tblAttendances = tblAttendances;
	}

	public Attendance addTblAttendance(Attendance tblAttendance) {
		getTblAttendances().add(tblAttendance);
		tblAttendance.setTblActivity(this);

		return tblAttendance;
	}

	public Attendance removeTblAttendance(Attendance tblAttendance) {
		getTblAttendances().remove(tblAttendance);
		tblAttendance.setTblActivity(null);

		return tblAttendance;
	}

	public Course getTblCourse() {
		return this.tblCourse;
	}

	public void setTblCourse(Course tblCourse) {
		this.tblCourse = tblCourse;
	}

	public List<PupilActivity> getTblPupilActivities() {
		return this.tblPupilActivities;
	}

	public void setTblPupilActivities(List<PupilActivity> tblPupilActivities) {
		this.tblPupilActivities = tblPupilActivities;
	}

	public PupilActivity addTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().add(tblPupilActivity);
		tblPupilActivity.setTblActivity(this);

		return tblPupilActivity;
	}

	public PupilActivity removeTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().remove(tblPupilActivity);
		tblPupilActivity.setTblActivity(null);

		return tblPupilActivity;
	}

	public List<RegToMoadonit> getTblRegToMoadonits() {
		return this.tblRegToMoadonits;
	}

	public void setTblRegToMoadonits(List<RegToMoadonit> tblRegToMoadonits) {
		this.tblRegToMoadonits = tblRegToMoadonits;
	}

	public RegToMoadonit addTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().add(tblRegToMoadonit);
		tblRegToMoadonit.setTblActivity(this);

		return tblRegToMoadonit;
	}

	public RegToMoadonit removeTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().remove(tblRegToMoadonit);
		tblRegToMoadonit.setTblActivity(null);

		return tblRegToMoadonit;
	}

}