package org.workin.exception;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class WorkinDataAccessException extends DataAccessException {
	
	private static final long serialVersionUID = -1617327610439319210L;

	public WorkinDataAccessException(String msg) {
		super(msg);
	}
	
	public WorkinDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
