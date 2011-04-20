/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 */
package org.workin.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class MockAppender extends AppenderSkeleton {

	private List<LoggingEvent> logs = new ArrayList<LoggingEvent>();

	public LoggingEvent getFirstLog() {
		if (logs.isEmpty())
			return null;
		return logs.get(0);
	}

	public LoggingEvent getLastLog() {
		if (logs.isEmpty())
			return null;
		return logs.get(logs.size() - 1);
	}

	public List<LoggingEvent> getAllLogs() {
		return logs;
	}

	public void clearLogs() {
		logs.clear();
	}

	public void addToLogger(Logger logger) {
		logger.addAppender(this);
	}

	public void addToLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		logger.addAppender(this);
	}

	public void addToLogger(Class<?> loggerClass) {
		Logger logger = Logger.getLogger(loggerClass);
		logger.addAppender(this);
	}

	@Override
	protected void append(LoggingEvent event) {
		logs.add(event);
	}

	/**
	 * @see AppenderSkeleton#close()
	 */
	@Override
	public void close() {
	}

	/**
	 * @see AppenderSkeleton#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}
}
