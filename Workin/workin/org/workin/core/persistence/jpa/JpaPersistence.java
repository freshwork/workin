package org.workin.core.persistence.jpa;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.workin.core.persistence.support.PaginationSupport;
import org.workin.core.persistence.support.ProcedureParameter;
import org.workin.core.persistence.support.PropertyFilter;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 * @param <T>
 * @param <PK>
 */
public interface JpaPersistence<T, PK extends Serializable> {

	/**
	 * 
	 * Make an objectToSave instance managed and persistent.
	 * 
	 * Throws:
	 *		EntityExistsException 		 
	 *			- if the entity already exists. (The EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.) 
	 *		IllegalStateException 		 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 	 
	 *			- if not an entity 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param  objectToSave
	 * @return objectToSave
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public T persist(final T objectToSave);

	/**
	 * 
	 * Make an objectToSave(batch) instance managed and persistent.
	 * 
	 * Throws:
	 *		EntityExistsException 		 
	 *			- if the entity already exists. (The EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.) 
	 *		IllegalStateException 		 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 	 
	 *			- if not an entity 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param  objectToSave
	 * @return void
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public void batchPersist(final List<T> objectToSave);

	/**
	 * 
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if instance is not an entity or is a removed entity. 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.	
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 *  
	 * @param  objectToMerge
	 * @return objectToMerge
	 * 		   	- the instance that the state was merged to
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public T merge(final T objectToMerge);

	/**
	 * 
	 * Merge(Batch) the state of the given entitys into the current persistence context.
	 * 
	 * Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if instance is not an entity or is a removed entity. 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.	
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 *  
	 * @param  objectsToMerge
	 * @return void
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public void batchMerge(final List<T> objectsToMerge);

	/**
	 * 
	 * Refresh the state of the instance from the database, overwriting changes made to the entity, if any.
	 * 
	 * Throws:
	 * 	   	IllegalStateException 
	 * 			- if this EntityManager has been closed. 
	 * 		IllegalArgumentException 
	 * 			- if not an entity or entity is not managed. 
	 * 		TransactionRequiredException 
	 * 			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction. 
	 * 		EntityNotFoundException 
	 * 			- if the entity no longer exists in the database.
	 * 
	 * Note:
	* 		All exceptions Will be converted to DataAccessException's subclass and thow
	* 
	 * @param   objectToRefresh
	 * @return  void
	 * 
	 * @throws org.springframework.dao.DataAccessException
	* 		   - If an error occurs.but usually throws DataAccessException's subclass
	* 
	 */
	public void refresh(final T objectToRefresh);

	/**
	 * 
	 * Remove the entity instance.
	 * 
	 *	Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if not an entity or if a detached entity 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param objectToRemove
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public void remove(final T objectToRemove);

	/**
	 * 
	 * 1) Find entity by Object's PK. 
	 * 2) Remove the entity instance.
	 * 
	 * Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if not an entity or if a detached entity 
	 *			- if the first argument does not denote an entity type or the second argument is not a valid type for that entity's primary key
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param id
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 *  
	 */
	public void remove(final Class<T> entityClass, final PK id);

	/**
	 * 
	 * Remove(Batch) the entitys instance.
	 *
	 * Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if not an entity or if a detached entity 
	 *		TransactionRequiredException 
	 *			- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction.
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 *  
	 * @param objectsToRemove
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 */
	public void batchRemove(final List<T> objectsToRemove);

	/**
	 * 
	 * Synchronize the persistence context to the underlying database.
	 * 
	 * Throws:
	 *  	IllegalStateException 
	 *  		- if this EntityManager has been closed. 
	 *   	TransactionRequiredException 
	 *   		- if there is no transaction 
	 *   	PersistenceException 
	 *   		- if the flush fails
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 */
	public void flush();

