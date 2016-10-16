package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import controller.MyConnection;
import model.GradeCode;
import model.OneTimeReg;
import model.Pupil;
import model.PupilActivity;
import model.RegSource;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.RegType;
import model.User;
import util.DAOUtil;

public class LogisticsDAO extends AbstractDAO {

	public LogisticsDAO(MyConnection con) {
		super(con);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String getWeekFoodAmout = "{ call ms2016.getWeekFoodAmout( ? )}";

	@SuppressWarnings("unchecked")
	public JSONArray getWeekFoodAmout(java.sql.Date sunday) {
		JSONArray list = new JSONArray();

		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(),
						getWeekFoodAmout, new Object[] { sunday });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				JSONObject f = mapFoodAmounts(resultSet);
				list.add(f);

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private JSONObject mapFoodAmounts(ResultSet resultSet) throws SQLException {
		JSONObject row = new JSONObject();
		
		row.put("typeID",resultSet.getInt("typeID"));
		row.put("typeName",resultSet.getString("typeName"));
		row.put("sunday",resultSet.getInt("sunday"));
		row.put("monday",resultSet.getInt("monday"));
		row.put("tuesday",resultSet.getInt("tuesday"));
		row.put("wednesday",resultSet.getInt("wednesday"));
		row.put("thursday",resultSet.getInt("thursday"));
		
		return row;
	}
	
	public int executeSql(String query, String oper){
		int result = -1;
		
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), query,false, new Object[] { });) {
		
			result  = statement.executeUpdate();
					
		
		} catch (SQLException e) {
			if(e.getErrorCode() == 1451){
				throw new DAOException("לא ניתן למחוק רשומה זו ,הרשומה משמשת כנתון בטבלאות אחרות",e);
			}
			throw new DAOException(e);
		}
		
		return result;
	}
	
	public String selectValues(String Query, HashMap<String,String> options ,String... Fileds ) throws IllegalArgumentException,
	DAOException {

	//List<Object[]> list = new ArrayList<>();
	String res = "";
	
	try (PreparedStatement statement = DAOUtil.prepareStatement(
			this.con.getConnection(), Query,false, new Object[] { });
			ResultSet resultSet = statement.executeQuery();) {
	
		while (resultSet.next()) {
			//res +=  resultSet.getObject(Fileds[0]).toString() + ":" + resultSet.getObject(Fileds[1]).toString();
			options.put(resultSet.getObject(Fileds[0]).toString(), resultSet.getObject(Fileds[1]).toString());
			
			for (int i = 0; i < Fileds.length; i++) {
				res +=  resultSet.getObject(Fileds[i]).toString() + ":";
			}
			
			res = res.substring(0,res.length()-1);			
			res+=";";
			
		}
	
	} catch (SQLException e) {
		throw new DAOException(e);
	}

	return res;
	}
	
	public int getTableRowCount(String Query ) throws IllegalArgumentException,
	DAOException {

		int count = -1;
		
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), Query,false, new Object[] { });
				ResultSet resultSet = statement.executeQuery();) {
		
			while (resultSet.next()) {
				//res +=  resultSet.getObject(Fileds[0]).toString() + ":" + resultSet.getObject(Fileds[1]).toString();
				count = resultSet.getInt(1);
				
			}
		
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray GetGridData(String table, ArrayList<Object[]> arrlist , String sind, String sord, int rowOffset, int rowsPerPage ) {
		// TODO Auto-generated method stub
		JSONArray list = new JSONArray();
		String Query = "SELECT * FROM " + table + " "; 
		if(rowOffset == 0 && rowsPerPage == 0)
			Query += " ORDER BY "+ sind +" "+ sord ;		
		else
			Query += " ORDER BY "+ sind +" "+ sord + " LIMIT " + rowOffset + ", " + rowsPerPage + "";
		//pupilNum, , , , , , numOfDays, RegDays, Comments
		try (PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(), Query, false, new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) { //for each data row
				
				
					JSONObject row = new JSONObject();
					for (int i = 0; i < arrlist.size(); i++) { //for each col
						Entry<String, String> keyEntry = (Entry<String, String>) arrlist.get(i)[0];
						HashMap<String, Object> mapProp = (HashMap<String, Object>) arrlist.get(i)[1];
						HashMap<String,String> optMap = (HashMap<String,String>)arrlist.get(i)[2];
						if(optMap.isEmpty())
							if(mapProp.get("Datatype").equals("Date")){
								java.sql.Date d = null;
								d = resultSet.getDate(keyEntry.getValue());								
								row.put(keyEntry.getValue(), d != null ? d.getTime() : "");
							}
							else
							row.put(keyEntry.getValue(), resultSet.getObject(keyEntry.getValue()));
						else{
							String val = optMap.get(resultSet.getObject(keyEntry.getValue()).toString());
							row.put(keyEntry.getValue(), val);
						}
					
					}
					
				
				
				/*row.put(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName"),resultSet.getString("gradeName"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName"),resultSet.getString("lastName"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName"),resultSet.getString("firstName"));
				//user.put(new AbstractMap.SimpleEntry<>("tbl_reg_types","type"),resultSet.getString("type"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_gender_ref","genderName"),resultSet.getString("genderName"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","startDate"),resultSet.getDate("startDate"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","מספר ימים לחיוב"),resultSet.getInt("numOfDays"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","ימי רישום"),resultSet.getString("RegDays"));
				row.put(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","הערות"),resultSet.getString("Comments"));
							*/
				list.add(row);
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return list; 
	}
}
