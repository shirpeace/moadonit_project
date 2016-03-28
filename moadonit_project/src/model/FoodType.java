package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_food_type database table.
 * 
 */
@Entity
@Table(name="tbl_food_type")
@NamedQuery(name="FoodType.findAll", query="SELECT f FROM FoodType f")
public class FoodType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int foodTypeID;

	private String foodType;

	//bi-directional many-to-one association to RegisterPupil
	@OneToMany(mappedBy="tblFoodType")
	private List<RegisterPupil> tblRegisterPupils;

	public FoodType() {
	}

	public int getFoodTypeID() {
		return this.foodTypeID;
	}

	public void setFoodTypeID(int foodTypeID) {
		this.foodTypeID = foodTypeID;
	}

	public String getFoodType() {
		return this.foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public List<RegisterPupil> getTblRegisterPupils() {
		return this.tblRegisterPupils;
	}

	public void setTblRegisterPupils(List<RegisterPupil> tblRegisterPupils) {
		this.tblRegisterPupils = tblRegisterPupils;
	}

	public RegisterPupil addTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		getTblRegisterPupils().add(tblRegisterPupil);
		tblRegisterPupil.setTblFoodType(this);

		return tblRegisterPupil;
	}

	public RegisterPupil removeTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		getTblRegisterPupils().remove(tblRegisterPupil);
		tblRegisterPupil.setTblFoodType(null);

		return tblRegisterPupil;
	}

}