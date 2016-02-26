package com.laborguru.exception;


/**
 * Placeholder for Error messages. 
 * MessageKey contains the i18n error message we want to display
 * arrayParameters[] parameters passed to the message
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ErrorMessage {
	
	private String messageKey;
	private String[] parameters;
	private int row;
	
	/**
	 * Constructor
	 * 
	 * @param message The message key
	 * @param arrayObject The parameters to pass.
	 */
	public ErrorMessage(ErrorEnum error, String[] arrayParameters, int row) {
		this(error.name(), arrayParameters);
		setRow(row);
	}
	
	/**
	 * Constructor
	 * 
	 * @param message The message key
	 * @param arrayObject The parameters to pass.
	 */
	public ErrorMessage(String message, String[] arrayParameters) {
		super();
		this.messageKey = message;
		this.parameters = arrayParameters;
	}
	
	/**
	 * Constructor
	 * @param message The message key
	 */
	public ErrorMessage(String message) {
		super();
		this.messageKey = message;
	}
	
	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String message) {
		this.messageKey = message;
	}
	public String[] getParameters() {
		return parameters;
	}
	public void setParameters(String[] arrayParameters) {
		this.parameters = arrayParameters;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}	
}
