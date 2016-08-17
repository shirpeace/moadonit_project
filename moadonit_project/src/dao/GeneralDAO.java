package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.FamilyRelation;
import model.FoodType;
import model.GenderRef;
import model.GradeCode;
import model.RegSource;
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

	private String getRegDatesToValid = "{ call ms2016.getRegDatesToValid() }"; // ;

	public GeneralDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
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

	public List<Object> getRegDatesToValid() throws IllegalArgumentException, DAOException {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getRegDatesToValid,
				new Object[] { });
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

}
