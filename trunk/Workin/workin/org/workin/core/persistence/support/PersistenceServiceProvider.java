package org.workin.core.persistence.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.workin.core.persistence.ibatis.SqlMapPersistence;
import org.workin.core.persistence.jpa.JpaPersistence;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 * @param <T>
 * @param <PK>
 */

@Repository
@SuppressWarnings("unchecked")
public class PersistenceServiceProvider<T, PK extends Serializable> implements PersistenceService<T, PK> {

	// Handle service with JPA
	private JpaPersistence jpaPersistence;

	// Handle service with iBatis
	private SqlMapPersistence sqlMapPersistence;

	// Handle service with other...(can add any one, if system need)

	public PersistenceServiceProvider() {
		Assert.notNull(null, " Please config EntityPersistence first!");
	}
	
	public PersistenceServiceProvider(JpaPersistence jpaPersistence) {
		Assert.notNull(jpaPersistence, " jpaPersistence cannot be null!");
		this.jpaPersistence = jpaPersistence;
	}
	
	public PersistenceServiceProvider(JpaPersistence jpaPersistence, SqlMapPersistence sqlMapPersistence) {
		Assert.notNull(jpaPersistence, " jpaPersistence cannot be null!");
		Assert.notNull(sqlMapPersistence, " sqlMapPersistence cannot be null!");
		this.jpaPersistence = jpaPersistence;
		this.sqlMapPersistence = sqlMapPersistence;
	}
	
	@Override
	public T persist(final T objectToSave) {
		return (T) this.jpaPersistence.persist(objectToSave);
	}
	
	@Override
	public void batchPersist(final List objectsToSave) {
		this.jpaPersistence.batchPersist(objectsToSave);
	}
	
	@Override
	public T merge(final T objectToMerge) {
		return (T) this.jpaPersistence.merge(objectToMerge);
	}
	
	@Override
	public void batchMerge(final List objectsToMerge) {
		this.jpaPersistence.batchMerge(objectsToMerge);
	}
	
	@Override
	public void refresh(final T objectToRefresh) {
		this.jpaPersistence.refresh(objectToRefresh);
	}
	
	@Override
	public void remove(final T objectToRemove) {
		this.jpaPersistence.remove(objectToRemove);
	}
	
	@Override
	public void remove(final Class<T> entityClass, final PK id) {
		this.jpaPersistence.remove(entityClass, id);
	}
	
	@Override
	public void batchRemove(final List objectsToRemove) {
		this.jpaPersistence.batchRemove(objectsToRemove);
	}
	
	@Override
	public void flush() {
		this.jpaPersistence.flush();
	}
	
	@Override
	public void clear() {
		this.jpaPersistence.clear();
	}
	
	@Override
	public boolean contains(final T entity) {
		return this.jpaPersistence.contains(entity);
	}
	
	@Override
	public boolean contains(final Class<T> entityClass, final PK id) {
		return this.jpaPersistence.contains(entityClass, id);
	}
	
	@Override
	public T findById(final Class<T> entityClass, final PK id) {
		return (T) this.jpaPersistence.findById(entityClass, id);
	}
	
	@Override
	public T findUniqueByNamedOfQuery(final String queryName, final Object... values) {
		return (T) this.jpaPersistence.findUniqueByNamedOfQuery(queryName, values);
	}
	
	@Override
	public T findUniqueByNamedOfQuery(final String queryName, final Map<String, ?> params) {
		return (T) this.jpaPersistence.findUniqueByNamedOfQuery(queryName, params);
	}
	
