package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import model.Activity;
import model.ActivityType;
import model.Course;
import model.RegToMoadonit;
import model.Staff;
import util.DAOUtil;
import controller.MyConnection;

public class ActivityDAO extends AbstractDAO {

	public ActivityDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	private String selectCourses = "{call ms2016.getCourses (?)}";
	private String searchCoursesByParam = "{ call ms2016.searchCoursesByParam( ? , ? , ?, ? , ?, ?, ? , ?, ? ) }";
	private String getCurrentYearEndDate = "select getCurrentYearEndDate() as endDate";
	/**
	 * getCourses
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @param _activityNum
	 * @param _activityName
	 * @param _weekDay
	 * @param _startTime
	 * @param _endTime
	 * @param firstName
	 * @param _pricePerMonth
	 * @param _extraPrice
	 * @param _regularOrPrivate
	 * @param _category
	 * @return
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	public List<Activity> searchCoursesByParam( String _activityName, String _weekDay,String _startTime,
			String _endTime, String firstName, float _pricePerMonth, float _extraPrice, String _regularOrPrivate, int _category)
			throws IllegalArgumentException, DAOException {
		List<Activity> list = new ArrayList<>();

		Object[] values = {    _activityName, _weekDay, 
				_startTime, _endTime, firstName, _pricePerMonth, _extraPrice, _regularOrPrivate, _category};
		
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), searchCoursesByParam, values );
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Activity p = mapActivity(resultSet);
				if (p.getTblActivityType().getTypeID() == 1) {
					Course c = mapCourse(resultSet);
					c.setActivityNum(p.getActivityNum());
					c.setTblActivity(p);
					p.setTblCourse(c);
					list.add(p);
				}
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}
	
	
	
	/**
	 * Get courses/Course accocding to given id.
	 * @param id - id of the course, if 0 returns all courses
	 * @return list of courses
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	public List<Activity> selectActivites(int id)
			throws IllegalArgumentException, DAOException {
		List<Activity> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), selectCourses, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Activity p = mapActivity(resultSet);
				if (p.getTblActivityType().getTypeID() == 1) {
					Course c = mapCourse(resultSet);
					c.setActivityNum(p.getActivityNum());
					c.setTblActivity(p);
					p.setTblCourse(c);
					list.add(p);
				}
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private Course mapCourse(ResultSet resultSet) throws SQLException {
		//activityNum, pricePerMonth, extraPrice, regularOrPrivate, category
		Course c = new Course();
		
		c.setActivityNum(resultSet.getInt("activityNum"));
		c.setCategory(resultSet.getInt("category"));
		c.setExtraPrice(resultSet.getInt("extraPrice"));
		c.setPricePerMonth(resultSet.getInt("pricePerMonth"));
		c.setRegularOrPrivate(resultSet.getString("regularOrPrivate"));

		return c;
	}

	private Activity mapActivity(ResultSet resultSet) throws SQLException {
		//, , , , , , schoolYear, responsibleStaff, 
		Activity act = new Activity();
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		
		Staff s = new Staff();
		s.setStaffID(resultSet.getInt("activityType"));
		s.setFirstName(resultSet.getString("firstName") );
		s.setLastName(resultSet.getString("lastName") );
		act.setTblStaff(s);		
		
		act.setTblActivityType(type);
		act.setActivityName(resultSet.getString("activityName"));
		act.setActivityNum(resultSet.getInt("activityNum"));		
		act.setStartTime(resultSet.getTime("startTime"));
		act.setEndTime(resultSet.getTime("endTime"));
		act.setWeekDay(resultSet.getString("weekDay"));
		//act.setTblSchoolYear(null);
		//act.setTblStaff(null);		
		
		return act;
	}



	public Date getCurrentYearEndDate() {
		// TODO Auto-generated method stub
		Date date = null;
		
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), getCurrentYearEndDate, false, new Object[] { });
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				date = resultSet.getDate("endDate");
			}

			return date;
		} catch (SQLException e) {
			throw new DAOException(e);
		}	

	}

}
