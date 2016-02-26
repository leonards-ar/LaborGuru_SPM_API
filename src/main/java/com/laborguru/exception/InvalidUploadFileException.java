package com.laborguru.exception;

/**
 * This exception is thrown when processing an invalid file 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class InvalidUploadFileException extends SpmUncheckedException {

	private static final long serialVersionUID = 1L;

	public InvalidUploadFileException(String reason){
		this(reason, ErrorEnum.INVALID_UPLOAD_FILE);
	}
	
	public InvalidUploadFileException(Throwable t, String reason){
		super(t, reason, ErrorEnum.INVALID_UPLOAD_FILE);
	}
	
	
	public InvalidUploadFileException(String reason, String[] arrayObjects){
		super(reason, ErrorEnum.INVALID_UPLOAD_FILE, arrayObjects);
	}
	
	public InvalidUploadFileException(String reason, ErrorEnum errorCode) {
		super(reason, errorCode);
	}
}
