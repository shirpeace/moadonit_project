package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import controller.MyConnection;
import model.OneTimeReg;
import model.Pupil;
import model.RegSource;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.RegType;
import model.User;
import util.DAOUtil;

public class ReportsDAO extends AbstractDAO {

	public ReportsDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String getAllCourseRegsData = "{ call ms2016.getAllCourseRegsData() }";
	private String getGirlsRegsToCourses = "select a.activityNum, activityName, count(pa.pupilNum) as girlRegs " 
				+ " from tbl_course c inner join tbl_activity a on c.activityNum = a.activityNum "
				+	" left join tbl_pupil_activities pa on a.activityNum = pa.activityNum "
				+	" left join tbl_pupil p on p.pupilNum=pa.pupilNum "
				+   " where p.gender =2 	group by a.activityNum;";
	private String getBoyRegsToCourses = "{ call getBoyRegsToCourses() }";
	private String getcapacityToCourses = "select a.activityNum, activityName, pupilCapacity " 
			+ " from tbl_course c inner join tbl_activity a on c.activityNum = a.activityNum ;";
	

	@SuppressWarnings("unchecked")
	public JSONArray getAllCourseRegsData() throws IllegalArgumentException, DAOException {
		
		JSONArray CourseRegsData = new JSONArray();
		Map<Integer,JSONObject> jsonMap = new HashMap<Integer,JSONObject>();
		
		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(), getBoyRegsToCourses,
						new Object[] {});
				ResultSet resultSet1 = statement.executeQuery();
				PreparedStatement statement2 = DAOUtil
						.prepareStatement(this.con.getConnection(), getGirlsRegsToCourses,
								false, new Object[] {});
						ResultSet resultSet2 = statement2.executeQuery(); 
						PreparedStatement statement3 = DAOUtil
						.prepareStatement(this.con.getConnection(), getcapacityToCourses,
								false, new Object[] {});
						ResultSet resultSet3 = statement3.executeQuery();){

			while (resultSet3.next()) {
				JSONObject row;
				
				row =  new JSONObject();
				jsonMap.put(resultSet3.getInt("activityNum"), row);
				row.put("course",resultSet3.getString("activityName"));
				row.put("girls",0);		
				row.put("boys",0);
				row.put("free",resultSet3.getString("pupilCapacity"));			
				
			}
			
			while (resultSet1.next()) {
				JSONObject row;
				row =  jsonMap.get(resultSet1.getInt("activityNum"));
				int last = Integer.valueOf((String) row.get("free")) - resultSet1.getInt("boyRegs");
				row.put("boys",resultSet1.getString("boyRegs"));
				row.put("free",""+last);	
			}
			
			while (resultSet2.next()) {
				JSONObject row;
				row =  jsonMap.get(resultSet2.getInt("activityNum"));
				int last = Integer.valueOf((String) row.get("free")) - resultSet2.getInt("girlRegs");
				row.put("girls",resultSet2.getString("girlRegs"));
				row.put("free",""+last);				
			}

			
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		 Iterator<Entry<Integer, JSONObject>> it = jsonMap.entrySet().iterator();
		    while (it.hasNext()) {
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
		        CourseRegsData.add(pair.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		return CourseRegsData;
	}

/*	public JSONArray get()
			throws IllegalArgumentException, DAOException {
		JSONArray CourseRegsData = new JSONArray();

		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(), get,
						new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				
				JSONObject row =  mapGet(resultSet);
				CourseRegsData.add(row);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return CourseRegsData;
	}*/
	
	@SuppressWarnings("unchecked")
	private JSONObject mapGet(ResultSet resultSet) throws SQLException {
		
		JSONObject row =  new JSONObject();		
		row.put("activityName", resultSet.getString("activityName"));
		row.put("רשומים",resultSet.getString("NumberOfReg"));

			return row;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject mapCourseData(ResultSet resultSet) throws SQLException {
		
		JSONObject row =  new JSONObject();		
		row.put("activityNum", resultSet.getInt("activityNum"));
		row.put("regularOrPrivate",resultSet.getString("regularOrPrivate"));
		row.put("category", resultSet.getInt("category"));
		row.put("activityName", resultSet.getString("activityName"));
		row.put("weekDay", resultSet.getString("weekDay"));
		row.put("pupilCapacity", resultSet.getInt("pupilCapacity"));
		row.put("gender", resultSet.getInt("gender"));
		row.put("numOfRegsbygender", resultSet.getInt("numOfRegsbygender"));
		row.put("available",resultSet.getInt("available"));
		
			return row;
	}

}
