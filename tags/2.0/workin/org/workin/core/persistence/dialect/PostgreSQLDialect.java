package org.workin.core.persistence.dialect;

import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PostgreSQLDialect extends AbstractDialect {

	public String getLimitString(String sql, int offset, int limit) {
		Assert.hasText(sql, "sql string can not be null");

		return new StringBuffer(sql.length() + 20).append(sql).append(
				offset > 0 ? " limit " + limit + " offset " + offset : " limit " + limit).toString();
	}
}
