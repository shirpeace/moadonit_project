package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DAOUtil;
import model.Parent;
import controller.MyConnection;

public class ParentDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String insert = "INSERT INTO tbl_parent"
			+ "( firstName, lastName, cellphone, parentEmail, relationToPupil) VALUES (?,?,?,?,?)";

	private String select = "SELECT * FROM tbl_parent  where parentid = ?";
	private String update = "UPDATE tbl_parent SET firstName=?,lastName=?,cellphone=?,parentEmail=?,relationToPupil=? WHERE parentID = ?";

	private String delete = "DELETE FROM tbl_parent WHERE parentID = ?;";

	private String SQL_LIST_ORDER_BY_ID = "SELECT * FROM tbl_parent ORDER BY parentid";
 ParentDAO(MyConnection con) {

		/*
		 * INSERT INTO tbl_parent(parentID, firstName, lastName, cellphone,
		 * parentEmail, relationToPupil) VALUES (?,?,?,?,?,?)
		 */
		super(con);
	}

	public void delete(Parent p) throws DAOException {
        Object[] values = { 
            p.getParentID()
        };

        try (
        		PreparedStatement statement = DAOUtil.prepareStatement(
        				this.con.getConnection(), delete, false, values);        		        
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                p.setParentID(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	
	public void update(Parent p) throws DAOException {
        if (p.getParentID() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

       
        Object[] values = {  p.getFirstName(),
				p.getLastName(),p.getCellphone(), p.getParentEmail(),
				p.getTblFamilyRelation().getIdFamilyRelation(), p.getParentID()

		};

        try (
        		PreparedStatement statement = DAOUtil.prepareStatement(
        				this.con.getConnection(), update, false, values);
        		
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
	
	
	public Parent selectById(int id) throws IllegalArgumentException,
			DAOException {
		Parent p = new Parent();

		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), select, false, new Object[] { id });
				ResultSet resultSet = statement.executeQuery();) 
			{

			if (resultSet.next()) {
				p = map(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return p;
	}

	private Parent map(ResultSet resultSet) throws SQLException {
		Parent p = new Parent();
		p.setParentID(resultSet.getInt("parentID"));
		p.setFirstName(resultSet.getString("firstName"));
		p.setLastName(resultSet.getString("lastName"));
		p.setCellphone(resultSet.getString("cellphone"));
		p.setParentEmail(resultSet.getString("parentEmail"));
		
		return p;
	}

	public void insert(Parent p) throws IllegalArgumentException, DAOException {
		if (p.getParentID() != 0) {
			throw new IllegalArgumentException(
					"parent is already created, the parent ID is not null.");
		}

		//insert in the order of query firstName, lastName, cellphone, parentEmail, relationToPupil
		Object[] values = { p.getFirstName(),
				p.getLastName(),p.getCellphone(),  p.getParentEmail(), 
				//p.getParentID(),
				p.getTblFamilyRelation().getIdFamilyRelation(),

		};

		try (

		PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), insert, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException(
						"Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					p.setParentID(generatedKeys.getInt(1));
				} else {
					throw new DAOException(
							"Creating user failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Parent> list() throws DAOException {
        List<Parent> parents = new ArrayList<>();

        try (
            
            PreparedStatement statement = DAOUtil.prepareStatement(this.con.getConnection(),SQL_LIST_ORDER_BY_ID,false, new Object[]{});
    				
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                parents.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return parents;
    }
	
}
