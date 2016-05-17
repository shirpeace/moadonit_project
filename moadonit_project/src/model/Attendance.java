package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_attendance database table.
 * 
 */
@Entity
@Table(name="tbl_attendance")
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AttendancePK id;

	@Column(name="attended_")
	private int attended;

	@Column(name="rec_type")
	private int recType;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="activityNum")
	private Activity tblActivity;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilID")
	private Pupil tblPupil;

	//bi-directional many-to-one association to SchoolYear
	@ManyToOne
	@JoinColumn(name="activeYear")
	private SchoolYear tblSchoolYear;

	public Attendance() {
	}

	public AttendancePK getId() {
		return this.id;
	}

	public void setId(AttendancePK id) {
		this.id = id;
	}

	public int getAttended() {
		return this.attended;
	}

	public void setAttended(int attended) {
		this.attended = attended;
	}

	public int getRecType() {
		return this.recType;
	}

	public void setRecType(int recType) {
		this.recType = recType;
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

	public SchoolYear getTblSchoolYear() {
		return this.tblSchoolYear;
	}

	public void setTblSchoolYear(SchoolYear tblSchoolYear) {
		this.tblSchoolYear = tblSchoolYear;
	}

}