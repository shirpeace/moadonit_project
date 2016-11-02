package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_payment_type database table.
 * 
 */
@Entity
@Table(name="tbl_payment_type")
@NamedQuery(name="PaymentType.findAll", query="SELECT p FROM PaymentType p")
public class PaymentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int paymentID;

	private String paymentName;

	//bi-directional many-to-one association to Staff
	@OneToMany(mappedBy="tblPaymentType")
	private List<Staff> tblStaffs;

	public PaymentType() {
	}

	public int getPaymentID() {
		return this.paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public String getPaymentName() {
		return this.paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public List<Staff> getTblStaffs() {
		return this.tblStaffs;
	}

	public void setTblStaffs(List<Staff> tblStaffs) {
		this.tblStaffs = tblStaffs;
	}

	public Staff addTblStaff(Staff tblStaff) {
		getTblStaffs().add(tblStaff);
		tblStaff.setTblPaymentType(this);

		return tblStaff;
	}

	public Staff removeTblStaff(Staff tblStaff) {
		getTblStaffs().remove(tblStaff);
		tblStaff.setTblPaymentType(null);

		return tblStaff;
	}

}