package org.workin.trace.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.workin.core.entity.IdEntity;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@Entity
@Table(name="StoredLog")
public class StoredLog extends IdEntity {

	private static final long serialVersionUID = -5765589801658858248L;
	
	private Date logdttm;
	
	private String logLevel;
	
	private String whereClass;
	
	private String userMessage;
	
	private String throwAbleMessage;

	public Date getLogdttm() {
		return logdttm;
	}

	public void setLogdttm(Date logdttm) {
		this.logdttm = logdttm;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getWhereClass() {
		return whereClass;
	}

	public void setWhereClass(String whereClass) {
		this.whereClass = whereClass;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getThrowAbleMessage() {
		return throwAbleMessage;
	}

	public void setThrowAbleMessage(String throwAbleMessage) {
		this.throwAbleMessage = throwAbleMessage;
	}
}
