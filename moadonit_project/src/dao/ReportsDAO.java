package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private String get = "select activityName, count(pa.pupilNum) as NumberOfReg "
			+ " from tbl_activity a left join tbl_pupil_activities pa"
			+ " on a.activityNum = pa.activityNum where a.activitytype = 1 group by activityName;";
	
	

	@SuppressWarnings("unchecked")
	public JSONArray getAllCourseRegsData()
			throws IllegalArgumentException, DAOException {
		JSONArray CourseRegsData = new JSONArray();

		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(), getAllCourseRegsData,
						new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				
				JSONObject row =  mapCourseData(resultSet);
				CourseRegsData.add(row);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return CourseRegsData;
	}

	public JSONArray get()
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
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject mapGet(ResultSet resultSet) throws SQLException {
		
		JSONObject row =  new JSONObject();		
		row.put("activityName", resultSet.getString("activityName"));
		row.put("NumberOfReg",resultSet.getInt("NumberOfReg"));

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
