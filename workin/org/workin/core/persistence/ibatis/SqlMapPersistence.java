package org.workin.core.persistence.ibatis;

import java.util.List;
import java.util.Map;

import org.workin.core.persistence.ibatis.plugin.SqlMapCounter;
import org.workin.core.persistence.support.PaginationSupport;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public interface SqlMapPersistence extends SqlMapCounter {

	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a
	 * number of result objects.
	 * <p/>
	 * The parameter object is generally used to supply the input data for the
	 * WHERE clause parameter(s) of the SELECT statement.
	 * 
	 * Throws: java.sql.SQLException
	 * 
	 * @param sqlMapId
	 *            -The name of the statement to execute.
	 * @param parameterObject
	 *            -The parameter object (e.g. JavaBean, Map, XML etc.).
	 * @return A List of result objects.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 *            -If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List findListBySqlMap(String sqlMapId, Object parameterObject);
	
	
	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a
	 * number of result objects that will be keyed into a Map.
	 * <p/>
	 * The parameter object is generally used to supply the input data for the
	 * WHERE clause parameter(s) of the SELECT statement.
	 * 
	 * Throws: java.sql.SQLException
	 * 
	 * @param sqlMapId
	 *            The name of the statement to execute.
	 * @param parameterObject
	 *            The parameter object (e.g. JavaBean, Map, XML etc.).
	 * @param key
	 *            The property to be used as the key in the Map.
	 * @return A Map keyed by keyProp with values being the result object
	 *         instance.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 *             - If an error occurs.but usually throws DataAccessException's
	 *             subclass
	 * 
	 */
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key);
	
	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a
	 * number of result objects from which one property will be keyed into a
	 * Map.
	 * <p/>
	 * The parameter object is generally used to supply the input data for the
	 * WHERE clause parameter(s) of the SELECT statement.
	 * 
	 * Throws: java.sql.SQLException
	 * 
	 * @param sqlMapId
	 *             -The name of the statement to execute.
	 * @param parameterObject
	 *             -The parameter object (e.g. JavaBean, Map, XML etc.).
	 * @param key
	 *             -The property to be used as the key in the Map.
	 * @param value
	 *             -The property to be used as the value in the Map.
	 * @return A Map keyed by keyProp with values of valueProp.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 *             -If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key, String value);
	
	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a
	 * single object instance.
	 * <p/>
	 * The parameter object is generally used to supply the input data for the
	 * WHERE clause parameter(s) of the SELECT statement.
	 * 
	 * Throws: java.sql.SQLException
	 * 
	 * @param sqlMapId
	 *            -The name of the statement to execute.
	 * @param parameterObject
	 *            -The parameter object (e.g. JavaBean, Map, XML etc.).
	 * @return The single result object populated with the result set data, or null if no result was found
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 *            -If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public Object findObjectBySqlMap(String sqlMapId, Object parameterObject);
	
	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a
	 * number of result objects within a certain range.
	 * <p/>
	 * The parameter object is generally used to supply the input data for the
	 * WHERE clause parameter(s) of the SELECT statement.
	 * 
	 * @param sqlMapId
	 *            -The name of the statement to execute.
	 * @param parameterObject
	 *            -The parameter object (e.g. JavaBean, Map, XML etc.).
	 * @param offset
	 *            -The number of results to ignore.
	 * @param maxRows
	 *            -The maximum number of results to return.
	 * @return A List of result objects.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 *            -If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public PaginationSupport findPaginatedBySqlMap(String sqlMapId, Object parameterObject, int offset, int maxRows);
}

