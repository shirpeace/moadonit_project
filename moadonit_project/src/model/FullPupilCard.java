package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fullPupilCard database table.
 * 
 */

@NamedQuery(name="FullPupilCard.findAll", query="SELECT f FROM FullPupilCard f")
public class FullPupilCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String cellphone;

	private int ethiopian;

	private int familyID;

	private String firstName;

	private String foodSensitivity;

	private int foodType;

	private int gender;

	private int gradeID;

	private String gradeName;

	private String gradeTeacher;

	private String healthProblems;

	private String homeAddress;

	private String homePhoneNum;

	private String lastName;

	private String otherComments;

	private String p1cell;

	private String p1fname;

	private String p1lname;

	private String p1mail;

	private int p1relation;

	private String p2cell;

	private String p2fname;

	private String p2lname;

	private String p2mail;

	private int p2relation;

	private int parent1ID;

	private int parent2ID;

	private String photoPath;

	private int pupilNum;

	private int regPupilNum;

	private String staffChild;

	public FullPupilCard() {
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public int getEthiopian() {
		return this.ethiopian;
	}

	public void setEthiopian(int ethiopian) {
		this.ethiopian = ethiopian;
	}

	public int getFamilyID() {
		return this.familyID;
	}

	public void setFamilyID(int familyID) {
		this.familyID = familyID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFoodSensitivity() {
		return this.foodSensitivity;
	}

	public void setFoodSensitivity(String foodSensitivity) {
		this.foodSensitivity = foodSensitivity;
	}

	public int getFoodType() {
		return this.foodType;
	}

	public void setFoodType(int foodType) {
		this.foodType = foodType;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getGradeID() {
		return this.gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeTeacher() {
		return this.gradeTeacher;
	}

	public void setGradeTeacher(String gradeTeacher) {
		this.gradeTeacher = gradeTeacher;
	}

	public String getHealthProblems() {
		return this.healthProblems;
	}

	public void setHealthProblems(String healthProblems) {
		this.healthProblems = healthProblems;
	}

	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePhoneNum() {
		return this.homePhoneNum;
	}

	public void setHomePhoneNum(String homePhoneNum) {
		this.homePhoneNum = homePhoneNum;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOtherComments() {
		return this.otherComments;
	}

	public void setOtherComments(String otherComments) {
		this.otherComments = otherComments;
	}

	public String getP1cell() {
		return this.p1cell;
	}

	public void setP1cell(String p1cell) {
		this.p1cell = p1cell;
	}

	public String getP1fname() {
		return this.p1fname;
	}

	public void setP1fname(String p1fname) {
		this.p1fname = p1fname;
	}

	public String getP1lname() {
		return this.p1lname;
	}

	public void setP1lname(String p1lname) {
		this.p1lname = p1lname;
	}

	public String getP1mail() {
		return this.p1mail;
	}

	public void setP1mail(String p1mail) {
		this.p1mail = p1mail;
	}

	public int getP1relation() {
		return this.p1relation;
	}

	public void setP1relation(int p1relation) {
		this.p1relation = p1relation;
	}

	public String getP2cell() {
		return this.p2cell;
	}

	public void setP2cell(String p2cell) {
		this.p2cell = p2cell;
	}

	public String getP2fname() {
		return this.p2fname;
	}

	public void setP2fname(String p2fname) {
		this.p2fname = p2fname;
	}

	public String getP2lname() {
		return this.p2lname;
	}

	public void setP2lname(String p2lname) {
		this.p2lname = p2lname;
	}

	public String getP2mail() {
		return this.p2mail;
	}

	public void setP2mail(String p2mail) {
		this.p2mail = p2mail;
	}

	public int getP2relation() {
		return this.p2relation;
	}

	public void setP2relation(int p2relation) {
		this.p2relation = p2relation;
	}

	public int getParent1ID() {
		return this.parent1ID;
	}

	public void setParent1ID(int parent1ID) {
		this.parent1ID = parent1ID;
	}

	public int getParent2ID() {
		return this.parent2ID;
	}

	public void setParent2ID(int parent2ID) {
		this.parent2ID = parent2ID;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public int getPupilNum() {
		return this.pupilNum;
	}

	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
	}

	public int getRegPupilNum() {
		return this.regPupilNum;
	}

	public void setRegPupilNum(int regPupilNum) {
		this.regPupilNum = regPupilNum;
	}

	public String getStaffChild() {
		return this.staffChild;
	}

	public void setStaffChild(String staffChild) {
		this.staffChild = staffChild;
	}

}