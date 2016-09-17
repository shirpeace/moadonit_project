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
	
	
	

}
