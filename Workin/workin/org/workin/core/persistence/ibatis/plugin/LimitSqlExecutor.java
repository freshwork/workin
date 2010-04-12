package org.workin.core.persistence.ibatis.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.core.persistence.dialect.Dialect;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.scope.StatementScope;

import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class LimitSqlExecutor extends SqlExecutor {

	private Dialect dialect;

	private boolean enableLimit = true;

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	@Override
	public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters,
			int skipResults, int maxResults, RowHandlerCallback callback) throws SQLException {
		
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS) && supportsLimit()) {
			sql = dialect.getLimitString(sql, skipResults, maxResults);
			
			if (logger.isDebugEnabled()) {
				logger.debug("LimitSqlExecutor build sql: {}", sql);
			}
			
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
		super.executeQuery(statementScope, conn, sql, parameters, skipResults, maxResults, callback);
	}

	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return dialect.supportsLimit();
		}
		return false;
	}

	private static final transient Logger logger = LoggerFactory.getLogger(LimitSqlExecutor.class);
}
