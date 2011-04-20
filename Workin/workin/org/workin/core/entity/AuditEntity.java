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
@MappedSuperclass
@EntityListeners(AuditEventProvider.class)
public abstract class AuditEntity extends IdEntity implements Auditable {

	private static final long serialVersionUID = 1397834378562427109L;

	// Auto build create by whom
	private String createBy;

	// Auto build create at what time
	private Date createdttm;

	// Auto build update by whom
	private String updateBy;

	// Auto build update at what time
	private Date updatedttm;

	@Override
	@Column(length = 30, updatable = false)
	public Date getCreatedttm() {
		return createdttm;
	}

	@Override
	public void setCreatedttm(Date createdttm) {
		this.createdttm = createdttm;
	}

	@Override
	@Column(length = 20, updatable = false)
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	@Column(length = 30, insertable = false)
	public Date getUpdatedttm() {
		return updatedttm;
	}

	@Override
	public void setUpdatedttm(Date updatedttm) {
		this.updatedttm = updatedttm;
	}

	@Override
	@Column(length = 20, insertable = false)
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
