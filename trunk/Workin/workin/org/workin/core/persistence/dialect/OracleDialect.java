package org.workin.core.persistence.dialect;

import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class OracleDialect extends AbstractDialect {

	@Override
	public boolean supportsLimitOffset() {
		return false;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		Assert.hasText(sql, "sql string can not be null");
		String sqlTmp = sql;
		sqlTmp = sql.trim();
		boolean isForUpdate = false;
		if (sqlTmp.toLowerCase().endsWith(" for update")) {
			sqlTmp = sqlTmp.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}

		pagingSelect.append(sql);
		if (offset > 0) {
			pagingSelect.append(" ) row_ ) where rownum_ <= " + (offset + limit) + " and rownum_ > " + offset);
		} else {
			pagingSelect.append(" ) where rownum <= " + +(offset + limit));
		}

		if (isForUpdate)
			pagingSelect.append(" for update");

		return pagingSelect.toString();
	}
}
