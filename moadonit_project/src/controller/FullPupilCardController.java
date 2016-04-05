package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.DAOException;
import dao.FamilyDAO;
import dao.FullPupilCardDAO;
import dao.ParentDAO;
import dao.PupilDAO;
import dao.RegisterPupilDAO;
import util.DAOUtil;
import model.Family;
import model.FamilyRelation;
import model.FullPupilCard;
import model.GenderRef;
import model.Grade;
import model.Parent;
import model.Pupil;
import model.RegisterPupil;

@WebServlet("/FullPupilCardController")
public class FullPupilCardController extends HttpServlet implements
		Serializable {

	MyConnection con = null;
	private String action;
	FullPupilCard pupilCard = null;
	FullPupilCardDAO fullPupilDao;
	List<FullPupilCard> pupilList = null;

	PupilDAO pupilDao;
	FamilyDAO familyDao;
	ParentDAO parentDao;
	RegisterPupilDAO regPupilDao;
	JSONObject resultToClient = new JSONObject();
	 ;
	
	// Pupil
	/**
	 * 
	 */
	private static final long serialVersionUID = 1424940988973338461L;

	/**
	 * 
	 */
	public FullPupilCardController() {
		super();

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// check and set connection to session
		checkConnection(req, resp);

		try {

			action = req.getParameter("action");
			if (action.equals("get")) {

				pupilCard = getFullPupilCard(req, resp);

				if (pupilCard != null) {

					String jsonObj = DAOUtil.getJsonFromObject(pupilCard);

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(jsonObj);

				} else {
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print("error in get data for this pupil");

				}

			} else if (action.equals("getAll")) {
				pupilList = getFullPupilList(req, resp);

				/*
				 * if (pupilList != null) { String jsonObj = "{'pupils':[ +";
				 * for(int i=0;i<pupilList.size();i++){ ; jsonObj +=
				 * "'"+DAOUtil.getJsonFromObject(pupilList.get(i))+",' +"; }
				 * jsonObj += "]}'";
				 */
				pupilCard = pupilList.get(0);
				if (pupilCard != null) {

					String jsonArray = DAOUtil.getJsonFromObject(pupilCard);

					jsonArray = "{\"page\":1,\"total\":\"2\",\"records\":" + 1
							+ ",\"rows\":" + jsonArray + "}";

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(jsonArray);
					// resp.getWriter().print("{ msg: '1' , result: " +
					// jsonArray + "}");

				} else {
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter()
							.print("{ msg: '0' , result: " + null + "}");

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print("{ msg: '0' , result: " + null + "}");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req, resp);

		String pupilStr, pupilRegStr, familyStr, parentStr1, parentStr2;

		Pupil p = new Pupil();
		RegisterPupil rp = new RegisterPupil();
		Family fam = new Family();
		Parent parent1 = new Parent();
		Parent parent2 = new Parent();

		this.familyDao = new FamilyDAO(con);
		this.parentDao = new ParentDAO(con);
		this.pupilDao = new PupilDAO(con);
		this.regPupilDao = new RegisterPupilDAO(con);

		action = req.getParameter("action");
		
		if (action.equals("update")) {

			pupilStr = req.getParameter("pupilParam");
			pupilRegStr = req.getParameter("regPupilParam");
			familyStr = req.getParameter("familyParam");
			parentStr1 = req.getParameter("parent1Param");
			parentStr2 = req.getParameter("parent2Param");

			try {

				p = (Pupil) DAOUtil.getObjectFromJson(pupilStr, Pupil.class);
				rp = (RegisterPupil) DAOUtil.getObjectFromJson(pupilRegStr,
						RegisterPupil.class);
				fam = (Family) DAOUtil.getObjectFromJson(familyStr,
						Family.class);
				parent1 = (Parent) DAOUtil.getObjectFromJson(parentStr1,
						Parent.class);
				parent2 = (Parent) DAOUtil.getObjectFromJson(parentStr2,
						Parent.class);

				// * transaction block start *//
				this.con.getConnection().setAutoCommit(false);
				
				//save parent only if first name and last name arent empty
				if (!parent1.getFirstName().trim().equals("") && !parent1.getLastName().trim().equals("")) {
					// save parent 1
					this.parentDao.insert(parent1);

				}

				//save parent only if first name and last name arent empty
				if (!parent2.getFirstName().trim().equals("") && !parent2.getLastName().trim().equals("")) {
					// save parent 2
					this.parentDao.insert(parent2);
				}

				

				if (parent2.getParentID() > 0) {
					fam.setTblParent2(parent2);
				}

				// family must have parent1 (not null in DB ) 
				if (parent1.getParentID() > 0) {
					fam.setTblParent1(parent1);
					
					// save family
					this.familyDao.insert(fam);
					// save pupil
					p.setTblFamily(fam);
					
				}
				
				

				this.pupilDao.insert(p);

				// save regPupil
				rp.setPupilNum(p.getPupilNum());
				this.regPupilDao.insert(rp);

				this.con.getConnection().commit();

				
				resultToClient.put("msg", 1);
				resultToClient.put("result", p.getPupilNum());
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				
				resultToClient.put("msg", 0);
				resultToClient.put("result", null);
				resp.getWriter().print(resultToClient);			

				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resultToClient.put("msg", 0);
				resultToClient.put("result", null);
				resp.getWriter().print(resultToClient);			

				e.printStackTrace();
			}

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

		if (this.fullPupilDao == null)
			this.fullPupilDao = new FullPupilCardDAO(con);

	}

	protected FullPupilCard getFullPupilCard(HttpServletRequest req,
			HttpServletResponse resp) {

		FullPupilCard p = new FullPupilCard();
		int id = Integer.parseInt(req.getParameter("id"));
		p = this.fullPupilDao.selectById(id);

		return p;
	}

	protected List<FullPupilCard> getFullPupilList(HttpServletRequest req,
			HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectAll();

		return pupils;
	}
}
