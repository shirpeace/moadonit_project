package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_general_parameters database table.
 * 
 */
@Entity
@Table(name="tbl_general_parameters")
@NamedQuery(name="GeneralParameter.findAll", query="SELECT g FROM GeneralParameter g")
public class GeneralParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int paramID;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	private String paramName;

	private String paramValue;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	public GeneralParameter() {
	}

	public int getParamID() {
		return this.paramID;
	}

	public void setParamID(int paramID) {
		this.paramID = paramID;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}