package org.workin.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class LoggerTestCase {
	
	private static final transient Logger logger = LoggerFactory.getLogger(LoggerTestCase.class);
	
	public static void main(String[] args) {
		logger.info(" Hello,{}{}", "G.Lee", "!");
	}

}
