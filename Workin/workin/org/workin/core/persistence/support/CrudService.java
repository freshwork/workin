package org.workin.core.persistence.support;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface CrudService<T, PK extends Serializable> {
	
	/**
	 * 
	 * save entity to database.
	 * 
	 * @param  objectToSave
	 * 
	 * @return objectToSave
	 * 
	 */
	public T save(final T objectToSave);
	
	/**
	 * 
	 * make an objectsToPersist(batch) instance managed and persistent.
	 * 
	 * @param objectsToPersist
	 * 
	 */
	public void batchPersist(final List<T> objectsToPersist);
	
	
	/**
	 * 
	 * merge(save or update) entity to database.
	 * 
	 * @param  objectToMerge
	 * 
	 * @return objectToMerge
	 * 
	 */
	public T merge(final T objectToMerge);
	
	/**
	 * 
	 * merge(Batch) the state of the given entitys into the current persistence context.
	 * 
	 * @param objectsToMerge
	 */
	public void batchMerge(final List<T> objectsToMerge);
	
	
	/**
	 * 
	 * remove entity to database.
	 * 
	 * @param   objectToRemove
	 * 
	 * @return  void
	 * 
	 */
	public void remove(final T objectToRemove);
	
	/**
	 * 
	 * remove(Batch) the entitys instance.
	 * 
	 * @param objectsToRemove
	 */
	public void batchRemove(final List<T> objectsToRemove);
	
	/**
	 * 
	 * find entity by id.
	 * 
	 * @param   entityClass
	 * @param   entityId
	 * 
	 * @return  entity
	 * 
	 */
	public T findById(final Class<T> entityClass, final PK entityId);
	
	
	/**
	 * 
	 * get all data with entity .
	 * 
	 * @param   entityClass
	 * 
	 * @return  List<T>
	 * 
	 */
	public List<T> getAll(final Class<T> entityClass);
	
	/**
	 * 
	 * get all distinct data with entity.
	 * 
	 * @param   entityClass
	 * 
	 * @return  Class<T>
	 * 
	 */
	public List<T> getAllDistinct(final Class<T> entityClass);
	
	
	/**
	 * 
	 * @param targetClass
	 * @param filters
	 * @return
	 * 
	 */
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters);
	
	/**
	 * 
	 * @param targetClass
	 * @param filters
	 * @param isDistinct
	 * @return
	 * 
	 */
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters, final boolean isDistinct);

}
