package com.laborguru.exception;

/**
 * This exception is thrown when parsing a file 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class FileParserException extends SpmUncheckedException {

	private static final long serialVersionUID = 1L;

	public FileParserException(String reason){
		this(reason, ErrorEnum.INVALID_UPLOAD_FILE);
	}
	
	public FileParserException(Throwable t, String reason){
		super(t, reason, ErrorEnum.INVALID_UPLOAD_FILE);
	}
	
	private FileParserException(String reason, ErrorEnum errorCode) {
		super(reason, errorCode);
	}
}
