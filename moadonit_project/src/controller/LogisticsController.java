package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import model.Activity;
import model.ActivityType;
import model.Pupil;
import model.PupilActivity;
import model.PupilActivityPK;
import model.RegSource;
import model.RegToMoadonit;
import model.Staff;
import model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import util.DAOUtil;
import dao.ActivityDAO;
import dao.GeneralDAO;
import dao.LogisticsDAO;
import dao.PupilActivityDAO;

@WebServlet("/LogisticsController")
public class LogisticsController extends HttpServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyConnection con = null;
	JSONArray jsonArry;
	JSONArray jsonData;
	LogisticsDAO logDAO;
	JSONObject resultToClient = new JSONObject();
	GeneralDAO generalDAO;
	/**
	 * 
	 */
	public LogisticsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req, resp);
		this.logDAO = new LogisticsDAO(con);
		generalDAO = new GeneralDAO(con);
		String action = req.getParameter("action");
		String jsonResponse = "";
		this.jsonArry = new JSONArray();
		try {
			if (action.equals("getAmounts")) {
				jsonArry = getAmounts(req, resp);

				
					if (jsonArry != null && !jsonArry.isEmpty()) {

						jsonResponse = jsonArry.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ jsonArry.size()
								+ ",\"rows\":"
								+ jsonResponse
								+ "}";

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonResponse);
					} else {
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resultToClient.put("msg", 0);
						resultToClient.put("result", "לא נמצאו נתונים");
						resp.getWriter().print(resultToClient);
					}
				
			}else if (action.equals("getCurrentYearObject")) {
				JSONObject yearObj = this.logDAO.getCurrentYearObject();
				jsonResponse = yearObj.toJSONString();
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(yearObj);
			}
			else if (action.equals("getRegDaysParam")) {
				JSONObject params = this.logDAO.getGeneralParams();
			//	jsonResponse = params.toJSONString();
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(params);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONArray getAmounts(HttpServletRequest req,
			HttpServletResponse resp) {
		java.util.Date dat = new Date(Long.parseLong(req.getParameter("sunday").toString()));
		java.sql.Date sunday = DAOUtil.toSqlDate(dat);
			//	: new java.sql.Date(Long.parseLong(req.getParameter("sunday").toString()));
		if(sunday != null){
			this.logDAO = new LogisticsDAO(con);
		JSONArray list = this.logDAO.getWeekFoodAmout(sunday);

		return list;
		}
		else
		
		return null;
	}

	protected void checkConnection(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("connection") != null) {
			con = (MyConnection) session.getAttribute("connection");
		} else {
			try {
				con = new MyConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("connection", con);
		}

	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		checkConnection(req, resp);
		this.logDAO = new LogisticsDAO(con);
		String action = req.getParameter("action");		
		String oper = req.getParameter("oper");		
		
		String tableName = req.getParameter("tableName");
		String query = req.getParameter("query");
		String whereclause = req.getParameter("whereclause");
		int rows = req.getParameter("rows") != null ? Integer.parseInt(req
				.getParameter("rows")) : 0;
		int page = req.getParameter("page") != null ? Integer.parseInt(req
				.getParameter("page")) : 0;
		String sidx  = req.getParameter("sidx");
		String sord = req.getParameter("sord");
		
		try {
			ArrayList<Object[]> arrlist = new ArrayList<Object[]>();
			
			if (action != null && action.equals("getMetaData")){
				this.jsonArry = new JSONArray();
				this.jsonData = new JSONArray(); 
				
				arrlist =  this.getData(tableName, query, whereclause, jsonData);
				setDataInSession(req, resp, arrlist, "arrlist");				
				//this.jsonData = this.logDAO.GetGridData( tableName,  arrlist ,sidx, sord ,rows* (page - 1), rows);
				
				
				for (Object[] objects : arrlist) {
					JSONObject o = new JSONObject();
					Entry<String, String> key = (Entry<String, String>) objects[0];
					HashMap<String, Object> map = (HashMap<String, Object>) objects[1];
					
					o.put("Name", key.getValue());
					
					Iterator<Entry<String, Object>> it = map.entrySet().iterator();
				    while (it.hasNext()) {
				    	Entry<String, Object> pair = (Entry<String, Object>)it.next();
				        o.put(pair.getKey(),  pair.getValue());
				    	//System.out.println(pair.getKey() + " = " + pair.getValue());
				        //it.remove(); // avoids a ConcurrentModificationException
				        
				    }
				    
				    jsonArry.add(o);
				}
				
				
				
				resultToClient.put("cols", this.jsonArry.toJSONString());
				//resultToClient.put("rows", this.jsonData.toJSONString());
				setDataInSession(req, resp, jsonArry , "ColData");
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
				
			}
			else if (action != null && action.equals("getGridRows")){
				
				arrlist = (ArrayList<Object[]>) getDataInSession(req, resp, "arrlist");	
				
				String qry = null;
				int totalCount;
				int totalPages;
				String jsonResponse;
				
				if(tableName.equals("tbl_moadonit_groups")){
					qry= "select gradeID, yearID, activityNum, startMonth, endMonth, ID "
							+ "FROM ms2016.tbl_moadonit_groups t1 where t1.yearID = get_currentYearID() and endMonth = "
							+ " ( SELECT max(endMonth) FROM ms2016.tbl_moadonit_groups t2 where t1.gradeID = t2.gradeID and t1.yearID=t2.yearID  group by gradeID, yearID) "; 
				}
				else if(tableName.equals("tbl_grade_in_year")){
					qry = "SELECT * FROM ms2016.tbl_grade_in_year where yearID = get_currentYearID() ";
				}
				else if(tableName.equals("tbl_activity")){
					//activityGroup, activityName, responsibleStaff
					qry = "SELECT * FROM ms2016.tbl_activity where schoolYear = get_currentYearID() and activityGroup = 4 ";
				}
				
				this.jsonData = this.logDAO.GetGridData( tableName,  arrlist ,sidx, sord ,rows* (page - 1), rows, qry);
				jsonResponse = this.jsonData.toJSONString();
				
				if(tableName.equals("tbl_moadonit_groups") || tableName.equals("tbl_grade_in_year") || tableName.equals("tbl_activity")){
					totalCount = jsonData.size();
				}
				else{
					totalCount = this.logDAO.getTableRowCount("select count(*) from " + tableName);
				}
				
				
				if (totalCount > 0) {
					if (totalCount % rows == 0) {
						totalPages = totalCount / rows;
					} else {
						totalPages = (totalCount / rows) + 1;
					}

				} else {
					totalPages = 0;
				}
				
				jsonResponse = "{\"page\":" + page + ",\"total\":"
						+ totalPages + ",\"records\":" + totalCount
						+ ",\"rows\":" + jsonResponse + "}";

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jsonResponse);
			}

			if(oper != null){
				String sql ="";
				String where = "where ";
				tableName = req.getParameter("tableName");
				
				if(jsonArry == null || jsonArry.size()== 0){
					jsonArry = (JSONArray)getDataInSession(req, resp, "ColData");

				}
				
				if(oper.equals("edit")){
					this.updateRowInTable( sql, tableName, where, req, resp);

				}
				else if(oper.equals("add")){
					insertRowInTable(sql, tableName, req, resp);
				}
				else if(oper.equals("del")){
					deleteRowInTable(sql, tableName,where, req, resp);
				}
			}
			//
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			resultToClient.put("msg", 0);
			resultToClient.put("result", null);
			resp.getWriter().print(resultToClient);

			e.printStackTrace();
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");			
			
			resp.getWriter().print(e.getMessage());
		}
	}
	
	private int deleteRowInTable(String sql,String tableName,String where, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {
	
		int result;
		sql = "DELETE FROM " + tableName +" ";
		for (Iterator iterator = jsonArry.iterator(); iterator.hasNext();) {
			JSONObject col = (JSONObject) iterator.next();
			
			boolean isKey = (boolean)col.get("IsKey");
			if(isKey){
				if(req.getParameter((String)col.get("Name")) != null){
			    	where +=  " " + (String)col.get("Name") + " = " + req.getParameter((String)col.get("Name"))  + " AND";						    	
		    	}
			}			
		}
		
		if(sql.endsWith(" ,"))
			sql = sql.substring(0, sql.length()-2);
		
		if(where.endsWith(" AND"))
			where = where.substring(0, where.length()-4);
		
		sql += " " + where;
		//return result = 1; //this.logDAO.executeSql(sql, null);
		return result = this.logDAO.executeSql(sql, null);
	}
	
	private int insertRowInTable(String sql,String tableName,  HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {
		
		int result;
		sql = "INSERT INTO " + tableName + "( ";
		String values = "VALUES ( ";
		
		for (Iterator iterator = jsonArry.iterator(); iterator.hasNext();) {
			JSONObject col = (JSONObject) iterator.next();
			
				boolean isKey = (boolean)col.get("IsKey");
				if(isKey && !(boolean)col.get("IsRequired")){
					continue;
				}
			
				if(req.getParameter((String)col.get("Name")) != null){
					String fieldValue = req.getParameter((String)col.get("Name"));
					if(col.get("Datatype").equals("String") || col.get("Datatype").equals("Time") || col.get("Datatype").equals("phone")){
						
						sql += " " + (String)col.get("Name") + ",";
						
						values += " '" + fieldValue + "',";
					}
					else if(col.get("Datatype").equals("Date")){
						if(fieldValue.trim().length() > 0){
							String[] s = req.getParameter((String)col.get("Name")).split("/");
							String datVal = s[2] + "-" +s[1] + "-" +s[0];
							
							sql += " " + (String)col.get("Name") + ",";
							
							values += " '" + datVal + "',";
						}
						else{
							sql += " " + (String)col.get("Name") + ",";
							
							values += " '" + fieldValue + "',";
						}
						
					}
					else{
						sql += " " + (String)col.get("Name") + ",";
						
						values += " " + fieldValue + ",";
					}
		    		
		    	}
			
			
		}
		
		if(sql.endsWith(","))
			sql = sql.substring(0, sql.length()-1);
		
		if(values.endsWith(","))
			values = values.substring(0, values.length()-1);
		
		sql += " )" + values + " )";
		
		return result = this.logDAO.executeSql(sql, null);
		
		
	}
	
	private int updateRowInTable(String sql,String tableName,String where,  HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {
		
		int result;
		if(tableName.equals("tbl_moadonit_groups")){
			int actNum = Integer.parseInt(req.getParameter("activityNum"));			
			int rowKey = Integer.parseInt(req.getParameter("ID"));
			
			this.logDAO.update_moadonit_group("{ call ms2016.update_moadonit_group( ? ,?)}", new Object[] {actNum,rowKey});
			return result = 0;
		}
		else{
			sql = "UPDATE " + tableName + " SET ";
			for (Iterator iterator = jsonArry.iterator(); iterator.hasNext();) {
				JSONObject col = (JSONObject) iterator.next();
				
				boolean isKey = (boolean)col.get("IsKey");
				if(isKey){
					if(req.getParameter((String)col.get("Name")) != null){
				    	where +=  " " + (String)col.get("Name") + " = " + req.getParameter((String)col.get("Name"))  + " AND";						    	
			    	}
				}
				else{
					if(req.getParameter((String)col.get("Name")) != null){
						String fieldValue = req.getParameter((String)col.get("Name"));
						if(col.get("Datatype").equals("String") || col.get("Datatype").equals("Time") || col.get("Datatype").equals("phone") || col.get("Datatype").equals("colorpicker")){
							
						    sql += " " + (String)col.get("Name") + " = '" + req.getParameter((String)col.get("Name")) + "' ,";
						}
						else if(col.get("Datatype").equals("Date")){
							if(fieldValue.trim().length() > 0){
								String[] s = fieldValue.split("/");
								String datVal = s[2] + "-" +s[1] + "-" +s[0];
								sql += " " + (String)col.get("Name") + " = '" + datVal + "' ,";
							}
							else{
								sql += " " + (String)col.get("Name") + " = null ,";
							}
							
						}
						else{
							if(fieldValue.trim().length() > 0){
								sql += " " + (String)col.get("Name") + " = " + fieldValue + " ,";
							}
							else{
								sql += " " + (String)col.get("Name") + " = null ,";
							}
							
						}
			    		
			    	}
				}
				
			}
			
			if(sql.endsWith(" ,"))
				sql = sql.substring(0, sql.length()-2);
			
			if(where.endsWith(" AND"))
				where = where.substring(0, where.length()-4);
			
			sql += " " + where;
			return result = this.logDAO.executeSql(sql, null);
		}
	}
	
	protected Object getDataInSession(HttpServletRequest req,
			HttpServletResponse resp, String Key) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object data = null;
		if (session.getAttribute(Key) != null) {
			data =  session.getAttribute(Key);			
		} 
		
		return data;
	}
	
	
	protected void setDataInSession(HttpServletRequest req,
			HttpServletResponse resp,Object jsonArryCols , String Key) throws ServletException, IOException {
		HttpSession session = req.getSession();

			try {
				session.setAttribute(Key, jsonArryCols);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@SuppressWarnings("unchecked")
	protected ArrayList<Object[]> getData(String tableName,String query,String whereclause, JSONArray gridData){
		//paramID, paramName, paramValue, startDate, endDate
		JSONArray jsonResult = new JSONArray();
		
		ArrayList<Object[]> arrlist = new ArrayList<Object[]>();
		
		HashMap<Entry<String, String>, String[]> keysColComments = DAOUtil.getKeysColmunComments(this.con.getConnection(),query, whereclause);
		
		if(keysColComments != null)
			for (Entry<String, String> key : keysColComments.keySet()) {
				
				String name = key.getValue();
				boolean isKey = keysColComments.get(key)[1].equals("PRI")  ?  true : false;
				boolean isHidden = false, isRequired = false;
				boolean editable=true;
				Object DefaultValue = null;
				if(isKey){
					 isHidden = keysColComments.get(key)[2].equals("auto_increment")  ?  true : false  ;
					 isRequired = !isHidden;
					 editable = !isHidden;
				}
				
				String type = GetColType(keysColComments.get(key)[0]);
				String label = "";
				String comboQuery = "";
				String[] comboFields = null;
				
				if(keysColComments.get(key)[3] != null){
					label = keysColComments.get(key)[3];						
				}
				else{					
					label = key.getValue();
					
				}
				
				if (key.getKey().equals("tbl_staff")) {
					 if (key.getValue().equals("jobID")){
						 comboQuery =  "SELECT jobID, jobName FROM ms2016.tbl_job_type";
						 comboFields = new String[] { "jobID", "jobName"};
						 type = "dropdown";
					 }
					 else if (key.getValue().equals("paymentID")){
						 comboQuery =  "SELECT paymentID, paymentName FROM ms2016.tbl_payment_type;";
						 comboFields = new String[] { "paymentID", "paymentName"};
						 type = "dropdown";
					 }
					 else if (key.getValue().equals("cellphone")){
				
						 type = "phone";
					 }
					 
				}
				else if (key.getKey().equals("tbl_moadonit_groups")) {
					if (key.getValue().equals("activityNum")){
						 comboQuery =  "SELECT activityNum ,activityName FROM ms2016.tbl_activity where activityGroup = 4  and schoolYear = get_currentYearID()";
						 comboFields = new String[] { "activityNum", "activityName"};
						 type = "dropdown";
						
					}
					else if (key.getValue().equals("yearID")){
						isHidden = true;
					}
					else if (key.getValue().equals("gradeID")){
						 isRequired = false;
						 editable = false;
						 comboQuery =  "SELECT * FROM ms2016.tbl_grade_code";
						 comboFields = new String[] { "gradeID", "gradeName", "gradeColor"};
						 type = "custom";
					}					
					else if (key.getValue().equals("endMonth") || key.getValue().equals("startMonth")){
						isHidden = true;
					}
					//  
					
				}
				else if(key.getKey().equals("tbl_grade_code")){
					if (key.getValue().equals("gradeID")){
						isHidden = true;
						editable = false;
					}
					else if (key.getValue().equals("gradeColor")){
						type = "colorpicker";
					}
					else if (key.getValue().equals("gradeName")){
						editable = false;
					}
					//
				}
				else if(key.getKey().equals("tbl_grade_in_year")){
					if (key.getValue().equals("yearID")){
						isHidden = true;
						editable = false;
					}
					else if (key.getValue().equals("gradeID")){
						 isRequired = false;
						 editable = false;
						 comboQuery =  "SELECT * FROM ms2016.tbl_grade_code";
						 comboFields = new String[] { "gradeID", "gradeName", "gradeColor"};
						 type = "custom";
					}
					//
				}
				else if (key.getKey().equals("tbl_activity")) { //for moadonitGroups table in administration page
					if ( key.getValue().equals("weekDay") || key.getValue().equals("startTime") || key.getValue().equals("endTime")){
						isHidden = true;
						editable = false;
					}
					else if(key.getValue().equals("responsibleStaff")){
						comboQuery =  "SELECT staffID, concat(lastName,' ' ,firstName) as staffName FROM ms2016.tbl_staff";
						 comboFields = new String[] { "staffID", "staffName"};
						 type = "dropdown";
						 
					}
					else if (key.getValue().equals("activityGroup") ){
						isHidden = true;
						DefaultValue =  4;
					}
					else if ( key.getValue().equals("schoolYear")){
						isHidden = true;
						//DefaultValue =  "get_currentYearID()";
					}
				}
				
				HashMap<String,String> comboOptions = new HashMap<String, String>();
				
				HashMap<String, Object> mapProp = setColOptions(name,label, isKey,isHidden, isRequired, editable, DefaultValue, type, comboQuery,comboOptions, comboFields  );
				arrlist.add(new Object[] { key , mapProp, comboOptions });
				
				//arrlist.get(0)[1] = setColOptions(name,label, isKey,isH, isR, null, type, comboQuery, comboFields );
			}
		
		
		/*for (Object[] objects : arrlist) {
			JSONObject o = new JSONObject();
			Entry<String, String> key = (Entry<String, String>) objects[0];
			HashMap<String, Object> map = (HashMap<String, Object>) objects[1];
			
			o.put("Name", key.getValue());
			
			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		    while (it.hasNext()) {
		    	Entry<String, Object> pair = (Entry<String, Object>)it.next();
		        o.put(pair.getKey(),  pair.getValue());
		    	System.out.println(pair.getKey() + " = " + pair.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		        
		    }
		    
		    jsonResult.add(o);
		}*/
			
		//gridData = this.logDAO.GetGridData( tableName,  arrlist)	;  cellphone, email
		return arrlist;
	}
	
	private String GetColType (String DBtype){
		
		String r = "";
		switch (DBtype.toLowerCase()) {
			case "int":
				r = "int";
				break;
			case "varchar":
				r = "String";
				break;
			case "date":
				r = "Date";
				break;
			case "time":
				r = "time";
				break;
			case "float":
				r = "float";
				break;	
			default:
				break;
		}
		
		
		return r;
	}
	private HashMap<String, Object> setColOptions(String name ,String label, boolean IsKey, boolean IsHidden,boolean IsRequired,  boolean editable, Object DefaultValue, String Datatype, String query,HashMap<String,String> options,  String... fields){
		
		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put("Name",name);
		hash.put("IsKey",IsKey);
		hash.put("IsHidden",IsHidden);
		hash. put("IsRequired",IsRequired);
		hash.put("DefaultValue",DefaultValue);		    
		hash.put("Datatype",Datatype);
		hash.put("ValueListQuery",query);
		hash.put("ValueList",":;");
		hash.put("label",label);
		hash.put("editable",editable);
		
		if(hash.get("ValueListQuery") != null && !query.trim().equals("") && fields != null && fields.length > 0){
				if(logDAO != null){
					
					hash.put("ValueList",logDAO.selectValues(query ,options, fields));
				}
		}
		
		return hash;
		
		
	}
	
	

}
