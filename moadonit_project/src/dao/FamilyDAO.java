package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Family;
import util.DAOUtil;
import controller.MyConnection;

public class FamilyDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4505815614324512802L;

	private String insert = "INSERT INTO tbl_family"
			+ "( homeAddress, homePhoneNum, parentID1, parentID2) VALUES (?,?,?,?)";

	private String update = "UPDATE tbl_family "
			+ "SET homeAddress=?,homePhoneNum=?,parentID1=?,parentID2=? WHERE familyID = ?";

	private String delete = "DELETE FROM tbl_family WHERE familyID = ?;";

	private String select = "SELECT familyID, homeAddress, homePhoneNum, parentID1, parentID2 FROM tbl_family WHERE familyID = ?";

	private Family map(ResultSet resultSet) throws SQLException {
		Family fam = new Family();
		fam.setFamilyID(resultSet.getInt("familyID"));
		fam.setHomeAddress(resultSet.getString("homeAddress"));
		fam.setHomePhoneNum(resultSet.getString("homePhoneNum"));
		/*
		 * fam.set(resultSet.getString("parentID1"));
		 * fam.setParentID(resultSet.getInt("parentID2"));
		 */
		return fam;
	}

	public void delete(Family fam) throws DAOException {
		Object[] values = { fam.getFamilyID() };

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), delete, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Deleting user failed, no rows affected.");
			} else {
				fam.setFamilyID(0);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Family fam) throws DAOException {
		if (fam.getFamilyID() == 0) {
			throw new IllegalArgumentException(
					"User is not created yet, the user ID is null.");
		}

		// homeAddress=?,homePhoneNum=?,parentID1=?,parentID2=? WHERE familyID =
		// ?"
		Object[] values = { fam.getHomeAddress(), fam.getHomePhoneNum(),
				fam.getTblParent1().getParentID(),
				fam.getTblParent2().getParentID(), fam.getFamilyID()

		};

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), update, false, values);

		) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Updating user failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Family selectById(int id) throws IllegalArgumentException,
			DAOException {
		Family fam = new Family();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				fam = map(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return fam;
	}

	public void insert(Family fam) throws IllegalArgumentException, DAOException {
		if (fam.getFamilyID() != 0) {
			throw new IllegalArgumentException(
					"Family is already created, the parent ID is not null.");
		}

		Object[] values = { fam.getHomeAddress(), fam.getHomePhoneNum(),
				fam.getTblParent1().getParentID(),
				fam.getTblParent2().getParentID()

		};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					fam.setFamilyID(generatedKeys.getInt(1));
				} else {
					throw new DAOException(
							"Creating family failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public FamilyDAO(MyConnection con) {

		super(con);
		// TODO Auto-generated constructor stub
	}

}
