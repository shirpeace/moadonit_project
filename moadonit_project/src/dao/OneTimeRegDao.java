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
	private String select = " select * from ms2016.tbl_one_time_reg where pupilNum = ? AND schoolYear = ms2016.get_currentYearID() ORDER BY specificDate desc";
	private String checkOneTimeRegPK = "SELECT pupilNum, specificDate FROM tbl_one_time_reg where pupilNum = ? and specificDate = ? and schoolYear =  ms2016.get_currentYearID()";
	private String insertOTR = "INSERT INTO tbl_one_time_reg "
			+ "(pupilNum,specificDate,regType, schoolYear )"
			+ "VALUES(?,?,?, ms2016.get_currentYearID() );";
	private String delete = "DELETE from ms2016.tbl_one_time_reg where pupilNum = ? AND specificDate = ? AND schoolYear = ms2016.get_currentYearID()";
	
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
		RegType rt = new RegType();
		rt.setTypeNum(resultSet.getInt("regType"));
		r.setTblRegType(rt);

		return r;
	}

	public boolean insertOTR(OneTimeReg reg)
			throws IllegalArgumentException, DAOException {
		boolean result = false;
		if (reg.getId() == null) {
			throw new IllegalArgumentException(
					"Cant create OneTimeReg , the PK ID is not null.");
		}

		// (pupilNum,registerDate,startDate,sunday_,monday_,tuesday_,wednesday_,thursday_,writenBy,source)
		Object[] values = { reg.getId().getPupilNum(),
				DAOUtil.toSqlDate(reg.getId().getSpecificDate()),
				reg.getTblRegType().getTypeNum()
				};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insertOTR, true, values);) {
			int affectedRows = statement.executeUpdate();
			result = true;

			if (affectedRows == 0) {
				result = false;
				throw new DAOException(
						"Creating OneTimeReg failed, no rows affected.");
			}

		} catch (SQLException e) {
			result = false;
			throw new DAOException(e);
		}

		return result;
	}
	
	public boolean checkPk(OneTimeReg reg) {

		if (reg.getId() == null) {
			throw new IllegalArgumentException(
					"Cant check RegToMoadonit , the PK is null.");
		}

		Object[] pk = { 
				reg.getId().getPupilNum(),
				DAOUtil.toSqlDate(reg.getId().getSpecificDate()) };
		
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), checkOneTimeRegPK, false, pk);
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return false;
	}

	public boolean delete(OneTimeReg regToDel) {

		 if (regToDel.getId() == null) {
	            throw new IllegalArgumentException("cant delete row, the Registration ID is null.");
	        }

	        Object[] values = { 
	        		//pk of row
	        		regToDel.getId().getPupilNum(),
	        		regToDel.getId().getSpecificDate()
					

			};

		        try (
		        		PreparedStatement statement = DAOUtil.prepareStatement(
		        				this.con.getConnection(), delete , false, values);        		        
		        ) {
		            int affectedRows = statement.executeUpdate();
		            if (affectedRows == 0) {
		                throw new DAOException("Deleting registration failed, no rows affected.");		                
		            } else {
		            	regToDel.setId(null);
		            	return true;
		            }
		        } catch (SQLException e) {
		            throw new DAOException(e);
		        }		   
	}
}
