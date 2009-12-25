package org.workin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.util.StringUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ThrowableHandle {
	
	private static final transient Logger handleLogger = LoggerFactory.getLogger(ThrowableHandle.class);
	
	/**
	 * 
	 * Handle Throwable with handleLogger.
	 * 
	 * @param throwable
	 */
	public static void handle(Throwable throwable) {
		handle(throwable, handleLogger);
	}
	
	/**
	 * 
	 * Handle Throwable with handleLogger.
	 * 
	 * @param throwable
	 * @param logger
	 * 
	 */
	public static void handle(Throwable throwable, Logger logger) {
		throwable.printStackTrace();
		logger.error(throwable.getMessage(), throwable);
	}
	
	/**
	 * 
	 * Handle Throwable with handleLogger with user message.
	 * 
	 * @param userMessage
	 * @param throwable
	 * 
	 * 
	 */
	public static void handle(String userMessage, Throwable throwable) {
		handle(userMessage, throwable, handleLogger);
	}
	
	/**
	 * 
	 * Handle Throwable with userLogger with user message.
	 * 
	 * @param userMessage
	 * @param throwable
	 * @param logger
	 * 
	 */
	public static void handle(String userMessage, Throwable throwable, Logger logger) {
		throwable.printStackTrace();
		logger.error(userMessage, throwable.getMessage(), throwable);
	}
	
	/**
	 * 
	 * Handle Throwable and then throw again. with handleLogger
	 * 
	 * @param userMessage
	 * @param throwable
	 * 
	 */
	public static void handleThrow(String userMessage, Throwable throwable) {
		handleThrow(userMessage, throwable, handleLogger);
	}
	
	/**
	 * 
	 * @param throwable
	 * @param logger
	 */
	public static void handleThrow(Throwable throwable, Logger logger) {
		handleThrow(null, throwable, logger);
	}
	
	/**
	 * 
	 * Handle Throwable and then throw again. with userLogger
	 * 
	 * @param userMessage
	 * @param throwable
	 * @param logger
	 * 
	 * 
	 */
	public static void handleThrow(String userMessage, Throwable throwable, Logger logger) {
		throwable.printStackTrace();
		
		if(StringUtils.hasText(userMessage))
			logger.error(userMessage, throwable.getMessage(), throwable);
		else
			logger.error(throwable.getMessage(), throwable);
		
		throw new ServiceException(throwable);
	}
}
