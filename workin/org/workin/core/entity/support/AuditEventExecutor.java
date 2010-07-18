package org.workin.core.entity.support;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface AuditEventExecutor {
	
	
	public String getCurrentUserName();
	
	
	/**
	 * 
	 * Intercept entity, when pre-persist. 
	 * 
	 */
	public void auditOnPrePersist(Object entity);
	
	
	/**
	 * 
	 * Intercept entity, when pre-update. 
	 * 
	 */
	public void auditOnPreUpdate(Object entity);
	
	
	/**
	 * 
	 * Intercept entity, when pre-remove. 
	 * 
	 */
	public void auditOnPreRemove(Object entity);
	
	
	/**
	 * 
	 * Intercept entity, when pre-destroy. 
	 * 
	 */
	public void auditOnDestroy();
	
}
