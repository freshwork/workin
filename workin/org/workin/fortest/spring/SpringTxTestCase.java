package org.workin.fortest.spring;

import org.springframework.test.context.ContextConfiguration;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SpringTxTestCase extends SpringTxNoContextConfigTestCase {
	
}

