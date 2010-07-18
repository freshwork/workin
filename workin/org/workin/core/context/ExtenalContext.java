package org.workin.core.context;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface ExtenalContext {
	
	/**
	 * 
	 * Get context by attribute.
	 * 
	 * @param name
	 * @return
	 * 
	 */
	public Object getAttribute(String name);
	
	/**
	 * 
	 * Set context by attribute name and value.
	 * 
	 * @param name
	 * @param value
	 * 
	 */
	public void setAttribute(String name, Object value);
	
	/**
	 * 
	 * Remove context by attribute name.
	 * 
	 * @param name
	 * @return
	 * 
	 */
	public Object removeAttribute(String name);	
	
	/**
	 * 
	 * Clear all context.
	 * 
	 */
	public void clear() ;
}
