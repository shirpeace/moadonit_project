package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.GradePupil;
import model.Pupil;
import util.DAOUtil;
import controller.MyConnection;

public class GradePupilDAO extends AbstractDAO{

	public GradePupilDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String insert = "INSERT INTO ms2016.tbl_grade_pupil (pupilNum,gradeID,yearID) VALUES (?, ? , ms2016.get_currentYearID() )";
	private String update = "UPDATE ms2016.tbl_grade_pupil SET gradeID = ? WHERE pupilNum = ? AND yearID = ms2016.get_currentYearID()";
	
	public void update(GradePupil gp) throws DAOException {
		if (gp.getId().getPupilNum() == 0 || gp.getId().getGradeID() == 0) {
			throw new IllegalArgumentException(
					"cant update data , some ids are missing");
		}

        Object[] values = { 
        		gp.getId().getGradeID(),
        		gp.getId().getPupilNum()

		};

        try (
        		PreparedStatement statement = DAOUtil.prepareStatement(
        				this.con.getConnection(), update, false, values);
        		
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating data failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	public void insert(GradePupil gp) throws IllegalArgumentException, DAOException {
		if (gp.getId().getPupilNum() == 0 || gp.getId().getGradeID() == 0) {
			throw new IllegalArgumentException(
					"cant insert data , some ids are missing");
		}

		
		Object[] values = {
				
				gp.getId().getPupilNum(), gp.getId().getGradeID()

		};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating gradePupil failed, no rows affected.");
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
