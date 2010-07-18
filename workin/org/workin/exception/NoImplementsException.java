package org.workin.exception;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NoImplementsException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 6081536330445090985L;
	
	public NoImplementsException() {
		super();
	}
	
	public NoImplementsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoImplementsException(String message) {
		super(message);
	}

	public NoImplementsException(Throwable cause) {
		super(cause);
	}
}
