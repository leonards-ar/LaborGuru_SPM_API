package com.laborguru.service.dataimport.actualhours;

import java.io.File;

import com.laborguru.service.actualhours.ActualHoursService;
import com.laborguru.service.dataimport.FileProcessed;
import com.laborguru.service.dataimport.UploadFileInfo;


/**
 * This interface define the actual hours file processor used to import sales data to the SPM
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ActualHoursFileProcessor {
	
	/**
	 * This method process the file passed as parameter.
	 * @param file: the file to upload and save
	 * @param uploadFile: Optional helper object used to pass information to the service. 
	 * 
	 * @return The upload file entity created.
	 */
	FileProcessed processAndSaveFile(File file, UploadFileInfo uploadFileInfo);
	
	/**
	 * Sets the file parser to use when processing the file
	 * @param fileParser The file parser
	 */
	void setFileParser(ActualHoursFileParser fileParser);
	
	void setActualHoursService(ActualHoursService service);
}
