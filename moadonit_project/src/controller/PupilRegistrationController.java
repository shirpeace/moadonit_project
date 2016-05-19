package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.DAOUtil;
import model.Activity;
import model.FullPupilCard;
import model.OneTimeReg;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.RegType;
import model.User;
import dao.FullPupilCardDAO;
import dao.OneTimeRegDao;
import dao.PupilActivityDAO;
import dao.RegToMoadonitDAO;

@WebServlet("/PupilRegistration")
public class PupilRegistrationController extends HttpServlet implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyConnection con = null;
	RegToMoadonit reg;
	RegToMoadonitPK pkReg;
	JSONObject resultToClient = new JSONObject();
	List<RegToMoadonit> pupilRegList = null;
	List<Activity> pupilAcivitiesList = null;
	List<OneTimeReg> listOneTimeReg = null;
	RegToMoadonitDAO regDAO;
	OneTimeRegDao oneTimesDAO;
	private String action;
	private PupilActivityDAO pupilActDAO;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		checkConnection(req, resp);
		this.regDAO = new RegToMoadonitDAO(con);
		action = req.getParameter("action");

		this.reg = new RegToMoadonit();
		String jsonResponse = "";
		JSONArray registrationData;

		try {
			if (action.equals("getRegistration")
					|| action.equals("getWeekGrid")) { 

				if (action.equals("getRegistration")){
					this.pupilRegList = getRegistration(req, resp,1);
					
				}
				else if (action.equals("getWeekGrid")){
					this.pupilRegList = getRegistration(req, resp,0);
					this.pupilAcivitiesList = getCoursesByPupilNum(req, resp,0);
					// get courses data
				}
				registrationData = new JSONArray();

				if (!pupilRegList.isEmpty()) {

					if (action.equals("getRegistration")) {
						this.getRegistrationRow(registrationData, 1);

					} else if (action.equals("getWeekGrid")) {
						
						this.getRegistrationRow(registrationData, 0);
					}

					if (!registrationData.isEmpty()) {

						jsonResponse = registrationData.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ pupilRegList.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonResponse);

					} else {

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
								+ 0 + ",\"rows\":" + jsonResponse + "}";
						resp.getWriter().print(jsonResponse);
					}
				} else {
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					jsonResponse = registrationData.toJSONString();
					jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
							+ 0 + ",\"rows\":" + jsonResponse + "}";
					resp.getWriter().print(jsonResponse);
				}

			} else if (action.equals("addRegistration")) {

				String rtm = req.getParameter("rtm");
				this.reg = (RegToMoadonit) DAOUtil.getObjectFromJson(rtm,
						this.reg.getClass());
				if (req.getSession().getAttribute("userid") != null) {
					JSONObject user = (JSONObject) req.getSession()
							.getAttribute("userid");
					User u = new User();
					u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());
					
					this.reg.setTblUser(u);

					if (this.regDAO.checkPk(this.reg)) {

						resultToClient.put("msg", 0);
						resultToClient.put("result","לתלמיד זה כבר קיים רישום בתאריך הספציפי");
					} else {

						boolean r = addRegistration(req, resp);
						if (r) {
							resultToClient.put("msg", 1);
							resultToClient.put("result", null);
						} else {
							resultToClient.put("msg", 0);
							resultToClient
									.put("result", "שגיאה בשמירת הנתונים");
						}
					}				

				}else{
					resultToClient.put("msg", 0);
					resultToClient.put("result", "שגיאה בשמירת הנתונים");				
				}

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
			}else if (action.equals("edit")) {
				
				boolean r =  editRegistration(req, resp);
				
				if (r) {
					resultToClient.put("msg", 1);
					resultToClient.put("result", "נתונים נשמרו בהצלחה");
				} else {
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					resultToClient.put("msg", 0);
					resultToClient
							.put("result", "שגיאה בשמירת הנתונים");
				}
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
				
			}else if (action.equals("delete")) {
				
				boolean r =  deleteRegistration(req, resp);
				
				if (r) {
					resultToClient.put("msg", 1);
					resultToClient.put("result", "רשומה נמחקה בהצלחה");
				} else {
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					resultToClient.put("msg", 0);
					resultToClient
							.put("result", "שגיאה במחיקת הנתונים");
				}
				
				resultToClient.put("msg", 1);
				resultToClient.put("result", "רשומה נמחקה בהצלחה");
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
				
			} else if (action.equals("getOneTimeReg")) { /*Shir */
				this.listOneTimeReg = getOneTimeReg(req, resp);
				registrationData = new JSONArray();
				if (!listOneTimeReg.isEmpty()) {
					this.getOneTimeJson(registrationData);
					
					if (!registrationData.isEmpty()) {

						jsonResponse = registrationData.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ listOneTimeReg.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonResponse);

					} else {

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
								+ 0 + ",\"rows\":" + jsonResponse + "}";
						resp.getWriter().print(jsonResponse);
					}
				} else {
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					jsonResponse = registrationData.toJSONString();
					jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
							+ 0 + ",\"rows\":" + jsonResponse + "}";
					resp.getWriter().print(jsonResponse);
				}
			} else if (action.equals("saveOneTime")) { /*Shir */
				/////////////////////////
				System.out.println(req.getParameter("oneTimeReg"));
//				S/*tring rtm = req.getParameter("rtm");
//				this.reg = (RegToMoadonit) DAOUtil.getObjectFromJson(rtm,
//						this.reg.getClass());
//				if (req.getSession().getAttribute("userid") != null) {
//					JSONObject user = (JSONObject) req.getSession()
//							.getAttribute("userid");
//					User u = new User();
//					u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());
//					
//					this.reg.setTblUser(u);
//
//					if (this.regDAO.checkPk(this.reg)) {
//
//						resultToClient.put("msg", 0);
//						resultToClient.put("result","לתלמיד זה כבר קיים רישום בתאריך הספציפי");
//					} else {
//
//						boolean r = addRegistration(req, resp);
//						if (r) {
//							resultToClient.put("msg", 1);
//							resultToClient.put("result", null);
//						} else {
//							resultToClient.put("msg", 0);
//							resultToClient
//									.put("result", "שגיאה בשמירת הנתונים");
//						}
//					}				
//
//				}else{
//					resultToClient.put("msg", 0);
//					resultToClient.put("result", "שגיאה בשמירת הנתונים");				
//				}
//
//				resp.setContentType("application/json");
//				resp.setCharacterEncoding("UTF-8");
//				resp.getWrit*/er().print(resultToClient);
				
				////////////////////
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			resultToClient.put("msg", 0);
			resultToClient.put("result", null);
			resp.getWriter().print(resultToClient);
			
			
		} catch (IOException e) {
			e.printStackTrace();

			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			resultToClient.put("msg", 0);
			resultToClient.put("result", null);
			resp.getWriter().print(resultToClient);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private List<Activity> getCoursesByPupilNum(HttpServletRequest req,
			HttpServletResponse resp, int i) {
		// TODO Auto-generated method stub
		List<Activity> result = new ArrayList<Activity>();
		int pupilID = Integer.parseInt(req.getParameter("pupilID"));
		this.pupilActDAO = new PupilActivityDAO(con);
		result = this.pupilActDAO.getCoursesByPupilNum(pupilID);
		return result;
	}

	private boolean deleteRegistration(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {
		// TODO Auto-generated method stub
		boolean r = false;
		RegToMoadonit regToUpdate = new RegToMoadonit();
		
		if (req.getSession().getAttribute("userid") != null) {
			JSONObject user = (JSONObject) req.getSession()
					.getAttribute("userid");
			User u = new User();
			u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());

			regToUpdate = (RegToMoadonit) DAOUtil.getObjectFromJson(req.getParameter("rtm"), regToUpdate.getClass());
			regToUpdate.setTblUser(u);
			
			this.con.getConnection().setAutoCommit(false);
			r = this.regDAO.delete(regToUpdate);
			if (r) {
				this.con.getConnection().commit();
			}
			
			this.con.getConnection().setAutoCommit(true);
			
			return r;
			
		}
		else{
			return false;
		}
	}

	private boolean editRegistration(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException, ParseException {
		boolean r = false;
		// * transaction block start *//
		RegToMoadonit regToUpdate = new RegToMoadonit();
		if (req.getSession().getAttribute("userid") != null) {
			JSONObject user = (JSONObject) req.getSession()
					.getAttribute("userid");
			User u = new User();
			u = (User) DAOUtil.getObjectFromJson(user.toString(), u.getClass());

			regToUpdate = (RegToMoadonit) DAOUtil.getObjectFromJson(req.getParameter("rtm"), regToUpdate.getClass());
			regToUpdate.setTblUser(u);
			
			this.con.getConnection().setAutoCommit(false);
			long milliSeconds= Long.parseLong(req.getParameter("_oldDateVal"));
			Date date =  new Date(milliSeconds);
			r = this.regDAO.update(regToUpdate,date);
			if (r) {
				this.con.getConnection().commit();
			}
			
			this.con.getConnection().setAutoCommit(true);
			
			return r;
			
		}
		else{
			return false;
		}
		
		
	}
	private boolean addRegistration(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {
		// TODO Auto-generated method stub
		boolean r = false;

		// * transaction block start *//
		this.con.getConnection().setAutoCommit(false);
		r = this.regDAO.insert(this.reg);
		this.con.getConnection().commit();
		this.con.getConnection().setAutoCommit(true);
		return r;

	}

	protected List<RegToMoadonit> getRegistration(HttpServletRequest req,
			HttpServletResponse resp , int future) throws IOException, SQLException {

		List<RegToMoadonit> pupils = new ArrayList<>();

		int pupilID = Integer.parseInt(req.getParameter("pupilID"));
		// dao
		this.regDAO = new RegToMoadonitDAO(con);
		pupils = regDAO.selectAll(pupilID,future);

		return pupils;
	}

	private Map<String,Object> createRow(String key, Object value, Object date, Object title, Object type){
		
		Map<String,Object> row = new HashMap<String, Object>();
		
		
		String val = "";
		row.put("sunday", null);
		row.put("monday", null);
		row.put("tuesday", null);
		row.put("wednesday", null);
		row.put("thursday", null);					
		for (Map.Entry<String, Object> entry : row.entrySet())
		{
			
		    if (entry.getKey().equals(key)) {
		    	row.put(key, value);
		    	val += title + "," + key;
			}
		    else{
		    	entry.setValue(null);		    	
		    }
		}
		//val = val.substring(0,val.lastIndexOf(";"));
		row.put("title", val);
		row.put("type",  type);
		row.put("startDate", date);
		
		return row;
	}
	@SuppressWarnings("unchecked")
	protected void getRegistrationRow(JSONArray registrationData, int type) {

		if (type == 0) { // get data for weekGrid

			RegToMoadonit regPupil = this.pupilRegList.get(0);

			JSONObject user = new JSONObject();
			user.put("title", "");
			user.put("type", "סוג רישום");
			user.put("startDate", regPupil.getId().getStartDate().getTime());
			user.put("sunday", getRegType(regPupil.getTblRegType1().getTypeNum()));
			user.put("monday", getRegType(regPupil.getTblRegType2().getTypeNum()));
			user.put("tuesday", getRegType(regPupil.getTblRegType3().getTypeNum()));
			user.put("wednesday", getRegType(regPupil.getTblRegType4().getTypeNum()));
			user.put("thursday", getRegType(regPupil.getTblRegType5().getTypeNum()));
			
			registrationData.add(user);
			List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
			Map<String,Object> row ;
			if (!this.pupilAcivitiesList.isEmpty()) {
				for (Activity act : this.pupilAcivitiesList) {
					boolean isAdded = false;
					String val = "";
					
					String value = act.getStartTime().toString().substring(0,5) +  ","+
							act.getEndTime().toString().substring(0,5) + "," +act.getTblPupilActivities().get(0).getStartDate() ;
					row = null;
					
					if (map.isEmpty()) {
								
						if(act.getWeekDay().equals("א")){
							row = createRow("sunday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
									, value,  "שם החוג");
														
						}

						
						if(act.getWeekDay().equals("ב")){
							row = createRow("monday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
									, value,  "שם החוג");
						}

						
						if (act.getWeekDay().equals("ג")) {
							row = createRow("tuesday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
									, value,  "שם החוג");
						}

						
						if (act.getWeekDay().equals("ד")) {
							row = createRow("wednesday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
									, value,  "שם החוג");
						}

						
						if (act.getWeekDay().equals("ה")) {
							row = createRow("thursday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
									, value,  "שם החוג");
						}

						if(row != null)
						map.add(row);
					}
					else{
						
							for (Map<String, Object> mapedRow : map) {
								
								if (act.getWeekDay().equals("א") && mapedRow.get("sunday") != null) {
									continue;
									//Map<String, Object> newRow = new HashMap<String, Object>();
								}
								else if(act.getWeekDay().equals("א")){
									mapedRow.put("sunday", act.getActivityName());
									isAdded = true;
									mapedRow.put("title",mapedRow.get("title").toString()+";"+value+",sunday");
									
									break;
								}
								
								if (act.getWeekDay().equals("ב") && mapedRow.get("monday") != null) {
									continue;
									//Map<String, Object> newRow = new HashMap<String, Object>();
								}
								else  if(act.getWeekDay().equals("ב")){
									mapedRow.put("monday", act.getActivityName());
									mapedRow.put("title",mapedRow.get("title").toString()+";"+value+",monday");
									isAdded = true;
									break;
								}
								
								if (act.getWeekDay().equals("ג") && mapedRow.get("tuesday") != null) {
									continue;
									//Map<String, Object> newRow = new HashMap<String, Object>();
								}
								else if(act.getWeekDay().equals("ג")){
									mapedRow.put("tuesday", act.getActivityName());
									mapedRow.put("title",mapedRow.get("title").toString()+";"+value+",tuesday");
									isAdded = true;
									break;
								}
								
								if (act.getWeekDay().equals("ד") && mapedRow.get("wednesday") != null) {
									continue;
									//Map<String, Object> newRow = new HashMap<String, Object>();
								}
								else if(act.getWeekDay().equals("ד")){
									mapedRow.put("wednesday", act.getActivityName());
									mapedRow.put("title",mapedRow.get("title").toString()+";"+value+",wednesday");
									isAdded = true;
									break;
								}
								
								if (act.getWeekDay().equals("ה") && mapedRow.get("thursday") != null) {
									continue;
									//Map<String, Object> newRow = new HashMap<String, Object>();
								}
								
								else if(act.getWeekDay().equals("ה")){
									mapedRow.put("thursday", act.getActivityName());
									mapedRow.put("title",mapedRow.get("title").toString()+";"+value+",thursday");
									isAdded = true;
									break;
								}
							}
							
							if (!isAdded) {
								if (act.getWeekDay().equals("א")){
									row = createRow("sunday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
											, value,  "שם החוג");
								}
								if (act.getWeekDay().equals("ב")){
									row = createRow("monday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
											, value,  "שם החוג");
								}
								if (act.getWeekDay().equals("ג")){
									row = createRow("tuesday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
											, value,  "שם החוג");
								}
								if (act.getWeekDay().equals("ד")){
									row = createRow("wednesday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
											, value,  "שם החוג");
								}
								if (act.getWeekDay().equals("ה")){
									row = createRow("thursday", act.getActivityName() , act.getTblPupilActivities().get(0).getStartDate().getTime()
											, value,  "שם החוג");
								}
							}
							
							if(row != null)
							map.add(row);
					}
					
					

					
					// get all courses and add the to list as registration rows 
					/*JSONObject c = new JSONObject();
					c.put("type", "שם החוג");
					
					String val = act.getStartTime().toString().substring(0,5) +  ","+
							act.getEndTime().toString().substring(0,5) + "," +act.getTblPupilActivities().get(0).getStartDate() ;
					
					
					if(act.getWeekDay().equals("א")){
						c.put("sunday", act.getActivityName() );
						c.put("title", val);
					}
					else{
						c.put("sunday", null);
					}
					
					if(act.getWeekDay().equals("ב")){
						c.put("monday", act.getActivityName());
						c.put("title", val);
					}
					else{
						c.put("monday", null);
					}
					
					if (act.getWeekDay().equals("ג")) {
						c.put("tuesday", act.getActivityName());
						c.put("title", val);
					}
					else{
						c.put("tuesday", null);
					}
					
					if (act.getWeekDay().equals("ד")) {
						c.put("wednesday", act.getActivityName());
						c.put("title", val);
					}
					else{
						user.put("wednesday", null);
					}
					
					if (act.getWeekDay().equals("ה")) {
						c.put("thursday", act.getActivityName());
						c.put("title", val);
					}
					else{
						c.put("thursday", null);
					}
					
					c.put("startDate", act.getTblPupilActivities().get(0).getStartDate().getTime());
					
					registrationData.add(c);*/
				}
				
				for (Map<String, Object> mapedRow : map) {
					JSONObject newRow = new JSONObject();
					for (Map.Entry<String, Object> entry : mapedRow.entrySet())
					{
						newRow.put(entry.getKey(),entry.getValue());
					}
					registrationData.add(newRow);
				}
			}
			
			//System.out.println(map);

		} else if (type == 1) { // get data for for registration

			for (RegToMoadonit regPupil : this.pupilRegList) {
				JSONObject user = new JSONObject();

				user.put("startDate", regPupil.getId().getStartDate().getTime());
				user.put("sunday", getRegType(regPupil.getTblRegType1().getTypeNum()));
				user.put("monday", getRegType(regPupil.getTblRegType2().getTypeNum()));
				user.put("tuesday", getRegType(regPupil.getTblRegType3().getTypeNum()));
				user.put("wednesday", getRegType(regPupil.getTblRegType4().getTypeNum()));
				user.put("thursday", getRegType(regPupil.getTblRegType5().getTypeNum()));

				registrationData.add(user);

			}

		}

	}
	
	@SuppressWarnings("unchecked")
	protected void getOneTimeJson(JSONArray registrationData) { /*Shir */

	// get data for for registration

			for (OneTimeReg oneTimes : this.listOneTimeReg) {
				JSONObject user = new JSONObject();

				user.put("regDate", oneTimes.getId().getSpecificDate().getTime());
				//user.put("regType", getRegType(Integer.parseInt(oneTimes.getRegType()))); //why regtype is not an int?
								
				registrationData.add(user);

			

		}

	}
	private List<OneTimeReg> getOneTimeReg(HttpServletRequest req, /*Shir */
			HttpServletResponse resp) {
		List<OneTimeReg> oneTimes = new ArrayList<>();

		int pupilID = Integer.parseInt(req.getParameter("pupilID"));
		// dao
		this.oneTimesDAO = new OneTimeRegDao(con);
		oneTimes = oneTimesDAO.select(pupilID);

		return oneTimes;
	}

	private String getRegType(int type) {

		if (type == 1)
			return "לא רשום";
		else if (type == 2)
			return "מועדונית";
		else if (type == 3)
			return "אוכל בלבד";
		return "";
	}

	/**
	 * 
	 */
	public PupilRegistrationController() {
		super();
		// TODO Auto-generated constructor stub
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

}
