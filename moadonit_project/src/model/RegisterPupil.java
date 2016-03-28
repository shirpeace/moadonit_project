package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tbl_register_pupil database table.
 * 
 */
@Entity
@Table(name="tbl_register_pupil")
@NamedQuery(name="RegisterPupil.findAll", query="SELECT r FROM RegisterPupil r")
public class RegisterPupil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pupilNum;
	
	
	
	public int getPupilNum() {
		return pupilNum;
	}

	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}

	/***
	 * need to be changed to char 0 - 1
	 */
	private byte[] ethiopian;

	private String foodSensitivity;

	private String healthProblems;

	private String otherComments;

	private String staffChild;

	//bi-directional many-to-one association to FoodType
	@ManyToOne
	@JoinColumn(name="foodType")
	private FoodType tblFoodType;

	//bi-directional many-to-one association to Pupil
	@ManyToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	public RegisterPupil() {
	}

	public byte[] getEthiopian() {
		return this.ethiopian;
	}

	public void setEthiopian(byte[] ethiopian) {
		this.ethiopian = ethiopian;
	}

	public String getFoodSensitivity() {
		return this.foodSensitivity;
	}

	public void setFoodSensitivity(String foodSensitivity) {
		this.foodSensitivity = foodSensitivity;
	}

	public String getHealthProblems() {
		return this.healthProblems;
	}

	public void setHealthProblems(String healthProblems) {
		this.healthProblems = healthProblems;
	}

	public String getOtherComments() {
		return this.otherComments;
	}

	public void setOtherComments(String otherComments) {
		this.otherComments = otherComments;
	}

	public String getStaffChild() {
		return this.staffChild;
	}

	public void setStaffChild(String staffChild) {
		this.staffChild = staffChild;
	}

	public FoodType getTblFoodType() {
		return this.tblFoodType;
	}

	public void setTblFoodType(FoodType tblFoodType) {
		this.tblFoodType = tblFoodType;
	}

	public Pupil getTblPupil() {
		return this.tblPupil;
	}

	public void setTblPupil(Pupil tblPupil) {
		this.tblPupil = tblPupil;
	}

}