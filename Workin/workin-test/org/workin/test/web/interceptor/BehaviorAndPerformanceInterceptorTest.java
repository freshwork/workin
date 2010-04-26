package org.workin.test.web.interceptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.workin.fortest.web.StrutsTestCase;
import org.workin.web.struts2.interceptor.BehaviorAndPerformanceInterceptor;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorAndPerformanceInterceptorTest extends StrutsTestCase {
    
	private BehaviorAndPerformanceInterceptor interceptor;
	
	@Before
    protected void setUp() throws Exception {
        super.setUp();
        interceptor = new BehaviorAndPerformanceInterceptor();
        interceptor.init();
    }
	
	@Test
	public void interceptorTest() {
	}
	
	
	@After
    protected void tearDown() throws Exception {
        super.tearDown();
        interceptor.destroy();
        interceptor = null;
    }

}	
