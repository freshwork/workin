package org.workin.ws.result;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;
import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlType(name = "WSResult", namespace = WSConstants.NS)
public class WSResult implements Serializable {

	private static final long serialVersionUID = 1080443883144025884L;
	
	private String message;
	private String code = SUCCESS;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(String resultCode, String resultMessage) {
		this.code = resultCode;
		this.message = resultMessage;
	}

	public void setSystemError() {
		setResult(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE);
	}
	
	public static final String SUCCESS = "0";
	public static final String IMAGE_ERROR = "201";
	public static final String SYSTEM_ERROR = "300";

	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknow internal error.";
}
