package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_pupil database table.
 * 
 */
@Entity
@Table(name="tbl_pupil")
@NamedQuery(name="Pupil.findAll", query="SELECT p FROM Pupil p")
public class Pupil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pupilNum;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String cellphone;

	private String firstName;

	private String lastName;

	private String photoPath;

	//bi-directional many-to-one association to GenderRef
	@ManyToOne
	@JoinColumn(name="gender")
	private GenderRef genderRef;

	//bi-directional many-to-one association to Family
	@ManyToOne
	@JoinColumn(name="familyID")
	private Family tblFamily;

	//bi-directional many-to-one association to Grade
	@ManyToOne
	@JoinColumn(name="gradeID")
	private Grade tblGrade;

	//bi-directional many-to-one association to RegisterPupil
	@OneToMany(mappedBy="tblPupil")
	private List<RegisterPupil> tblRegisterPupils;

	public Pupil() {
	}

	public int getPupilNum() {
		return this.pupilNum;
	}

	public void setPupilNum(int pupilNum) {
		this.pupilNum = pupilNum;
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public GenderRef getGenderRef() {
		return this.genderRef;
	}

	public void setGenderRef(GenderRef genderRef) {
		this.genderRef = genderRef;
	}

	public Family getTblFamily() {
		return this.tblFamily;
	}

	public void setTblFamily(Family tblFamily) {
		this.tblFamily = tblFamily;
	}

	public Grade getTblGrade() {
		return this.tblGrade;
	}

	public void setTblGrade(Grade tblGrade) {
		this.tblGrade = tblGrade;
	}

	public List<RegisterPupil> getTblRegisterPupils() {
		return this.tblRegisterPupils;
	}

	public void setTblRegisterPupils(List<RegisterPupil> tblRegisterPupils) {
		this.tblRegisterPupils = tblRegisterPupils;
	}

	public RegisterPupil addTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		getTblRegisterPupils().add(tblRegisterPupil);
		tblRegisterPupil.setTblPupil(this);

		return tblRegisterPupil;
	}

	public RegisterPupil removeTblRegisterPupil(RegisterPupil tblRegisterPupil) {
		getTblRegisterPupils().remove(tblRegisterPupil);
		tblRegisterPupil.setTblPupil(null);

		return tblRegisterPupil;
	}

}