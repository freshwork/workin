package org.workin.trace.service;

import java.io.Serializable;

import org.workin.core.persistence.support.AbstractBeanService;
import org.workin.trace.domain.StoredLog;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class StoredLogServiceImpl extends AbstractBeanService<StoredLog, Serializable> implements StoredLogService {
	
	public enum LogLevel {
		DEBUG, INFO, WARN, ERROR, TRACE;
	}
	
}
