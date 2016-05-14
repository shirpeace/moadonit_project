package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Activity;
import model.RegToMoadonit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.ActivityDAO;
@WebServlet("/ActivityController")
public class ActivityController extends HttpServlet implements
Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyConnection con = null;
	JSONArray jsonArry;
	ActivityDAO actDOA;
	List<Activity> listAct;
	JSONObject resultToClient = new JSONObject();
	/**
	 * 
	 */
	public ActivityController() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req, resp);
		this.actDOA = new ActivityDAO(con);
		String action = req.getParameter("action");
		String jsonResponse = "";
		this.jsonArry = new JSONArray();
		try {
			if (action.equals("getCourses")){
				listAct  = getCourses(req,resp);
				
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
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req, resp);
		this.actDOA = new ActivityDAO(con);
		String action = req.getParameter("action");
		String jsonResponse = "";
		this.jsonArry = new JSONArray();
		try {
			if (action.equals("getCourses")){
				String search = req.getParameter("_search");
				
				if (search.equals("true")) {
					//search data
					listAct = searchCourses(req, resp);
				}else{
					
					//load all data
				    listAct  = getCourses(req,resp);
					
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void updateCourse(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	private void insertCourse(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	private List<Activity> searchCourses(HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub

		List<Activity> result = new ArrayList<>();
		String week = req.getParameter("weekDay");
		
		week = (week != null && week.trim().equals("")) ? null : week;

		String isRegular = req.getParameter("regularOrPrivate");
		isRegular = (isRegular != null && isRegular.trim().equals("")) ? null : isRegular;
		
		String firstName = req.getParameter("firstName");
		firstName = (firstName != null && firstName.trim().equals("")) ? null : firstName;
		
		//req.getParameter("regularOrPrivate").trim().equals("") ? null : req.getParameter("regularOrPrivate");
		
		result = this.actDOA.searchCoursesByParam(
				// 0, // id
				req.getParameter("activityName"), 
				week, 
				req.getParameter("startTime"), 
				req.getParameter("endTime"),
				firstName, //no need for _responsibleStaff
				req.getParameter("pricePerMonth") != null ? Float.parseFloat(req.getParameter("pricePerMonth")) : 0,
				req.getParameter("extraPrice") != null ? Float.parseFloat(req.getParameter("extraPrice")) : 0
				,isRegular, 0 //no need for category
				);
		return result;
	}
	@SuppressWarnings("unchecked")
	protected JSONObject getJsonActivities(Activity act ) {

//		/activityNum, activityType, activityName, weekDay, startTime, endTime, schoolYear, responsibleStaff, activityNum, pricePerMonth, extraPrice, regularOrPrivate, category
			
				JSONObject obj = new JSONObject();

				obj.put("activityNum", act.getActivityNum());
				obj.put("activityType", act.getTblActivityType().getTypeID());
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
	
	private List<Activity> getCourses(HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		List<Activity> result = new ArrayList<>();
		int actID = Integer.parseInt(req.getParameter("activityNum"));
		result = this.actDOA.selectActivites(actID);
		return result;
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
