package com.laborguru.exception;

/**
 * This exception is thrown when processing an invalid file 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class InvalidFieldUploadFileException extends SpmUncheckedException {

	private static final long serialVersionUID = 1L;

	public InvalidFieldUploadFileException(String reason){
		this(reason, ErrorEnum.INVALID_FIELD_IN_FILE_TO_UPLOAD);
	}
	
	public InvalidFieldUploadFileException(Throwable t, String reason){
		super(t, reason, ErrorEnum.INVALID_FIELD_IN_FILE_TO_UPLOAD);
	}
	
	
	public InvalidFieldUploadFileException(String reason, String[] arrayObjects){
		super(reason, ErrorEnum.INVALID_FIELD_IN_FILE_TO_UPLOAD, arrayObjects);
	}
	
	private InvalidFieldUploadFileException(String reason, ErrorEnum errorCode) {
		super(reason, errorCode);
	}

	public InvalidFieldUploadFileException(String reason, ErrorEnum errorCode, String[] arrayObjects){
		super(reason, errorCode, arrayObjects);
	}

}
