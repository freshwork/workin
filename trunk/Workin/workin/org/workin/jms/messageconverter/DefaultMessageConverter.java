package org.workin.jms.messageconverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.workin.exception.ThrowableHandle;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class DefaultMessageConverter implements MessageConverter {
	/**
	 * 
	 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
	 *
	 */
	public enum MessageStoredKeys {
		POJO, BYTESMAP;
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		logger.debug("Begin method of toMessage(Object object, Session session).");

		ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage) session.createObjectMessage();
		HashMap<String, byte[]> bytesMap = new HashMap<String, byte[]>();

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(object);
			bytesMap.put(MessageStoredKeys.POJO.toString(), bos.toByteArray());
			objectMessage.setObjectProperty(MessageStoredKeys.BYTESMAP.toString(), bytesMap);

		} catch (IOException ex) {
			ThrowableHandle.handleThrow("Hit IOException,When execute toMessage(...)", ex, logger);
		}

		return objectMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		logger.debug("Begin method of fromMessage(Message message).");

		if (message instanceof ObjectMessage) {

			HashMap<String, byte[]> bytesMap = (HashMap<String, byte[]>) ((ObjectMessage) message)
					.getObjectProperty(MessageStoredKeys.BYTESMAP.toString());
			
			Object returnObject = new Object();
			
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(bytesMap.get(MessageStoredKeys.POJO.toString()));
				ObjectInputStream ois = new ObjectInputStream(bis);
				returnObject = ois.readObject();
				return returnObject;
			} catch (IOException e) {
				ThrowableHandle.handleThrow("Hit IOException,When execute fromMessage(...)", e, logger);
			} catch (ClassNotFoundException e) {
				ThrowableHandle.handleThrow("Hit ClassNotFoundException,When execute fromMessage(...)", e, logger);
			}

			return returnObject;
		} else {
			throw new JMSException("Message:[" + message + "] is not bytesMap");
		}
	}

	private transient static final Logger logger = LoggerFactory.getLogger(DefaultMessageConverter.class);

}
