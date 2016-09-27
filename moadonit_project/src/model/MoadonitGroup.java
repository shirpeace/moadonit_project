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

	@EmbeddedId
	private MoadonitGroupPK id;

	@Temporal(TemporalType.DATE)
	private Date endMonth;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="activityNum")
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

	public MoadonitGroupPK getId() {
		return this.id;
	}

	public void setId(MoadonitGroupPK id) {
		this.id = id;
	}

	public Date getEndMonth() {
		return this.endMonth;
	}

	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
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