package org.workin.test.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.workin.core.entity.AuditEntity;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@Entity
@Table(name="Address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)		
public class Address extends AuditEntity {

	private static final long serialVersionUID = 4527440954037978391L;
	
	private String home;
	
	private String work;
	
	
	private String phone;
	
	@Column(length=50)
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	
	@Column(length=50)
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	
	@Column(length=20)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
