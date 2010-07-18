package org.workin.test.ws.soap.service;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlRootElement(name="Employee")
public class Employee {
	
	private long id;
	
	private String name;
	
	private Date birthday;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
