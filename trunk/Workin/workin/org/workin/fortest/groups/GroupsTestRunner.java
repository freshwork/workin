package org.workin.fortest.groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("deprecation")
public class GroupsTestRunner extends JUnit4ClassRunner {


	public static final String PROPERTY_NAME = "test.groups";

	public static final String PROPERTY_FILE = "application.test.properties";

	private static Logger logger = LoggerFactory.getLogger(GroupsTestRunner.class);

	private static List<String> groups;

	public GroupsTestRunner(Class<?> klass) throws InitializationError {
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

	public static boolean isTestClassInGroups(Class<?> testClass) {
		if (groups == null) {
			initGroups();
		}
		if (groups.contains(Groups.ALL))
			return true;

		Groups annotationGroup = testClass.getAnnotation(Groups.class);
		if ((annotationGroup == null) || groups.contains(annotationGroup.value()))
			return true;

		return false;
	}

	/**
	 * 
	 * @param testMethod
	 * @return
	 */
	public static boolean isTestMethodInGroups(Method testMethod) {
		if (groups == null) {
			initGroups();
		}
		if (groups.contains(Groups.ALL))
			return true;

		Groups annotationGroup = testMethod.getAnnotation(Groups.class);
		if ((annotationGroup == null) || groups.contains(annotationGroup.value()))
			return true;

		return false;
	}
	
	protected static void initGroups() {

		String groupsDef = getGroupsFromSystemProperty();

		if (groupsDef == null) {
			groupsDef = getGroupsFromPropertyFile();
			if (groupsDef == null) {
				groupsDef = Groups.ALL;
			}
		}

		groups = Arrays.asList(groupsDef.split(","));
	}


	protected static String getGroupsFromSystemProperty() {
		return System.getProperty(PROPERTY_NAME);
	}

	protected static String getGroupsFromPropertyFile() {
		Properties p;
		try {
			p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
			return p.getProperty(PROPERTY_NAME);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
}
