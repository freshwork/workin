package org.workin.core.persistence.dialect;

import org.workin.util.Assert;

/***
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MySQLDialect extends AbstractDialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		Assert.hasText(sql, "sql string can not be null");

		StringBuffer selectedOfSql = new StringBuffer(sql);
		selectedOfSql.append(" limit ");

		if (offset > 0) {
			return selectedOfSql.append(offset).append(",").append(limit).toString();
		} else {
			return selectedOfSql.append(limit).toString();
		}
	}
}
