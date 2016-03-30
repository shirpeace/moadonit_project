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

import dao.FullPupilCardDAO;
import dao.PupilDAO;
import util.DAOUtil;
import model.FullPupilCard;
import model.Pupil;

@WebServlet("/FullPupilCardController")
public class FullPupilCardController extends HttpServlet implements
		Serializable {

	MyConnection con = null;
	private String action;
	FullPupilCard regPupil = null;
	FullPupilCardDAO dao;
	Pupil p = null;
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

		try {
			

			if (this.dao == null)
				this.dao = new FullPupilCardDAO(con);

			int id = Integer.parseInt(req.getParameter("id"));
			PupilDAO pdao = new PupilDAO(con);
			p = pdao.selectById(id);
			
			action = req.getParameter("action");
			if (action.equals("get")) {
				
				regPupil = getFullPupilCard(req, resp);

				if (regPupil != null) {

					String jsonObj = DAOUtil.getJsonFromObject(regPupil);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(jsonObj);

				} else {
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print("error");

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	protected FullPupilCard getFullPupilCard(HttpServletRequest req,
			HttpServletResponse resp) {

		FullPupilCard p = new FullPupilCard();
		int id = Integer.parseInt(req.getParameter("id"));
		p = this.dao.selectById(id);

		return p;
	}

}
