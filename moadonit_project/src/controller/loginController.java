/**
 * 
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;

import dao.LoginDAO;

/**
 * @author Matan
 * 
 */
@WebServlet("/loginController")
public class loginController extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1094801825228386363L;

	private String user;
	private String pass;
	private String msg;
	private String action;
	private boolean loggedIn = false;

	/**
	 * 
	 */
	public loginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			JSONArray users = new JSONArray();

			MyConnection con = null;
			HttpSession session = request.getSession();
			if (session.getAttribute("connection") != null) {
				con = (MyConnection) session.getAttribute("connection");
			} else {
				con = new MyConnection();
				session.setAttribute("connection", con);
			}

			user = request.getParameter("user");
			pass = request.getParameter("pass");
			action = request.getParameter("action");
			;

			if (action.equals("login")) {
				PreparedStatement ps = null;
				ResultSet rs;
				String query = "SELECT *\n" + "FROM tbl_user \n"
						+ "WHERE username = ? AND password = ?";
				ps = con.getConnection().prepareStatement(query);
				ps.setString(1, user);
				ps.setString(2, pass);
				rs = ps.executeQuery();

				if (rs.next()) {
					JSONObject u = new JSONObject();
					
					u.put("username",rs.getString("userName"));
					u.put("password",rs.getString("password"));
					

					users.add(u);
					
					this.loggedIn = true;
					this.msg = "OK";
					session.setAttribute("userid", user);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(users);
					
				} else {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(users);
				}
			} else if (action.equals("logout")) {

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// validate login
	public boolean validateUsernamePassword(String user, String password) {
		if (user == null || user.trim().equals("") || password == null
				|| password.equals("")) {
			this.msg = "יש למלא את כל הפרטים";

			return false;
		}

		boolean valid = LoginDAO.validate(user, password);
		if (valid) {
			return true;
		} else {
			this.msg = "שם משתמש או סיסמא אינם נכונים , הזן פרטים שוב";
			/*
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage(FacesMessage.SEVERITY_WARN,
			 * "שם משתמש או סיסמא אינם נכונים , הזן פרטים שוב"));
			 */
			return false;
		}
	}

	public void loginMeOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Trying to LogOut now.....");
		this.loggedIn = false;
		request.getSession().setAttribute("connection", null);
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}

	// logout event, invalidate session
	public String logout() {
		/*
		 * HttpSession session = SessionBean.getSession(); session.invalidate();
		 * return "login";
		 */
		return null;
	}
}
