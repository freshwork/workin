package org.workin.fortest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@ContextConfiguration(locations={"/applicationContext.xml"})
public class AbstractTestCase extends AbstractJUnit38SpringContextTests {
	
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());

}
