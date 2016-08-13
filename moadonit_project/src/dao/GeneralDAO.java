package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.FoodType;
import model.GenderRef;
import model.GradeCode;
import util.DAOUtil;
import controller.MyConnection;

public class GeneralDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2627663536730229029L;

	private String callFoodTypeProc = "{ call ms2016.get_FoodType_code(?) }";

	public GeneralDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public FoodType selectById(int id) throws IllegalArgumentException,
			DAOException {
		FoodType foodType = new FoodType();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), callFoodTypeProc, false,
				new Object[] { id });

		ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				foodType = mapFoodType(resultSet);

				// TODO fill room data from room key in the grade
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return foodType;
	}

	public List<FoodType> getFoodTypes(int id) throws IllegalArgumentException,
			DAOException {

		List<FoodType> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), callFoodTypeProc, new Object[] { id });
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

	private FoodType mapFoodType(ResultSet resultSet) throws SQLException {

		FoodType ft = new FoodType();
		ft.setFoodTypeID(resultSet.getByte("foodTypeID"));
		ft.setFoodType(resultSet.getString("foodType"));

		return ft;
	}

}
