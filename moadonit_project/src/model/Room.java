package model;

import java.io.Serializable;
import javax.persistence.*;


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

}