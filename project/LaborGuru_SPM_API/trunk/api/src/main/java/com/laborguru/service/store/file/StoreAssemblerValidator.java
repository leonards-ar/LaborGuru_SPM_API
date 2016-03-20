package com.laborguru.service.store.file;


import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.InvalidFieldUploadFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreAssemblerValidator {

	private static final Logger log = LoggerFactory.getLogger(StoreAssembler.class);
	
	private StoreAssemblerValidator(){
	}

	
	/**
	 * Check the string passed as parameter is not empty
	 * @param field
	 * @return
	 */
	public static void validateRequiredField(String field, String fieldName){
		if ((field == null) || (field.equals(""))){
			String message = fieldName+" is invalid - "+fieldName+" is empty or null";
			log.error(message);
			throw new InvalidFieldUploadFileException(message, ErrorEnum.REQUIRED_FIELD_IN_FILE_TO_UPLOAD, new String[]{fieldName});	
		}
	}
}
