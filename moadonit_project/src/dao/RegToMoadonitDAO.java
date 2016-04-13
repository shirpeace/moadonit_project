package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.MyConnection;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import util.DAOUtil;

public class RegToMoadonitDAO extends AbstractDAO {

	public RegToMoadonitDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectAll = "SELECT * FROM ms2016.tbl_reg_to_moadonit WHERE pupilNum = ? and  startdate <= CURDATE() order by startdate desc";

	public List<RegToMoadonit> selectAll(int id)
			throws IllegalArgumentException, DAOException {
		List<RegToMoadonit> list = new ArrayList<>();

		// (where %s,fName=null?"firstName=fname":" ");
		try (PreparedStatement statement = DAOUtil
				.prepareStatement(this.con.getConnection(), selectAll, false,
						new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				RegToMoadonit p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private RegToMoadonit map(ResultSet resultSet)  throws SQLException {
		// TODO Auto-generated method stub
		RegToMoadonit rtm = new  RegToMoadonit();
		
		rtm.setSunday_(resultSet.getInt("sunday_"));
		rtm.setMonday_(resultSet.getInt("monday_"));
		rtm.setTuesday_(resultSet.getInt("tuesday_"));
		rtm.setWednesday_(resultSet.getInt("wednesday_"));
		rtm.setThursday_(resultSet.getInt("thursday_"));
		rtm.setRegisterDate(resultSet.getDate("registerDate"));
		rtm.setSource(resultSet.getInt("source"));
		rtm.setWritenBy(resultSet.getInt("writenBy"));
		RegToMoadonitPK pk=  new RegToMoadonitPK();
		pk.setPupilNum(resultSet.getInt("pupilNum"));
		pk.setStartDate(resultSet.getDate("startDate"));
		rtm.setId(pk);
		
		return rtm;
	}
}
