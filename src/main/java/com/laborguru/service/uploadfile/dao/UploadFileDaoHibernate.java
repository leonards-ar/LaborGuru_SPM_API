package com.laborguru.service.uploadfile.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.laborguru.model.UploadFile;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;
import com.laborguru.service.uploadfile.UploadEnumType;

/**
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UploadFileDaoHibernate extends SpmHibernateDao implements UploadFileDao{

	private static final Logger log = Logger.getLogger(UploadFileDaoHibernate.class);
	private static final String UPLOAD_FILE_NULL = "The upload file object passed in as parameter is null";
	private static final String UPLOAD_FILE_ID_NULL = "The upload file object passed in as parameter has ID null";
	
	/**
	 * Retrieves an upload file instance by id. Takes the id of the instance passed as parameter.
	 * @param id An upload file instance with id not null.
	 * @return The full instance associated with the id or null
	 */
	public UploadFile getUploadFileById(UploadFile uploadFile) {
		
		
		if (uploadFile == null){
			log.error(UPLOAD_FILE_NULL);
			throw new IllegalArgumentException(UPLOAD_FILE_NULL);
		}
		
		if (uploadFile.getId() == null){
			log.error(UPLOAD_FILE_ID_NULL);
			throw new IllegalArgumentException(UPLOAD_FILE_ID_NULL);
		}		
		
		return (UploadFile) getHibernateTemplate().get(UploadFile.class, uploadFile.getId());		
	}


	/**
	 * It creates or updates an uploadFile instance
	 * @param uploadFile the object to persist
	 */
	public void saveOrUpdate(UploadFile uploadFile) {
		
		if (uploadFile == null){
			log.error(UPLOAD_FILE_NULL);
			throw new IllegalArgumentException(UPLOAD_FILE_NULL);
		}
		log.debug("saveOrUpdate - Going to save file upload with id ["+uploadFile.getId()+"]"+" and name ["+uploadFile.getFilename()+"]");
		
		getHibernateTemplate().saveOrUpdate(uploadFile);
	}


	/**
	 * Returns all the upload instances stored in the DB
	 * @return a list of UploadFiles
	 * @see com.laborguru.service.uploadfile.dao.UploadFileDao#findAll()
	 */
	public List<UploadFile> findAll() {
		List<UploadFile> uploadFileList = (List<UploadFile>)getHibernateTemplate().find("from UploadFile");
		log.debug("find all - Found "+uploadFileList.size()+" Upload File instances");
		return uploadFileList;
	}


	/**
	 * Deletes an UploadFile
	 * @param uploadFile
	 * @return
	 * @see com.laborguru.service.uploadfile.dao.UploadFileDao#delete(com.laborguru.model.UploadFile)
	 */
	public void delete(UploadFile uploadFile) {
		
		if (uploadFile == null){
			log.error(UPLOAD_FILE_NULL);
			throw new IllegalArgumentException(UPLOAD_FILE_NULL);
		}
		
		if (uploadFile.getId() == null){
			log.error(UPLOAD_FILE_ID_NULL + "Upload File:"+uploadFile);
			throw new IllegalArgumentException(UPLOAD_FILE_ID_NULL);
		}
		log.debug("delete - Going to delete file upload with id ["+uploadFile.getId()+"]"+" and name ["+uploadFile.getFilename()+"]");
		
		getHibernateTemplate().delete(uploadFile);		
	}
	
	/**
	 * @param storeId
	 * @param hsDate
	 * @param uploadType
	 * @return
	 */
	public UploadFile getUploadsByStoreAndHSDateAndType(Integer storeId, Date hsDate, UploadEnumType uploadType){

		checkNullArgumentAndThrowException(storeId, "store id", log);
		checkNullArgumentAndThrowException(hsDate, "date", log);
		checkNullArgumentAndThrowException(uploadType, "upload type", log);		
		
		DateTime selectedDate = new DateTime(hsDate);

		//select distinct uf.* from tbl_upload_files uf, tbl_historic_sales hs where uf.upload_type = 'WEB_UI' and hs.upload_file_id = uf.upload_file_id
		//and DATE(hs.date_time) = STR_TO_DATE('2009-10-11','%Y-%m-%d') and hs.store_id =1;
		List<UploadFile> uploadFileList = (List<UploadFile>) getHibernateTemplate().findByNamedParam("select distinct uf from UploadFile uf inner join uf.salesRecords hs" +
				" with hs.store.id = :storeId and DATE(hs.dateTime) = STR_TO_DATE(:selectedDate,'%Y-%m-%d')" +
				" where uf.uploadType =:uploadType ",
				new String[]{"storeId","selectedDate", "uploadType"}, new Object[]{storeId,selectedDate.toString("yyyy-MM-dd"), uploadType});
		
		if (uploadFileList.isEmpty()){
			return null;
		}
		
		return uploadFileList.get(0);
	}


	/**
	 * @param uploadType
	 * @return
	 * @see com.laborguru.service.uploadfile.dao.UploadFileDao#findByType(com.laborguru.service.uploadfile.UploadEnumType)
	 */
	public List<UploadFile> findByType(UploadEnumType uploadType) {

		checkNullArgumentAndThrowException(uploadType, "upload type", log);		

		List<UploadFile> uploadFileList = (List<UploadFile>)getHibernateTemplate().findByNamedParam("from UploadFile where uploadType =:uploadType", 
				"uploadType", uploadType);
		
		log.debug("find findByType - Found ["+uploadFileList.size()+"] UploadFiles of type ["+uploadType.name()+"]");
		return uploadFileList;
	}
	

	/**
	 * @param uploadFile
	 * @return
	 * @see com.laborguru.service.uploadfile.dao.UploadFileDao#getHistoricSalesSize(com.laborguru.model.UploadFile)
	 */
	public Integer getHistoricSalesSize(UploadFile uploadFile){		
		checkNullArgumentAndThrowException(uploadFile, "upload file", log);		
		Integer hsSize =  ((Long)getHibernateTemplate().getSessionFactory().getCurrentSession().createFilter(uploadFile.getSalesRecords(),"select count(*)").list().get(0)).intValue();
		log.debug("getHistoricSalesSize - Found ["+hsSize+"] historic sales record for the file upload with id ["+uploadFile.getId()+"]");
		return hsSize;		
	}	
}
