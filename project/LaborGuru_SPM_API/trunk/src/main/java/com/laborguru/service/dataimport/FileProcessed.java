package com.laborguru.service.dataimport;

public class FileProcessed {

	private int numberOfRecordsAdded;
	private int numberOfRecordsWithErrors;
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
