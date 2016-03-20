package com.laborguru.exception;


/**
 * SpmCheckedException
 * This is the base checked exception, should be extended in order to throw specific error.
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SpmCheckedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private ErrorMessage errorMessage;
	private ErrorEnum errorCode;

	/**
	 * SpmUncheckedException constructor 
	 * @param t The exception cause.
	 * @param reason Internal description for the exception
	 * @param errorCode Error code
	 */
	public SpmCheckedException(Throwable t, String reason, ErrorEnum errorCode){
		super(reason, t);
		this.errorMessage = new ErrorMessage(errorCode.name());
	}
	
	/**
	 * SpmUncheckedException constructor
	 * @param reason Internal description for the exception
	 * @param errorCode Error code
	 */
	public SpmCheckedException(String reason, ErrorEnum errorCode){
		super(reason);
		this.errorMessage = new ErrorMessage(errorCode.name());
	}

	/**
	 * SpmUncheckedException constructor
	 * @param t The exception cause.
	 * @param reason Internal description for the exception
	 * @param errorCode Error code
	 * @param arrayObjects array of additional parameter objects
	 */
	public SpmCheckedException(Throwable t, String reason, ErrorEnum errorCode, String[] arrayObjects){
		super(reason, t);
		this.errorMessage = new ErrorMessage(errorCode.name(), arrayObjects);
	}
	
	/**
	 * @param reason Internal description for the exception
	 * @param errorCode ErrorCode
	 * @param arrayObjects array of additional parameter objects
	 */
	public SpmCheckedException(String reason, ErrorEnum errorCode, String[] arrayObjects){
		super(reason);
		this.errorMessage = new ErrorMessage(errorCode.name(), arrayObjects);
	}	
	
	/**
	 * Returns the error code
	 * @return the Error Code
	 */
	public ErrorEnum getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Set the errorCode
	 * @param errorCode the errorCode
	 */
	public void setErrorCode(ErrorEnum errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Returns the error message
	 * @return the error message
	 */
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Set the error message
	 * @param errorMessage  the error message
	 */
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
