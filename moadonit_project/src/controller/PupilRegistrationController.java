package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import model.FullPupilCard;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.RegType;
import model.User;
import dao.FullPupilCardDAO;
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
	RegToMoadonitDAO regDAO;
	private String action;

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

				if (action.equals("getRegistration"))
					this.pupilRegList = getRegistration(req, resp,1);
				else if (action.equals("getWeekGrid"))
					this.pupilRegList = getRegistration(req, resp,0);
				
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

	@SuppressWarnings("unchecked")
	protected void getRegistrationRow(JSONArray registrationData, int type) {

		if (type == 0) { // get data for weekGrid

			RegToMoadonit regPupil = this.pupilRegList.get(0);

			JSONObject user = new JSONObject();
			user.put("type", "סוג רישום");
			user.put("startDate", regPupil.getId().getStartDate().getTime());
			user.put("sunday", getRegType(regPupil.getTblRegType1().getTypeNum()));
			user.put("monday", getRegType(regPupil.getTblRegType2().getTypeNum()));
			user.put("tuesday", getRegType(regPupil.getTblRegType3().getTypeNum()));
			user.put("wednesday", getRegType(regPupil.getTblRegType4().getTypeNum()));
			user.put("thursday", getRegType(regPupil.getTblRegType5().getTypeNum()));

			registrationData.add(user);

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
