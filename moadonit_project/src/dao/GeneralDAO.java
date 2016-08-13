package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GenderRef;
import util.DAOUtil;
import controller.MyConnection;

public class GeneralDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2627663536730229029L;

	private String select = " SELECT foodTypeID , foodType FROM tbl_food_type where foodTypeID = ?;";

	public GeneralDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}



}
