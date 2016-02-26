package com.laborguru.action.sales;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.frontend.model.UploadFileElement;
import com.laborguru.model.UploadFile;
import com.laborguru.model.service.UploadFileProcessed;
import com.laborguru.service.dataimport.csv.SalesFileProcessorService;
import com.laborguru.service.uploadfile.UploadEnumType;
import com.laborguru.service.uploadfile.UploadFileService;

public class UploadPrepareAction extends SpmAction {

	private static final long serialVersionUID = 1L;

	//The 3 variables below, are declared following the convention defined by UploadFileInterceptor (Strust2 object that handles a file upload).
	//Don't change the names without look the documentation in struts2 for File Uploads.
	private File salesFile;
	private String salesFileContentType;
	private String salesFileFileName;
	
	private Integer storeId;	
	
	private Integer numberOfRecordsAdded = 0;
	private Integer numberOfRecordsWithError = 0;

	private Long uploadFileId;
	private boolean uploadFileRemoved;
	private String salesFileRemovedName;
	
	//The class UploadFileElement holds an UploadFile and the number of historic sales record for the upload file.
	//We use this wrapper to avoid calling size() on the collection, since that call will initilize the intire collection of historic sales
	//for the file. The only info what we need is the number of historic sales, so there is no point to initialize the collection (imagine a file
	//with more than 100000 records). 
	private UploadFileElement uploadFileSelected;	
	private List<UploadFileElement> uploadFileList;
	
