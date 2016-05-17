package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_pupil_activities database table.
 * 
 */
@Entity
@Table(name="tbl_pupil_activities")
@NamedQuery(name="PupilActivity.findAll", query="SELECT p FROM PupilActivity p")
public class PupilActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PupilActivityPK id;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.DATE)
	private Date regDate;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="activityNum")
	private Activity tblActivity;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="writtenBy")
	private User tblUser;

	public PupilActivity() {
	}

	public PupilActivityPK getId() {
		return this.id;
	}

	public void setId(PupilActivityPK id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Activity getTblActivity() {
		return this.tblActivity;
	}

	public void setTblActivity(Activity tblActivity) {
		this.tblActivity = tblActivity;
	}

	public Pupil getTblPupil() {
		return this.tblPupil;
	}

	public void setTblPupil(Pupil tblPupil) {
		this.tblPupil = tblPupil;
	}

	public User getTblUser() {
		return this.tblUser;
	}

	public void setTblUser(User tblUser) {
		this.tblUser = tblUser;
	}

}