package org.workin.fortest.spring;

import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.notification.RunNotifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.workin.fortest.groups.GroupsTestRunner;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("deprecation")
public class SpringGroupsTestRunner extends SpringJUnit4ClassRunner {

	public SpringGroupsTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}


	@Override
	public void run(RunNotifier notifier) {
		if (!GroupsTestRunner.isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}
		super.run(notifier);
	}

	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {

		if (!GroupsTestRunner.isTestMethodInGroups(method)) {
			notifier.fireTestIgnored(getDescription());
			return;
		}
		super.invokeTestMethod(method, notifier);
	}
}
