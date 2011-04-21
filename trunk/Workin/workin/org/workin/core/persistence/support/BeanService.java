package org.workin.core.persistence.support;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface BeanService {

	public abstract PersistenceService getPersistenceService();

	public abstract void setPersistenceService(PersistenceService persistenceService);

}
