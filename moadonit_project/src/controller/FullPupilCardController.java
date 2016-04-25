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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.DAOException;
import dao.FamilyDAO;
import dao.FullPupilCardDAO;
import dao.GradeCodeDAO;
import dao.GradePupilDAO;
/*import dao.GradeDAO;*/
import dao.ParentDAO;
import dao.PupilDAO;
import dao.RegisterPupilDAO;
import util.DAOUtil;
import model.Family;
import model.FamilyRelation;
import model.FullPupilCard;
import model.GenderRef;
/*import model.Grade;*/
import model.GradeCode;
import model.Parent;
import model.Pupil;
import model.RegisterPupil;

@WebServlet("/FullPupilCardController")
public class FullPupilCardController extends HttpServlet implements
		Serializable {

	MyConnection con = null;
	private String action;
	JSONObject filters;
	FullPupilCard pupilCard = null;
	FullPupilCardDAO fullPupilDao;
	Pupil p = null;
	RegisterPupil rp = null;
	Family fam = null;
	Parent parent1 = null;
	Parent parent2 = null;
	List<FullPupilCard> pupilList = null;

	PupilDAO pupilDao;
	FamilyDAO familyDao;
	ParentDAO parentDao;
	RegisterPupilDAO regPupilDao;
	GradeCodeDAO gradeDAO;
	GradePupilDAO gradePupilDAO;
	JSONObject resultToClient = new JSONObject();;

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
		gradeDAO= new GradeCodeDAO(con);
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
			if (action.equals("getGrades")){
				
				JSONObject jsonObj = getGradesJson();
				
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

	@SuppressWarnings("unchecked")
	private JSONObject getGradesJson() {
		List<GradeCode> list = gradeDAO.selectIndex(0);
		String values= "";
		for (int i=0;i<list.size();i++) { //{ value: ":;1:בן;2:בת"}
			GradeCode grade=list.get(i);
			values += grade.getGradeID()+":"+ grade.getGradeName()+";";
		}
		values=values.substring(0, values.length()-1);
		JSONObject json = new JSONObject();
		json.put("value", values);
		return json;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkConnection(req,resp);
		
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

		action = req.getParameter("action");
		
		
		try {

			if (action.equals("insert")) {
				
				insertPupil(req, resp);
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);

			}
			
			
		else
			if (action.equals("pupilSearch")) {
				// on searching
				String search =req.getParameter("_search");
				if(search.equals("true")){
					pupilList = searchPupilList(req, resp);
					JSONArray jsonPupilList = new JSONArray();
					getList(jsonPupilList);
					
					if (!jsonPupilList.isEmpty()) {
	
							String jsonResponse = jsonPupilList.toJSONString();
							jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
			                        + pupilList.size() + ",\"rows\":" + jsonResponse + "}";
							
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
				
				else{ // on page load
					pupilList = getFullPupilList(req, resp);
					JSONArray jsonPupilList = new JSONArray();
					getList(jsonPupilList);
					if (!jsonPupilList.isEmpty()) {
	
							String jsonResponse = jsonPupilList.toJSONString();
							jsonResponse = "{\"page\":1,\"total\":\"1\",\"records\":"
			                        + pupilList.size() + ",\"rows\":" + jsonResponse + "}";
							
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
			}
			else if (action.equals("update")) {
				
				updatePupil(req, resp);
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(resultToClient);
				
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
		}
		else{
			// update parent 1
			this.parentDao.update(parent1);
						
		}
		
		
		if (parent2.getParentID() == 0) {
			if (!parent2.getFirstName().trim().equals("")
					&& !parent2.getLastName().trim().equals("")) {
				// insert parent 2
				this.parentDao.insert(parent2);

			}
		}
		else{
			// update parent 2
			this.parentDao.update(parent2);
						
		}
		
		if (parent1.getParentID() > 0 && fam != null) {
			
			if (fam.getFamilyID() > 0) {
				// update family
				fam.setTblParent1(parent1);
				this.familyDao.update(fam);	
			}
			else{
				// insert family
				fam.setTblParent1(parent1);
				this.familyDao.insert(fam);
			}
			
			
			// save pupil
			p.setTblFamily(fam);			

		}
		
		if (p.getPupilNum() > 0) {
			// update pupil		
			this.pupilDao.update(p);
			
			// update gradePupil
			if(!p.getTblGradePupils().isEmpty())
			this.gradePupilDAO.update(p.getTblGradePupils().get(0));
			
			if (rp.getPupilNum() > 0) {
				//check if update regPupil		
				RegisterPupil temp = this.regPupilDao.selectById(rp.getPupilNum());
				if(temp.getPupilNum() > 0){
					if(rp.getEthiopian() != 0 || !rp.getFoodSensitivity().trim().equals("") || !rp.getHealthProblems().trim().equals("")
							|| !rp.getOtherComments().trim().equals("") || (rp.getStaffChild() != null && !rp.getStaffChild().trim().equals("")) || rp.getTblFoodType().getFoodTypeID() != 0 ){
						rp.setPupilNum(p.getPupilNum());
						this.regPupilDao.update(rp);
					}
					
				}
			}
			else{
				
				// insert regPupil				
				rp.setPupilNum(p.getPupilNum());
				if(rp.getEthiopian() != 0 || !rp.getFoodSensitivity().trim().equals("") || !rp.getHealthProblems().trim().equals("")
						|| !rp.getOtherComments().trim().equals("") || (rp.getStaffChild() != null && !rp.getStaffChild().trim().equals("")) || rp.getTblFoodType().getFoodTypeID() != 0 ){
					rp.setPupilNum(p.getPupilNum());
					this.regPupilDao.insert(rp);
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

		this.pupilDao.insert(p);
		
		
		//save pupil is to the pupilGrade id entity and save pupilGrade to DB
		p.getTblGradePupils().get(0).getId().setPupilNum(p.getPupilNum());
		//insert row to pupilgrade for this pupil
		this.gradePupilDAO.insert(p.getTblGradePupils().get(0));

		// save regPupil

		if(rp.getEthiopian() != 0 || !rp.getFoodSensitivity().trim().equals("") || !rp.getHealthProblems().trim().equals("")
				|| !rp.getOtherComments().trim().equals("") || (rp.getStaffChild() != null && !rp.getStaffChild().trim().equals("")) || rp.getTblFoodType().getFoodTypeID() != 0 ){
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

	protected List<FullPupilCard> getFullPupilList(HttpServletRequest req,
			HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectAll(req.getParameter("sidx"),
				req.getParameter("sord"));

		return pupils;
	}
	
	private List<FullPupilCard> searchPupilList(HttpServletRequest req, HttpServletResponse resp) {
		List<FullPupilCard> pupils = new ArrayList<>();
		pupils = this.fullPupilDao.selectSearch(req.getParameter("sidx"), req.getParameter("sord"),req.getParameter("firstName"),req.getParameter("lastName"),req.getParameter("gender"),req.getParameter("gradeName"),req.getParameter("isReg"));

		return pupils;
	}


@SuppressWarnings("unchecked")
protected void getList(JSONArray jsonResult){
	for (FullPupilCard pupil : pupilList) {
		int id = pupil.getPupilNum();
		String fName = pupil.getFirstName();
		String lName = pupil.getLastName();
		String gend;
		if(pupil.getGender()==1)
			gend="בן";
		else
			if(pupil.getGender()==2)
				gend="בת";
			else
				gend=" ";
		String grade = pupil.getGradeName();
		Boolean reg;
		if( pupil.getRegPupilNum() == 0){
			reg = false;
		}else reg = true;
		
		JSONObject user = new JSONObject();
		user.put("id",id);
		user.put("firstName",fName);
		user.put("lastName",lName);
		user.put("gender",gend);
		user.put("gradeName",grade);
		user.put("isReg",reg);
		
		jsonResult.add(user);
		}
	}
}