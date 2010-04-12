package org.workin.core.persistence.dialect;

import org.workin.core.constant.Constants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class AbstractDialect implements Dialect {

	@Override
	public String getForUpdateString() {
		return Constants.SQLMAP_KEYWORDS_FORUPDATE;
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}
	
	@Override
	public abstract String getLimitString(String sql, int offset, int limit);
}
