package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.MyConnection;
import model.OneTimeReg;
import model.Pupil;
import model.RegSource;
import model.RegToMoadonit;
import model.RegToMoadonitPK;
import model.RegType;
import model.User;
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
	private String insert = "INSERT INTO tbl_reg_to_moadonit "
			+ "(pupilNum,registerDate,startDate,sunday_,monday_,tuesday_,wednesday_,thursday_,writenBy,source , activeYear )"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?, ms2016.get_currentYearID() );";
	private String checkRegToMoPK = "SELECT pupilNum, startDate FROM tbl_reg_to_moadonit where pupilNum = ? and startDate = ? and activeYear =  ms2016.get_currentYearID()";
//	private String selectAll = "SELECT * FROM ms2016.tbl_reg_to_moadonit WHERE pupilNum = ? and  startdate <= CURDATE() and activeYear =  ms2016.get_currentYearID() order by startdate desc";
	private String getActiveRegInPeriod = "{call ms2016.getActiveRegInPeriodTry (?)}";
	private String getAllRegsForPupil = "{ call ms2016.get_Regs_For_Pupils( ? , ? ) }";
	private String getActiveRegForPupil = "{ call ms2016.getActiveRegByPupilId( ? ) }";
	private String update = "{ call ms2016.updateRegToMoadonitForPupil(?,? ,? , ?,? , ?, ?, ?,?) }";
	private String delete = "DELETE FROM tbl_reg_to_moadonit where pupilNum = ? and startDate = ? and activeYear =  ms2016.get_currentYearID()";

	public boolean update(RegToMoadonit regToUpdate, Date oldDate) throws DAOException {
        if (regToUpdate.getId() == null) {
            throw new IllegalArgumentException("Row is not created yet, the Registration ID is null.");
        }

        Object[] values = { 
        		
        		regToUpdate.getTblRegType1().getTypeNum(),
        		regToUpdate.getTblRegType2().getTypeNum(),
        		regToUpdate.getTblRegType3().getTypeNum(),
        		regToUpdate.getTblRegType4().getTypeNum(),
        		regToUpdate.getTblRegType5().getTypeNum(),
        		regToUpdate.getId().getPupilNum(),
        		regToUpdate.getTblUser().getUserID(),
        		regToUpdate.getId().getStartDate(),
        		oldDate
				

		};

        try (
        		PreparedStatement statement = DAOUtil.prepareCallbackStatement(this.con.getConnection(), update,values);
        		
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 1) {
                throw new DAOException("Updating Row was not currect , " + affectedRows + " rows affected.");
            }
            
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	public List<RegToMoadonit> selectAll(int id, int future)
			throws IllegalArgumentException, DAOException {
		List<RegToMoadonit> list = new ArrayList<>();


		// (where %s,fName=null?"firstName=fname":" ");
		try (PreparedStatement statement = DAOUtil
				.prepareCallbackStatement(this.con.getConnection(), getAllRegsForPupil,
						new Object[] { id , future});
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

	public boolean checkPk(RegToMoadonit regToMo) {

		if (regToMo.getId() == null) {
			throw new IllegalArgumentException(
					"Cant check RegToMoadonit , the PK ID is not null.");
		}

		Object[] pk = { 
				regToMo.getId().getPupilNum(),
				DAOUtil.toSqlDate(regToMo.getId().getStartDate()) };
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), checkRegToMoPK, false, pk);
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return false;
	}
	

	public boolean insert(RegToMoadonit regToMo)
			throws IllegalArgumentException, DAOException {
		boolean result = false;
		if (regToMo.getId() == null) {
			throw new IllegalArgumentException(
					"Cant create RegToMoadonit , the PK ID is not null.");
		}

		// (pupilNum,registerDate,startDate,sunday_,monday_,tuesday_,wednesday_,thursday_,writenBy,source)
		Object[] values = { regToMo.getId().getPupilNum(),
				DAOUtil.toSqlDate(regToMo.getId().getStartDate()),
				regToMo.getId().getStartDate(), regToMo.getTblRegType1().getTypeNum(),
				regToMo.getTblRegType2().getTypeNum(), regToMo.getTblRegType3().getTypeNum(),
				regToMo.getTblRegType4().getTypeNum(), regToMo.getTblRegType5().getTypeNum(),
				regToMo.getTblUser().getUserID(), regToMo.getTblRegSource().getSourceNum() };

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

	public List<RegToMoadonit> getActiveRegs(Date id)
			throws IllegalArgumentException, DAOException {
		List<RegToMoadonit> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getActiveRegInPeriod,
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
	
	public List<RegToMoadonit> getActiveRegForPupil(int id)
			throws IllegalArgumentException, DAOException {
		List<RegToMoadonit> list = new ArrayList<>();

		try (PreparedStatement statement = DAOUtil.prepareCallbackStatement(
				this.con.getConnection(), getActiveRegForPupil,
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

	private RegToMoadonit map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
				RegToMoadonit rtm = new RegToMoadonit();

				RegToMoadonitPK pk = new RegToMoadonitPK();
				pk.setPupilNum(resultSet.getInt("pupilNum"));
				pk.setStartDate(resultSet.getDate("startDate"));
				rtm.setId(pk);
				
				RegType rt = new RegType();
				rt.setTypeNum(resultSet.getInt("sunday_"));		
				rtm.setTblRegType1(rt);		
				
				rt = new RegType();
				rt.setTypeNum(resultSet.getInt("monday_"));		
				rtm.setTblRegType2(rt);
				
				rt = new RegType();
				rt.setTypeNum(resultSet.getInt("tuesday_"));		
				rtm.setTblRegType3(rt);
				
				rt = new RegType();
				rt.setTypeNum(resultSet.getInt("wednesday_"));		
				rtm.setTblRegType4(rt);
				
				rt = new RegType();
				rt.setTypeNum(resultSet.getInt("thursday_"));		
				rtm.setTblRegType5(rt);
						
				rtm.setRegisterDate(resultSet.getDate("registerDate"));
				
				User u = new User();
				u.setUserID(resultSet.getInt("writenBy"));
				rtm.setTblUser(u);
				
				RegSource rs = new RegSource();
				rs.setSourceNum(resultSet.getInt("source"));
				rtm.setTblRegSource(rs);
				
				

				return rtm;
	}

	public boolean delete(RegToMoadonit regToUpdate) throws IllegalArgumentException, DAOException {
	
		 if (regToUpdate.getId() == null) {
	            throw new IllegalArgumentException("cant delete row, the Registration ID is null.");
	        }

	        Object[] values = { 
	        		//pk of row
	        		regToUpdate.getId().getPupilNum(),
	        		regToUpdate.getId().getStartDate()
					

			};

		        try (
		        		PreparedStatement statement = DAOUtil.prepareStatement(
		        				this.con.getConnection(), delete , false, values);        		        
		        ) {
		            int affectedRows = statement.executeUpdate();
		            if (affectedRows == 0) {
		                throw new DAOException("Deleting registration failed, no rows affected.");		                
		            } else {
		            	regToUpdate.setId(null);
		            	return true;
		            }
		        } catch (SQLException e) {
		            throw new DAOException(e);
		        }		        
	}
}
