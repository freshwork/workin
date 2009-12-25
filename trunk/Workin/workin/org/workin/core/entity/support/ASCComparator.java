package org.workin.core.entity.support;

import java.util.Comparator;

import org.workin.core.entity.Idable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ASCComparator implements Comparator<Idable>, org.workin.core.entity.support.Comparator {

	static class ASCComparatorHolder {
		static ASCComparator instance = new ASCComparator();
	}

	public static ASCComparator getInstance() {
		return ASCComparatorHolder.instance;
	}
	
	
	@Override
	public int compare(Idable entity, Idable nextEntity) {
		return comparator.compare(entity, nextEntity);
	}
}
