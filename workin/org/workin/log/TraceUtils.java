/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 */
package org.workin.log;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.MDC;


public class TraceUtils {
	
	public static final String TRACE_ID_KEY = "traceId";
	
	public static final int TRACE_ID_LENGTH = 10;
	
	public static void beginTrace() {
		String traceId = RandomStringUtils.randomAlphanumeric(TRACE_ID_LENGTH);
		MDC.put(TRACE_ID_KEY, traceId);
	}
	
	public static void beginTrace(String traceId) {
		MDC.put(TRACE_ID_KEY, traceId);
	}

	public static void endTrace() {
		MDC.remove(TRACE_ID_KEY);
	}
}