	@Override
	public T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return (T) this.jpaPersistence.findUniqueByProperty(entityClass, propertyName, value);
	}
	
	@Override
	public T findUniqueByPropertys(final Class<T> entityClass, final Map<String, ?> params) {
		return (T) this.jpaPersistence.findUniqueByPropertys(entityClass, params);
	}
	
	@Override
	public List findByNamedOfQuery(final String queryName, final Object... values) {
		return this.jpaPersistence.findByNamedOfQuery(queryName, values);
	}
	
	@Override
	public List findByNamedOfQuery(final String queryName) {
		return this.jpaPersistence.findByNamedOfQuery(queryName);
	}
	
	@Override
	public List findByNamedOfQuery(final String queryName, final Map nameAndValue) {
		return this.jpaPersistence.findByNamedOfQuery(queryName, nameAndValue);
	}
	
	@Override
	public int executeNamedOfQuery(String queryName, Object... values) {
		return this.jpaPersistence.executeNamedOfQuery(queryName, values);
	}

	@Override
	public int executeNamedOfQuery(String queryName, Map<String, ?> nameAndValue) {
		return this.jpaPersistence.executeNamedOfQuery(queryName, nameAndValue);
	}
	
	@Override
	public List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return this.jpaPersistence.findByProperty(entityClass, propertyName, value);
	}
	
	@Override
	public List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value,
			final int start, final int maxRows) {
		return this.jpaPersistence.findByProperty(entityClass, propertyName, value, start, maxRows);
	}
	
	@Override
	public PaginationSupport<T> findPaginatedByProperty(final Class<T> entityClass,
			final String propertyName, final Object value, final int start, final int maxRows) {
		return this.jpaPersistence.findPaginatedByProperty(entityClass, propertyName, value, start, maxRows);
	}
	
	@Override
	public List<T> findByPropertys(final Class<T> entityClass, final Map<String, ?> params) {
		return this.jpaPersistence.findByPropertys(entityClass, params);
	}
	
	@Override
	public List<T> findByPropertys(final Class<T> entityClass, final Map<String, ?> params, final int start,
			final int maxRows) {
		return this.jpaPersistence.findByPropertys(entityClass, params, start, maxRows);
	}
	
	@Override
	public PaginationSupport<T> findPaginatedByPropertys(final Class<T> entityClass,
			final Map<String, ?> params, final int start, final int maxRows) {
		return this.jpaPersistence.findPaginatedByPropertys(entityClass, params, start, maxRows);
	}
	
	@Override
	public List<T> getAll(final Class<T> entityClass) {
		return this.jpaPersistence.getAll(entityClass);
	}
	
	@Override
	public List<T> getAllDistinct(final Class<T> entityClass) {
		return this.jpaPersistence.getAllDistinct(entityClass);
	}
	
	@Override
	public void updateOrDelByNamedOfQuery(final String queryName, final Object... values) {
		this.jpaPersistence.updateOrDelByNamedOfQuery(queryName, values);
	}
	
	@Override
	public void updateOrDelByNamedOfQuery(final String queryName, final Map<String, ?> params) {
		this.jpaPersistence.updateOrDelByNamedOfQuery(queryName, params);
	}
	
	@Override
	public int countByProperty(final Class<?> entityClass, final String propertyName, final Object value) {
		return this.jpaPersistence.countByProperty(entityClass, propertyName, value);
	}
	
	@Override
	public int countByPropertys(final Class<?> entityClass, final Map<String, ?> params) {
		return this.jpaPersistence.countByPropertys(entityClass, params);
	}
	
	@Override
	public List<?> findByNativeQuery(final String queryString) {
		return this.jpaPersistence.findByNativeQuery(queryString);
	}
	
	@Override
	public List<?> findByNativeQuery(final String queryString, final Object... values) {
		return this.jpaPersistence.findByNativeQuery(queryString, values);
	}
	
	@Override
	public List<?> findByNativeQuery(final String queryString, final int start, final int maxRows,
			final Object... values) {
		return this.jpaPersistence.findByNativeQuery(queryString, start, maxRows, values);
	}
	
	@Override
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString,
			final Object... values) {
		return this.jpaPersistence.findByNativeQuery(returnClass, queryString, values);
	}
	
	@Override
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final int start,
			final int maxRows, final Object... values) {
		return this.jpaPersistence.findByNativeQuery(returnClass, queryString, start, maxRows, values);
	}
	
	@Override
	public T findUniqueByNativeQuery(final Class<T> returnClass, final String queryString,
			final Object... values) {
		return (T) this.jpaPersistence.findUniqueByNativeQuery(returnClass, queryString, values);
	}
	
	@Override
	public List<?> findByNativeQuery(final String queryString, final Map<String, ?> params) {
		return this.jpaPersistence.findByNativeQuery(queryString, params);
	}
	
	@Override
	public List<?> findByNativeQuery(final String queryString, final int start, final int maxRows,
			final Map<String, ?> params) {
		return this.jpaPersistence.findByNativeQuery(queryString, start, maxRows, params);
	}
	
	@Override
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString,
			final Map<String, ?> params) {
		return this.jpaPersistence.findByNativeQuery(returnClass, queryString, params);
	}
	
	@Override
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final int start,
			final int maxRows, final Map<String, ?> params) {
		return this.jpaPersistence.findByNativeQuery(returnClass, queryString, start, maxRows, params);
	}
	
	@Override
	public T findUniqueByNativeQuery(final Class<T> returnClass, final String queryString,
			final Map<String, ?> params) {
		return (T) this.jpaPersistence.findUniqueByNativeQuery(returnClass, queryString, params);
	}
	
	@Override
	public int persistByNativeQuery(String queryString) {
		return this.jpaPersistence.persistByNativeQuery(queryString);
	}

	@Override
	public int persistByNativeQuery(String queryString, Map<String, ?> params) {
		return this.jpaPersistence.persistByNativeQuery(queryString, params);
	}

	@Override
	public int persistByNativeQuery(String queryString, Object... values) {
		return this.jpaPersistence.persistByNativeQuery(queryString, values);
	}
	
	@Override
	public List findListBySqlMap(String sqlMapId, Object parameterObject) {
		return this.sqlMapPersistence.findListBySqlMap(sqlMapId, parameterObject);
	}
	
	@Override
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key) {
		return this.sqlMapPersistence.findMapBySqlMap(sqlMapId, parameterObject, key);
	}
	
	@Override
	public Map findMapBySqlMap(String sqlMapId, Object parameterObject, String key, String value) {
		return this.sqlMapPersistence.findMapBySqlMap(sqlMapId, parameterObject, key, value);
	}
	
	@Override
	public Object findObjectBySqlMap(String sqlMapId, Object parameterObject) {
		return this.sqlMapPersistence.findObjectBySqlMap(sqlMapId, parameterObject);
	}

	@Override
	public PaginationSupport findPaginatedBySqlMap(String sqlMapId, Object parameterObject, int offset, int maxRows) {
		return this.sqlMapPersistence.findPaginatedBySqlMap(sqlMapId, parameterObject, offset, maxRows);
	}
}
