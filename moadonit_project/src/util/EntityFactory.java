package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class EntityFactory {

    public EntityFactory(Connection connection, String queryString) {
        this.queryString = queryString;
        this.connection = connection;
    }

    public LinkedHashMap<String, Object> findSingle(Object[] params) throws SQLException {
        List<LinkedHashMap<String, Object>> objects = this.findMultiple(params);

        if (objects.size() != 1) {
            throw new SQLException("Query did not produce one object it produced: " + objects.size() + " objects.");
        }

        LinkedHashMap<String, Object> object = objects.get(0);  //extract only the first item;

        return object;
    }

    public List<LinkedHashMap<String, Object>> findMultiple(Object[] params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(this.queryString);
            for (int i = 0; i < params.length; ++i) {
                ps.setObject(1, params[i]);
            }

            rs = ps.executeQuery();
            return getEntitiesFromResultSet(rs);
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    protected List<LinkedHashMap<String, Object>> getEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<LinkedHashMap<String, Object>> entities = new ArrayList<>();
        while (resultSet.next()) {
            entities.add(getEntityFromResultSet(resultSet));
        }
        return entities;
    }

    protected LinkedHashMap<String, Object> getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        LinkedHashMap<String, Object> resultsMap = new LinkedHashMap<String, Object>();
        for (int i = 1; i <= columnCount; ++i) {
            String columnName = metaData.getColumnName(i).toLowerCase();
            Object object = resultSet.getObject(i);
            resultsMap.put(columnName, object);
        }
        return resultsMap;
    }
    
    private String queryString;
    protected Connection connection;
}