package org.workin.trace.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.workin.core.entity.IdEntity;


@Entity
@Table(name="BehaviorPerformance")
public class BehaviorPerformance extends IdEntity {

	private static final long serialVersionUID = -8831422713756416545L;
	
	private long userId;
	
	private long spentTime;
	
	private String userName;
	
	private String requestIp;
	
	private String requestURI;
	
	private Date requestdttm;
	
	private Date responsedttm;
	
	
	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public long getSpentTime() {
		return spentTime;
	}


	public void setSpentTime(long spentTime) {
		this.spentTime = spentTime;
	}


	public String getRequestIp() {
		return requestIp;
	}


	public Date getRequestdttm() {
		return requestdttm;
	}


	public void setRequestdttm(Date requestdttm) {
		this.requestdttm = requestdttm;
	}


	public Date getResponsedttm() {
		return responsedttm;
	}


	public void setResponsedttm(Date responsedttm) {
		this.responsedttm = responsedttm;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}


	public String getRequestURI() {
		return requestURI;
	}


	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public void setDynCompareValue() {
		super.setDynCompareField(requestdttm);
	}
}
