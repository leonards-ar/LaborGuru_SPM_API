package com.laborguru.service.dataimport.actualhours.csv;

import java.io.File;

import org.apache.log4j.Logger;

import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.model.ActualHours;
import com.laborguru.service.actualhours.ActualHoursService;
import com.laborguru.service.dataimport.FileProcessed;
import com.laborguru.service.dataimport.UploadFileInfo;
import com.laborguru.service.dataimport.actualhours.ActualHoursFileParser;
import com.laborguru.service.dataimport.actualhours.ActualHoursFileProcessor;

 
/**
 * This class import sales data from a CSV file.
 * Process the file, build the HistoricSales objects and persit them in the database.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ActualHoursFileProcessorBean implements ActualHoursFileProcessor {

	private static final Logger log = Logger.getLogger(ActualHoursFileProcessorBean.class);

	private ActualHoursFileParser fileParser;
	private ActualHoursService actualHoursService;
	
	/**
	 * This method process the file passed as parameter, creates and persist the entities contained by the file.
	 * @param file
	 * @param uploadFileInfo 
	 * @return The persisted upload file information
	 */
	public FileProcessed processAndSaveFile(File file, UploadFileInfo uploadFileInfo) {

		int numberOfErrors = 0;
		int validLines = 0;
		
		try{
			
			if (uploadFileInfo == null){
				throw new IllegalArgumentException("file information object passed as parameter is null");
			}

			if (uploadFileInfo.getFilename() == null){
				throw new IllegalArgumentException("file name in upload file info is null");
			}

			fileParser.assembleFileParser(file, 1);
						
			if (!fileParser.isFileValid()){
				String msg = "The file " + uploadFileInfo.getFilename() +" passed in as parameter is not valid.";
				log.error(msg);
				throw new InvalidUploadFileException(msg);
			}
			
	
			ActualHours objectToPersist = null;
			
			objectToPersist = fileParser.getNextRecord();
			
			while(objectToPersist != null){						
				actualHoursService.saveOrUpdate(objectToPersist);
				validLines++;
				objectToPersist = fileParser.getNextRecord();
			}
			
			//If there was no valid lines we reject the uplod
			if (validLines == 0){
				String msg = "The file " + uploadFileInfo.getFilename() +" is not valid. There were not valid lines in the file.";
				log.error(msg);
				throw new InvalidUploadFileException(msg);	
			}
			
			numberOfErrors = fileParser.getErrorLinesCounter();

			
			FileProcessed response = new FileProcessed();
			response.setNumberOfRecordsAdded(validLines);
			response.setNumberOfRecordsWithErrors(numberOfErrors);
			
			return response;			
		} finally {
			fileParser.close();
		}
	}


	public void setFileParser(ActualHoursFileParser fileParser) {
		this.fileParser = fileParser;
	}


	/**
	 * @param actualHoursService the actualHoursService to set
	 */
	public void setActualHoursService(ActualHoursService actualHoursService) {
		this.actualHoursService = actualHoursService;
	}
}
