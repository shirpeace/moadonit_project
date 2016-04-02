package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

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
import model.FullPupilCard;
import model.Parent;
import model.Pupil;
import model.RegisterPupil;

@WebServlet("/FullPupilCardController")
public class FullPupilCardController extends HttpServlet implements
		Serializable {

	MyConnection con = null;
	private String action;
	FullPupilCard fullPupil = null;
	FullPupilCardDAO fullPupilDao;
	
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
				
				fullPupil = getFullPupilCard(req, resp);
				
				
				if (fullPupil != null) {

					String jsonObj = DAOUtil.getJsonFromObject(fullPupil);
					
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
		
		
		String pupilStr, pupilRegStr, familyStr, parentStr;
		
		Pupil p = new Pupil();
		RegisterPupil rp = new RegisterPupil();
		Family fam = new Family();
		Parent prent = new Parent();
		
		action = req.getParameter("action");
		
		this.familyDao = new FamilyDAO(con);
		this.parentDao = new ParentDAO(con);
		this.pupilDao = new PupilDAO(con);
		this.regPupilDao = new RegisterPupilDAO(con);
		
		if (action.equals("update")){
			
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

}
