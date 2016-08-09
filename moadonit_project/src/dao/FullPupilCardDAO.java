package dao;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.FullPupilCard;
import model.RegToMoadonit;
import util.DAOUtil;
import controller.MyConnection;

public class FullPupilCardDAO extends AbstractDAO {

	private String select = "SELECT * FROM fullPupilCard where pupilNum = ?";
	private String selectAll = "SELECT * FROM fullPupilCard ";
	private String selectSearch = "SELECT * FROM fullPupilCard where firstName = ?";
	private String selectFilter = "{ CALL ms2016.getFullPupilByParam (?, ?, ?, ?, ?, ?) }";
	private String SelectPupilNotInActivity = "SELECT * FROM fullPupilCard  where pupilNum  NOT IN ( select pupilNum from tbl_pupil_activities where activityNum = ? and CURDATE() <= endDate)";
	/**
	 * 
	 */
	private static final long serialVersionUID = -5859383209364623012L;

	public FullPupilCardDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public FullPupilCard selectById(int id) throws IllegalArgumentException,
			DAOException {
		FullPupilCard p = new FullPupilCard();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				p = map(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return p;
	}
	
	public List<FullPupilCard> selectAll(String sind, String sord, int rowOffset, int rowsPerPage ) throws IllegalArgumentException, DAOException {
		List<FullPupilCard> list = new ArrayList<>();
		String stat;
		if(rowOffset == 0 && rowsPerPage == 0)
			stat = selectAll +" ORDER BY "+ sind +" "+ sord ;		
		else
			stat = selectAll +" ORDER BY "+ sind +" "+ sord + " LIMIT " + rowOffset + ", " + rowsPerPage + "";	
				//(where %s,fName=null?"firstName=fname":" ");
		try (PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), stat, false); ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
}
	
