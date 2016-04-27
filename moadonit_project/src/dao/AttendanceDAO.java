package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Activity;
import model.Attendance;
import model.AttendancePK;
import util.DAOUtil;
import controller.MyConnection;

public class AttendanceDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4505815614324512802L;

	private String insert = "INSERT INTO tbl_attendance"
			+ "( pupilID, specifficDate, attended_, rec_type, activityNum) VALUES (?,?,?,?,?)";

	private String update = "UPDATE tbl_attendance "
			+ "SET attended_=?, rec_type=?, activityNum=? WHERE pupilID=? and specifficDate=?";

	private String delete = "DELETE FROM tbl_attendance WHERE pupilID = ?;";

	private String select = "SELECT pupilID, specifficDate, attended_, rec_type, activityNum FROM tbl_attendance WHERE pupilID = ?";

	private Attendance map(ResultSet resultSet) throws SQLException {
		Attendance att = new Attendance();
		AttendancePK pk = new AttendancePK();
		pk.setPupilID(resultSet.getInt("pupilID"));
		pk.setSpecifficDate(resultSet.getDate("specifficDate"));
		att.setId(pk);
		att.setAttended(resultSet.getInt("attended_"));
		att.setRecType(resultSet.getInt("recType"));
		Activity act = new Activity();
		act.setActivityNum(resultSet.getInt("activityNum"));
		att.setTblActivity(act);

		return att;
	}

	public void delete(Attendance att) throws DAOException {
		Object[] values = { att.getId() };

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), delete, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Deleting attendance failed, no rows affected.");
			} else {
				att.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Attendance att) throws DAOException {
		if (att.getId() == null) {
			throw new IllegalArgumentException(
					"Attendance is not created yet, the Attendance ID is null.");
		}

		Object[] values = { att.getAttended(), att.getRecType(),
				att.getTblActivity().getActivityNum(),
				att.getId().getPupilID(), att.getId().getSpecifficDate()

		};

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), update, false, values);

		) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Updating Attendance failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Attendance selectById(AttendancePK id)
			throws IllegalArgumentException, DAOException {
		Attendance att = new Attendance();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				att = map(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return att;
	}

	public Boolean insert(Attendance att) throws IllegalArgumentException,
			DAOException {

		Object[] values = { att.getId().getPupilID(),
				att.getId().getSpecifficDate(), att.getAttended(),
				att.getRecType(), att.getTblActivity().getActivityNum() };

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating Attendance failed, no rows affected.");
			} else
				return true;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public AttendanceDAO(MyConnection con) {

		super(con);
		// TODO Auto-generated constructor stub
	}

}
