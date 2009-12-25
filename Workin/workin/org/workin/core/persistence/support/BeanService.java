package org.workin.core.persistence.support;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public interface BeanService extends CrudService {
	
	public abstract PersistenceService getPersistenceService();
	
	public abstract void setPersistenceService(PersistenceService persistenceService);
}
