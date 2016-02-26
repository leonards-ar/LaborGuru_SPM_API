package com.laborguru.service.dataimport.csv;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;

import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.UploadFile;
import com.laborguru.model.service.UploadFileProcessed;
import com.laborguru.service.dao.SpmDaoUtils;
import com.laborguru.service.historicsales.HistoricSalesService;
import com.laborguru.service.uploadfile.UploadEnumType;
import com.laborguru.service.uploadfile.dao.UploadFileDao;

/**
 * This class import sales data from a CSV file.
 * Process the file, build the HistoricSales objects and persit them in the database.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SalesCSVFileProcessorBean implements SalesFileProcessorService {

	private static final Logger log = Logger.getLogger(SalesCSVFileProcessorBean.class);

	private SalesFileParser fileParser;
	private HistoricSalesService historicSalesService;
	private UploadFileDao uploadFileDao;
	private SpmDaoUtils spmDaoUtils;
	
	/**
	 * This method process the file passed as parameter, creates and persist an Upload File entity and the sales records contained in the file.
	 * @param file
	 * @param uploadFile: Optional helper object used to pass information to the method. The values defined in the object are going to be used. 
	 * If null method defaults are used. 
	 * @return The persisted upload file information
	 * @see com.laborguru.service.dataimport.csv.SalesFileProcessorService#processAndSaveFile(java.io.File)
	 */
	public UploadFileProcessed processAndSaveFile(File file, UploadFile uploadFile) {

		UploadFile uploadToSave = null;
		int numberOfErrors = 0;
		int validLines = 0;
		
		try{
			fileParser.assembleSalesFileParser(file, 1);
			
			//Setting default values for Upload File
			String filename = file.getName();
			Date uploadDate = new Date();
			
			uploadToSave = new UploadFile(); 
			
			uploadToSave.setFilename(filename);
			uploadToSave.setUploadDate(uploadDate);
			uploadToSave.setUploadType(UploadEnumType.FILE);
			
			//Setting the uploadFile parameter values if present
			if (uploadFile != null){
				
				if (uploadFile.getFilename() != null){
					uploadToSave.setFilename(uploadFile.getFilename());
				}
				
				if (uploadFile.getUploadDate() != null){
					uploadToSave.setUploadDate(uploadFile.getUploadDate());
				}
				
			}
			
			if (!fileParser.isFileValid()){
				String msg = "The file " + uploadToSave.getFilename() +" passed in as parameter is not valid.";
				log.error(msg);
				throw new InvalidUploadFileException(msg);
			}
			
	
			HistoricSales historicSales = null;
			
			historicSales = fileParser.getNextRecord();

			//If we find at least one historicSales to persist
			//We persist the upload File instance first, so the historic sales are associated with the upload file		
			if (historicSales != null){
				uploadFileDao.saveOrUpdate(uploadToSave);			
			}
			
			while(historicSales != null){						
				historicSales.setUploadFile(uploadToSave);			
				getHistoricSalesService().createOrReplace(historicSales);	

				//We persist the historic sales. To keep a light session and to improve the performace of the operation
				//every 20 records we flush the session.
				if (fileParser.getValidLinesCounter() % 20 == 0){
					spmDaoUtils.flushSession();
					spmDaoUtils.clearSession();
				}
							
				historicSales = fileParser.getNextRecord();			
			}
			
			validLines = fileParser.getValidLinesCounter();

			//If there was no valid lines we reject the uplod
			if (validLines == 0){
				String msg = "The file " + uploadToSave.getFilename() +" is not valid. There were not valid lines in the file.";
				log.error(msg);
				throw new InvalidUploadFileException(msg);	
			}
			
			numberOfErrors = fileParser.getErrorLinesCounter();

			//Refreshing the uploadFile into the hibernate session
			UploadFile retUploadFile = uploadFileDao.getUploadFileById(uploadToSave);
			
			UploadFileProcessed response = new UploadFileProcessed();
			response.setUploadFile(retUploadFile);
			response.setNumberOfRecordsAdded(validLines);
			response.setNumberOfRecordsWithErrors(numberOfErrors);
			
			return response;			
		} finally {
			fileParser.close();
		}
	}

	/**
	 * @param fileParser
	 * @see com.laborguru.service.dataimport.csv.SalesFileProcessorService#setFileParser(com.laborguru.service.dataimport.csv.SalesFileParser)
	 */
	public void setFileParser(SalesFileParser fileParser) {
		this.fileParser = fileParser;
	}

	/**
	 * @return the spmDaoUtils
	 */
	public SpmDaoUtils getSpmDaoUtils() {
		return spmDaoUtils;
	}

	/**
	 * @param spmDaoUtils the spmDaoUtils to set
	 */
	public void setSpmDaoUtils(SpmDaoUtils spmDaoUtils) {
		this.spmDaoUtils = spmDaoUtils;
	}

	/**
	 * @return the fileParser
	 */
	public SalesFileParser getFileParser() {
		return fileParser;
	}

	/**
	 * @return the uploadFileDao
	 */
	public UploadFileDao getUploadFileDao() {
		return uploadFileDao;
	}

	/**
	 * @param uploadFileDao the uploadFileDao to set
	 */
	public void setUploadFileDao(UploadFileDao uploadFileDao) {
		this.uploadFileDao = uploadFileDao;
	}

	/**
	 * @return the historicSalesSevice
	 */
	public HistoricSalesService getHistoricSalesService() {
		return historicSalesService;
	}

	/**
	 * @param historicSalesSevice the historicSalesSevice to set
	 */
	public void setHistoricSalesService(HistoricSalesService historicSalesService) {
		this.historicSalesService = historicSalesService;
	}

}
