package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Activity;
import model.ActivityType;
import model.FullPupilCard;
import model.Pupil;
import model.PupilActivity;
import model.PupilActivityPK;
import model.RegToMoadonit;
import model.Staff;
import model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import util.DAOUtil;
import dao.ActivityDAO;
import dao.GeneralDAO;
import dao.LogisticsDAO;
import dao.PupilActivityDAO;
import dao.RegToMoadonitDAO;
import dao.ReportsDAO;
@WebServlet("/ReportsController")
public class ReportsController extends HttpServlet implements
Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyConnection con = null;
	JSONArray jsonArry;
	ReportsDAO repDOA;
	PupilActivityDAO pupilActDAO;
	ActivityDAO actDOA;
	List<PupilActivity> listPupilActivity;
	List<Activity> listAct;
	JSONObject resultToClient = new JSONObject();
	Map<Integer, Object> regTypes;
	RegToMoadonitDAO regDAO;
	LogisticsDAO logDAO ;
	/**
	 * 
	 */
	public ReportsController() {
		super();
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req, resp);
		this.repDOA = new ReportsDAO(con);
		String action = req.getParameter("action");
		String jsonResponse = "";
		this.jsonArry = new JSONArray();
		try {
			if (action.equals("getAllCourseRegsData")){
				jsonArry  = this.repDOA.getAllCourseRegsData();
				
				if (!jsonArry.isEmpty()) {
					
					jsonResponse = jsonArry.toJSONString();
					

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(jsonResponse);
				}
				else{
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resultToClient.put("msg", 0);
					resultToClient.put("result", "לא נמצאו נתונים");
					resp.getWriter().print(resultToClient);
				}
				
	
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	
	@SuppressWarnings("unchecked")
	protected JSONArray getJsonPupilActivities( List<PupilActivity> list){
		
		JSONArray result = new JSONArray();
		//activityNum, activityName, pupilNum, startDate, regDate, endDate, firstName, lastName 
		for (PupilActivity pa : list) {
			
			JSONObject obj = new JSONObject();
			/*
			 *PupilActivity pa = new PupilActivity();
		PupilActivityPK pk = new PupilActivityPK();
		pk.setActivityNum(1);
		pk.setPupilNum(1);
		pa.setId(pk);
		
		Activity act = new Activity();
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		act.setTblActivityType(type);
		act.setActivityName(resultSet.getString("activityName"));
		
		pa.setTblActivity(act);
		
		pa.setRegDate(resultSet.getDate("regDate"));
		pa.setStartDate(resultSet.getDate("startDate"));
		pa.setEndDate(resultSet.getDate("endDate"));
		
		Pupil p = new Pupil();
		p.setPupilNum(1);
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));

		
			 * */
			obj.put("activityNum", pa.getTblActivity().getActivityNum());
			obj.put("activityName", pa.getTblActivity().getActivityName());
			obj.put("pupilNum", pa.getTblPupil().getPupilNum());
			obj.put("startDate", pa.getId().getStartDate().getTime());
			obj.put("regDate", pa.getRegDate().getTime());
			obj.put("endDate", pa.getEndDate() == null ? null : pa.getEndDate().getTime());
			obj.put("firstName", pa.getTblPupil().getFirstName());
			obj.put("lastName", pa.getTblPupil().getLastName());
			result.add(obj);

		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected JSONArray getJsonPupilActForExport( List<PupilActivity> list){
		
		JSONArray result = new JSONArray();
		//activityNum, activityName, pupilNum, startDate, regDate, endDate, firstName, lastName 
		for (PupilActivity pa : list) {
			
			JSONObject obj = new JSONObject();
			/*
			 *PupilActivity pa = new PupilActivity();
		PupilActivityPK pk = new PupilActivityPK();
		pk.setActivityNum(1);
		pk.setPupilNum(1);
		pa.setId(pk);
		
		Activity act = new Activity();
		ActivityType type = new ActivityType();
		type.setTypeID(resultSet.getInt("activityType"));
		act.setTblActivityType(type);
		act.setActivityName(resultSet.getString("activityName"));
		
		pa.setTblActivity(act);
		
		pa.setRegDate(resultSet.getDate("regDate"));
		pa.setStartDate(resultSet.getDate("startDate"));
		pa.setEndDate(resultSet.getDate("endDate"));
		
		Pupil p = new Pupil();
		p.setPupilNum(1);
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		
		pa.setTblPupil(p);
		
		
			 * */
			obj.put(new AbstractMap.SimpleEntry<>("tbl_activity","activityNum"), pa.getTblActivity().getActivityNum());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_activity","activityName") , pa.getTblActivity().getActivityName());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil","pupilNum") , pa.getTblPupil().getPupilNum());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil_activities","startDate") , new java.sql.Date(pa.getId().getStartDate().getTime()));
/*			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil_activities","regDate") , new java.sql.Date(pa.getRegDate().getTime()) );*/
			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil_activities","endDate") , pa.getEndDate() == null ? "" : new java.sql.Date(pa.getEndDate().getTime()));
			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName") , pa.getTblPupil().getFirstName());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName") , pa.getTblPupil().getLastName());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName") , pa.getTblPupil().getTblGradeInYears1().get(0).getTblGradeCode().getGradeName());
			obj.put(new AbstractMap.SimpleEntry<>("tbl_gender_ref","genderName") , pa.getTblPupil().getTblGenderRef().getGenderName());
			result.add(obj);

		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		checkConnection(req, resp);
		this.repDOA = new ReportsDAO(con);
		this.actDOA = new ActivityDAO(con);
		String action = req.getParameter("action");
		String jsonResponse = "";
		this.jsonArry = new JSONArray();
		try {
			if (action.equals("getCourses")){
				String search = req.getParameter("_search");
				
				if (search.equals("true")) {
					//search data
					
				}else{
					
					//load all data				
					
				}
				
				if (!listAct.isEmpty()){
					getJsonCourses(jsonArry); // get courses
					if (!jsonArry.isEmpty()) {
						
						jsonResponse = jsonArry.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ jsonArry.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonResponse);
					}
					else{
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resultToClient.put("msg", 0);
						resultToClient.put("result", "לא נמצאו נתונים");
						resp.getWriter().print(resultToClient);
					}
				}
				else{
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resultToClient.put("msg", 0);
					resultToClient.put("result", "לא נמצאו נתונים");
					resp.getWriter().print(resultToClient);
				}
			}else if (action.equals("insert")) {
				
					insertCourse(req, resp);
					
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(resultToClient);

				
			}else if (action.equals("update")) {
				updateCourse(req, resp);

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
			}
			else if (action.equals("insertPupilActivity")) {
				
				boolean r = insertPupilActivity(req, resp);
				
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
			}
			else if (action.equals("editPupilInCourse")){
				// 
				boolean r = editPupilInCourse(req, resp);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
			}else if(action.equals("deletePupilActivity")){
				
				//
				boolean r = deletePupilActivity(req, resp);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
			}
			else if(action.equals("export")){
				
				exportExcel(req, resp, null);
				/*boolean r = deletePupilActivity(req, resp);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);*/
			}
			else if (action.equals("getCourseForReport")){
				int year = Integer.parseInt(req.getParameter("year"));
				List<Activity> list = getCourses(year);
				jsonArry = new JSONArray();
				if(!list.isEmpty()){
					for (Activity activity : list) {
						JSONObject o = new JSONObject();
						o.put("activityName", activity.getActivityName());
						o.put("activityNum", activity.getActivityNum());
						jsonArry.add(o);
					}
					
					jsonResponse = jsonArry.toJSONString();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(jsonResponse);

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
	private boolean deletePupilActivity(HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		this.pupilActDAO = new PupilActivityDAO(con);
		PupilActivity pa = new PupilActivity();
		pa = (PupilActivity)DAOUtil.getObjectFromJson(req.getParameter("pupilActivity"), pa.getClass());
		boolean r  = this.pupilActDAO.delete(pa);	
		if (r) {
			resultToClient.put("msg", 1);
			resultToClient.put("result", "רשומה נמחקה בהצלחה");
			return true;
		} else {
			resultToClient.put("msg", 0);
			resultToClient
					.put("result", "שגיאה בשמירת הנתונים");
			return false;
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean editPupilInCourse(HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		this.pupilActDAO = new PupilActivityDAO(con);
		PupilActivity pa = new PupilActivity();
		if (req.getSession().getAttribute("userid") != null) {
			JSONObject user = (JSONObject) req.getSession()
					.getAttribute("userid");
			User u = new User();
			u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());
			pa = (PupilActivity)DAOUtil.getObjectFromJson(req.getParameter("pupilActivity"), pa.getClass());
			pa.setTblUser(u);
			boolean r  = this.pupilActDAO.update(pa);	
			if (r) {
				resultToClient.put("msg", 1);
				resultToClient.put("result", "רישום נשמר בהצלחה");
			} else {
				resultToClient.put("msg", 0);
				resultToClient
						.put("result", "שגיאה בשמירת הנתונים");
			}
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private boolean insertPupilActivity( HttpServletRequest req, HttpServletResponse resp) {
		
		boolean r = false;
		try {
			
			PupilActivity pa;
			String str = req.getParameter("activityData");
			if (req.getSession().getAttribute("userid") != null) {
				JSONObject user = (JSONObject) req.getSession()
						.getAttribute("userid");
				User u = new User();
				u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());

				pa = (PupilActivity) DAOUtil.getObjectFromJson(str, PupilActivity.class);
				pa.setTblUser(u);
				
				r = this.pupilActDAO.insert(pa);	
				if (r) {
					resultToClient.put("msg", 1);
					resultToClient.put("result", "רישום נשמר בהצלחה");
				} else {
					resultToClient.put("msg", 0);
					resultToClient
							.put("result", "שגיאה בשמירת הנתונים");
				}
			}
			else{
				r = false;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			r = false;
			resultToClient.put("msg", 0);
			resultToClient.put("result",e.getMessage());
		}
		
		return r;
	}
 	
	private void insertCourse(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject updateCourse(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		// TODO Auto-generated method stub
		Activity act ;
		String activityData = req.getParameter("activityData");
		String startTime = req.getParameter("startTime");
		/*String endTime = req.getParameter("endTime");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = format.parse(startTime);*/
		//Time t = new Time();
		
		/*********
		 * NOT FINISHED
		 * ******
		 */
		act = (Activity) DAOUtil.getObjectFromJson(activityData, Activity.class);
		resultToClient.put("msg", 1);
		resultToClient.put("result", act.getActivityNum());
		return resultToClient;
		
	}
	

	
	@SuppressWarnings("unchecked")
	protected JSONObject getJsonActivities(Activity act ) {

//		/activityNum, activityType, activityName, weekDay, startTime, endTime, schoolYear, responsibleStaff, activityNum, pricePerMonth, extraPrice, regularOrPrivate, category
			
				JSONObject obj = new JSONObject();

				obj.put("activityNum", act.getActivityNum());
				obj.put("activityType", act.getTblActivityGroup().getTblActivityType().getTypeID());
				obj.put("activityName", act.getActivityName());
				obj.put("weekDay", act.getWeekDay());			
				obj.put("startTime", act.getStartTime().toString().substring(0,5));
				obj.put("endTime",act.getEndTime().toString().substring(0,5));
				//obj.put("schoolYear", getRegType(act.getTblRegType5().getTypeNum()));
				obj.put("firstName", act.getTblStaff().getFirstName());
				return obj;
	}
	
	@SuppressWarnings("unchecked")
	protected void getJsonCourses(JSONArray registrationData) {

//		/ pricePerMonth, extraPrice, regularOrPrivate, category
			for (Activity act : this.listAct) {
				JSONObject obj ;
				obj = getJsonActivities(act);
				
				obj.put("pricePerMonth", act.getTblCourse().getPricePerMonth());
				obj.put("extraPrice", act.getTblCourse().getExtraPrice());
				obj.put("regularOrPrivate", act.getTblCourse().getRegularOrPrivate());
				obj.put("category", act.getTblCourse().getCategory());
				registrationData.add(obj);

			}

		

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("connection", con);
		}

	}
	
	protected void exportExcel(HttpServletRequest req, HttpServletResponse resp, HttpServlet controller )
			throws ServletException, IOException, SQLException {

		checkConnection(req, resp);
		
		resp.setContentType("text/html;charset=UTF-8");
		Cookie downloadCookie = new Cookie("fileDownload", "true");

		String fileName = req.getParameter("fileName");
		String fileType = req.getParameter("fileType");

		String htmlfromFunc = getHtmlFromJsonData(req, resp, controller);

		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // this is for Excel 2007
																								
		// response.setContentType("application/vnd.ms-excel"); // this is for
		// Excel 2003

		resp.setHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "." + fileType + "\"");
		
		PrintWriter out = resp.getWriter();
		
		/*Cookie[] cookies = req.getCookies();

		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("fileDownload")) {
			   cookie.setValue(null);
               cookie.setMaxAge(0);
               //cookie.setPath(theSamePathAsYouUsedBeforeIfAny);
               resp.addCookie(cookie);
		    }
		  }
		}*/
		
		if(htmlfromFunc != null){
			resp.addCookie(downloadCookie);
			out.print(htmlfromFunc);
		}
		else{
			out.print("Some error!!!!!");
		}
				
		out.close();
	}

	public String getHtmlFromJsonData(HttpServletRequest req,
			HttpServletResponse resp, HttpServlet controller) throws IOException, SQLException {
		String pageName = req.getParameter("pageName");
		//int colNum = Integer.parseInt(req.getParameter("colNum"));
		String query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME, DATA_TYPE, COLUMN_KEY ,EXTRA FROM information_schema.columns ";
		String whereClouse = null;
		String pageHead = ""; // header for exel file	
		//String[] arrKeys = new String[colNum] ;
		FullPupilCardController pupilControler;
		ArrayList<Entry<String, String>> arrlist = new ArrayList<Entry<String, String>>();
		Calendar calendar = Calendar.getInstance();
		JSONArray jsonList = new JSONArray();
		String html = "<!DOCTYPE html><html lang=\"he\" ><head> <meta charset=\"utf-8\" /><style script='css/text'>table.tableList_1 th {border:1px solid #a8da7f; border-bottom:2px solid #a8da7f; text-align:center; vertical-align: middle; padding:5px; background:#e4fad0;}table.tableList_1 td {border:1px solid #a8da7f; text-align: left; vertical-align: top; padding:5px;} div.tableTitle {  font-size: 16px; font-weight: bold; vertical-align: middle}</style></head><body dir=\"rtl\">";
		int month;
		int year;
		Date d;
		Long monthTime;
		Map<Integer, Object> map;
		switch (pageName) {
			case "pupils_search":
				List<FullPupilCard> list = null;
				//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
				whereClouse = " WHERE (table_name = 'fullPupilCard'); ";
				pupilControler = (FullPupilCardController)controller;
				list = pupilControler.searchPupilList(req, resp, 0, 0); // get list of rows to add
				pupilControler.getJsonForExport(jsonList, list);
				pageHead = "רשימת תלמידים";
				
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","lastName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","firstName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","gender"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","gradeName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","state"));
				
				if (!jsonList.isEmpty() && arrlist.size() > 0) {
					// start html text with style for table
						
						
						
						html += buildTableForHtml(query,whereClouse,arrlist,jsonList,pageHead);
						html += "</body></html>";
						//return html;
					
				} else {

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resultToClient.put("msg", 0);
					resultToClient.put("result", "לא נמצאו נתונים");
					resp.getWriter().print(resultToClient);
				}
				
				//pupil cellphone, family homePhoneNum, 
				
				/*arrKeys[0] = "lastName";arrKeys[1] = "firstName";arrKeys[2] = "gender";arrKeys[3] = "gradeName";
				arrKeys[4] = "isReg";*/
				//arrKeys1 = { "lastName","firstName","gender","gradeName","isReg" };
				
				break;
			case "pupils_phones":
				
				List<FullPupilCard> listContact = null;
				//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
				whereClouse = " WHERE (table_name = 'fullPupilCard'); ";
				pupilControler = (FullPupilCardController)controller;
				listContact = pupilControler.searchContactList(req, resp); // get list of rows to add
				pupilControler.getContactListForExport(jsonList, listContact);
				pageHead = "רשימת תלמידים";
				//arrKeys[0] = "lastName";arrKeys[1] = "firstName";arrKeys[2] = "gender";arrKeys[3] = "gradeName";
				//arrKeys[4] = "isReg";
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","lastName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","firstName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","gender"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","gradeName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","state"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","cellphone"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","homePhoneNum"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p1fname"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p1cell"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p1mail"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p2fname"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p2cell"));
				arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","p2mail"));
				
				if (!jsonList.isEmpty() && arrlist.size() > 0) {
					// start html text with style for table
						
					html += buildTableForHtml(query,whereClouse,arrlist,jsonList,pageHead);
						html += "</body></html>";
						//return html;
					
				} else {

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resultToClient.put("msg", 0);
					resultToClient.put("result", "לא נמצאו נתונים");
					resp.getWriter().print(resultToClient);
				}
				
				break;
			case "staffDetails":
				whereClouse = " WHERE (table_name = 'tbl_staff' or table_name = 'tbl_job_type' or table_name = 'tbl_payment_type'); ";
								
				this.logDAO = new LogisticsDAO(con);
				map  = this.regTypes; 
				
				pageHead = "דף קשר סגל";
				html += "<div class='tableTitle'>"+ pageHead + "</div>";
				//staffID, firstName, lastName, cellphone, email, jobID, tekenHours, hourSalary, paymentID, startWorkDate
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","firstName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","lastName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","cellphone"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","email"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","tekenHours"));	
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","hourSalary"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_staff","startWorkDate"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_job_type","jobName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_payment_type","paymentName"));
				
				
				jsonList = this.logDAO.getStaffDetails();
			
				if (!jsonList.isEmpty() && arrlist.size() > 0) {
					html += buildTableForHtml(query,whereClouse,arrlist,jsonList,pageHead);							
				}					
				
				
				html += "</body></html>";
				
				break; // END STAFF
		case "OneTimeReport":
				//List<FullPupilCard> list = null;
				//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
				whereClouse = " WHERE (table_name = 'tbl_pupil' or table_name = 'tbl_one_time_reg' or table_name = 'tbl_grade_code' or table_name = 'tbl_reg_types'); ";
				/* month = Integer.parseInt(req.getParameter("month"));
				 year = Integer.parseInt(req.getParameter("year"));*/
				monthTime = Long.parseLong(req.getParameter("month"));
				 
				  d = new Date(monthTime);
				 
				 calendar = Calendar.getInstance();
				 calendar.clear();
				 calendar.setTime(d);
				 month = (calendar.get(Calendar.MONTH)+1);
				 year = calendar.get(Calendar.YEAR);
				// Date fDay = calendar.getTime();
				// calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				// Date eDay = calendar.getTime();
				 
				
				this.regDAO = new RegToMoadonitDAO(con);
				regTypes = this.regDAO.getRegTypeCodes();
				
				if(this.regTypes == null || this.regTypes.isEmpty())
					return null;
				
				map  = this.regTypes; 
				
				//pupilControler = (FullPupilCardController)controller;
				//list = pupilControler.searchPupilList(req, resp, 0, 0); // get list of rows to add
				//pupilControler.getJsonForExport(jsonList, list);
				
			//	pageHead = "רישום חד פעמי";
				pageHead = "דוח רישום חד פעמי לחודש "+ month +"/" + year;
				html += "<div class='tableTitle'>"+ pageHead + "</div>";
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_types","type"));
				arrlist.add(new AbstractMap.SimpleEntry<>("tbl_one_time_reg","ימים"));				
				
				for (Entry<Integer, Object> entry : map.entrySet())
				{
					jsonList = this.repDOA.getOneTimeReport(month, year, entry.getKey());
					if (!jsonList.isEmpty() && arrlist.size() > 0) {
						html += buildTableForHtml(query,whereClouse,arrlist,jsonList,entry.getValue().toString());							
					}					
				}
				
				html += "</body></html>";
				

				
				//pupil cellphone, family homePhoneNum, 
				
				/*arrKeys[0] = "lastName";arrKeys[1] = "firstName";arrKeys[2] = "gender";arrKeys[3] = "gradeName";
				arrKeys[4] = "isReg";*/
				//arrKeys1 = { "lastName","firstName","gender","gradeName","isReg" };
				
				break;
		case "CourseData":
			resp.setContentType("text/html;charset=UTF-8");
			//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
			whereClouse = " WHERE (table_name = 'tbl_pupil' or table_name = 'tbl_pupil_activities' or table_name = 'tbl_grade_code' or table_name = 'tbl_activity' or table_name = 'tbl_gender_ref'); ";
			 String values = req.getParameter("options"); //get values of selected courses and split them
			 String[] arr = values.split(",");
			
			 year = Integer.parseInt(req.getParameter("year"));
			 
			pageHead = "תלמידים בחוגים";
			html += "<div class='tableTitle'>"+ pageHead + "</div>";
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName"));
			/*arrlist.add(new AbstractMap.SimpleEntry<>("fullPupilCard","gender"));*/
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_gender_ref","genderName"));
			//arrlist.add(new AbstractMap.SimpleEntry<>("tbl_activity","activityName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil_activities","startDate"));
			
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil_activities","endDate"));

			 for (int i = 0; i < arr.length; i++) {
				 	// get each course id, name
					String[] courseVal = arr[i].split(";");
					int id = Integer.parseInt(courseVal[0]);
					String name = courseVal[1];
					
					// get registered pupil for each course and add them to the result json array
					jsonList = this.getJsonPupilActForExport(getPupilInCourse(id,year));
					if (!jsonList.isEmpty() && arrlist.size() > 0) {
						html += buildTableForHtml(query,whereClouse,arrlist,jsonList,name);							
					}
				}
		
			
			html += "</body></html>";
			break;
		case	"MoadonitPay":
			resp.setContentType("text/html;charset=UTF-8");
			//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
			whereClouse = " WHERE (table_name = 'tbl_pupil' or table_name = 'tbl_reg_to_moadonit' or table_name = 'tbl_grade_code' or table_name = 'tbl_gender_ref'); ";
			
			 
			 monthTime = Long.parseLong(req.getParameter("month"));
			 
			  d = new Date(monthTime);
			 
			 calendar = Calendar.getInstance();
			 calendar.clear();
			 calendar.setTime(d);
			 
			 Date fDay = calendar.getTime();
			 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			 Date eDay = calendar.getTime();
			 
			pageHead = "דוח חיוב מועדונית לחודש "+ (calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
			html += "<div class='tableTitle'>"+ pageHead + "</div>";
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_gender_ref","genderName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","startDate"));	
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","מספר ימים לחיוב"));	
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","ימי רישום"));	
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","הערות"));				
			
			this.regDAO = new RegToMoadonitDAO(con);
			regTypes = this.regDAO.getRegTypeCodes();
			
			if(this.regTypes == null || this.regTypes.isEmpty())
				return null;
			
		    map = this.regTypes; 
		
		    for (Entry<Integer, Object> entry : map.entrySet())
			{
		    	if(entry.getKey() == 1) continue;
				jsonList = this.repDOA.MoadonitPay(fDay, eDay, entry.getKey());
				if (!jsonList.isEmpty() && arrlist.size() > 0) {
					html += buildTableForHtml(query,whereClouse,arrlist,jsonList,entry.getValue().toString());							
				}					
			}
			
			html += "</body></html>";
			break;	
		case "MoadonitData":
			resp.setContentType("text/html;charset=UTF-8");
			//query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME FROM information_schema.columns ";
			whereClouse = " WHERE (table_name = 'tbl_pupil' or table_name = 'tbl_reg_to_moadonit'"
					+ " or table_name = 'tbl_grade_code' or table_name = 'tbl_gender_ref' or table_name = 'tbl_food_type' "
					+ "or table_name = 'tbl_register_pupil'); ";
			
			 
			 monthTime = Long.parseLong(req.getParameter("month"));
			 
			 Date s = new Date(monthTime);
			 
			 calendar = Calendar.getInstance();
			 calendar.clear();
			 calendar.setTime(s);
			 
			 /*Date fDay = calendar.getTime();
			 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			 Date eDay = calendar.getTime();*/
			 
			pageHead = "דוח קבוצות מועדונית לתאריך "+ calendar.get(Calendar.DAY_OF_MONTH)+ "/" +(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
			html += "<div class='tableTitle'>"+ pageHead + "</div>";
			
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_grade_code","gradeName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","lastName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_pupil","firstName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_gender_ref","genderName"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_register_pupil","healthProblems"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_register_pupil","ethiopian"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_register_pupil","staffChild"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_register_pupil","foodSensitivity"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_register_pupil","otherComments"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_food_type","סוג מנה"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","ימי רישום"));
			arrlist.add(new AbstractMap.SimpleEntry<>("tbl_reg_to_moadonit","הערות לרישום"));
	
			
			this.regDAO = new RegToMoadonitDAO(con);
			map = this.regDAO.getMoadonitGroupsToDate(s);
			
			if(map == null || map.isEmpty())
				return null;					   
		
		    for (Entry<Integer, Object> entry : map.entrySet())
			{
		    	if(entry.getKey() == 1) continue;
				jsonList = this.repDOA.getPupilsForMoadonitGroup( entry.getKey(), s);
				if (!jsonList.isEmpty() && arrlist.size() > 0) {
					html += buildTableForHtml(query,whereClouse,arrlist,jsonList,entry.getValue().toString());							
				}					
			}
			
			html += "</body></html>";
			break;		
			default:
				break;
		}
	


		return html;
	}
	
	protected String buildTableForHtml( String query, String whereClouse,ArrayList<Entry<String, String>> arrlist ,
			JSONArray jsonList, String pageHead ) throws  SQLException 
	{
		
		
		String html	= "<div class='tableTitle'>"
				+ pageHead
				+ "</div>"
				+ "<table border='1' class='tableList_1 t_space' cellspacing='10' cellpadding='0'>";
		// headers of table
		String theads = "";

		HashMap<Entry<String, String>, String[]> keysColComments = DAOUtil.getKeysColmunComments(this.con.getConnection(),query, whereClouse);
	
		if(keysColComments != null)
		for (int i = 0; i < arrlist.size(); i++)
		{
			Entry<String, String> key = arrlist.get(i);				
			
			if(keysColComments.get(key) != null){
				String title  = keysColComments.get(key)[3];					
				theads = theads + "<th>" + title + "</th>";
			}
			else{
				theads = theads + "<th>" + key.getValue() + "</th>";
			//	System.out.println(key.getValue());
			}
		}
		
		/*for (int i = 0; i < arrKeys.length; i++)
		{
			String s = arrKeys[i];
			String title  = t.get(s);
			theads = theads + "<th>" + title + "</th>";
		}*/

		theads = "<tr>" + theads + "</tr>";
		html += theads;

		// get all rows and build the html
		for (Object object : jsonList) {
			JSONObject obj = (JSONObject) object;
			html += "<tr>";
			for (int i = 0; i < arrlist.size(); i++) {
				Entry<String, String> keyEntry = arrlist.get(i);
				//String key = arrKeys[i];
				html += "<td>";
				if(obj.get(keyEntry) != null){
					if(obj.get(keyEntry) instanceof Boolean){
						boolean b = (boolean)obj.get(keyEntry);
						html += b ? "כן" : "לא" + "</td>";
					}
					else{
						html +=  obj.get(keyEntry) +  "</td>";
					}
				}
				
			}
			
			html += "</tr>";
		}

		html += "</table>";
		
		return html;
	}
	
	private List<Activity> getCourses(int year) {
		List<Activity> result = new ArrayList<>();		
		result = this.actDOA.selectActivites(0, year);
		return result;
	}
	
	private List<PupilActivity> getPupilInCourse(int actID, int year) {
		// TODO Auto-generated method stub

		this.pupilActDAO = new PupilActivityDAO(con);
		List<PupilActivity> list = this.pupilActDAO.getPupilInCourse(actID,
				null,year);

		return list;
	}
	
	
}
