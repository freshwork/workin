package org.workin.core.entity.support;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.workin.core.entity.Idable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class EntityComparator implements Comparator<Idable> {
	
	static class EntityComparatorHolder {
		static EntityComparator instance = new EntityComparator();
	}

	public static EntityComparator getInstance() {
		return EntityComparatorHolder.instance;
	}

	@Override
	public int compare(Idable entity, Idable nextEntity) {

		int compareResult = 0;

		entity.setDynCompareValue();
		nextEntity.setDynCompareValue();

		if (entity == null || nextEntity == null) {
			compareResult = 0;
		} else if (entity.getDynCompareField() == null) {
			compareResult = 0;
		} else if (nextEntity.getDynCompareField() == null) {
			compareResult = -1;
		} else {
			if(entity.getDynCompareField() instanceof Number) {
				Double valueOfEntity = Double.valueOf(String.valueOf(entity.getDynCompareField()));
				Double valueOfNextEntity = Double.valueOf(String.valueOf(nextEntity.getDynCompareField()));
				compareResult = NumberUtils.compare(valueOfEntity, valueOfNextEntity);
			} else if(entity.getDynCompareField() instanceof  Date) {
				Date date = (Date)entity.getDynCompareField();
				Date nextDate = (Date)nextEntity.getDynCompareField();
				
				if(date.before(nextDate)){
					compareResult = -1;
				} else {
					compareResult = 1;
				}
			} else {
				compareResult = String.valueOf(entity.getDynCompareField()).compareTo(
						String.valueOf(nextEntity.getDynCompareField()));
			}
		}

		return compareResult;
	}
}
