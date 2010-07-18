package org.workin.ws.soap.exception;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

import org.workin.exception.NestedRuntimeException;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebFault(name = "WSException")
public class WSException extends NestedRuntimeException {

	private static final long serialVersionUID = -6225761030466053501L;
	
	private WSServiceFault details;

	public WSException(String msg) {
		super(msg);
	}

	public WSException(String msg, WSServiceFault details) {
		super(msg);
		this.details = details;
	}

	public WSServiceFault getFaultInfo() {
		return details;
	}

	@XmlRootElement(name = "WSServiceFault")
	public static class WSServiceFault {
		private String t;

		public WSServiceFault() {
		}

		public WSServiceFault(String t) {
			this.t = t;
		}

		public String getT() {
			return t;
		}

		public void setT(String t) {
			this.t = t;
		}
	}
}
