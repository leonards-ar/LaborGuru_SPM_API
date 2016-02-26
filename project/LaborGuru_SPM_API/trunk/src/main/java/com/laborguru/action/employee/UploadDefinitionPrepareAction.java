/**
 * 
 */
package com.laborguru.action.employee;

import java.io.File;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.service.employee.file.UploadEmployeeDefinitionService;
import com.laborguru.service.employee.file.UploadEmployeesResult;

/**
 * @author mariano
 *
 */
public class UploadDefinitionPrepareAction extends SpmAction {
	
	//The 3 variables below, are declared following the convention defined by UploadFileInterceptor (Strust2 object that handles a file upload).
	//Don't change the names without look the documentation in struts2 for File Uploads.
	private File employeeDefinition;
	private String employeeDefinitionContentType;
	private String employeeDefinitionFileName;
	
	private UploadEmployeeDefinitionService uploadEmployeeDefinitionService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -478811111917025848L;

	/**
	 * 
	 */
	public UploadDefinitionPrepareAction() {
	}

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
		try {		
			UploadEmployeesResult result = getUploadEmployeeDefinitionService().processEmployeeDefinitionAndSave(getEmployeeDefinition());
			
			if(result.hasErrors()) {
				this.addActionMessage(getText("employee.upload.success.message", new String[]{getEmployeeDefinitionFileName(), String.valueOf(result.getTotal()), String.valueOf(result.getCreated()), String.valueOf(result.getUpdated())}));		
				for(ErrorMessage msg : result.getErrorMessages()) {
					String errorMsg = getText(msg.getMessageKey(), msg.getParameters());
					addActionError(getText(ErrorEnum.EMPLOYEE_INVALID_ROW.name(), new String[] {String.valueOf(msg.getRow()), errorMsg}));
				}
			} else {
				this.addActionMessage(getText("employee.upload.error.message", new String[]{getEmployeeDefinitionFileName(), String.valueOf(result.getTotal()), String.valueOf(result.getCreated()), String.valueOf(result.getUpdated()), String.valueOf(result.getError())}));		
			}
		} catch(InvalidFieldUploadFileException invalidFieldException) {
			ErrorMessage errorMessage = new ErrorMessage("error.employee.definition.file", new String[] {getEmployeeDefinitionFileName()});
			this.addActionError(errorMessage);
			this.addActionError(invalidFieldException.getErrorMessage());
		} catch(InvalidUploadFileException invalidUploadException) {
			this.addActionError(invalidUploadException.getErrorMessage());
		} catch (SpmUncheckedException e) {
			ErrorMessage errorMessage = new ErrorMessage("error.employee.definition.file", new String[] {getEmployeeDefinitionFileName()});
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
	 * @return the employeeDefinition
	 */
	public File getEmployeeDefinition() {
		return employeeDefinition;
	}

	/**
	 * @param employeeDefinition the employeeDefinition to set
	 */
	public void setEmployeeDefinition(File employeeDefinition) {
		this.employeeDefinition = employeeDefinition;
	}

	/**
	 * @return the employeeDefinitionContentType
	 */
	public String getEmployeeDefinitionContentType() {
		return employeeDefinitionContentType;
	}

	/**
	 * @param employeeDefinitionContentType the employeeDefinitionContentType to set
	 */
	public void setEmployeeDefinitionContentType(
			String employeeDefinitionContentType) {
		this.employeeDefinitionContentType = employeeDefinitionContentType;
	}

	/**
	 * @return the employeeDefinitionFileName
	 */
	public String getEmployeeDefinitionFileName() {
		return employeeDefinitionFileName;
	}

	/**
	 * @param employeeDefinitionFileName the employeeDefinitionFileName to set
	 */
	public void setEmployeeDefinitionFileName(String employeeDefinitionFileName) {
		this.employeeDefinitionFileName = employeeDefinitionFileName;
	}

	/**
	 * @return the uploadEmployeeDefinitionService
	 */
	public UploadEmployeeDefinitionService getUploadEmployeeDefinitionService() {
		return uploadEmployeeDefinitionService;
	}

	/**
	 * @param uploadEmployeeDefinitionService the uploadEmployeeDefinitionService to set
	 */
	public void setUploadEmployeeDefinitionService(
			UploadEmployeeDefinitionService uploadEmployeeDefinitionService) {
		this.uploadEmployeeDefinitionService = uploadEmployeeDefinitionService;
	}
	
}
