package com.laborguru.action.store;

import java.io.File;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.Store;
import com.laborguru.service.store.file.UploadStoreDefinitionService;

public class UploadDefinitionPrepareAction extends SpmAction{

	private static final long serialVersionUID = 1L;

	//The 3 variables below, are declared following the convention defined by UploadFileInterceptor (Strust2 object that handles a file upload).
	//Don't change the names without look the documentation in struts2 for File Uploads.
	private File storeDefinition;
	private String storeDefinitionContentType;
	private String storeDefinitionFileName;
	
	private UploadStoreDefinitionService uploadStoreDefinitionService;
	
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
		
		try{		
			Store store = uploadStoreDefinitionService.processStoreDefinitionAndSave(storeDefinition);
			this.addActionMessage(getText("store.storeDefinition.file.upload.success", new String[]{store.getName()}));		
		}catch(InvalidFieldUploadFileException invalidFieldException){
			ErrorMessage errorMessage = new ErrorMessage("error.store.storeDefinition.file", new String[] {storeDefinitionFileName});
			this.addActionError(errorMessage);
			this.addActionError(invalidFieldException.getErrorMessage());
		}catch(InvalidUploadFileException invalidUploadException){
			this.addActionError(invalidUploadException.getErrorMessage());
		}		
		catch (SpmUncheckedException e){
			ErrorMessage errorMessage = new ErrorMessage("error.store.storeDefinition.file", new String[] {storeDefinitionFileName});
			this.addActionError(errorMessage);
		}			
		return SpmActionResult.EDIT.getResult();
	}	
	
	/**
	 * @return
	 */
	public String cancel(){
		return SpmActionResult.CANCEL.getResult();
	}
	
	/**
	 * @return the storeDefinition
	 */
	public File getStoreDefinition() {
		return storeDefinition;
	}


	/**
	 * @param storeDefinition the storeDefinition to set
	 */
	public void setStoreDefinition(File storeDefinition) {
		this.storeDefinition = storeDefinition;
	}


	/**
	 * @return the storeDefinitionContentType
	 */
	public String getStoreDefinitionContentType() {
		return storeDefinitionContentType;
	}


	/**
	 * @param storeDefinitionContentType the storeDefinitionContentType to set
	 */
	public void setStoreDefinitionContentType(String storeDefinitionContentType) {
		this.storeDefinitionContentType = storeDefinitionContentType;
	}


	/**
	 * @return the storeDefinitionFileName
	 */
	public String getStoreDefinitionFileName() {
		return storeDefinitionFileName;
	}


	/**
	 * @param storeDefinitionFileName the storeDefinitionFileName to set
	 */
	public void setStoreDefinitionFileName(String storeDefinitionFileName) {
		this.storeDefinitionFileName = storeDefinitionFileName;
	}

	/**
	 * @return the uploadStoreDefinitionService
	 */
	public UploadStoreDefinitionService getUploadStoreDefinitionService() {
		return uploadStoreDefinitionService;
	}

	/**
	 * @param uploadStoreDefinitionService the uploadStoreDefinitionService to set
	 */
	public void setUploadStoreDefinitionService(UploadStoreDefinitionService uploadStoreDefinitionService) {
		this.uploadStoreDefinitionService = uploadStoreDefinitionService;
	}
}
