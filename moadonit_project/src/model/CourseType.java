package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_course_type database table.
 * 
 */
@Entity
@Table(name="tbl_course_type")
@NamedQuery(name="CourseType.findAll", query="SELECT c FROM CourseType c")
public class CourseType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int courseTypeID;

	private String courseType;

	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="tblCourseType")
	private List<Course> tblCourses;

	public CourseType() {
	}

	public int getCourseTypeID() {
		return this.courseTypeID;
	}

	public void setCourseTypeID(int courseTypeID) {
		this.courseTypeID = courseTypeID;
	}

	public String getCourseType() {
		return this.courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public List<Course> getTblCourses() {
		return this.tblCourses;
	}

	public void setTblCourses(List<Course> tblCourses) {
		this.tblCourses = tblCourses;
	}

	public Course addTblCours(Course tblCours) {
		getTblCourses().add(tblCours);
		tblCours.setTblCourseType(this);

		return tblCours;
	}

	public Course removeTblCours(Course tblCours) {
		getTblCourses().remove(tblCours);
		tblCours.setTblCourseType(null);

		return tblCours;
	}

}