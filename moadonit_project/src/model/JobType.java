package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_job_type database table.
 * 
 */
@Entity
@Table(name="tbl_job_type")
@NamedQuery(name="JobType.findAll", query="SELECT j FROM JobType j")
public class JobType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int jobID;

	private String jobName;

	//bi-directional many-to-one association to Staff
	@OneToMany(mappedBy="tblJobType")
	private List<Staff> tblStaffs;

	public JobType() {
	}

	public int getJobID() {
		return this.jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<Staff> getTblStaffs() {
		return this.tblStaffs;
	}

	public void setTblStaffs(List<Staff> tblStaffs) {
		this.tblStaffs = tblStaffs;
	}

	public Staff addTblStaff(Staff tblStaff) {
		getTblStaffs().add(tblStaff);
		tblStaff.setTblJobType(this);

		return tblStaff;
	}

	public Staff removeTblStaff(Staff tblStaff) {
		getTblStaffs().remove(tblStaff);
		tblStaff.setTblJobType(null);

		return tblStaff;
	}

}