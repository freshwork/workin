package org.workin.core.persistence.ibatis.plugin;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface SqlMapCounter {
	
	public long getObjectTotal(String selectStatementId); 
	
	public long getObjectTotal(String selectStatementId, Object parameterObject); 
	
}
