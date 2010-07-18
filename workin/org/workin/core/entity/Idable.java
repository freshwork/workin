package org.workin.core.entity;

import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface Idable {
	
	public Long getId();
	
	public void setId(Long id);
	
	public Serializable getDynCompareField();
	
	public void setDynCompareField(Serializable dynCompareField);
	
	public void setDynCompareValue();
	
	public long getVersion();
	
	public void setVersion(long version);
}
