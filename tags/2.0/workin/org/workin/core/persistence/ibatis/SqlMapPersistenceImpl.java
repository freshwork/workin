package org.workin.core.persistence.ibatis;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.workin.core.persistence.ibatis.plugin.CountStatementHelper;
import org.workin.core.persistence.ibatis.plugin.LimitSqlExecutor;
import org.workin.core.persistence.support.PaginationSupport;
import org.workin.util.Assert;
import org.workin.util.ReflectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 * 
 */

@Repository
@SuppressWarnings( { "unchecked", "deprecation" })
public class SqlMapPersistenceImpl extends SqlMapClientDaoSupport implements SqlMapPersistence {

	private SqlExecutor sqlExecutor;

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public void setEnableLimit(boolean enableLimit) {
		if (sqlExecutor instanceof LimitSqlExecutor) {
			((LimitSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
		}
	}

	public void initialize() throws Exception {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectionUtils.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor",
						sqlExecutor);
			}
		}
	}

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

		Number count = this.getObjectTotal(sqlMapId, parameterObject);

		if (count == null || count.intValue() <= 0) {
			logger.info("After the query execution Count the number of records is zero.");
			return new PaginationSupport(new LinkedList(), count.intValue(), offset, maxRows);
		}
		
		int tmpOffset = (offset < 0 ? 0 : offset);
		int tmpMaxRows = (maxRows <= 0 ? 1 : maxRows);

		List<?> resultList = getSqlMapClientTemplate().queryForList(sqlMapId, parameterObject, tmpOffset, tmpMaxRows);
		return new PaginationSupport(resultList, count.intValue(), tmpOffset, tmpMaxRows);
	}

	@Override
	public long getObjectTotal(String selectStatementId) {
		prepareCountSql(selectStatementId);
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementHelper.getCountStatementId(selectStatementId));
	}

	@Override
	public long getObjectTotal(String selectStatementId, Object parameterObject) {
		prepareCountSql(selectStatementId);
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementHelper.getCountStatementId(selectStatementId), parameterObject);
	}

	/**
	 * 
	 * @param selectSql
	 * 
	 */
	protected void prepareCountSql(String selectStatementId) {
		String countQuery = CountStatementHelper.getCountStatementId(selectStatementId);

		if (logger.isDebugEnabled()) {
			logger.debug("In prepareCountSql Convert {} to {} ", selectStatementId, countQuery);
		}

		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) sqlMapClient).getDelegate();

			try {
				delegate.getMappedStatement(countQuery);
			} catch (SqlMapException e) {
				delegate.addMappedStatement(CountStatementHelper.createCountStatement(delegate
						.getMappedStatement(selectStatementId)));
			}
		}
	}

	public static final String SQLID_COUNT = "_count";

	private static final transient Logger logger = LoggerFactory.getLogger(SqlMapPersistenceImpl.class);
}
