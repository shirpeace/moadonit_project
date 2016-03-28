package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Grade;
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

	private Grade map(ResultSet resultSet) throws SQLException {
		
		Grade g = new Grade();
		g.setGradeID(resultSet.getInt("gradeID"));
		g.setGradeName((resultSet.getString("gradeName")));
		g.setGradeTeacher(resultSet.getString("gradeTeacher"));
		
		// fill room with all data later
		Room r = new Room();
		r.setRoomID(resultSet.getInt("locationForMoadonit"));
		g.setTblRoom(r);
		
		  
		return g;
	}
}
