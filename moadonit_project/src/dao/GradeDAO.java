package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Grade;
import model.RegToMoadonit;
import model.Room;
import util.DAOUtil;
import controller.MyConnection;

public class GradeDAO extends AbstractDAO {

	public GradeDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String select = "SELECT gradeID,  "
			+ "tbl_grade.gradeName,    tbl_grade.gradeTeacher,   "
			+ " tbl_grade.locationForMoadonit FROM tbl_grade where gradeid = ?";

	private String selectIndex = "SELECT gradeID, tbl_grade.gradeName FROM tbl_grade";

	public Grade selectById(int id) throws IllegalArgumentException,
			DAOException {
		Grade grade = new Grade();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				grade = map(resultSet);

				// TODO fill room data from room key in the grade
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return grade;
	}

	public List<Grade> selectIndex() throws IllegalArgumentException,
			DAOException {
		
		List<Grade> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), selectIndex, false, new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Grade p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private Grade map(ResultSet resultSet) throws SQLException {

		Grade g = new Grade();
		g.setGradeID(resultSet.getInt("gradeID"));
		g.setGradeName((resultSet.getString("gradeName")));
		return g;
	}
}
