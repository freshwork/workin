package org.workin.notify;

import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MailNotifier implements Serializable {
	
	private static final long serialVersionUID = -2842876987886708750L;
	
	private String name;
	
	private String email;
	
	public MailNotifier() {
	}
	
	public MailNotifier(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
