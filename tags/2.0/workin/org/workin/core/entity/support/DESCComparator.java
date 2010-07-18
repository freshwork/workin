package org.workin.core.entity.support;

import java.util.Comparator;

import org.workin.core.entity.Idable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class DESCComparator implements Comparator<Idable>, org.workin.core.entity.support.Comparator {
	
	static class DESCComparatorHolder {
		static DESCComparator instance = new DESCComparator();
	}

	public static DESCComparator getInstance() {
		return DESCComparatorHolder.instance;
	}

	@Override
	public int compare(Idable entity, Idable nextEntity) {
		int compareResult = comparator.compare(entity, nextEntity);
		return -compareResult;
	}
	
}
