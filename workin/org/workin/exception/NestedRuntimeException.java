package org.workin.exception;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NestedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6506403720885785922L;
	
	public NestedRuntimeException() {
		super();
	}

	public NestedRuntimeException(String message) {
		super(message);
	}

	public NestedRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NestedRuntimeException(Throwable cause) {
		super(cause);
	}

}
