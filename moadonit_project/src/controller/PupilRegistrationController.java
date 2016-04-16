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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.FullPupilCard;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import dao.FullPupilCardDAO;
import dao.RegToMoadonitDAO;

@WebServlet("/PupilRegistration")
public class PupilRegistrationController extends HttpServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyConnection con = null;
	RegToMoadonit reg;
	RegToMoadonitPK pkReg;
	JSONObject resultToClient = new JSONObject();
	List<RegToMoadonit> pupilRegList = null;
	RegToMoadonitDAO regDAO ;
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
		

		action = req.getParameter("action");

		this.reg = new RegToMoadonit();
		try {
			if (action.equals("getRegistration")) {

				this.pupilRegList = getRegistration(req, resp);
				String jsonResponse = "";
				JSONArray registrationData = new JSONArray();
				
				if (!pupilRegList.isEmpty()) {
					
					this.getRegistrationRow(registrationData);
					
					if (!registrationData.isEmpty()) {

						jsonResponse = registrationData.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ pupilRegList.size() + ",\"rows\":" + jsonResponse
								+ "}";

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonResponse);

					} else {

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
								+ 0 + ",\"rows\":" + jsonResponse
								+ "}";
						resp.getWriter().print(jsonResponse);
					}
				}else{
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					jsonResponse = registrationData.toJSONString();
					jsonResponse = "{\"page\":0,\"total\":\"0\",\"records\":"
							+ 0 + ",\"rows\":" + jsonResponse
							+ "}";
					resp.getWriter().print(jsonResponse);
				}
				

			}else if (action.equals("getWeekGrid")) {
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected List<RegToMoadonit> getRegistration(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {

		List<RegToMoadonit> pupils = new ArrayList<>();

		int pupilID = Integer.parseInt(req.getParameter("pupilID"));
		// dao
		this.regDAO = new RegToMoadonitDAO(con);
		pupils = regDAO.selectAll(pupilID);

		return pupils;
	}

	@SuppressWarnings("unchecked")
	protected void getRegistrationRow(JSONArray registrationData) {

		RegToMoadonit regPupil = this.pupilRegList.get(0);

		JSONObject user = new JSONObject();
		user.put("type", "סוג רישום");
		user.put("sunday", getRegType(regPupil.getSunday_()));
		user.put("monday", getRegType(regPupil.getMonday_()));
		user.put("tuesday", getRegType(regPupil.getTuesday_()));
		user.put("wednesday", getRegType(regPupil.getWednesday_()));
		user.put("thursday", getRegType(regPupil.getThursday_()));

		registrationData.add(user);

	}

	private String getRegType(int type) {

		if (type == 0)
			return "לא רשום";
		else if (type == 1)
			return "מועדונית";
		else
			return "אוכל בלבד";
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
