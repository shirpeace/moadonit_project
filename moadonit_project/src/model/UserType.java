package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_user_type database table.
 * 
 */
@Entity
@Table(name="tbl_user_type")
@NamedQuery(name="UserType.findAll", query="SELECT u FROM UserType u")
public class UserType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userTypeID;

	private String userType;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="tblUserType")
	private List<User> tblUsers;

	public UserType() {
	}

	public int getUserTypeID() {
		return this.userTypeID;
	}

	public void setUserTypeID(int userTypeID) {
		this.userTypeID = userTypeID;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<User> getTblUsers() {
		return this.tblUsers;
	}

	public void setTblUsers(List<User> tblUsers) {
		this.tblUsers = tblUsers;
	}

	public User addTblUser(User tblUser) {
		getTblUsers().add(tblUser);
		tblUser.setTblUserType(this);

		return tblUser;
	}

	public User removeTblUser(User tblUser) {
		getTblUsers().remove(tblUser);
		tblUser.setTblUserType(null);

		return tblUser;
	}

}