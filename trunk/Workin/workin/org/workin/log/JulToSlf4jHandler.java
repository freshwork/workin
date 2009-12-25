package org.workin.log;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JulToSlf4jHandler {
	
	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
