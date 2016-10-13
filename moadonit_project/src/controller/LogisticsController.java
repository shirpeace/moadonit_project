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

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		checkConnection(req, resp);
		this.logDAO = new LogisticsDAO(con);
		String action = req.getParameter("action");
		String tableName = req.getParameter("tableName");
		String query = req.getParameter("query");
		String whereclause = req.getParameter("whereclause");
		String jsonResponse = "";
		
		this.jsonArry = new JSONArray();
		this.jsonData = new JSONArray(); 
		try {
			if (action.equals("getMetaData")){
				ArrayList<Object[]> arrlist = new ArrayList<Object[]>();
				arrlist =  this.getData(tableName, query, whereclause, jsonData);
				this.jsonData = this.logDAO.GetGridData( tableName,  arrlist);
				
				
				for (Object[] objects : arrlist) {
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
				    
				    jsonArry.add(o);
				}
				
				
				
				resultToClient.put("cols", this.jsonArry.toJSONString());
				resultToClient.put("rows", this.jsonData.toJSONString());

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
				
			}
			else if (action.equals("getGridData")){
				switch (tableName) {
				case "tbl_staff":
					
					break;

				default:
					break;
				}
			}
			
			//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");			
			resultToClient.put("msg", 0);
			resultToClient.put("result", null);
			resp.getWriter().print(resultToClient);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected ArrayList<Object[]> getData(String tableName,String query,String whereclause, JSONArray gridData){
		//paramID, paramName, paramValue, startDate, endDate
		JSONArray jsonResult = new JSONArray();
		
		ArrayList<Object[]> arrlist = new ArrayList<Object[]>();
		/*arrlist.add(new Object[] { new AbstractMap.SimpleEntry<>("tbl_general_parameters","paramID")});
		arrlist.add(new Object[] { new AbstractMap.SimpleEntry<>("tbl_general_parameters","paramName")});
		arrlist.add(new Object[] { new AbstractMap.SimpleEntry<>("tbl_general_parameters","paramValue")});
		arrlist.add(new Object[] { new AbstractMap.SimpleEntry<>("tbl_general_parameters","startDate")});
		arrlist.add(new Object[] { new AbstractMap.SimpleEntry<>("tbl_general_parameters","endDate")});*/
		
		HashMap<Entry<String, String>, String[]> keysColComments = DAOUtil.getKeysColmunComments(this.con.getConnection(),query, whereclause);
		
		if(keysColComments != null)
			for (Entry<String, String> key : keysColComments.keySet()) {
				
				String name = key.getValue();
				boolean isKey = keysColComments.get(key)[1].equals("PRI")  ?  true : false;
				boolean isH = keysColComments.get(key)[1].equals("PRI") &&  keysColComments.get(key)[2].equals("auto_increment")  ?  true : false  ;
				boolean isR = false;
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
					 
				}
				else if (key.getKey().equals("")) {
					
				}
				
				HashMap<String,String> comboOptions = new HashMap<String, String>();
				
				HashMap<String, Object> mapProp = setColOptions(name,label, isKey,isH, isR, null, type, comboQuery,comboOptions, comboFields  );
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
			
		//gridData = this.logDAO.GetGridData( tableName,  arrlist)	;
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
	private HashMap<String, Object> setColOptions(String name ,String label, boolean IsKey, boolean IsHidden,boolean IsRequired, Object DefaultValue, String Datatype, String query,HashMap<String,String> options,  String... fields){
		
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
		
		if(hash.get("ValueListQuery") != null && !query.trim().equals("") && fields != null && fields.length > 0){
				if(logDAO != null){
					
					hash.put("ValueList",logDAO.selectValues(query ,options, fields));
				}
		}
		
		return hash;
		
		
	}
	
	

}
