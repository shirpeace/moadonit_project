package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_user database table.
 * 
 */
@Entity
@Table(name="tbl_user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userID;

	private String password;

	private String userName;

	//bi-directional many-to-one association to PupilActivity
	@OneToMany(mappedBy="tblUser")
	private List<PupilActivity> tblPupilActivities;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblUser")
	private List<RegToMoadonit> tblRegToMoadonits;

	public User() {
	}

	public int getUserID() {
		return this.userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<PupilActivity> getTblPupilActivities() {
		return this.tblPupilActivities;
	}

	public void setTblPupilActivities(List<PupilActivity> tblPupilActivities) {
		this.tblPupilActivities = tblPupilActivities;
	}

	public PupilActivity addTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().add(tblPupilActivity);
		tblPupilActivity.setTblUser(this);

		return tblPupilActivity;
	}

	public PupilActivity removeTblPupilActivity(PupilActivity tblPupilActivity) {
		getTblPupilActivities().remove(tblPupilActivity);
		tblPupilActivity.setTblUser(null);

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
		tblRegToMoadonit.setTblUser(this);

		return tblRegToMoadonit;
	}

	public RegToMoadonit removeTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().remove(tblRegToMoadonit);
		tblRegToMoadonit.setTblUser(null);

		return tblRegToMoadonit;
	}

}