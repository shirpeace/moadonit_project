package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import model.OneTimeReg;
import model.OneTimeRegPK;
import model.RegType;
import util.DAOUtil;
import controller.MyConnection;

public class OneTimeRegDao extends AbstractDAO {

	/**
	 * select all oneTimeRegs of a pupil by a giving id 
	 */
	private String select = " select * from ms2016.tbl_one_time_reg where pupilNum = ? ";

	public OneTimeRegDao(MyConnection con) {
		super(con);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5155823344858834940L;



	public List<OneTimeReg> select(int pupilId) throws IllegalArgumentException,
			DAOException {

		List<OneTimeReg> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] {  pupilId});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				OneTimeReg o = map(resultSet);
				list.add(o);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private OneTimeReg map(ResultSet resultSet) throws SQLException {

		OneTimeReg r = new OneTimeReg();
		OneTimeRegPK pk = new OneTimeRegPK();
		pk.setPupilNum(resultSet.getInt("pupilNum"));
		pk.setSpecificDate(resultSet.getDate("specificDate"));
		
		r.setId(pk);
		r.setRegType(resultSet.getString("regType"));
//		r.setTblPupil((resultSet.getString("gradeName")));
		return r;
	}

}
