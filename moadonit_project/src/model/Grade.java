package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_grade database table.
 * 
 */
@Entity
@Table(name="tbl_grade")
@NamedQuery(name="Grade.findAll", query="SELECT g FROM Grade g")
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int gradeID;

	private String gradeName;

	private String gradeTeacher;

	//bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name="locationForMoadonit")
	private Room tblRoom;

	//bi-directional many-to-one association to Pupil
	@OneToMany(mappedBy="tblGrade")
	private List<Pupil> tblPupils;

	public Grade() {
	}

	public int getGradeID() {
		return this.gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeTeacher() {
		return this.gradeTeacher;
	}

	public void setGradeTeacher(String gradeTeacher) {
		this.gradeTeacher = gradeTeacher;
	}

	public Room getTblRoom() {
		return this.tblRoom;
	}

	public void setTblRoom(Room tblRoom) {
		this.tblRoom = tblRoom;
	}

	public List<Pupil> getTblPupils() {
		return this.tblPupils;
	}

	public void setTblPupils(List<Pupil> tblPupils) {
		this.tblPupils = tblPupils;
	}

	public Pupil addTblPupil(Pupil tblPupil) {
		getTblPupils().add(tblPupil);
		tblPupil.setTblGrade(this);

		return tblPupil;
	}

	public Pupil removeTblPupil(Pupil tblPupil) {
		getTblPupils().remove(tblPupil);
		tblPupil.setTblGrade(null);

		return tblPupil;
	}

}