package org.workin.core.persistence.dialect;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface Dialect {
	
	public String getForUpdateString();
	
    public boolean supportsLimit();

    public boolean supportsLimitOffset();
    
    /**
     * 
     * @param sql
     * @param offset
     * @param limit
     * @return
     * 
     */
    public String getLimitString(String sql, int offset, int limit);
}