package org.workin.core.persistence.support;

import java.io.Serializable;
import java.util.List;

import org.workin.core.entity.Idable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public interface CrudService {
	
	/**
	 * 
	 * save entity to database.
	 * 
	 * @param  entity
	 * 
	 * @return Idable
	 * 
	 */
	Idable save(final Idable entity);
	
	/**
	 * 
	 * make an objectsToSave(batch) instance managed and persistent.
	 * 
	 * @param objectsToSave
	 * 
	 */
	void batchPersist(final List objectsToSave);
	
	
	/**
	 * 
	 * merge(save or update) entity to database.
	 * 
	 * @param  entity
	 * 
	 * @return Idable
	 * 
	 */
	Idable merge(final Idable entity);
	
	/**
	 * 
	 * merge(Batch) the state of the given entitys into the current persistence context.
	 * 
	 * @param objectsToMerge
	 */
	void batchMerge(final List objectsToMerge);
	
	
	/**
	 * 
	 * remove entity to database.
	 * 
	 * @param   entity
	 * 
	 * @return  void
	 * 
	 */
	void remove(final Idable entity);
	
	/**
	 * 
	 * remove(Batch) the entitys instance.
	 * 
	 * @param objectsToRemove
	 */
	void batchRemove(final List objectsToRemove);
	
	/**
	 * 
	 * find entity by id.
	 * 
	 * @param   entityClass
	 * @param   entityId
	 * 
	 * @return  Idable
	 * 
	 */
	Idable findById(Class<?> entityClass, final Serializable entityId);
	
	
	/**
	 * 
	 * get all data with entity .
	 * 
	 * @param   entityClass
	 * 
	 * @return  List<?>
	 * 
	 */
	List<?> getAll(final Class<?> entityClass);
	
	/**
	 * 
	 * get all distinct data with entity.
	 * 
	 * @param   entityClass
	 * 
	 * @return  List<?>
	 * 
	 */
	List<?> getAllDistinct(final Class<?> entityClass);
}
