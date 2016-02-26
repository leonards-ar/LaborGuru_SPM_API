package com.laborguru.action.actualhours;

import java.io.File;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.service.dataimport.FileProcessed;
import com.laborguru.service.dataimport.UploadFileInfo;
import com.laborguru.service.dataimport.actualhours.ActualHoursFileProcessor;

public class UploadPrepareAction extends SpmAction{

	private static final long serialVersionUID = 1L;

	//The 3 variables below, are declared following the convention defined by UploadFileInterceptor (Strust2 object that handles a file upload).
	//Don't change the names without look the documentation in struts2 for File Uploads.
	private File actualHours;
	private String actualHoursContentType;
	private String actualHoursFileName;
	
	private ActualHoursFileProcessor actualHoursFileProcessor;

	/**
	 * @return
	 */
	public String edit(){
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * @return
	 */
	public String upload(){
		UploadFileInfo auxUpload = new UploadFileInfo();
		auxUpload.setFilename(getActualHoursFileName());
		try{
			FileProcessed retFile = actualHoursFileProcessor.processAndSaveFile(actualHours, auxUpload);
			int recordsAdded = retFile.getNumberOfRecordsAdded();
			int recordsFailed = retFile.getNumberOfRecordsWithErrors();
			
			if (recordsAdded > 0){
				this.addActionMessage(getText("actualhours.upload.success.message", new String[] {getActualHoursFileName()}));
				this.addActionMessage(getText("actualhours.upload.records.addded.message", new String[] {String.valueOf(recordsAdded)}));
			}

			if (recordsFailed > 0){
				this.addActionError(getText("actualhours.upload.records.failed.message", new String[] {String.valueOf(recordsFailed)}));
			}

		} 
		catch (SpmUncheckedException e){
			ErrorMessage errorMessage = new ErrorMessage("error.upload.file", new String[] {actualHoursFileName});
			this.addActionError(errorMessage);
		}
				
		return edit();
	}	
	
	/**
	 * @return
	 */
	public String cancel(){
		return SpmActionResult.CANCEL.getResult();
	}

	/**
	 * @return the actualHours
	 */
	public File getActualHours() {
		return actualHours;
	}

	/**
	 * @param actualHours the actualHours to set
	 */
	public void setActualHours(File actualHours) {
		this.actualHours = actualHours;
	}

	/**
	 * @return the actualHoursContentType
	 */
	public String getActualHoursContentType() {
		return actualHoursContentType;
	}

	/**
	 * @param actualHoursContentType the actualHoursContentType to set
	 */
	public void setActualHoursContentType(String actualHoursContentType) {
		this.actualHoursContentType = actualHoursContentType;
	}

	/**
	 * @return the actualHoursFileName
	 */
	public String getActualHoursFileName() {
		return actualHoursFileName;
	}

	/**
	 * @param actualHoursFileName the actualHoursFileName to set
	 */
	public void setActualHoursFileName(String actualHoursFileName) {
		this.actualHoursFileName = actualHoursFileName;
	}


	/**
	 * @return the actualHoursFileProcessor
	 */
	public ActualHoursFileProcessor getActualHoursFileProcessor() {
		return actualHoursFileProcessor;
	}

	/**
	 * @param actualHoursFileProcessor the actualHoursFileProcessor to set
	 */
	public void setActualHoursFileProcessor(ActualHoursFileProcessor actualHoursFileProcessor) {
		this.actualHoursFileProcessor = actualHoursFileProcessor;
	}


}
