package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Family;
import model.GenderRef;
import model.Grade;
import model.Pupil;
import util.DAOUtil;
import controller.MyConnection;

public class PupilDAO  extends AbstractDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3381926667682712141L;
	private String insert = "INSERT INTO tbl_pupil"
			+ "( firstName, lastName, cellphone, photoPath, birthDate, familyID, gradeID, gender) VALUES (?,?,?,?,?,?,?,?,?)";
	private String delete = "DELETE FROM tbl_pupil WHERE pupilNum  = ?";
	
	private String select = "SELECT pupilNum, firstName, lastName, cellphone, photoPath, birthDate, familyID, gradeID, gender FROM tbl_pupil WHERE pupilNum = ?";
	
	private String update = "UPDATE tbl_pupil "
	+ "SET firstName=?,lastName=?,cellphone=?,photoPath=?,birthDate=?,familyID=?,gradeID=?,gender=? where pupilNum = ?";
	
	public PupilDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	
	public void delete(Pupil p) throws DAOException {
        Object[] values = { 
            p.getPupilNum()
        };

        try (
        		PreparedStatement statement = DAOUtil.prepareStatement(
        				this.con.getConnection(), delete, false, values);        		        
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting pupil failed, no rows affected.");
            } else {
                p.setPupilNum(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	
	public void update(Pupil p) throws DAOException {
        if (p.getPupilNum() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = { 
        		
        		p.getFirstName(),
				p.getLastName(),p.getCellphone(), p.getPhotoPath(),
				p.getBirthDate(), p.getTblFamily().getFamilyID(),
				p.getTblGrade().getGradeID(),p.getTblGenderRef().getGender(),p.getPupilNum()

		};

        try (
        		PreparedStatement statement = DAOUtil.prepareStatement(
        				this.con.getConnection(), update, false, values);
        		
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating pupil failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	
	public Pupil selectById(int id) throws IllegalArgumentException,
			DAOException {
		Pupil p = new Pupil();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) 
			{

			if (resultSet.next()) {
				p = map(resultSet);
				
				if(p.getTblFamily() != null)
				p.setTblFamily(new FamilyDAO(con).selectById(p.getTblFamily().getFamilyID()));
				
				if(p.getTblGrade() != null)
				p.setTblGrade(new GradeDAO(con).selectById(p.getTblGrade().getGradeID()));
				
				if(p.getTblGenderRef() != null)
				p.setTblGenderRef(new GenderDAO(con).selectById(p.getTblGenderRef().getGender()));
			
			}
			
			
			

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return p;
	}

	private Pupil map(ResultSet resultSet) throws SQLException {
		Pupil p = new Pupil();
			
		p.setBirthDate(resultSet.getDate("birthDate"));
		
		//set the  id for later fetch of grade data
		Grade gr = new Grade();
		gr.setGradeID(resultSet.getInt("gradeID"));
		p.setTblGrade(gr);
		
		p.setPhotoPath(resultSet.getString("photoPath"));

		//set the  id for later fetch of family data
		Family f = new Family();
		f.setFamilyID(resultSet.getInt("familyID"));
		p.setTblFamily(f);
		
		//set the  id for later fetch of gender data
		GenderRef g = new GenderRef();
		g.setGender(resultSet.getInt("gender"));
		p.setTblGenderRef(g);
		
		p.setPupilNum(resultSet.getInt("pupilNum"));
		
		
		p.setCellphone(resultSet.getString("cellphone"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		
		return p;
	}

	public void insert(Pupil p) throws IllegalArgumentException, DAOException {
		if (p.getPupilNum() != 0) {
			throw new IllegalArgumentException(
					"parent is already created, the parent ID is not null.");
		}

		// firstName, lastName, cellphone, photoPath, birthDate, familyID, gradeID, gender
		Object[] values = {
				
				p.getFirstName(),
				p.getLastName(),
				p.getCellphone(), 
				p.getPhotoPath(),
				DAOUtil.toSqlDate(p.getBirthDate()), 
				p.getTblFamily().getFamilyID(),
				p.getTblGrade().getGradeID(),
				p.getTblGenderRef().getGender()
				,p.getPupilNum()

		};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating pupil failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					p.setPupilNum(generatedKeys.getInt(1));
				} else {
					throw new DAOException(
							"Creating pupil failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
