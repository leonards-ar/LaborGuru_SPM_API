package com.laborguru.model.service;

import com.laborguru.model.UploadFile;

public class UploadFileProcessed {

	private UploadFile uploadFile;
	private int numberOfRecordsAdded;
	private int numberOfRecordsWithErrors;
	
	/**
	 * @return the uploadFile
	 */
	public UploadFile getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * @return the numberOfRecordsAdded
	 */
	public int getNumberOfRecordsAdded() {
		return numberOfRecordsAdded;
	}
	/**
	 * @param numberOfRecordsAdded the numberOfRecordsAdded to set
	 */
	public void setNumberOfRecordsAdded(int numberOfRecordsAdded) {
		this.numberOfRecordsAdded = numberOfRecordsAdded;
	}
	/**
	 * @return the numberOfRecordsWithErrors
	 */
	public int getNumberOfRecordsWithErrors() {
		return numberOfRecordsWithErrors;
	}
	/**
	 * @param numberOfRecordsWithErrors the numberOfRecordsWithErrors to set
	 */
	public void setNumberOfRecordsWithErrors(int numberOfRecordsWithErrors) {
		this.numberOfRecordsWithErrors = numberOfRecordsWithErrors;
	}
	
	
}
