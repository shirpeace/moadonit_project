package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.ActivityGroup;
import model.ActivityType;
import model.GenderRef;
import model.GradeCode;
import model.GradeInYear;
import model.GradeInYearPK;
import model.Pupil;
import model.PupilActivity;
import model.PupilActivityPK;
import model.SchoolYear;
import util.DAOUtil;
import controller.MyConnection;

public class PupilActivityDAO extends AbstractDAO {

	public PupilActivityDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	private String getPupilInCourse = "{ call ms2016.getPupilInCourse( ?, ? , ?) }";
	private String insertPupilActivity = "{ call ms2016.insertPupilActivity(?,?,?,?,?,?) }";
	private String update = "{ call ms2016.updatePupilInCourse(?,? ,? , ?,? , ?) }";
	private String delete = "DELETE FROM tbl_pupil_activities where pupilNum = ? and activityNum = ? ";
	private String getCoursesByPupilNum = "{ call ms2016.getCoursesByPupilNum( ? ) } ";
	/**
	 * { call ms2016.insertPupilActivity(?,?,?,?,?,?) }
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param id
	 *            course id
	 * @return list of pupil in course
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	public List<PupilActivity> getPupilInCourse(int id, Date date, int year)
			throws IllegalArgumentException, DAOException {
		List<PupilActivity> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(),
						getPupilInCourse, new Object[] { id, date, year });
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

	/**
	 * get courses of pupil
	 * 
	 * @param pupilID
	 * @return List<Activity> of courses
	 */
	public List<Activity> getCoursesByPupilNum(int pupilID) {
		// TODO Auto-generated method stub
		List<Activity> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getCoursesByPupilNum,
				new Object[] { pupilID });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Activity act = mapCourses(resultSet);
				list.add(act);

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	/**
	 * map a course data for pupil - to show in week gridview
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Activity mapCourses(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		Activity act = new Activity();

		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));

		ActivityGroup ag = new ActivityGroup();
		ag.setActivityGroupNum(resultSet.getInt("activityGroup"));

		// type.getTblActivityGroups().add(ag);

		ag.setTblActivityType(type);

		act.setTblActivityGroup(ag);

		act.setActivityName(resultSet.getString("activityName"));
		act.setActivityNum(resultSet.getInt("activityNum"));
		act.setStartTime(resultSet.getTime("startTime"));
		act.setEndTime(resultSet.getTime("endTime"));
		act.setWeekDay(resultSet.getString("weekDay"));

		PupilActivity pa = new PupilActivity();
		PupilActivityPK pk = new PupilActivityPK();
		pk.setActivityNum(resultSet.getInt("activityNum"));
		pk.setPupilNum(resultSet.getInt("pupilNum"));
		pk.setStartDate(resultSet.getDate("startDate"));
		
		pa.setId(pk);
		//pa.setStartDate(resultSet.getDate("startDate"));

		List<PupilActivity> list = new ArrayList<PupilActivity>();
		list.add(pa);

		act.setTblPupilActivities(list);

		return act;
	}

	private PupilActivity mapPupilActivity(ResultSet resultSet)
			throws SQLException {
		// TODO Auto-generated method stub activityNum, activityName, pupilNum,
		// startDate, regDate, endDate, firstName, lastName
		PupilActivity pa = new PupilActivity();

		PupilActivityPK pk = new PupilActivityPK();
		pk.setActivityNum(resultSet.getInt("activityNum"));
		pk.setPupilNum(resultSet.getInt("pupilNum"));
		pk.setStartDate(resultSet.getDate("startDate"));
		
		pa.setId(pk);

		Activity act = new Activity();
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		
		ActivityGroup ag = new ActivityGroup();
		ag.setActivityGroupNum(resultSet.getInt("activityGroup"));

		ag.setTblActivityType(type);

		act.setTblActivityGroup(ag);

		act.setActivityName(resultSet.getString("activityName"));

		pa.setTblActivity(act);

		pa.setRegDate(resultSet.getDate("regDate"));
		//pa.setStartDate(resultSet.getDate("startDate"));
		pa.setEndDate(resultSet.getDate("endDate"));

		SchoolYear sy = new SchoolYear();
		sy.setYearID(resultSet.getInt("yearID"));
		
		
		GradeInYear gy = new GradeInYear();
		
		gy.setTblSchoolYear(sy);
		
		GradeCode gc = new GradeCode();
		gc.setGradeID(resultSet.getInt("gradeID"));
		gc.setGradeName(resultSet.getString("gradeName"));
		gc.setGradeColor(resultSet.getString("gradeColor"));
		
		gy.setTblGradeCode(gc);
		
		Pupil p = new Pupil();
		ArrayList<GradeInYear> l = new ArrayList<GradeInYear>();
		l.add(gy);
		
		p.setTblGradeInYears1(l);
		p.setPupilNum(resultSet.getInt("pupilNum"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		GenderRef gen = new GenderRef();
		gen.setGenderName(resultSet.getString("genderName"));
		gen.setGender(resultSet.getInt("gender"));
		p.setTblGenderRef(gen);

		pa.setTblPupil(p);

		return pa;
	}

	public boolean insert(PupilActivity pa) throws IllegalArgumentException,
			DAOException {

		boolean result = false;
		if (pa.getId() == null) {
			throw new IllegalArgumentException(
					"Cant Add Row , the PK ID is  null.");
		}

		// pupilNum,activityNum,startDate,regDate,writtenBy,endDate

		Object[] values = { pa.getId().getPupilNum(),
				pa.getId().getActivityNum(),
				DAOUtil.toSqlDate(pa.getId().getStartDate()),
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
				throw new DAOException("Creating PupilActivity for Pupilid = "
						+ pa.getId().getPupilNum()
						+ " failed, no rows affected.");
			}

		} catch (SQLException e) {
			result = false;
			throw new DAOException(e);
		}

		return result;
	}

	public boolean update(PupilActivity pa) {
		// TODO Auto-generated method stub
		if (pa.getId() == null) {
			throw new IllegalArgumentException(
					"Row is not created yet, the PupilActivity ID is null.");
		}

		Object[] values = { pa.getId().getPupilNum(),
				pa.getId().getActivityNum(), pa.getId().getStartDate(),
				pa.getRegDate(), pa.getTblUser().getUserID(), pa.getEndDate()

		};

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), update, values);

		) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows > 1) {
				throw new DAOException("Updating Row was not currect , "
						+ affectedRows + " rows affected.");
			}

			return true;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean delete(PupilActivity pa) {
		// TODO Auto-generated method stub
		if (pa.getId() == null) {
			throw new IllegalArgumentException(
					"cant delete row, the Registration ID is null.");
		}

		Object[] values = {
				// pk of row
				pa.getId().getPupilNum(), pa.getId().getActivityNum()

		};

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), delete, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Deleting PupilActivity failed, no rows affected.");
			} else {
				pa.setId(null);
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
