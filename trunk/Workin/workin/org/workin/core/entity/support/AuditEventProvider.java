package org.workin.core.entity.support;

import javax.annotation.PreDestroy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.workin.core.constant.Constants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 * 
 */
public class AuditEventProvider implements ApplicationContextAware {
	
	/**
	 * 
	 * Intercept entity, when pre-persist. 
	 * 
	 */
	@PrePersist
	public void onPrePersist(Object entity) {
		auditEventExecutor.auditOnPrePersist(entity);
	}
	
	/**
	 * 
	 * Intercept entity, when pre-update. 
	 * 
	 */
	@PreUpdate
	public void onPreUpdate(Object entity) {
		auditEventExecutor.auditOnPreUpdate(entity);
	}
	
	
	/**
	 * 
	 * Intercept entity, when pre-remove. 
	 * 
	 */
	@PreRemove
	public void onPreRemove(Object entity) {
		auditEventExecutor.auditOnPreRemove(entity);
	}
	
	
	/**
	 * 
	 * Intercept entity, when pre-destroy. 
	 * 
	 */
	@PreDestroy
	public void onPreDestroy() {
		auditEventExecutor.auditOnDestroy();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		try {
			auditEventExecutor = (AuditEventExecutor) ctx.getBean(Constants.AUDIT_EVENT_EXECUTOR);
		} catch (Exception ex) {
			ex.printStackTrace();
			auditEventExecutor = new AuditEventExecutorImpl();
		}
		
		if(auditEventExecutor == null) {
			auditEventExecutor = new AuditEventExecutorImpl();
		}
		
		logger.info(" Audit Event Executor : {}", auditEventExecutor.getClass().getName());
	}
	
	private static AuditEventExecutor auditEventExecutor;
	
	protected static final transient Logger logger = LoggerFactory.getLogger(AuditEventProvider.class);
}
