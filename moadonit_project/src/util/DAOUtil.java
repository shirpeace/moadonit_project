package util;


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is been refactored in
 * single static methods. As far it contains a PreparedStatement values setter and a
 * <code>java.util.Date</code> to <code>java.sql.Date</code> converter.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DAOUtil {

    // Constructors -------------------------------------------------------------------------------

    private DAOUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     * @param connection The Connection to create the PreparedStatement from.
     * @param sql The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during creating the PreparedStatement.
     */
    public static PreparedStatement prepareStatement
        (Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
            throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql,
            returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        
       
        setValues(statement, values);
        
        
        return statement;
    }

    public static CallableStatement prepareCallbackStatement
    (Connection connection, String sql, Object... values)
        throws SQLException
{
    	CallableStatement statement = connection.prepareCall(sql);
    
   
    setValues(statement, values);
    
    
    return statement;
}
    
    public static  String getJsonFromObject(Object o){
    	String jsonInString = "";
    	
    	ObjectMapper mapper = new ObjectMapper();
    	//Object to JSON in String
    	 try {
			jsonInString = mapper.writeValueAsString(o);
    	 } catch (JsonGenerationException e) {
 			e.printStackTrace();
 		} catch (JsonMappingException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    	 
    	return jsonInString;
    }
    
    public static Object getObjectFromJson(String o, Class<?> classType ){
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
    	Object obj = null;
		try {
			obj = mapper.readValue(o, classType);
			
		} catch (JsonGenerationException e) {
 			e.printStackTrace();
 		} catch (JsonMappingException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    	
    	return obj;
    }
    /**
     * Set the given parameter values in the given PreparedStatement.
     * @param connection The PreparedStatement to set the given parameter values in.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    public static void setValues(PreparedStatement statement, Object... values)
        throws SQLException
    {
        for (int i = 0; i < values.length; i++) {
        	if (values[i] instanceof String) {
        		statement.setString(i + 1, (String)values[i]);	
			}
        	else
            statement.setObject(i + 1, values[i]);
        }
    }
    //

    /**
     * Converts the given java.util.Date to java.sql.Date.
     * @param date The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static Date toSqlDate(java.util.Date date) {
     return (date != null) ? new Date(date.getTime()) : null;
    }
    
    public static java.util.Date getZeroTimeDate(java.util.Date fecha) {
    	java.util.Date res = fecha;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( fecha );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        res = calendar.getTime();

        return res;
    }

}