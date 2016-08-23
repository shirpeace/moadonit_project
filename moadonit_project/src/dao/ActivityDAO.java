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
import model.ActivityGroup;
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
	private String searchCoursesByParam = "{ call ms2016.searchCoursesByParam( ? , ? , ?, ? , ?, ?, ? , ?, ? ,? ) }";
	private String getCurrentYearEndDate = "select getCurrentYearEndDate() as endDate";
	private String updateCourse = "{call ms2016.updateCourse (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
	 * @param _staffName
	 * @param _pricePerMonth
	 * @param _extraPrice
	 * @param _regularOrPrivate
	 * @param _category
	 * @return
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	public List<Activity> searchCoursesByParam( String _activityName, String _weekDay,String _startTime,
			String _endTime, String _staffName, float _pricePerMonth, float _extraPrice, 
			String _regularOrPrivate, int _category, String _actGroupName)
			throws IllegalArgumentException, DAOException {
		List<Activity> list = new ArrayList<>();

		Object[] values = {    _activityName, _weekDay, 
				_startTime, _endTime, _staffName, _pricePerMonth, _extraPrice, _regularOrPrivate, _category, _actGroupName};
		
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), searchCoursesByParam, values );
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Activity act = mapActivity(resultSet);
				if (act.getTblActivityGroup().getTblActivityType().getTypeID() == 1) {
					Course c = mapCourse(resultSet);
					c.setActivityNum(act.getActivityNum());
					c.setTblActivity(act);
					act.setTblCourse(c);
					list.add(act);
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
				if (p.getTblActivityGroup().getTblActivityType().getTypeID() == 1) {
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
	 * update Course according to given id and params.
	 * @param id - id of the course
	 * @param year - if 0 then will take the current year from the DB
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 */
	/*public void updateCourse(int id, String actName, String weekDay, Time start, 
				Time end, int staffID, float price, float extraPrice, String regularOrPrivate,
				int category, int actGroup, int capacity, int year)*/
	public Boolean updateCourse(Activity act)
			throws IllegalArgumentException, DAOException {
		List<Activity> list = new ArrayList<>();

		Object[] values = { 
						act.getActivityNum(), 
						act.getActivityName(), 
						act.getWeekDay(), 
						act.getStartTime(), 
						act.getEndTime(), 
						act.getTblStaff().getStaffID(), 
						act.getTblCourse().getPricePerMonth(),
						act.getTblCourse().getExtraPrice(), 
						act.getTblCourse().getRegularOrPrivate(), 
						act.getTblCourse().getCategory(), 
						act.getTblActivityGroup().getActivityGroupNum(), 
						act.getTblCourse().getPupilCapacity(),
						act.getTblSchoolYear() != null ? act.getTblSchoolYear().getYearID() : null
					};
		
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), updateCourse, values);) {

			{
	            int affectedRows = statement.executeUpdate();
	            if (affectedRows > 1) {
	                throw new DAOException("Updating Row was not currect , " + affectedRows + " rows affected.");
	            }
			}
	            
	            return true;
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	}

	
	private Course mapCourse(ResultSet resultSet) throws SQLException {
		//activityNum, pricePerMonth, extraPrice, regularOrPrivate, category
		Course c = new Course();
		
		c.setActivityNum(resultSet.getInt("activityNum"));
		c.setCategory(resultSet.getInt("category"));
		c.setExtraPrice(resultSet.getInt("extraPrice"));
		c.setPricePerMonth(resultSet.getInt("pricePerMonth"));
		c.setRegularOrPrivate(resultSet.getString("regularOrPrivate"));
		c.setPupilCapacity(resultSet.getInt("pupilCapacity"));
		
		return c;
	}

	private Activity mapActivity(ResultSet resultSet) throws SQLException {
		//, , , , , , schoolYear, responsibleStaff, 
		Activity act = new Activity();
		
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		
		ActivityGroup ag = new ActivityGroup();
		ag.setActivityGroupNum(resultSet.getInt("activityGroup")); 
		ag.setActGroupName(resultSet.getString("actGroupName"));
		ag.setTblActivityType(type);
		
		Staff s = new Staff();
		s.setStaffID(resultSet.getInt("staffID"));
		s.setFirstName(resultSet.getString("firstName") );
		s.setLastName(resultSet.getString("lastName") );
		act.setTblStaff(s);		
		
		act.setTblActivityGroup(ag);
		
		act.setActivityName(resultSet.getString("activityName"));
		act.setActivityNum(resultSet.getInt("activityNum"));		
		act.setStartTime(resultSet.getTime("startTime"));
		act.setEndTime(resultSet.getTime("endTime"));
		act.setWeekDay(resultSet.getString("weekDay"));
		//act.setTblSchoolYear(null);	
		
		return act;
	}



	public Date getCurrentYearEndDate() {
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
