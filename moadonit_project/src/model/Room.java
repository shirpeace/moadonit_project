package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_room database table.
 * 
 */
@Entity
@Table(name="tbl_room")
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int roomID;

	private String name;

	//bi-directional many-to-one association to Grade
	@OneToMany(mappedBy="tblRoom")
	private List<Grade> tblGrades;

	public Room() {
	}

	public int getRoomID() {
		return this.roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Grade> getTblGrades() {
		return this.tblGrades;
	}

	public void setTblGrades(List<Grade> tblGrades) {
		this.tblGrades = tblGrades;
	}

	public Grade addTblGrade(Grade tblGrade) {
		getTblGrades().add(tblGrade);
		tblGrade.setTblRoom(this);

		return tblGrade;
	}

	public Grade removeTblGrade(Grade tblGrade) {
		getTblGrades().remove(tblGrade);
		tblGrade.setTblRoom(null);

		return tblGrade;
	}

}