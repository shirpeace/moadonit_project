package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.ActivityGroup;
import model.FamilyRelation;
import model.FoodType;
import model.GenderRef;
import model.GradeCode;
import model.RegSource;
import model.Staff;
import util.DAOUtil;
import controller.MyConnection;

public class GeneralDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2627663536730229029L;

	private String callFoodTypeProc = "{ call ms2016.get_FoodType_code(?) }";
	private String callfamilyRelationProc = "{ call ms2016.get_family_Relation(?) }"; // tbl_family_relation
	private String callgetRegSourceProc = "{ call ms2016.get_Reg_Source(?) }"; //
	private String get_Staff = "{ call ms2016.get_Staff(?) }"; // get_Staff
	private String get_GenderRef = "{ call ms2016.get_gender_ref(?) }"; //
	private String get_actGroup = "{ call ms2016.get_actGroup(? , ?) }"; // get_actGroup
	private String getRegDatesToValid = "{ call ms2016.getRegDatesToValid() }"; // ;
	private String updateWhoIsReg = "{ call ms2016.updateWhoIsReg(?) }";
	
	public GeneralDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public List<ActivityGroup> get_actGroup(int id, int atype) throws IllegalArgumentException,
	DAOException {

	List<ActivityGroup> list = new ArrayList<>();
	try (PreparedStatement statement = DAOUtil
			.prepareCallbackStatement(this.con.getConnection(),
					get_actGroup, new Object[] { id , atype});
			ResultSet resultSet = statement.executeQuery();) {
	
		while (resultSet.next()) {
			ActivityGroup p = mapActGroup(resultSet);
			list.add(p);
		}

} catch (SQLException e) {
	throw new DAOException(e);
}

return list;
}
	
	public List<Staff> get_Staff(int id) throws IllegalArgumentException,
			DAOException {

		List<Staff> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(),
						get_Staff, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Staff p = mapStaff(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public JSONArray get_GenderRef(int id) throws IllegalArgumentException,
	DAOException {
		//gender, genderName
		JSONArray list = new JSONArray();
		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(),
						get_GenderRef, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {
		
			while (resultSet.next()) {
				JSONObject o = new JSONObject();
				o.put("gender",resultSet.getInt("gender"));
				o.put("genderName",resultSet.getString("genderName"));
				list.add(o);
			}
		
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return list;
	}
	
	public List<FoodType> getFoodTypes(int id) throws IllegalArgumentException,
			DAOException {

		List<FoodType> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(),
						callFoodTypeProc, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FoodType p = mapFoodType(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	public List<FamilyRelation> getFamilyRelation(int id)
			throws IllegalArgumentException, DAOException {

		List<FamilyRelation> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), callfamilyRelationProc,
				new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				FamilyRelation p = mapFamilyRelation(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	public List<RegSource> getRegSource(int id)
			throws IllegalArgumentException, DAOException {

		List<RegSource> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), callgetRegSourceProc,
				new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				RegSource p = mapRegSource(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private FamilyRelation mapFamilyRelation(ResultSet resultSet)
			throws SQLException {
		FamilyRelation fr = new FamilyRelation();
		fr.setIdFamilyRelation(resultSet.getByte("id_family_relation"));
		fr.setRelation(resultSet.getString("relation"));
		return fr;
	}

	private FoodType mapFoodType(ResultSet resultSet) throws SQLException {

		FoodType ft = new FoodType();
		ft.setFoodTypeID(resultSet.getByte("foodTypeID"));
		ft.setFoodType(resultSet.getString("foodType"));

		return ft;
	}

	private RegSource mapRegSource(ResultSet resultSet) throws SQLException {

		RegSource rs = new RegSource();
		rs.setSourceNum(resultSet.getByte("sourceNum"));
		rs.setSourceName(resultSet.getString("sourceName"));

		return rs;
	}

	private Staff mapStaff(ResultSet resultSet) throws IllegalArgumentException, SQLException {
		Staff p = new Staff();
		p.setStaffID(resultSet.getInt("staffID"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		p.setCellphone(resultSet.getString("cellphone"));
		p.setEmail(resultSet.getString("email"));
		
		return p;
	}
	
	private ActivityGroup mapActGroup(ResultSet resultSet) throws IllegalArgumentException, SQLException {
		ActivityGroup p = new ActivityGroup();
		p.setActivityGroupNum(resultSet.getInt("activityGroupNum"));
		p.setActGroupName(resultSet.getString("actGroupName"));
		
		return p;
	}
	
	public List<Object> getRegDatesToValid() throws IllegalArgumentException,
			DAOException {
		List<Object> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getRegDatesToValid, new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Date startDate = resultSet.getDate("startDate");
				Date lastDateToReg = resultSet.getDate("lastDateToReg");
				int numOfDaysToModify = resultSet.getByte("@numOfDaysToModify");

				list.add(startDate.getTime());
				list.add(lastDateToReg.getTime());
				list.add(numOfDaysToModify);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	public static String getValIfNotNull(String val){
		
		return val != null  ? val : "";
	}

	public void updateWhoIsReg(Date date) {
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), updateWhoIsReg, new Object[] {date});
				) {

			int resultSet = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
			
		}
	}
}
