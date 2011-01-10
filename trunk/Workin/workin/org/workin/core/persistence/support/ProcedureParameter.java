package org.workin.core.persistence.support;

import java.sql.Types;

public class ProcedureParameter {

	public static final int IN = 1;
	public static final int OUT = 2;
	public static final int INOUT = 3;

	//parameter name
	private String paramName;
	//parameter value
	private Object paramValue;
	//If parameter is in parameter, or out parameter 
	private int inOut = IN;
	//parameter type
	private int paramType = Types.VARCHAR;

	/**
	 * @param paramName
	 * @param paramValue
	 * @param inOut
	 * @param paramType
	 */
	public ProcedureParameter(String paramName, Object paramValue, int inOut, int paramType) {
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.inOut = inOut;
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}

	public int getInOut() {
		return inOut;
	}

	public void setInOut(int inOut) {
		this.inOut = inOut;
	}

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}

}
