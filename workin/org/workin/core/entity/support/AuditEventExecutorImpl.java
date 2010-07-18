package org.workin.core.entity.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.core.entity.Auditable;
import org.workin.exception.ServiceException;
import org.workin.spring.security.SpringSecurityUtils;
import org.workin.util.DateUtils;
import org.workin.util.StringUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class AuditEventExecutorImpl implements AuditEventExecutor {
	
	@Override
	public void auditOnPrePersist(Object entity) {
		this.autoFillEntity(entity, true);
	}

	@Override
	public void auditOnPreUpdate(Object entity) {
		this.autoFillEntity(entity, false);
	}
	
	@Override
	public void auditOnDestroy() {
	}
	
	@Override
	public void auditOnPreRemove(Object entity) {
	}
	
	@Override
	public String getCurrentUserName() {
		String currentUserName = SpringSecurityUtils.getCurrentUserName();
		
		if(StringUtils.isBlankOrNull(currentUserName)) {
			throw new ServiceException(" Current user name is empty! Please login again.");
		}
		
		return currentUserName;
	}
	
	/**
	 * 
	 * Automatic Fill Entity.
	 * 
	 * @param entity
	 * @param isPersist 
	 * 
	 * @return void
	 * 
	 */
	private void autoFillEntity(Object entity, boolean isPersist) {
		Auditable auditable = null;
		
		if(entity instanceof Auditable) {
			auditable = (Auditable) entity;
			String currentUserName = this.getCurrentUserName();
			
			if(isPersist) {
				auditable.setCreateBy(currentUserName);
				auditable.setCreatedttm(DateUtils.getNow());
			} else {
				auditable.setUpdatedttm(DateUtils.getNow());
				auditable.setUpdateBy(currentUserName);
			}
			logger.debug(" Field entity created or updated By: {}", currentUserName);
		}	
	}
	
	protected static final transient Logger logger = LoggerFactory.getLogger(AuditEventExecutorImpl.class);
}
