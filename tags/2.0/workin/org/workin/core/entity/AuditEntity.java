package org.workin.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.workin.core.entity.support.AuditEventProvider;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditEventProvider.class)
public abstract class AuditEntity extends IdEntity implements Auditable {
	
	// Auto build create by who
	private String createBy;
	
	// Auto build create at what time
	private Date createdttm;

	// Auto build update by who
	private String updateBy;
	
	// Auto build update at what time
	private Date updatedttm;
	
	
	@Column(length = 30, updatable = false)
	public Date getCreatedttm() {
		return createdttm;
	}

	public void setCreatedttm(Date createdttm) {
		this.createdttm = createdttm;
	}
	
	@Column(length = 20, updatable = false)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@Column(length = 30, insertable = false)
	public Date getUpdatedttm() {
		return updatedttm;
	}

	public void setUpdatedttm(Date updatedttm) {
		this.updatedttm = updatedttm;
	}
	
	@Column(length = 20, insertable = false)
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
