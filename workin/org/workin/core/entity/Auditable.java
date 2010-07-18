package org.workin.core.entity;

import java.util.Date;

/**
 * 
 * This is a mark interface only. 
 * 
 * Allow Automatic Fill Entity field values for audi 
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface Auditable extends Idable {
	
	public static final String CREATE_BY = "createBy";
	
	public static final String CREATE_DTTM = "createdttm";
	
	public static final String UPDATE_BY = "updateby";
	
	public static final String UPDATE_DTTM = "updatedttm";
	

	
	public Date getCreatedttm();
	
	public void setCreatedttm(Date createdttm);

	public String getCreateBy();

	public void setCreateBy(String createBy);
	
	public void setUpdatedttm(Date updatedttm);

	public Date getUpdatedttm();

	public void setUpdateBy(String updateBy);

	public String getUpdateBy();
}
