package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_moadonit_groups database table.
 * 
 */
@Entity
@Table(name="tbl_moadonit_groups")
@NamedQuery(name="MoadonitGroup.findAll", query="SELECT m FROM MoadonitGroup m")
public class MoadonitGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date endMonth;
	
	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	private Date startMonth;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="activityNum", referencedColumnName="activityNum")
	private Activity tblActivity;

	//bi-directional many-to-one association to GradeInYear
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="gradeID", referencedColumnName="gradeID"),
		@JoinColumn(name="yearID", referencedColumnName="yearID")
		})
	private GradeInYear tblGradeInYear;

	public MoadonitGroup() {
	}

	public Date getEndMonth() {
		return this.endMonth;
	}

	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartMonth() {
		return this.startMonth;
	}

	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}

	public Activity getTblActivity() {
		return this.tblActivity;
	}

	public void setTblActivity(Activity tblActivity) {
		this.tblActivity = tblActivity;
	}

	public GradeInYear getTblGradeInYear() {
		return this.tblGradeInYear;
	}

	public void setTblGradeInYear(GradeInYear tblGradeInYear) {
		this.tblGradeInYear = tblGradeInYear;
	}

}