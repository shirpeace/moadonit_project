package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

	private String insert = "INSERT INTO tbl_reg_to_moadonit "
			+ "(pupilNum,registerDate,startDate,sunday_,monday_,tuesday_,wednesday_,thursday_,writenBy,source)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?);";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectAll = "SELECT * FROM ms2016.tbl_reg_to_moadonit WHERE pupilNum = ? and  startdate <= CURDATE() order by startdate desc";
	private String getActiveRegInPeriod = "{call ms2016.getActiveRegInPeriod (?)}";
			
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
	
	public boolean insert(RegToMoadonit regToMo) throws IllegalArgumentException, DAOException {
		boolean result = false;
		if (regToMo.getId() ==  null) {
			throw new IllegalArgumentException(
					"RegToMoadonit is already created, the parent ID is not null.");
		}

		//(pupilNum,registerDate,startDate,sunday_,monday_,tuesday_,wednesday_,thursday_,writenBy,source)
		Object[] values = { regToMo.getId().getPupilNum(),
				DAOUtil.toSqlDate(new Date()) ,
				regToMo.getId().getStartDate(),
				regToMo.getSunday_(),regToMo.getMonday_(),regToMo.getTuesday_(),regToMo.getWednesday_(),regToMo.getThursday_(),
				regToMo.getWritenBy(),
				regToMo.getSource()
		};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			result = true;
			
			if (affectedRows == 0) {
				result = false;
				throw new DAOException(
						"Creating RegToMoadonit failed, no rows affected.");
			}

		} catch (SQLException e) {
			result = false;
			throw new DAOException(e);
		}
		
		return result;
	}

	public List<RegToMoadonit> getActiveRegs(Date id) throws IllegalArgumentException, DAOException {
		List<RegToMoadonit> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(), getActiveRegInPeriod, new Object[] { id });
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
