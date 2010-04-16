package org.workin.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageListenerTemplate implements MessageListener {
	
	public abstract void onMessage(Message message);
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}

