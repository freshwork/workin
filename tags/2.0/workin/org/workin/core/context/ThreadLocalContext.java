package org.workin.core.context;

import java.util.HashMap;
import java.util.Map;

import org.workin.util.CollectionUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class ThreadLocalContext implements ExtenalContext {
	
	public static final ThreadLocal context = new ThreadLocal();
	
	public Object getAttribute(String name) {
		if ( null == context.get()) {
			return null;
		}
		
		return getValue(name);
	}
	
	public void setAttribute(String name, Object value) {
		setValue(name, value);		
	}

	public Object removeAttribute(String name) {
		return remove(name);
	}
	
	public void clear() {
		context.set(null);
	}
	
	private Object getValue(String name) {
		Map values = (Map)context.get();
		if ( !CollectionUtils.isEmpty(values))
			return values.get(name);
		
		return null;
	}
	
	private Object remove(String name) {
		Map values = (Map)context.get();
		
		if ( !CollectionUtils.isEmpty(values))
			return values.remove(name);
		
		return null;
	}
	
	private void setValue(String name, Object value) {
		Map values = (Map)context.get();
		
		if (CollectionUtils.isEmpty(values))
			values = new HashMap();
			
		values.put(name, value);
		context.set(values);
	}
}