	/**
	 * 
	 * Clear the persistence context, causing all managed entities to become detached. 
	 * Changes made to entities that have not been flushed to the database will not be persisted.
	 * 
	 * Throws:
	 *  	IllegalStateException 
	 *  		- if this EntityManager has been closed.
	 *
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 */
	public void clear();

	/**
	 * 
	 * Check if the instance belongs to the current persistence context.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if not an entity		
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entity
	 * @return 	boolean
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 *  
	 */
	public boolean contains(final T entity);

	/**
	 * 
	 * 1) Find entity by Object's PK. 
	 * 2) Check if the instance belongs to the current persistence context.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if not an entity	
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 *
	 * @param 	entityClass
	 * @param 	id
	 * @return 	boolean
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public boolean contains(final Class<T> entityClass, final PK id);

	/**
	 * 
	 * Find entity by Object's PK. 
	 * 
	 * Throws:
	 *		IllegalStateException 
	 *			- if this EntityManager has been closed. 
	 *		IllegalArgumentException 
	 *			- if the first argument does not denote an entity type or the second argument is not a valid type for that entity's primary key
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param id
	 * 
	 * @return entity
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public T findById(final Class<T> entityClass, final PK id);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 	
	 * Throws:
	 * 		IllegalArgumentException 
	 * 			- if a query has not been defined with the given name
	 *		NoResultException 		 
	 *			- if there is no result 
	 *		NonUniqueResultException 
	 *			- if more than one result 
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and throw
	 * 
	 * @param queryName
	 * @param values
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public T findUniqueByNamedOfQuery(final String queryName, final Object... values);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 	
	 * Throws:
	 * 		IllegalArgumentException 
	 * 			- if a query has not been defined with the given name
	 *		NoResultException 		 
	 *			- if there is no result 
	 *		NonUniqueResultException 
	 *			- if more than one result 
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryName
	 * @param params
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public T findUniqueByNamedOfQuery(final String queryName, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param params
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public T findUniqueByPropertys(final Class<T> entityClass, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 * 		IllegalArgumentException 
	 * 			- if a query has not been defined with the given name
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryName
	 * @param values
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<Object> findByNamedOfQuery(final String queryName, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 * 		IllegalArgumentException 
	 * 			- if a query has not been defined with the given name
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryName
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<Object> findByNamedOfQuery(final String queryName);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 * 		IllegalArgumentException 
	 * 			- if a query has not been defined with the given name
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryName
	 * @param nameAndValue
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<Object> findByNamedOfQuery(final String queryName, final Map<String, Object> nameAndValue);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<Object> find(final String queryString);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param start
	 * @param maxRows
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<Object> find(final String queryString, final int start, final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a PaginationSupport.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param start
	 * @param maxRows
	 * 
	 * @return PaginationSupport
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public PaginationSupport<Object> findPaginationSupport(final String queryString, final int start, final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param values
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<Object> find(final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param values
	 * @param start
	 * @param maxRows
	 * 
	 * @return List
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<Object> find(final int start, final int maxRows, final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a PaginationSupport.
	 * 
	 * Throws:
	 * 		IllegalStateException 
	 * 			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param values
	 * @param start
	 * @param maxRows
	 * 
	 * @return PaginationSupport
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public PaginationSupport<Object> findPaginationSupport(final int start, final int maxRows,
			final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 
	 * Throws:
	 * 		NoResultException 
	 * 			- if there is no result
	 *		NonUniqueResultException 
	 *			- if more than one result
	 *		IllegalStateException 
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * 
	 * @return int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int countByQueryString(final String queryString);

	/**
	 * 
	 * Execute a SELECT query that returns a single result.
	 * 
	 * Throws:
	 * 		NoResultException 
	 * 			- if there is no result
	 *		NonUniqueResultException 
	 *			- if more than one result
	 *		IllegalStateException 
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 * 
	 * @param queryString
	 * @param values
	 * 
	 * @return int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int countByQueryString(final String queryString, final Object... values);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * 
	 * @return 	int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int executeNamedOfQuery(final String queryName);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * @param 	values
	 * 
	 * @return 	int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int executeNamedOfQuery(final String queryName, final Object... values);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * @param 	nameAndValue
	 * 
	 * @return 	int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int executeNamedOfQuery(final String queryName, final Map<String, ?> nameAndValue);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int execute(final String queryString);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * @param 	values
	 * 
	 * @return 	int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int execute(final String queryString, final Object... values);

	/**
	 * 
	 * Execute an update or delete statement and return a int value.
	 * 
	 *	Throws:
	 *			IllegalStateException 
	 *				- if called for a Java Persistence query language SELECT statement
	 *			TransactionRequiredException 
	 *				- if there is no transaction
	 * 
	 * @param 	queryString
	 * @param 	nameAndValue
	 * 
	 * @return 	int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public int execute(final String queryString, final Map<String, ?> nameAndValue);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * 
	 * @param start
	 * @param maxRows
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value,
			final int start, final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a PaginationSupport.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * 
	 * @param start
	 * @param maxRows
	 * 
	 * @return PaginationSupport<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public PaginationSupport<T> findPaginatedByProperty(final Class<T> entityClass, final String propertyName,
			final Object value, final int start, final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entityClass
	 * @param 	params
	 * 
	 * @return 	List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<T> findByPropertys(final Class<T> entityClass, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entityClass
	 * @param 	params
	 * 
	 * @param 	start
	 * @param 	maxRows
	 * 
	 * @return 	List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public List<T> findByPropertys(final Class<T> entityClass, final Map<String, ?> params, final int start,
			final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a PaginationSupport.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entityClass
	 * @param 	params
	 * 
	 * @param 	start
	 * @param 	maxRows
	 * 
	 * @return 	PaginationSupport<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public PaginationSupport<T> findPaginatedByPropertys(final Class<T> entityClass, final Map<String, ?> params,
			final int start, final int maxRows);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entityClass
	 * @return 	List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> getAll(final Class<T> entityClass);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 
	 *
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param 	entityClass
	 * @return 	List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> getAllDistinct(final Class<T> entityClass);

	/**
	 * 
	 * Execute a SELECT query and return the count.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * 
	 * @return int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int countByProperty(final Class<?> entityClass, final String propertyName, final Object value);

	/**
	 * 
	 * Execute a SELECT query and return the count.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param params
	 * 
	 * @return int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int countByPropertys(final Class<?> entityClass, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the count.
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param entityClass
	 * @param filters
	 * 
	 * @return int
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int countByPropertyFilter(final Class<T> targetClass, final List<PropertyFilter> filters);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * 
	 * @return List<?>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<?> findByNativeQuery(final String queryString);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param values
	 * 
	 * @return List<?>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<?> findByNativeQuery(final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param start
	 * @param maxRows
	 * @param values
	 * 
	 * @return List<?>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<?> findByNativeQuery(final String queryString, final int start, final int maxRows,
			final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param returnClass
	 * @param values
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a List.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param returnClass
	 * @param start
	 * @param maxRows
	 * @param values
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final int start,
			final int maxRows, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a Entity.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param returnClass
	 * @param queryString
	 * @param values
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public T findUniqueByNativeQuery(final Class<T> returnClass, final String queryString, final Object... values);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a list.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param params
	 * 
	 * @return List<?>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<?> findByNativeQuery(final String queryString, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a list.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param start
	 * @param maxRows
	 * @param params
	 * 
	 * @return List<?>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<?> findByNativeQuery(final String queryString, final int start, final int maxRows,
			final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a list.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param returnClass
	 * @param queryString
	 * @param params
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a list.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param returnClass
	 * @param queryString
	 * @param start
	 * @param maxRows
	 * @param params
	 * 
	 * @return List<T>
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public List<T> findByNativeQuery(final Class<T> returnClass, final String queryString, final int start,
			final int maxRows, final Map<String, ?> params);

	/**
	 * 
	 * Execute a SELECT query and return the query results as a Entity.
	 * 	
	 * Throws:
	 *	    IllegalStateException 	 
	 *			- if this EntityManager has been closed
	 *			- if called for a Java Persistence query language UPDATE or DELETE statement
	 *			
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param returnClass
	 * @param queryString
	 * @param params
	 * 
	 * @return T
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 * 
	 */
	public T findUniqueByNativeQuery(final Class<T> returnClass, final String queryString, final Map<String, ?> params);

