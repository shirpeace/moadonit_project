package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.FoodType;
import model.RegisterPupil;
import util.DAOUtil;
import controller.MyConnection;

public class RegisterPupilDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 795371246335541689L;

	private String insert = "INSERT INTO tbl_register_pupil"
			+ "(pupilNum, healthProblems, ethiopian, staffChild, foodSensitivity, otherComments, foodType) VALUES (?,?,?,?,?,?,?)";

	private String select = "SELECT pupilNum, healthProblems, ethiopian, staffChild, foodSensitivity, otherComments, foodType FROM tbl_register_pupil WHERE  pupilNum = ?";

	private String update = "UPDATE tbl_register_pupil SET healthProblems=?,ethiopian=?,staffChild=?,foodSensitivity=?,otherComments=?,foodType=? WHERE pupilNum = ?";

	private String delete = "DELETE FROM tbl_register_pupil WHERE pupilNum = ?";

	public RegisterPupilDAO(MyConnection con) {
		super(con);

	}

	public void delete(RegisterPupil p) throws DAOException {
		Object[] values = { p.getPupilNum() };

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), delete, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Deleting pupil failed, no rows affected.");
			} else {
				p.setPupilNum(0);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void insert(RegisterPupil p) throws IllegalArgumentException, DAOException {
		if (p.getPupilNum() != 0) {
			throw new IllegalArgumentException(
					"parent is already created, the parent ID is not null.");
		}

		Object[] values = {

				/*
				pupilNum, healthProblems, ethiopian, staffChild, foodSensitivity, otherComments, foodType
				 */
				 p.getPupilNum(), p.getHealthProblems(), p.getEthiopian(), p.getStaffChild(),
						p.getFoodSensitivity(), p.getOtherComments(),
						p.getTblFoodType().getFoodTypeID()
				};

		try (

		    PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating pupil failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					p.setPupilNum(generatedKeys.getInt(1));
				} else {
					throw new DAOException(
							"Creating pupil failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void update(RegisterPupil p) throws DAOException {
		if (p.getPupilNum() == 0) {
			throw new IllegalArgumentException(
					"Pupil is not created yet, the user ID is null.");
		}

		Object[] values = {

		/*
		 * healthProblems=?,ethiopian=?,staffChild=?,foodSensitivity=?,otherComments
		 * =?, foodType=? WHERE pupilNum = ?";
		 */
		p.getHealthProblems(), p.getEthiopian(), p.getStaffChild(),
				p.getFoodSensitivity(), p.getOtherComments(),
				p.getTblFoodType().getFoodTypeID(), p.getPupilNum()

		};

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), update, false, values);

		) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Updating pupil failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public RegisterPupil selectById(int id) throws IllegalArgumentException,
			DAOException {
		RegisterPupil regP = new RegisterPupil();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				regP = map(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return regP;
	}

	private RegisterPupil map(ResultSet resultSet) throws SQLException {
		
		RegisterPupil  regP= new RegisterPupil();
		
		regP.setPupilNum(resultSet.getInt("pupilNum"));
		regP.setHealthProblems(resultSet.getString("healthProblems"));
		regP.setFoodSensitivity(resultSet.getString("foodSensitivity"));
		regP.setOtherComments(resultSet.getString("otherComments"));
		regP.setEthiopian(resultSet.getInt("ethiopian"));
		regP.setStaffChild(resultSet.getString("staffChild"));
		FoodType ft = new FoodType();
		ft.setFoodType(resultSet.getString("foodType"));
		regP.setTblFoodType(ft);
		
		return regP;
	}

}
