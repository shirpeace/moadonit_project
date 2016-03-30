package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GenderRef;
import util.DAOUtil;
import controller.MyConnection;

public class GenderDAO extends AbstractDAO {

	private String select = "SELECT gender, genderName FROM tbl_gender_ref where gender = ?;";

	private static final long serialVersionUID = -7282625935008626298L;

	public GenderDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public GenderRef selectById(int id) throws IllegalArgumentException,
			DAOException {
		GenderRef gender = new GenderRef();

		try (
				PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), select, false, new Object[] { id });
		
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				gender = map(resultSet);

				// TODO fill room data from room key in the grade
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return gender;
	}

	private GenderRef map(ResultSet resultSet) throws SQLException {

		GenderRef g = new GenderRef(); 
		g.setGender(resultSet.getByte("gender"));
		g.setGenderName(resultSet.getString("genderName"));
		
		return g;
	}

}