	/**
	 * 
	 * Execute an update or delete statement.
	 * 
	 * Throws:
	 *	IllegalStateException 
	 *		- if called for a Java Persistence query language SELECT statement or for a criteria query
	 *	TransactionRequiredException 
	 *		- if there is no transaction
	 *	QueryTimeoutException 
	 *		- if the statement execution exceeds the query timeout value set and only the statement is rolled back
	 *	PersistenceException 
	 *		- if the query execution exceeds the query timeout value set and the transaction is rolled back
	 * 
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @return
	 * 		The number of entities updated or deleted.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int persistByNativeQuery(final String queryString);

	/**
	 * 
	 * Execute an update or delete statement.
	 * 
	 * Throws:
	 *	IllegalStateException 
	 *		- if called for a Java Persistence query language SELECT statement or for a criteria query
	 *	TransactionRequiredException 
	 *		- if there is no transaction
	 *	QueryTimeoutException 
	 *		- if the statement execution exceeds the query timeout value set and only the statement is rolled back
	 *	PersistenceException 
	 *		- if the query execution exceeds the query timeout value set and the transaction is rolled back
	 * 
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 * 		The number of entities updated or deleted.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int persistByNativeQuery(final String queryString, final Map<String, ?> params);

	/**
	 * 
	 * Execute an update or delete statement.
	 * 
	 * Throws:
	 *	IllegalStateException 
	 *		- if called for a Java Persistence query language SELECT statement or for a criteria query
	 *	TransactionRequiredException 
	 *		- if there is no transaction
	 *	QueryTimeoutException 
	 *		- if the statement execution exceeds the query timeout value set and only the statement is rolled back
	 *	PersistenceException 
	 *		- if the query execution exceeds the query timeout value set and the transaction is rolled back
	 * 
	 * 
	 * Note:
	 * 		All exceptions Will be converted to DataAccessException's subclass and thow
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 * 		The number of entities updated or deleted.
	 * 
	 * @throws org.springframework.dao.DataAccessException
	 * 		   	- If an error occurs.but usually throws DataAccessException's subclass
	 * 
	 */
	public int persistByNativeQuery(final String queryString, final Object... values);

	/**
	 * 
	 * Find all results by CriteriaQuery and PropertyFilter.
	 * 
	 * @param targetClass
	 * @param filters
	 * @return
	 * 
	 */
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters);

	/**
	 * 
	 * Find all results by CriteriaQuery and PropertyFilter.
	 * 
	 * @param targetClass
	 * @param filters
	 * @param start
	 * @param maxRows
	 * @return
	 * 
	 */
	public PaginationSupport<T> findPaginationSupportByCriteriaQuery(final Class<T> targetClass,
			final List<PropertyFilter> filters, final int start, final int maxRows);

	/**
	 * 
	 * Find distinct or all results by CriteriaQuery and PropertyFilter.
	 * 
	 * @param targetClass
	 * @param filters
	 * @param isDistinct
	 * @return
	 * 
	 */
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters,
			final boolean isDistinct);

	/**
	 * Execute stored procedure by Hibernate JPA implementation
	 * @param procedureName
	 * @param procedureParams
	 * @return
	 */
	public Map<String, Object> executeProcedure(final String procedureName,
			final List<ProcedureParameter> procedureParams);

	/**
	 * Return JDBC connection
	 * @return
	 */
	public Connection getJdbcConnection();

}
