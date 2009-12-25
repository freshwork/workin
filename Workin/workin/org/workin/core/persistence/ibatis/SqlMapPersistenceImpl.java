package org.workin.core.persistence.ibatis;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.workin.core.persistence.support.PaginationSupport;
import org.workin.exception.WorkinDataAccessException;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 * 
 */

@Repository
@SuppressWarnings({"unchecked"})
public class SqlMapPersistenceImpl extends SqlMapClientDaoSupport implements SqlMapPersistence {
	
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
	public List findListBySqlMap(String sqlMapId, Object parameterObject) {
		Assert.hasText(sqlMapId, "sqlMapId cannot be null..., in SqlMapPersistenceImpl.queryForList()");
		List dataList = getSqlMapClientTemplate().queryForList(sqlMapId, parameterObject);
		return dataList;
	}

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
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key) {
		Assert.hasText(sqlMapId, "sqlMapId cannot be null..., in SqlMapPersistenceImpl.queryForMap()");
		return getSqlMapClientTemplate().queryForMap(sqlMapId, parameterObject, key);
	}

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
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key, String value) {
		Assert.hasText(sqlMapId, "sqlMapId cannot be null..., in SqlMapPersistenceImpl.queryForMap()");
		return getSqlMapClientTemplate().queryForMap(sqlMapId, parameterObject, key, value);
	}

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
	 *             -If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public Object findObjectBySqlMap(String sqlMapId, Object parameterObject) {
		Assert.hasText(sqlMapId, "sqlMapId cannot be null..., in SqlMapPersistenceImpl.queryForObject()");
		return getSqlMapClientTemplate().queryForObject(sqlMapId, parameterObject);
	}

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
	public PaginationSupport findPaginatedBySqlMap(String sqlMapId, Object parameterObject, int offset, int maxRows) {
		Assert.hasText(sqlMapId, "sqlMapId cannot be null..., in SqlMapPersistenceImpl.queryForList()");
		Assert.isTrue(maxRows != 0, "maxRows cannot be 0, in SqlMapPersistenceImpl.queryForList()");
		
		Integer count = null;
		try {
			count = (Integer) this.findObjectBySqlMap(sqlMapId + SQLID_COUNT, parameterObject);
		} catch (Exception e) {
			String msg = String.valueOf("Config file cannot be COUNT:") + sqlMapId; 
			logger.warn(msg, e);
			throw new WorkinDataAccessException(msg, e);
		}
		
		if (count == null || count.intValue() <= 0) {
			logger.debug(" Excute later count is 0, no data in this search.");
			return new PaginationSupport(new LinkedList(), count.intValue(), maxRows, offset);
		}
		
		int tmpOffset = (offset < 0 ? 0 : offset);
		int tmpMaxRows = (maxRows <= 0 ? 1 : maxRows);
		
		List<?> resultList = getSqlMapClientTemplate().queryForList(sqlMapId, parameterObject, tmpOffset, tmpMaxRows);
		return new PaginationSupport(resultList, count.intValue(), tmpMaxRows, tmpOffset);
	}
	
	
	public static final String SQLID_COUNT = "_count"; 
}
