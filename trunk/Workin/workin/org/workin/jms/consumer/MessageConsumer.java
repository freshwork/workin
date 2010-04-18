package org.workin.jms.consumer;

import org.workin.mail.MailPackage;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.domain.StoredLog;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface MessageConsumer {
	
	public void receiveMessage(final MailPackage message);
	
	public void receiveMessage(final BehaviorPerformance message);
	
	public void receiveMessage(final StoredLog message);
	
}
