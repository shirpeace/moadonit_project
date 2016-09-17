package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