	public List<FullPupilCard> SelectPupilNotInActivity(String sind, String sord,int ActivityId) throws IllegalArgumentException, DAOException {
		List<FullPupilCard> list = new ArrayList<>();
		String stat = SelectPupilNotInActivity +" ORDER BY "+ sind +" "+ sord;
		Object[] values = {   ActivityId };
				//(where %s,fName=null?"firstName=fname":" ");
		try (PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), stat, false, values); ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
}
	

	private FullPupilCard map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		FullPupilCard p = new FullPupilCard();					
		p.setPupilNum(resultSet.getInt("pupilNum"));
		p.setCellphone(resultSet.getString("cellphone"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setBirthDate(resultSet.getDate("birthDate"));
		p.setLastName(resultSet.getString("lastName"));
		p.setRegPupilNum(resultSet.getInt("regPupilNum"));		
		p.setPhotoPath(resultSet.getString("photoPath"));		
		p.setFamilyID(resultSet.getInt("familyID"));		
		p.setGradeID(resultSet.getInt("gradeID"));
		p.setGradeName(resultSet.getString("gradeName"));
		p.setGender(resultSet.getInt("gender"));
		p.setGradeTeacher(resultSet.getString("gradeTeacher"));
		p.setHealthProblems(resultSet.getString("healthProblems"));
		p.setEthiopian(resultSet.getInt("ethiopian"));
		p.setStaffChild(resultSet.getString("staffChild"));
		p.setFoodSensitivity(resultSet.getString("foodSensitivity"));
		p.setOtherComments(resultSet.getString("otherComments"));
		p.setFoodType(resultSet.getInt("foodType"));
		p.setHomeAddress(resultSet.getString("homeAddress"));
		p.setHomePhoneNum(resultSet.getString("homePhoneNum"));
		p.setP1fname(resultSet.getString("p1fname"));
		p. setP1lname (resultSet.getString("p1lname"));
		p.setP1cell(resultSet.getString("p1cell"));
		p.setP1mail(resultSet.getString("p1mail"));
		p.setP1relation(resultSet.getInt("p1relation"));
		p.setP2fname(resultSet.getString("p2fname"));
		p.setP2lname(resultSet.getString("p2lname"));
		p.setP2cell(resultSet.getString("p2cell"));
		p.setP2mail(resultSet.getString("p2mail"));
		p.setP2relation(resultSet.getInt("p2relation"));
		p.setParent1ID(resultSet.getInt("parent1ID"));
		p.setParent2ID(resultSet.getInt("parent2ID"));
		return p;
	}

	public List<FullPupilCard> fillterPupilNotInActivity(String sind, String sord,String fName,String lName,String gend,String grade,String isReg, int activityNum) {
		List<FullPupilCard> list = new ArrayList<>();
		String stat = SelectPupilNotInActivity;
		int withRegPupil = 0;
		if(fName!=null ||lName!=null || gend!=null   || (grade!=null && !grade.equals(" "))  || isReg!=null){
			stat+=" and ";
			if(fName!=null){
				stat+="firstName LIKE '%" +fName +"%' and ";
			}
			if(lName!=null){
				stat+="lastName LIKE '%" +lName +"%' and ";
			}
			if(gend!=null){
				stat+="gender =" +gend +" and ";
			}
			if(grade!=null && !grade.equals(" ")){
				stat+="gradeID =" +grade +" and ";
			}
			if(isReg!=null){
				if(isReg.equals("1")){
					//stat+="regPupilNum is not null";
					withRegPupil = 1; //-- get only registered pupil
				}
				else{
					//stat+="regPupilNum is null";
					withRegPupil = 2; //-- get only non registered pupil
				}
			}else{
				stat = stat.substring(0, stat.length()-4);
				withRegPupil = 0; //-- get all pupil    
			}
			
			if (stat.endsWith("and ")) {
				stat = stat.substring(0, stat.length()-4);
			}
		}
		///
		/*this.regToMoadonitDAO = new RegToMoadonitDAO(con);
		List<RegToMoadonit> active = regToMoadonitDAO.getActiveRegForPupil(pupil.getPupilNum());
		Boolean reg = false;
		if(!active.isEmpty()){
			RegToMoadonit r = active.get(0);
			if(r.getTblRegType1().getTypeNum()==1 && r.getTblRegType2().getTypeNum()==1 && r.getTblRegType3().getTypeNum()==1 && 
					r.getTblRegType4().getTypeNum()==1 && r.getTblRegType5().getTypeNum()==1 )
				reg = false;
			else
				reg = true;
		}*/
		///
		Object[] values = {   activityNum };
		try (PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), stat ,false,values);
						ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}
	
	public List<FullPupilCard> selectSearch(String sind, String sord,String fName,String lName,String gend,String grade,String isReg,int rowOffset,int rowsPerPage) {
		
		List<FullPupilCard> list = new ArrayList<>();
		String stat = selectAll;
		int withRegPupil = 0;
		if(fName!=null ||lName!=null || gend!=null   || (grade!=null && !grade.equals(" "))  || (isReg!=null && !isReg.trim().equals(""))){
			stat+=" where ";
			if(fName!=null && !fName.trim().equals("")){
				stat+="firstName LIKE '%" +fName +"%' and ";
			}
			if(lName!=null && !lName.trim().equals("")){
				stat+="lastName LIKE '%" +lName +"%' and ";
			}
			if(gend!=null && !gend.trim().equals("")){
				stat+="gender =" +gend +" and ";
			}
			if(grade!=null && !grade.equals(" ") && !grade.trim().equals("")){
				stat+="gradeID =" +grade +" and ";
			}
			if(isReg!=null && !isReg.trim().equals("")){
				if(isReg.equals("1")){
					//stat+="regPupilNum is not null";
					withRegPupil = 1; //-- get only registered pupil
				}
				else{
					//stat+="regPupilNum is null";
					withRegPupil = 2; //-- get only non registered pupil
				}
			}else{
				
				withRegPupil = 0; //-- get all pupil    
			}
			
			if(withRegPupil == 0 && stat.endsWith("and "))
				stat = stat.substring(0, stat.length()-4);
			else if(stat.endsWith("where "))
				stat = stat.substring(0, stat.length()-6);
		}
		
	/*	if(rowOffset == 0 && rowsPerPage == 0)
			stat = stat +" ORDER BY "+ sind +" "+ sord ;		
		else
			stat = stat +" ORDER BY "+ sind +" "+ sord + " LIMIT " + rowOffset + ", " + rowsPerPage + "";*/
		
		///
		/*this.regToMoadonitDAO = new RegToMoadonitDAO(con);
		List<RegToMoadonit> active = regToMoadonitDAO.getActiveRegForPupil(pupil.getPupilNum());
		Boolean reg = false;
		if(!active.isEmpty()){
			RegToMoadonit r = active.get(0);
			if(r.getTblRegType1().getTypeNum()==1 && r.getTblRegType2().getTypeNum()==1 && r.getTblRegType3().getTypeNum()==1 && 
					r.getTblRegType4().getTypeNum()==1 && r.getTblRegType5().getTypeNum()==1 )
				reg = false;
			else
				reg = true;
		}*/
		///
		Object[] values = {   stat, withRegPupil, rowOffset, rowsPerPage, sind, sord };
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(this.con.getConnection(), selectFilter ,values);
						ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}
	
	public List<FullPupilCard> selectSearch(String sind, String sord,String fName,String lName,String gend,String grade,
							String pupilCell, String homePhone,String p1name,String p1cell,String p2name,String p2cell,String isReg) {
		List<FullPupilCard> list = new ArrayList<>();
		String stat = selectAll;
		int withRegPupil = 0;
		if(fName!=null ||lName!=null || gend!=null   || (grade!=null && !grade.equals(" "))  || pupilCell!=null|| 
				homePhone!=null|| p1name!=null|| p1cell!=null|| p2name!=null|| p2cell!=null || (isReg!=null && !isReg.trim().equals(""))){
			stat+=" where ";
			if(fName!=null){
				stat+="firstName LIKE '%" +fName +"%' and ";
			}
			if(lName!=null){
				stat+="lastName LIKE '%" +lName +"%' and ";
			}
			if(gend!=null){
				stat+="gender =" +gend +" and ";
			}
			if(grade!=null && !grade.equals(" ")){
				stat+="gradeID =" +grade +" and ";
			}
			if(pupilCell!=null){
				stat+="cellphone LIKE '%" +pupilCell +"%' and ";
			}
			if(homePhone!=null){
				stat+="homePhoneNum LIKE '%" +homePhone +"%' and ";
			}
			if(p1name!=null){
				stat+="p1fname LIKE '%" +p1name +"%' and ";
			}
			if(p1cell!=null){
				stat+="p1cell LIKE '%" +p1cell +"%' and ";
			}
			if(p2name!=null){
				stat+="p2fname LIKE '%" +p2name +"%' and ";
			}
			if(p2cell!=null){
				stat+="p2cell LIKE '%" +p2cell +"%' and ";
			}
			if(isReg!=null){
				if(isReg.equals("1")){
					//stat+="regPupilNum is not null";
					withRegPupil = 1; //-- get only registered pupil
				}
				else{
					//stat+="regPupilNum is null";
					withRegPupil = 2; //-- get only non registered pupil
				}
			}
			
			else{
				//stat = stat.substring(0, stat.length()-4);
				withRegPupil = 0; //-- get all pupil    
			}
			
			if(withRegPupil == 0 && stat.endsWith("and "))
				stat = stat.substring(0, stat.length()-4);
			/*else
				stat = stat.substring(0, stat.length()-4);*/
		}
		
		Object[] values = {   stat, withRegPupil, 0, 0, sind, sord };
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(this.con.getConnection(), selectFilter ,values);
						ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
		/*try (PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), stat , false); ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FullPupilCard p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list; */
	}

}