	private SalesFileProcessorService salesFileProcessorService;
	private UploadFileService uploadFileService;
	
	
	public String edit(){
		getUploadFileList().clear();
		
		List<UploadFile> auxList = uploadFileService.findUploadFilesByType(UploadEnumType.FILE);
		
		for (UploadFile uploadFile : auxList){
			//Gets the size for the collection without initialize the collection
			Integer hsSize = getUploadFileService().getHistoricSalesSize(uploadFile);
			UploadFileElement aFrontEndUpload = new UploadFileElement(uploadFile, hsSize);
			getUploadFileList().add(aFrontEndUpload);
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	
	public String upload(){
		
		UploadFile auxUpload = new UploadFile();
		auxUpload.setFilename(salesFileFileName);
		try{
			UploadFileProcessed retSales = salesFileProcessorService.processAndSaveFile(salesFile, auxUpload);
			setNumberOfRecordsAdded(retSales.getNumberOfRecordsAdded());
			setNumberOfRecordsWithError(retSales.getNumberOfRecordsWithErrors());

		} 
		catch (SpmUncheckedException e){
			ErrorMessage errorMessage = new ErrorMessage("error.upload.salesFile", new String[] {salesFileFileName});
			this.addActionError(errorMessage);
			clearDisplay();
		}
		
		return edit();
	}
	
	
	private void clearDisplay() {
		setNumberOfRecordsAdded(0);
		setNumberOfRecordsWithError(0);
	}

	public String cancel(){
		return SpmActionResult.CANCEL_EDIT.getResult();
	}

	public String remove(){
		UploadFile auxUpload = new UploadFile();
		auxUpload.setId(getUploadFileId());
		
		UploadFile uploadFileToRemove =  uploadFileService.getUploadFileById(auxUpload);
		
		//Gets the size for the collection without initialize the collection
		Integer hsSize = uploadFileService.getHistoricSalesSize(uploadFileToRemove);
		
		UploadFileElement anElement = new UploadFileElement(uploadFileToRemove, hsSize);
		setUploadFileSelected(anElement);
		
		return SpmActionResult.REMOVE.getResult();
	}	

	public String delete(){
		
		UploadFile auxUpload = new UploadFile();
		auxUpload.setId(getUploadFileId());
		UploadFile uploadFileRemoved =  uploadFileService.delete(auxUpload);
		setUploadFileRemoved(true);
		setSalesFileRemovedName(uploadFileRemoved.getFilename());
		
		return edit();
	}	
	
	/**
	 * @return the storeId
	 */
	public Integer getStoreId() {
		return storeId;
	}
	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	/**
	 * @return the salesFile
	 */
	public File getSalesFile() {
		return salesFile;
	}
	/**
	 * @param salesFile the salesFile to set
	 */
	public void setSalesFile(File salesFile) {
		this.salesFile = salesFile;
	}
	/**
	 * @return the salesFileContentType
	 */
	public String getSalesFileContentType() {
		return salesFileContentType;
	}
	/**
	 * @param salesFileContentType the salesFileContentType to set
	 */
	public void setSalesFileContentType(String salesFileContentType) {
		this.salesFileContentType = salesFileContentType;
	}
	/**
	 * @return the salesFileFileName
	 */
	public String getSalesFileFileName() {
		return salesFileFileName;
	}
	/**
	 * @param salesFileFileName the salesFileFileName to set
	 */
	public void setSalesFileFileName(String salesFileFileName) {
		this.salesFileFileName = salesFileFileName;
	}
	
	/**
	 * @return the numberOfRecordsAdded
	 */
	public Integer getNumberOfRecordsAdded() {
		return numberOfRecordsAdded;
	}

	/**
	 * @param retSales the numberOfRecordsAdded to set
	 */
	public void setNumberOfRecordsAdded(Integer retSales) {
		this.numberOfRecordsAdded = retSales;
	}

	/**
	 * @return the salesFileProcessor
	 */
	public SalesFileProcessorService getSalesFileProcessorService() {
		return salesFileProcessorService;
	}

	/**
	 * @param salesFileProcessor the salesFileProcessor to set
	 */
	public void setSalesFileProcessorService(SalesFileProcessorService salesFileProcessor) {
		this.salesFileProcessorService = salesFileProcessor;
	}

	/**
	 * @return the uploadFileService
	 */
	public UploadFileService getUploadFileService() {
		return uploadFileService;
	}

	/**
	 * @param uploadFileService the uploadFileService to set
	 */
	public void setUploadFileService(UploadFileService uploadFileService) {
		this.uploadFileService = uploadFileService;
	}

	/**
	 * @return the uploadFileList
	 */
	public List<UploadFileElement> getUploadFileList() {
		if (uploadFileList == null){
			uploadFileList = new ArrayList<UploadFileElement>();
		}
		return uploadFileList;
	}

	/**
	 * @param uploadFileList the uploadFileList to set
	 */
	public void setUploadFileList(List<UploadFileElement> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	/**
	 * @return the uploadFileId
	 */
	public Long getUploadFileId() {
		return uploadFileId;
	}

	/**
	 * @param uploadFileId the uploadFileId to set
	 */
	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	/**
	 * @return the uploadFileRemoved
	 */
	public boolean isUploadFileRemoved() {
		return uploadFileRemoved;
	}

	/**
	 * @param uploadFileRemoved the uploadFileRemoved to set
	 */
	public void setUploadFileRemoved(boolean uploadFileRemoved) {
		this.uploadFileRemoved = uploadFileRemoved;
	}

	/**
	 * @return the salesFileRemovedName
	 */
	public String getSalesFileRemovedName() {
		return salesFileRemovedName;
	}

	/**
	 * @param salesFileRemovedName the salesFileRemovedName to set
	 */
	public void setSalesFileRemovedName(String salesFileRemovedName) {
		this.salesFileRemovedName = salesFileRemovedName;
	}

	/**
	 * @return the uploadFileSelected
	 */
	public UploadFileElement getUploadFileSelected() {
		return uploadFileSelected;
	}

	/**
	 * @param uploadFileSelected the uploadFileSelected to set
	 */
	public void setUploadFileSelected(UploadFileElement uploadFileSelected) {
		this.uploadFileSelected = uploadFileSelected;
	}

	/**
	 * @return the numberOfRecordsWithError
	 */
	public Integer getNumberOfRecordsWithError() {
		return numberOfRecordsWithError;
	}

	/**
	 * @param numberOfRecordsWithError the numberOfRecordsWithError to set
	 */
	public void setNumberOfRecordsWithError(Integer numberOfRecordsWithError) {
		this.numberOfRecordsWithError = numberOfRecordsWithError;
	}
	
}
