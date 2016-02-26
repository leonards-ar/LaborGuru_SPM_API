package com.laborguru.service.uploadfile;

import java.util.List;

import com.laborguru.model.UploadFile;
import com.laborguru.service.uploadfile.dao.UploadFileDao;

/**
 * Defines the implementation for UploadFile operations on the spm
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UploadFileServiceBean implements UploadFileService {

	private UploadFileDao uploadFileDao;
		
	/**
	 * @param uploadFile
	 * @return
	 * @see com.laborguru.service.uploadfile.UploadFileService#getUploadFileById(com.laborguru.model.UploadFile)
	 */
	public UploadFile getUploadFileById(UploadFile uploadFile) {
		return uploadFileDao.getUploadFileById(uploadFile);
	}


	/**
	 * @param uploadFile
	 * @return
	 * @see com.laborguru.service.uploadfile.UploadFileService#delete(com.laborguru.model.UploadFile)
	 */
	public UploadFile delete(UploadFile uploadFile) {		
		UploadFile uploadFileRemoved = uploadFileDao.getUploadFileById(uploadFile);
		uploadFileDao.delete(uploadFileRemoved);
		return uploadFileRemoved;
	}

	/**
	 * Retrieves all the upload files instances that exist in the system 
	 * @return
	 * @see com.laborguru.service.uploadfile.UploadFileService#findAllUploadFilesByType()
	 */
	public List<UploadFile> findUploadFilesByType(UploadEnumType uploadType) {
		return uploadFileDao.findByType(uploadType);
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
	 * @param uploadFiles
	 * @return
	 * @see com.laborguru.service.uploadfile.UploadFileService#getHistoricSalesSize(com.laborguru.model.UploadFile)
	 */
	public Integer getHistoricSalesSize(UploadFile uploadFiles) {
		return getUploadFileDao().getHistoricSalesSize(uploadFiles);
	}	
}
