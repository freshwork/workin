package org.workin.fortest.selenium;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.notification.RunNotifier;
import org.workin.fortest.groups.GroupsTestRunner;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("deprecation")
public class SeleniumTestRunner extends GroupsTestRunner {

	public SeleniumTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	public void run(RunNotifier notifier) {
		notifier.addFirstListener(new SeleniumTestListener());
		super.run(notifier);
	}
}

