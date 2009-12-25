package org.workin.mail;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface MailService {
	
	/**
	 * 
	 * Send mail. 
	 * 
	 * @param userName
	 */
	public void sendMail(final String userName);
	
	/**
	 * 
	 * Send mail to pass users. 
	 * 
	 * @param userName
	 * @param send2s
	 * 
	 */
	public void sendMail(final String userName, final String... send2s);
	
}
