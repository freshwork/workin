package org.workin.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ServiceException;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class LoggerAspectProxy {

	public Object invoke(ProceedingJoinPoint joinPoint) {
		
		Object result = null;
		
		if(joinPoint == null) return result;
		
		long startTime = System.currentTimeMillis();  
		
		try {
			
			iLogger(buildMessage(joinPoint, 1));
			result = joinPoint.proceed();
			iLogger(buildMessage(joinPoint, 2) + (System.currentTimeMillis() - startTime) + STYLE_METHOD_INVOCATION_TIME_END);
			
			return result;  
		} catch (ServiceException ex) {
			logger.error(buildMessage(joinPoint, 3));
			ex.printStackTrace();
			throw ex;
		} catch (Throwable e) {
			logger.error(buildMessage(joinPoint, 3));
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 
	 * @param msg
	 * 
	 */
	private static void iLogger(String msg) {
		if(logger.isInfoEnabled())
			logger.info(msg);
	}
	
	
	/**
	 * 
	 * build logger message.
	 * 
	 * @param joinPoint
	 * @param msgType
	 * @return
	 * 
	 */
	private static String buildMessage(ProceedingJoinPoint joinPoint, int msgType) {
		
		if (!logger.isInfoEnabled()) return STYLE_COMPART;
		
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(className);
		sb.append(STYLE_COMPART);
		sb.append(methodName);
		sb.append(STYLE_COMPART_METHOD);
		
		// msg type
		// 1 -> Start
		// 2 -> End
		// 3 -> Invoke Error
		
		if(1 == msgType){
			sb.append(STYLE_COMPART_START);
		} else if(2 == msgType){
			sb.append(STYLE_COMPART_END);
		} else if(3 == msgType) {
			sb.append(STYLE_COMPART_INVOKE_ERROR);	
		} 
			
		return sb.toString();
	}
	
	private static String STYLE_COMPART = ".";
	
	private static String STYLE_COMPART_METHOD = "()";
	
	private static String STYLE_COMPART_START = "-->Start.";
	
	private static String STYLE_COMPART_END = "-->End. [Invocation Time : ";
	
	private static String STYLE_COMPART_INVOKE_ERROR = "--Invoke Error.";
	
	private static String STYLE_METHOD_INVOCATION_TIME_END = " ms.]";
	
	private transient static final Logger logger = LoggerFactory.getLogger(LoggerAspectProxy.class);
}
