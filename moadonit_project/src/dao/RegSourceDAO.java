package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import model.RegSource;
import util.DAOUtil;
import controller.MyConnection;

public class RegSourceDAO extends AbstractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectIndex = "SELECT * FROM tbl_reg_source;";

	public RegSourceDAO(MyConnection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public List<RegSource> selectIndex() throws IllegalArgumentException,
			DAOException {

		List<RegSource> list = new ArrayList<>();
		try (PreparedStatement statement = DAOUtil.prepareStatement(
				this.con.getConnection(), selectIndex, false, new Object[] {});
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				RegSource p = map(resultSet);
				list.add(p);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return list;
	}

	private RegSource map(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		RegSource regSource = new RegSource();
		regSource.setSourceNum(resultSet.getInt("sourceNum"));
		regSource.setSourceName(resultSet.getString("sourceName"));
		return regSource;
	}
}
