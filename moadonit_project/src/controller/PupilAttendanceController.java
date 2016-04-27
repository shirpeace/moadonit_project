package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.AttendancePK;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.Attendance;
import dao.AttendanceDAO;
import dao.RegToMoadonitDAO;

@WebServlet("/PupilAttendanceController")
public class PupilAttendanceController extends HttpServlet implements Serializable {

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
	AttendanceDAO attendDao;
	private String action;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		checkConnection(req, resp);
		

		action = req.getParameter("action");

		this.reg = new RegToMoadonit();
		try {
			if (action.equals("getBlankAttend")) {
				prepareAttendanceTbl(new Date(1,1,16), new Date(18,4,16));
				

			}
			if(action.equals("loadGrid")){
				
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
		pupils = regDAO.selectAll(pupilID,0);

		return pupils;
	}

	@SuppressWarnings("unchecked")
	protected void getRegistrationRow(JSONArray registrationData) {

		RegToMoadonit regPupil = this.pupilRegList.get(0);

		JSONObject user = new JSONObject();
		user.put("type", "סוג רישום");
		user.put("sunday", getRegType(regPupil.getTblRegType1().getTypeNum()));
		user.put("monday", getRegType(regPupil.getTblRegType2().getTypeNum()));
		user.put("tuesday", getRegType(regPupil.getTblRegType3().getTypeNum()));
		user.put("wednesday", getRegType(regPupil.getTblRegType4().getTypeNum()));
		user.put("thursday", getRegType(regPupil.getTblRegType5().getTypeNum()));

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
	public PupilAttendanceController() {
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
//TODO consider the case where you get 2 regs per pupil (present&future regs)
	private void prepareAttendanceTbl(Date start, Date end) throws SQLException{
		this.regDAO = new RegToMoadonitDAO(con);
		List<RegToMoadonit> list = regDAO.getActiveRegs(end);
		for (RegToMoadonit reg : list) {
			List<Integer> regedTo = new ArrayList<Integer>();
			if(reg.getTblRegType1().getTypeNum()!=0) regedTo.add(Calendar.SUNDAY);
			if(reg.getTblRegType2().getTypeNum()!=0) regedTo.add(Calendar.MONDAY);
			if(reg.getTblRegType3().getTypeNum()!=0) regedTo.add(Calendar.TUESDAY);
			if(reg.getTblRegType4().getTypeNum()!=0) regedTo.add(Calendar.WEDNESDAY);
			if(reg.getTblRegType5().getTypeNum()!=0) regedTo.add(Calendar.THURSDAY);
			
			Calendar cal = Calendar.getInstance();
			for(cal.setTime(start) ; cal.after(end) ; cal.add(Calendar.DAY_OF_YEAR, 1)){
				if(list.contains(cal.get(Calendar.DAY_OF_WEEK))) {
					Attendance att = new Attendance();
					AttendancePK pk=  new AttendancePK();
					pk.setPupilID(reg.getId().getPupilNum());
					pk.setSpecifficDate(cal.getTime());
					att.setId(pk);
					attendDao.insert(att);
				}
			}
		}
	}
}
