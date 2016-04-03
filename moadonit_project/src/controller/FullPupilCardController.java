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
	//Pupil 
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
		checkConnection(req,resp);
		
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

			}
			else
				if (action.equals("getAll")) {
					pupilList = getFullPupilList(req, resp);
					
					
					/*	if (pupilList != null) {
						String jsonObj = "{'pupils':[ +";
						for(int i=0;i<pupilList.size();i++){
							;
							jsonObj += "'"+DAOUtil.getJsonFromObject(pupilList.get(i))+",' +";
						}
						jsonObj += "]}'";*/
						pupilCard = pupilList.get(0);
						if (pupilCard != null) {

							String jsonArray = DAOUtil.getJsonFromObject(pupilCard);
							
							
			                jsonArray = "{\"page\":1,\"total\":\"2\",\"records\":"
			                        + 1 + ",\"rows\":" + jsonArray + "}";
							
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print(jsonArray);

					} else {
						resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().print("error in get data for pupils list");

					}
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print("error in get data for this pupil");
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req,resp);
		
		
		String pupilStr, pupilRegStr, familyStr, parentStr1,parentStr2;
		int gradeID , gender; 
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
		
		if (action.equals("update")){
			
			pupilStr = req.getParameter("pupilParam");
			pupilRegStr = req.getParameter("regPupilParam");
			familyStr = req.getParameter("familyParam");
			parentStr1 = req.getParameter("parent1Param");
			parentStr2 = req.getParameter("parent2Param");
			gender = Integer.parseInt(req.getParameter("gender"));
			gradeID = Integer.parseInt(req.getParameter("gradeID"));
			
			try {
				
				
				p = (Pupil) DAOUtil.getObjectFromJson(pupilStr, Pupil.class);
				rp = (RegisterPupil) DAOUtil.getObjectFromJson(pupilRegStr, RegisterPupil.class);
				fam = (Family) DAOUtil.getObjectFromJson(familyStr, Family.class);
				parent1 = (Parent) DAOUtil.getObjectFromJson(parentStr1, Parent.class);
				parent2 = (Parent) DAOUtil.getObjectFromJson(parentStr2, Parent.class);
				
				//* transaction block start *//
			    this.con.getConnection().setAutoCommit(false);
				
				if (!parent1.getFirstName().trim().equals("")) {
					//save parent 1
					FamilyRelation fr = new FamilyRelation();
					
					this.parentDao.insert(parent1);
					
				}
				
				else if(!parent2.getFirstName().trim().equals("")){
					//save parent 2
					this.parentDao.insert(parent2);
				}
				
				if (parent1.getParentID() > 0) {
					fam.setTblParent1(parent1);					
				}
				
				if (parent2.getParentID() > 0) {
					fam.setTblParent2(parent2);					
				}
				
				//save family
				this.familyDao.insert(fam);
				//save pupil
				p.setTblFamily(fam);
				
				GenderRef g = new GenderRef();
				g.setGender(gender);				
				p.setTblGenderRef(g);
				
				Grade grade = new Grade();
				grade.setGradeID(gradeID);
				p.setTblGrade(grade);
				
				this.pupilDao.insert(p);
				
				//save regPupil
				rp.setPupilNum(p.getPupilNum());
				this.regPupilDao.insert(rp);
				
				this.con.getConnection().commit();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}

	protected void checkConnection(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
