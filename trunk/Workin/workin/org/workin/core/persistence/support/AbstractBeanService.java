package org.workin.core.persistence.support;

import java.io.Serializable;
import java.util.List;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.workin.core.entity.Idable;
import org.workin.trace.service.StoredLogService;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings({"unchecked","unused"})
public abstract class AbstractBeanService<T, PK extends Serializable> implements BeanService, CrudService<T, PK> {
	
	public PersistenceService getPersistenceService() {
		Assert.notNull(persistenceService, " Persistence Service cannot be null!");
		return persistenceService;
	}
	
	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	
	@Override
	@Transactional
	@Profiled
	public T save(final T objectToSave) {
		return (T) this.persistenceService.persist(objectToSave);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchPersist(final List<T> objectsToPersist) {
		this.persistenceService.batchPersist(objectsToPersist);
	}
	
	
	@Override
	@Transactional
	@Profiled
	public T merge(final T objectToMerge) {
		return (T) this.persistenceService.merge(objectToMerge);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchMerge(final List<T> objectsToMerge) {
		this.persistenceService.batchMerge(objectsToMerge);
	}
	
	@Override
	@Transactional
	@Profiled
	public void remove(final T objectToRemove) {
		this.persistenceService.remove(objectToRemove);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchRemove(final List<T> objectsToRemove) {
		this.persistenceService.batchRemove(objectsToRemove);
	}
	
	@Override
	@Profiled
	public T findById(Class<T> entityClass, final PK entityId) {
		return (T) this.persistenceService.findById(entityClass, entityId);
	}
	
	@Override
	@Profiled
	public  List<T> getAll(final Class<T> entityClass) {
		return this.persistenceService.getAll(entityClass);
	}
	
	@Override
	@Profiled
	public  List<T> getAllDistinct(final Class<T> entityClass) {
		return this.persistenceService.getAllDistinct(entityClass);
	}
	
	@Override
	@Profiled
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters) {
		return this.persistenceService.findByCriteriaQuery(targetClass, filters);
	}
	
	
	@Override
	@Profiled
	public List<T> findByCriteriaQuery(final Class<T> targetClass, final List<PropertyFilter> filters, final boolean isDistinct) {
		return this.persistenceService.findByCriteriaQuery(targetClass, filters, isDistinct);
	}
	
	@Autowired
	private PersistenceService persistenceService;

	// logger for all service
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
