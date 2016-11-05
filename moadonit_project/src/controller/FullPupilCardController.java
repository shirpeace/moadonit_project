package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import org.eclipse.persistence.exceptions.IntegrityChecker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xhtmlrenderer.pdf.ITextRenderer;

import dao.DAOException;
import dao.FamilyDAO;
import dao.FullPupilCardDAO;
import dao.GeneralDAO;
import dao.GradeCodeDAO;
import dao.GradePupilDAO;
/*import dao.GradeDAO;*/
import dao.ParentDAO;
import dao.PupilDAO;
import dao.RegToMoadonitDAO;
import dao.RegisterPupilDAO;
import util.DAOUtil;
import model.Family;
import model.FamilyRelation;
import model.FoodType;
import model.FullPupilCard;
import model.GenderRef;
/*import model.Grade;*/
import model.GradeCode;
import model.Parent;
import model.Pupil;
import model.PupilState;
import model.RegSource;
import model.RegToMoadonit;
import model.RegisterPupil;

@WebServlet("/FullPupilCardController")
public class FullPupilCardController extends HttpServlet implements
		Serializable {

	MyConnection con = null;
	private String action;
	JSONObject filters;
	FullPupilCard pupilCard = null;
	FullPupilCardDAO fullPupilDao;
	ReportsController reporter;
	Pupil p = null;
	RegisterPupil rp = null;
	Family fam = null;
	Parent parent1 = null;
	Parent parent2 = null;
	List<FullPupilCard> data = null;

	PupilDAO pupilDao;
	FamilyDAO familyDao;
	ParentDAO parentDao;
	RegisterPupilDAO regPupilDao;
	RegToMoadonitDAO regToMoadonitDAO;
	GradeCodeDAO gradeDAO;
	GeneralDAO generalDAO;
	GradePupilDAO gradePupilDAO;
	JSONObject resultToClient = new JSONObject();;
	int rows;
	int page;
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

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// check and set connection to session
		checkConnection(req, resp);
		gradeDAO = new GradeCodeDAO(con);
		generalDAO = new GeneralDAO(con);
		this.fullPupilDao = new FullPupilCardDAO(con);
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
			if (action.equals("getGrades")) {

				JSONObject jsonObj = getGradesJson();

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jsonObj);
			}
			
			if (action.equals("getFoodTypes")) {

				JSONObject jsonObj = getFoodTypesJson();

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jsonObj);
			}
			
			if (action.equals("getFamilyRelation")) {

				JSONObject jsonObj = getFamilyRelationJson();

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jsonObj);
			}
			
			if (action.equals("getRegSource")) {

				JSONObject jsonObj = getRegSourceJson();

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jsonObj);
			}
			

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

	private JSONObject getRegSourceJson() {
		// TODO Auto-generated method stub
		List<RegSource> list = generalDAO.getRegSource(0);
		
		String values = " : ;";
		for (int i = 0; i < list.size(); i++) { // { value: ":;1:בן;2:בת"}
			RegSource ft = list.get(i);
			values += ft.getSourceNum() + ":" + ft.getSourceName()  + ";";
		}
		values = values.substring(0, values.length() - 1);
		JSONObject json = new JSONObject();
		json.put("value", values);
		return json;
	}

	private JSONObject getFamilyRelationJson() {
		List<FamilyRelation> list = generalDAO.getFamilyRelation(0);
		
		String values = " : ;";
		for (int i = 0; i < list.size(); i++) { // { value: ":;1:בן;2:בת"}
			FamilyRelation ft = list.get(i);
			values += ft.getIdFamilyRelation() + ":" + ft.getRelation()  + ";";
		}
		values = values.substring(0, values.length() - 1);
		JSONObject json = new JSONObject();
		json.put("value", values);
		return json;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getGradesJson() {
		List<GradeCode> list = gradeDAO.selectIndex(0);
		String values = " : ;";
		for (int i = 0; i < list.size(); i++) { // { value: ":;1:בן;2:בת"}
			GradeCode grade = list.get(i);
			values += grade.getGradeID() + ":" + grade.getGradeName() + ":"
					+ grade.getGradeColor() + ";";
		}
		values = values.substring(0, values.length() - 1);
		JSONObject json = new JSONObject();
		json.put("value", values);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getFoodTypesJson() {
		List<FoodType> list = generalDAO.getFoodTypes(0);
		
		String values = " : ;";
		for (int i = 0; i < list.size(); i++) { // { value: ":;1:בן;2:בת"}
			FoodType ft = list.get(i);
			values += ft.getFoodTypeID() + ":" + ft.getFoodType()  + ";";
		}
		values = values.substring(0, values.length() - 1);
		JSONObject json = new JSONObject();
		json.put("value", values);
		return json;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		checkConnection(req, resp);

		p = new Pupil();
		rp = new RegisterPupil();
		fam = new Family();
		parent1 = new Parent();
		parent2 = new Parent();

		this.familyDao = new FamilyDAO(con);
		this.parentDao = new ParentDAO(con);
		this.pupilDao = new PupilDAO(con);
		this.regPupilDao = new RegisterPupilDAO(con);
		this.fullPupilDao = new FullPupilCardDAO(con);
		this.gradePupilDAO = new GradePupilDAO(con);
		this.gradeDAO = new GradeCodeDAO(con);
		this.generalDAO = new GeneralDAO(con);
		
		action = req.getParameter("action");
		rows = req.getParameter("rows") != null ? Integer.parseInt(req
				.getParameter("rows")) : 0;
		page = req.getParameter("page") != null ? Integer.parseInt(req
				.getParameter("page")) : 0;
		int totalPages = 0;
		int totalCount = 0;
		try {

			if (action.equals("insert")) {

				insertPupil(req, resp);

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);

			}
			//
			else if (action.equals("SelectPupilNotInActivity")) {

				String search = req.getParameter("_search");
				if (search.equals("true")) {
					// on filtering popup window
					 List<FullPupilCard> list = fillterPupilNotInActivity(req, resp);
					JSONArray jsonPupilList = new JSONArray();
					getPupilList(jsonPupilList, list);

					if (!jsonPupilList.isEmpty()) {

						String jsonResponse = jsonPupilList.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ list.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

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

				else { // on pop up search page load

					List<FullPupilCard> list = SelectPupilNotInActivity(req, resp);
					JSONArray jsonPupilList = new JSONArray();
					getPupilList(jsonPupilList, list);
					if (!jsonPupilList.isEmpty()) {

						String jsonResponse = jsonPupilList.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ list.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

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

			} else if (action.equals("pupilSearch")) {
				// on searching
				String search = req.getParameter("_search");
				//boolean isExport = req.getParameter("isExport") != null;
				if (search.equals("true")) {
					//if (!isExport) {
					List<FullPupilCard> list = searchPupilList(req, resp, rows
								* (page - 1), rows);
						JSONArray jsonPupilList = new JSONArray();
						getPupilList(jsonPupilList, list);
						if (!jsonPupilList.isEmpty()) {

							String jsonResponse = jsonPupilList.toJSONString();

							totalCount = searchPupilList(req, resp, 0, 0)
									.size();

							if (totalCount > 0) {
								if (totalCount % rows == 0) {
									totalPages = totalCount / rows;
								} else {
									totalPages = (totalCount / rows) + 1;
								}

							} else {
								totalPages = 0;
							}

							jsonResponse = "{\"page\":" + page + ",\"total\":"
									+ totalPages + ",\"records\":" + totalCount
									+ ",\"rows\":" + jsonResponse + "}";

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
					//} else {
						/**
						 * exprot with filters
						 */

					//	exportExcel(req, resp);

					//}

				} else { // on search page load
					List<FullPupilCard> list = getFullPupilList(req, resp, rows * (page - 1),
							rows);
					JSONArray jsonPupilList = new JSONArray();
					getPupilList(jsonPupilList, list);
					if (!jsonPupilList.isEmpty()) {

						totalCount = getFullPupilList(req, resp, 0, 0).size();

						if (totalCount > 0) {
							if (totalCount % rows == 0) {
								totalPages = totalCount / rows;
							} else {
								totalPages = (totalCount / rows) + 1;
							}

						} else {
							totalPages = 0;
						}

						String jsonResponse = jsonPupilList.toJSONString();
						jsonResponse = "{\"page\":" + page + ",\"total\":"
								+ totalPages + ",\"records\":" + totalCount
								+ ",\"rows\":" + jsonResponse + "}";

						/*
						 * jsonResponse =
						 * "{\"page\":1,\"total\":\"1\",\"records\":" +
						 * pupilList.size() + ",\"rows\":" + jsonResponse + "}";
						 */

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
			} else if (action.equals("contactPage")) {
				// on contact page searching
				String search = req.getParameter("_search");
				if (search.equals("true")) {
					data = searchContactList(req, resp);
					JSONArray jsonPupilList = new JSONArray();
					getContactList(jsonPupilList);

					if (!jsonPupilList.isEmpty()) {

						String jsonResponse = jsonPupilList.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ data.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

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

				else { // on contact page load

					data = getFullPupilList(req, resp, rows * (page - 1),
							rows);
					JSONArray jsonPupilList = new JSONArray();
					getContactList(jsonPupilList);
					if (!jsonPupilList.isEmpty()) {

						String jsonResponse = jsonPupilList.toJSONString();
						jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
								+ data.size()
								+ ",\"rows\":"
								+ jsonResponse + "}";

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
			} else if (action.equals("update")) {

				updatePupil(req, resp);

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);

			} else if (action.equals("getGrades")) {
				JSONObject jo = this.getGradesJson();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(jo);

			} else if (action.equals("export")) {

		//		String[] arrKeys = { "lastName","firstName","gender","gradeName","isReg" };
				reporter = new ReportsController();
				reporter.exportExcel(req, resp , this);

			}

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



	protected void checkConnection(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("connection") != null) {
			con = (MyConnection) session.getAttribute("connection");
		} else {
			try {
				con = new MyConnection();
				session.setAttribute("connection", con);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@SuppressWarnings("unchecked")
	protected JSONObject updatePupil(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {

		p = new Pupil();
		rp = new RegisterPupil();
		fam = new Family();
		parent1 = new Parent();
		parent2 = new Parent();

		String pupilStr, pupilRegStr, familyStr, parentStr1, parentStr2;

		pupilStr = req.getParameter("pupilParam");
		pupilRegStr = req.getParameter("regPupilParam");
		familyStr = req.getParameter("familyParam");
		parentStr1 = req.getParameter("parent1Param");
		parentStr2 = req.getParameter("parent2Param");

		p = (Pupil) DAOUtil.getObjectFromJson(pupilStr, Pupil.class);
		rp = (RegisterPupil) DAOUtil.getObjectFromJson(pupilRegStr,
				RegisterPupil.class);
		fam = (Family) DAOUtil.getObjectFromJson(familyStr, Family.class);
		parent1 = (Parent) DAOUtil.getObjectFromJson(parentStr1, Parent.class);
		parent2 = (Parent) DAOUtil.getObjectFromJson(parentStr2, Parent.class);

		// * transaction block start *//
		this.con.getConnection().setAutoCommit(false);

		if (parent1.getParentID() == 0) {
			if (!parent1.getFirstName().trim().equals("")
					&& !parent1.getLastName().trim().equals("")) {
				// insert parent 1
				this.parentDao.insert(parent1);

			}
		} else {
			// update parent 1
			this.parentDao.update(parent1);

		}

		if (parent2.getParentID() == 0) {
			if (!parent2.getFirstName().trim().equals("")
					&& !parent2.getLastName().trim().equals("")) {
				// insert parent 2
				this.parentDao.insert(parent2);

			}
		} else {
			// update parent 2
			this.parentDao.update(parent2);

		}

		if (parent1.getParentID() > 0 && fam != null) {

			fam.setTblParent1(parent1);
		}

		if (parent2.getParentID() > 0 && fam != null) {
			fam.setTblParent2(parent2);

		}
		
		if (fam.getFamilyID() > 0) {
			// update family
			this.familyDao.update(fam);
			
		} else {
			// insert family			
			this.familyDao.insert(fam);
		}
		
		p.setTblFamily(fam);
		
		if (p.getPupilNum() > 0) {
			// update pupil
			this.pupilDao.update(p);

			// update gradePupil
			if (!p.getTblGradePupils().isEmpty())
				this.gradePupilDAO.update(p.getTblGradePupils().get(0));

			if (p.getPupilNum() > 0) {
				// check if update regPupil
				RegisterPupil temp = this.regPupilDao.selectById(rp
						.getPupilNum());
				if (temp.getPupilNum() > 0) {
					if (rp.getEthiopian() != 0
							|| !rp.getFoodSensitivity().trim().equals("")
							|| !rp.getHealthProblems().trim().equals("")
							|| !rp.getOtherComments().trim().equals("")
							|| (rp.getStaffChild() != null && !rp
									.getStaffChild().trim().equals(""))
							|| rp.getTblFoodType().getFoodTypeID() != 0) {

						if (rp.getTblFoodType().getFoodTypeID() == 0) {
							rp.getTblFoodType().setFoodTypeID(1);
						}

						rp.setPupilNum(p.getPupilNum());
						this.regPupilDao.update(rp);
					}

				}
				else{
					// insert regPupil
					rp.setPupilNum(p.getPupilNum());
					if (rp.getEthiopian() != 0
							|| !rp.getFoodSensitivity().trim().equals("")
							|| !rp.getHealthProblems().trim().equals("")
							|| !rp.getOtherComments().trim().equals("")
							|| (rp.getStaffChild() != null && !rp.getStaffChild()
									.trim().equals(""))
							|| rp.getTblFoodType().getFoodTypeID() != 0) {

						if (rp.getTblFoodType().getFoodTypeID() == 0) {
							rp.getTblFoodType().setFoodTypeID(1);
						}

						rp.setPupilNum(p.getPupilNum());
						this.regPupilDao.insert(rp);
					}
				}
			}

		}

		this.con.getConnection().commit();

		resultToClient.put("msg", 1);
		resultToClient.put("result", p.getPupilNum());

		return resultToClient;

	}

	@SuppressWarnings("unchecked")
	protected JSONObject insertPupil(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException {

		String pupilStr, pupilRegStr, familyStr, parentStr1, parentStr2;

		p = new Pupil();
		rp = new RegisterPupil();
		fam = new Family();
		parent1 = new Parent();
		parent2 = new Parent();

		pupilStr = req.getParameter("pupilParam");
		pupilRegStr = req.getParameter("regPupilParam");
		familyStr = req.getParameter("familyParam");
		parentStr1 = req.getParameter("parent1Param");
		parentStr2 = req.getParameter("parent2Param");

		p = (Pupil) DAOUtil.getObjectFromJson(pupilStr, Pupil.class);
		rp = (RegisterPupil) DAOUtil.getObjectFromJson(pupilRegStr,
				RegisterPupil.class);
		fam = (Family) DAOUtil.getObjectFromJson(familyStr, Family.class);
		parent1 = (Parent) DAOUtil.getObjectFromJson(parentStr1, Parent.class);
		parent2 = (Parent) DAOUtil.getObjectFromJson(parentStr2, Parent.class);

		// * transaction block start *//
		this.con.getConnection().setAutoCommit(false);

		// save parent only if first name and last name arent empty
		if (!parent1.getFirstName().trim().equals("")
				&& !parent1.getLastName().trim().equals("")) {
			// save parent 1
			this.parentDao.insert(parent1);

		}

		// save parent only if first name and last name arent empty
		if (!parent2.getFirstName().trim().equals("")
				&& !parent2.getLastName().trim().equals("")) {
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

		PupilState s = new PupilState();
		s.setStateNum(1);
		p.setTblPupilState(s);
		
		this.pupilDao.insert(p);

		// save pupil is to the pupilGrade id entity and save pupilGrade to DB
		p.getTblGradePupils().get(0).getId().setPupilNum(p.getPupilNum());
		// insert row to pupilgrade for this pupil
		this.gradePupilDAO.insert(p.getTblGradePupils().get(0));

		// save regPupil

		if (rp.getEthiopian() != 0
				|| !rp.getFoodSensitivity().trim().equals("")
				|| !rp.getHealthProblems().trim().equals("")
				|| !rp.getOtherComments().trim().equals("")
				|| (rp.getStaffChild() != null && !rp.getStaffChild().trim()
						.equals(""))
				|| rp.getTblFoodType().getFoodTypeID() != 0) {
			rp.setPupilNum(p.getPupilNum());
			this.regPupilDao.insert(rp);
		}
		this.con.getConnection().commit();

		resultToClient.put("msg", 1);
		resultToClient.put("result", p.getPupilNum());

		return resultToClient;
	}

	protected FullPupilCard getFullPupilCard(HttpServletRequest req,
			HttpServletResponse resp) {

		FullPupilCard p = new FullPupilCard();
		int id = Integer.parseInt(req.getParameter("id"));
		p = this.fullPupilDao.selectById(id);

		return p;
	}

	protected List<FullPupilCard> SelectPupilNotInActivity(
			HttpServletRequest req, HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		int activityNum = Integer.parseInt(req.getParameter("activityNum"));
		pupils = this.fullPupilDao
				.SelectPupilNotInActivity(req.getParameter("sidx"),
						req.getParameter("sord"), activityNum);

		return pupils;
	}

	public List<FullPupilCard> fillterPupilNotInActivity(
			HttpServletRequest req, HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		int activityNum = Integer.parseInt(req.getParameter("activityNum"));
		pupils = this.fullPupilDao.fillterPupilNotInActivity(
				req.getParameter("sidx"), req.getParameter("sord"),
				req.getParameter("firstName"), req.getParameter("lastName"),
				req.getParameter("gender"), req.getParameter("gradeName"),
				req.getParameter("isReg"), activityNum);

		return pupils;
	}

	protected List<FullPupilCard> getFullPupilList(HttpServletRequest req,
			HttpServletResponse resp, int offset, int rowsPerPage) {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectAll(req.getParameter("sidx"),
				req.getParameter("sord"), offset, rowsPerPage);

		return pupils;
	}

	protected List<FullPupilCard> searchPupilList(HttpServletRequest req,
			HttpServletResponse resp, int offset, int rowsPerPage)
			throws UnsupportedEncodingException {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectSearch(req.getParameter("sidx"),
				req.getParameter("sord"), req.getParameter("firstName"),
				req.getParameter("lastName"), req.getParameter("gender"),
				req.getParameter("gradeName"), req.getParameter("isReg"),
				offset, rowsPerPage);

		return pupils;
	}

	public List<FullPupilCard> searchContactList(HttpServletRequest req,
			HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectSearch(req.getParameter("sidx"),
				req.getParameter("sord"), req.getParameter("firstName"),
				req.getParameter("lastName"), req.getParameter("gender"),
				req.getParameter("gradeName"), req.getParameter("pupilCell"),
				req.getParameter("homePhone"), req.getParameter("p1Name"),
				req.getParameter("p1Cell"), req.getParameter("p2Name"),
				req.getParameter("p2Cell"), req.getParameter("isReg"));

		return pupils;
	}

	@SuppressWarnings("unchecked")
	protected void getPupilList(JSONArray jsonResult, List<FullPupilCard> list) {
		for (FullPupilCard pupil : list) {
			String gend;
			if (pupil.getGender() == 1)
				gend = "בן";
			else if (pupil.getGender() == 2)

				gend = "בת";
			else
				gend = " ";
			// / delete this part after fixing sp
			/*
			 * this.regToMoadonitDAO = new RegToMoadonitDAO(con);
			 * List<RegToMoadonit> active =
			 * regToMoadonitDAO.getActiveRegForPupil(pupil.getPupilNum());
			 */
			/*Boolean reg;
			if (pupil.getRegPupilNum() == 0) {
				reg = false;
			} else
				reg = true;*/

			JSONObject user = new JSONObject();
			user.put("id", pupil.getPupilNum());
			user.put("firstName", pupil.getFirstName());
			user.put("lastName", pupil.getLastName());
			user.put("gender", gend);
			user.put("gradeName", pupil.getGradeName());
			user.put("isReg", pupil.getStateNum() == 2 ? true : false);			

			jsonResult.add(user);
		}
	}

	@SuppressWarnings("unchecked")
	protected void getJsonForExport(JSONArray jsonResult, List<FullPupilCard> list) {
		for (FullPupilCard pupil : list) {
			String gend;
			if (pupil.getGender() == 1)
				gend = "בן";
			else if (pupil.getGender() == 2)

				gend = "בת";
			else
				gend = " ";
			// / delete this part after fixing sp
			/*
			 * this.regToMoadonitDAO = new RegToMoadonitDAO(con);
			 * List<RegToMoadonit> active =
			 * regToMoadonitDAO.getActiveRegForPupil(pupil.getPupilNum());
			 */
			/*Boolean reg;
			if (pupil.getRegPupilNum() == 0) {
				reg = false;
			} else
				reg = true;*/

			JSONObject user = new JSONObject();
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","pupilNum"), pupil.getPupilNum());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","firstName"), pupil.getFirstName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","lastName"), pupil.getLastName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","gender"), gend);
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","gradeName"), pupil.getGradeName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","state"), pupil.getState());
			
			
			/*user.put("firstName", pupil.getFirstName());
			user.put("lastName", pupil.getLastName());
			user.put("gender", gend);
			user.put("gradeName", pupil.getGradeName());
			user.put("isReg", pupil.getStateNum() == 2 ? true : false);			*/

			jsonResult.add(user);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void getContactList(JSONArray jsonResult) {
		for (FullPupilCard pupil : data) {
			String gend;
			if (pupil.getGender() == 1)
				gend = "בן";
			else if (pupil.getGender() == 2)
				gend = "בת";
			else
				gend = " ";
			/*Boolean reg;
			if (pupil.getRegPupilNum() == 0) {
				reg = false;
			} else
				reg = true;*/

			JSONObject user = new JSONObject();
			user.put("id", pupil.getPupilNum());
			user.put("isReg", pupil.getStateNum() == 2 ? true : false);
			user.put("firstName", pupil.getFirstName());
			user.put("lastName", pupil.getLastName());
			user.put("gender", gend);
			user.put("gradeName", pupil.getGradeName());
			user.put("pupilCell", pupil.getCellphone());
			user.put("homePhone", pupil.getHomePhoneNum());
			user.put("p1Name", pupil.getP1fname());
			user.put("p1Cell", pupil.getP1cell());
			user.put("p1mail", pupil.getP1mail());
			user.put("p2Name", pupil.getP2fname());
			user.put("p2Cell", pupil.getP2cell());
			user.put("p2mail", pupil.getP2mail());
			
			jsonResult.add(user);
		}
	}

	/*@SuppressWarnings("unchecked")
	protected void getPupilListForHTML(JSONArray jsonResult) {
		for (FullPupilCard pupil : data) {
			String gend;
			if (pupil.getGender() == 1)
				gend = "בן";
			else if (pupil.getGender() == 2)

				gend = "בת";
			else
				gend = " ";

			Boolean reg;
			if (pupil.getRegPupilNum() == 0) {
				reg = false;
			} else
				reg = true;

			JSONObject user = new JSONObject();
			user.put("מספר", pupil.getPupilNum());
			user.put("שם פרטי", pupil.getFirstName());
			user.put("שם משפחה", pupil.getLastName());
			user.put("מגדר", gend);
			user.put("כיתה", pupil.getGradeName());
			user.put("רשום", reg ? "כן" : "לא");

			jsonResult.add(user);
		}
	}*/

	@SuppressWarnings("unchecked")
	protected void getContactListForExport(JSONArray jsonResult,  List<FullPupilCard> list) {
		for (FullPupilCard pupil : list) {
			String gend;
			if (pupil.getGender() == 1)
				gend = "בן";
			else if (pupil.getGender() == 2)
				gend = "בת";
			else
				gend = " ";			

			JSONObject user = new JSONObject();
			user.put("id", pupil.getPupilNum());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","pupilNum"), pupil.getPupilNum());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","firstName"), pupil.getFirstName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","lastName"), pupil.getLastName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","gender"), gend);
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","gradeName"), pupil.getGradeName());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","state"), pupil.getState());
			
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","cellphone"), pupil.getCellphone());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","homePhoneNum"), pupil.getHomePhoneNum() != null ? pupil.getHomePhoneNum() : "");
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p1fname"), pupil.getP1fname());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p1cell"), pupil.getP1cell());
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p1mail"), pupil.getP1mail() != null ? pupil.getP1mail() : "");
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p2fname"), pupil.getP2fname() != null ? pupil.getP2fname() : "");
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p2cell"), pupil.getP2cell() != null ? pupil.getP2cell() : "");
			user.put(new AbstractMap.SimpleEntry<>("fullPupilCard","p2mail"), pupil.getP2mail() != null ? pupil.getP2mail() : "");
			
			jsonResult.add(user);
		}
	}
}