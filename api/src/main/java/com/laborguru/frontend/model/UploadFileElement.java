package com.laborguru.frontend.model;

import java.util.Date;

import com.laborguru.model.UploadFile;

/**
 * Front end wrapper for UploadFile
 * 
 * Holds an UploadFile and the number of historic sales records for the upload file.
 * We use this wrapper to avoid calling size() on the collection, since that call will initilize the intire collection of historic sales
 * for the file. The only info what we need is the number of historic sales, so there is no point to initialize the collection 
 * (imagine a file with more than 100000 records). 
 * @see com.laborguru.service.uploadfile.UploadFileService#getHistoricSalesSize(com.laborguru.model.UploadFile)
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UploadFileElement {

	UploadFile uploadFile;
	Integer historicSalesSize;
	
	/**
	 * @return the uploadFile
	 */
	public UploadFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * @return the historicSalesSize
	 */
	public Integer getHistoricSalesSize() {
		return historicSalesSize;
	}

	/**
	 * @param historicSalesSize
	 * @param uploadFile
	 */
	public UploadFileElement(UploadFile uploadFile, Integer historicSalesSize) {
		super();
		
		if (uploadFile == null){
			throw new IllegalArgumentException("uploadFile cannot be null.");
		}
		
		this.historicSalesSize = historicSalesSize;
		this.uploadFile = uploadFile;
	}
	
	public Date getUploadDate(){
		return getUploadFile().getUploadDate();
	}
	
	public String getFilename(){
		return getUploadFile().getFilename();
	}
	
	public Long getId(){
		return getUploadFile().getId();
	}	
}
