package org.workin.core.persistence.support;

import java.io.Serializable;
import java.util.List;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.workin.core.entity.Idable;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings({"unchecked","unused"})
public abstract class AbstractBeanService implements BeanService {
	
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
	public Idable save(final Idable entity) {
		return (Idable) this.persistenceService.persist(entity);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchPersist(final List objectsToSave) {
		this.persistenceService.batchPersist(objectsToSave);
	}
	
	
	@Override
	@Transactional
	@Profiled
	public Idable merge(final Idable entity) {
		return (Idable) this.persistenceService.merge(entity);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchMerge(final List objectsToMerge) {
		this.persistenceService.batchMerge(objectsToMerge);
	}
	
	@Override
	@Transactional
	@Profiled
	public void remove(final Idable entity) {
		this.persistenceService.remove(entity);
	}
	
	@Override
	@Transactional
	@Profiled
	public void batchRemove(final List objectsToRemove) {
		this.persistenceService.batchRemove(objectsToRemove);
	}
	
	@Override
	@Profiled
	public Idable findById(Class<?> entityClass, final Serializable entityId) {
		return (Idable) this.persistenceService.findById(entityClass, entityId);
	}
	
	@Override
	@Profiled
	public  List<?> getAll(final Class<?> entityClass) {
		return this.persistenceService.getAll(entityClass);
	}
	
	@Override
	@Profiled
	public  List<?> getAllDistinct(final Class<?> entityClass) {
		return this.persistenceService.getAllDistinct(entityClass);
	}
	
	@Autowired
	private PersistenceService persistenceService;
	
	// logger for all service
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
