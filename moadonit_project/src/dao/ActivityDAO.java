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
	}

	private String selectCourses = "{call ms2016.getCourses (?, ?)}";
	private String searchCoursesByParam = "{ call ms2016.searchCoursesByParam( ? , ? , ?, ? , ?, ?, ? , ?, ? ,? ) }";
	private String getCurrentYearEndDate = "select getCurrentYearEndDate() as endDate";
	private String get_currentYearID = "select ms2016.get_currentYearID()";
	private String updateCourse = "{call ms2016.updateCourse (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private String insertActivity = "INSERT INTO tbl_activity (activityGroup, activityName, " +
					"weekDay, startTime, endTime, schoolYear, responsibleStaff) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private String insertActGroup = "INSERT INTO tbl_activity_group ( actGroupName, activityType)" +
					"VALUES (?, ?)";
	private String  insertCourse= "INSERT INTO tbl_course (activityNum, pricePerMonth, extraPrice, " +
					"regularOrPrivate, category, pupilCapacity) VALUES (?, ?, ?, ?, ?, ?)";

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
	public List<Activity> selectActivites(int id, int year)
			throws IllegalArgumentException, DAOException {
		List<Activity> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), selectCourses, new Object[] { id , year });
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

		if(act.getTblActivityGroup().getActivityGroupNum() == -1){
			Object[] actGroupValues = { 
				//	act.getTblActivityGroup().getActivityGroupNum(),
					act.getTblActivityGroup().getActGroupName(),
					act.getTblActivityGroup().getTblActivityType().getTypeID()
				};
			try (PreparedStatement statement3 = DAOUtil.prepareStatement(this.con.getConnection(), insertActGroup, true, actGroupValues);){
				statement3.executeUpdate();
				ResultSet generatedKeys;
				try  {
					generatedKeys = statement3.getGeneratedKeys();
					if (generatedKeys.next()) {
						act.getTblActivityGroup().setActivityGroupNum(generatedKeys.getInt(1));
					
					} else {
						throw new DAOException(
								"Creating activity group failed, no generated key obtained.");
					}
					}catch(SQLException e) {
			            throw new DAOException(e);
			        }
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}		
		
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
						act.getTblSchoolYear() == null ? null : act.getTblSchoolYear().getYearID() != 0 ? act.getTblSchoolYear().getYearID() : get_currentYearID(),
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

	/**
	 * update Course according to given id and params.
	 * @param id - id of the course
	 * @param year - if 0 then will take the current year from the DB
	 * @throws IllegalArgumentException
	 * @throws DAOException
	 * @throws SQLException 
	 */
	public Boolean insertCourse(Activity act) throws IllegalArgumentException, DAOException{
		
		try {
			// * transaction block start *//
			this.con.getConnection().setAutoCommit(false);
		} catch (SQLException e1) {
			throw new DAOException(e1);
		}
		
		if(act.getTblActivityGroup().getActivityGroupNum() == -1){
			Object[] actGroupValues = { 
				//	act.getTblActivityGroup().getActivityGroupNum(),
					act.getTblActivityGroup().getActGroupName(),
					act.getTblActivityGroup().getTblActivityType().getTypeID()
				};
			try (PreparedStatement statement3 = DAOUtil.prepareStatement(this.con.getConnection(), insertActGroup, true, actGroupValues);){
				statement3.executeUpdate();
				ResultSet generatedKeys;
				try  {
					generatedKeys = statement3.getGeneratedKeys();
					if (generatedKeys.next()) {
						act.getTblActivityGroup().setActivityGroupNum(generatedKeys.getInt(1));
					
					} else {
						throw new DAOException(
								"Creating activity group failed, no generated key obtained.");
					}
					}catch(SQLException e) {
			            throw new DAOException(e);
			        }
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		Object[] activityValues = { 
						act.getTblActivityGroup().getActivityGroupNum(), 
						act.getActivityName(), 
						act.getWeekDay(), 
						act.getStartTime(), 
						act.getEndTime(), 
						act.getTblSchoolYear() == null ? null : act.getTblSchoolYear().getYearID() != 0 ? act.getTblSchoolYear().getYearID() : get_currentYearID(),
						act.getTblStaff().getStaffID()
					};
		
		
	/*	*/
		
		try (PreparedStatement statement2 = DAOUtil.prepareStatement(this.con.getConnection(), insertActivity, true, activityValues);
				/*PreparedStatement statement3 = DAOUtil.prepareStatement(this.con.getConnection(), insertActGroup, true, actGroupValues);*/) {
		
			
				
  
				statement2.executeUpdate();
				ResultSet generatedKeys;
				try  {
					generatedKeys = statement2.getGeneratedKeys();
					if (generatedKeys.next()) {
						act.setActivityNum(generatedKeys.getInt(1));
					
					} else {
						throw new DAOException(
								"Creating activity failed, no generated key obtained.");
					}
					}catch(SQLException e) {
			            throw new DAOException(e);
			        }
				if(generatedKeys != null){
					Object[] courseValues = { 
							act.getActivityNum(), 
							act.getTblCourse().getPricePerMonth(),
							act.getTblCourse().getExtraPrice(), 
							act.getTblCourse().getRegularOrPrivate(), 
							act.getTblCourse().getCategory(), 
							act.getTblCourse().getPupilCapacity()
						};
					PreparedStatement statement1 = DAOUtil.prepareStatement(this.con.getConnection(), insertCourse, true, courseValues);
					
		            statement1.executeUpdate();
				}
				
			
				this.con.getConnection().commit();
			// * transaction block end *//
				
	            return true;
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	}
	
	private int get_currentYearID() {
		int result = 0;
		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), get_currentYearID, new Object[] { });
				ResultSet resultSet = statement.executeQuery();) {
			
			while (resultSet.next()) {
				result = resultSet.getInt("ms2016.get_currentYearID()");
				
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return result;
	}



	private Course mapCourse(ResultSet resultSet) throws SQLException {
		//activityNum, pricePerMonth, extraPrice, regularOrPrivate, category
		Course c = new Course();
		
		c.setActivityNum(resultSet.getInt("activityNum"));
		c.setCategory(resultSet.getInt("category"));
		c.setExtraPrice(resultSet.getInt("extraPrice"));
		c.setPricePerMonth(resultSet.getInt("pricePerMonth"));
		c.setRegularOrPrivate(resultSet.getString("courseTypeID"));
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
