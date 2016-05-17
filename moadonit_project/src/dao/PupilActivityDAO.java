package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.ActivityType;
import model.Pupil;
import model.PupilActivity;
import model.PupilActivityPK;
import util.DAOUtil;
import controller.MyConnection;

public class PupilActivityDAO extends AbstractDAO {

	public PupilActivityDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	private String getPupilInCourse = "{ call ms2016.getPupilInCourse( ? ) }";
	private String insertPupilActivity = "{ call ms2016.insertPupilActivity(?,?,?,?,?,?) }";
	/**
	 *  { call ms2016.insertPupilActivity(?,?,?,?,?,?) }
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param id course id
	 * @return list of pupil in course
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	public List<PupilActivity> getPupilInCourse(int id)
			throws IllegalArgumentException, DAOException {
		List<PupilActivity> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getPupilInCourse, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			
			while (resultSet.next()) {
				PupilActivity p = mapPupilActivity(resultSet);				
				list.add(p);
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private PupilActivity mapPupilActivity(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub activityNum, activityName, pupilNum, startDate, regDate, endDate, firstName, lastName
		PupilActivity pa = new PupilActivity();
		PupilActivityPK pk = new PupilActivityPK();
		pk.setActivityNum(1);
		pk.setPupilNum(1);
		pa.setId(pk);
		
		Activity act = new Activity();
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		act.setTblActivityType(type);
		act.setActivityName(resultSet.getString("activityName"));
		
		pa.setTblActivity(act);
		
		pa.setRegDate(resultSet.getDate("regDate"));
		pa.setStartDate(resultSet.getDate("startDate"));
		pa.setEndDate(resultSet.getDate("endDate"));
		
		Pupil p = new Pupil();
		p.setPupilNum(resultSet.getInt("pupilNum"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		
		pa.setTblPupil(p);
		
		return pa;
	}
	
	public boolean insert(PupilActivity pa) throws IllegalArgumentException, DAOException {

		boolean result = false;
		if (pa.getId() == null) {
			throw new IllegalArgumentException(
					"Cant Add Row , the PK ID is not null.");
		}

		//pupilNum,activityNum,startDate,regDate,writtenBy,endDate
		
		Object[] values = { pa.getId().getPupilNum(),
				pa.getId().getActivityNum(),
				DAOUtil.toSqlDate(pa.getStartDate()),
				DAOUtil.toSqlDate(pa.getRegDate()),
				pa.getTblUser().getUserID(),
				DAOUtil.toSqlDate(pa.getEndDate()) };

		try (

		PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), insertPupilActivity, values);) {
			int affectedRows = statement.executeUpdate();
			result = true;

			if (affectedRows == 0) {
				result = false;
				throw new DAOException(
						"Creating PupilActivity for Pupilid = " + pa.getId().getPupilNum() + " failed, no rows affected.");
			}

		} catch (SQLException e) {
			result = false;
			throw new DAOException(e);
		}

		return result;
	}
	
}
