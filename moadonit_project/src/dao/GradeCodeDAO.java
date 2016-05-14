package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.GenderRef;
import model.GradeCode;
import util.DAOUtil;
import controller.MyConnection;

public class GradeCodeDAO extends AbstractDAO {

	/**
	 * select a specific grade by a giving id , or all grade if id is not presented
	 * {@value id} the id , if not giving , 0 is sent to proc
	 */
	private String selectIndex = "{ call ms2016.get_grade_code (?) }";

	public GradeCodeDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5155823344858834940L;

/*	public GradeCode selectById(int id) throws IllegalArgumentException,
			DAOException {
		GradeCode grade = new GradeCode();

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
	}*/

	public List<GradeCode> selectIndex(int id) throws IllegalArgumentException,
			DAOException {

		List<GradeCode> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), selectIndex, new Object[] {  id});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				GradeCode p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private GradeCode map(ResultSet resultSet) throws SQLException {

		GradeCode g = new GradeCode();
		g.setGradeID(resultSet.getInt("gradeID"));
		g.setGradeName((resultSet.getString("gradeName")));
		g.setGradeColor(resultSet.getString("gradeColor"));
		return g;
	}

}
