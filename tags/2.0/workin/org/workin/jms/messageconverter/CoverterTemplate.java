package org.workin.jms.messageconverter;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class CoverterTemplate implements MessageConverter {

	@Autowired
	public MessageConverter defaultMessageConverter;

	@Autowired
	public Map<String, MessageConverter> converters = new HashMap<String, MessageConverter>();

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		Assert.notNull(object, "object cannot be null, When toMessage(...) in CoverterTemplate.");
		
		Message message = null;
		String clazz = object.getClass().getName();
		
		if (converters.containsKey(clazz)) {
			message = converters.get(clazz).toMessage(object, session);
			message.setObjectProperty("meta-class", clazz);
		} else if (defaultMessageConverter != null) {
			message = defaultMessageConverter.toMessage(object, session);
			message.setObjectProperty("default-meta-class", clazz);
		}
		
		return message;
	}

	
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		Assert.notNull(message, "message cannot be null, When fromMessage(...) in CoverterTemplate.");
		
		Object object = new Object();
		
		if (message.getObjectProperty("meta-class") != null) {
			String clazz = message.getObjectProperty("meta-class").toString();
			if (converters.containsKey(clazz)) {
				object = converters.get(clazz).fromMessage(message);
			}
		} else if (defaultMessageConverter != null && message.getObjectProperty("default-meta-class") != null) {
			object = defaultMessageConverter.fromMessage(message);
		} else {
			throw new JMSException("Message:[" + message + "] is not bytesMap");
		}

		return object;
	}


	public void setDefaultMessageConverter(MessageConverter defaultMessageConverter) {
		this.defaultMessageConverter = defaultMessageConverter;
	}


	public void setConverters(Map<String, MessageConverter> converters) {
		this.converters = converters;
	}
}
