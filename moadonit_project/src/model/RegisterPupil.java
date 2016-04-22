package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	private int ethiopian;

	private String foodSensitivity;

	private String healthProblems;

	private String otherComments;

	private String staffChild;

	//bi-directional many-to-one association to RegToMoadonit
	@OneToMany(mappedBy="tblRegisterPupil")
	private List<RegToMoadonit> tblRegToMoadonits;

	//bi-directional many-to-one association to FoodType
	@ManyToOne
	@JoinColumn(name="foodType")
	private FoodType tblFoodType;

	//bi-directional one-to-one association to Pupil
	@OneToOne
	@JoinColumn(name="pupilNum")
	private Pupil tblPupil;

	public RegisterPupil() {
	}

	public int getPupilNum() {
		return this.pupilNum;
	}

	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}

	public int getEthiopian() {
		return this.ethiopian;
	}

	public void setEthiopian(int ethiopian) {
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

	public List<RegToMoadonit> getTblRegToMoadonits() {
		return this.tblRegToMoadonits;
	}

	public void setTblRegToMoadonits(List<RegToMoadonit> tblRegToMoadonits) {
		this.tblRegToMoadonits = tblRegToMoadonits;
	}

	public RegToMoadonit addTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().add(tblRegToMoadonit);
		tblRegToMoadonit.setTblRegisterPupil(this);

		return tblRegToMoadonit;
	}

	public RegToMoadonit removeTblRegToMoadonit(RegToMoadonit tblRegToMoadonit) {
		getTblRegToMoadonits().remove(tblRegToMoadonit);
		tblRegToMoadonit.setTblRegisterPupil(null);

		return tblRegToMoadonit;
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