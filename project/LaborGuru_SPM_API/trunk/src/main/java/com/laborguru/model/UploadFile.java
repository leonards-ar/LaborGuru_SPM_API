package com.laborguru.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.service.uploadfile.UploadEnumType;

/**
 * This represent an Upload file entity.
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UploadFile extends SpmObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date uploadDate;
	private String filename;
	private UploadEnumType uploadType;
	private Set<HistoricSales> salesRecords;
	
	
	/**
	 * Default constructor
	 */
	public UploadFile(){
		
	}
	
	public UploadFile(Date date, String filename){
		this.uploadDate = date;
		this.filename = filename;
	}
	
	/**
	 * Returns sales records list size
	 * @return
	 */
	public int getSalesRecordsSize(){
		return getSalesRecords().size();
	}
	
	/**
	 * @return the uploadType
	 */
	public UploadEnumType getUploadType() {
		return uploadType;
	}

	/**
	 * @param uploadType the uploadType to set
	 */
	public void setUploadType(UploadEnumType uploadType) {
		this.uploadType = uploadType;
	}

	/**
	 * Equals
	 * @param other
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		
		if (this == other)
			return true;
		
		if (other == null)
			return false;
		
		if (getClass() != other.getClass())
			return false;		
		
		final UploadFile aUploadFile = (UploadFile) other;
		
		return new EqualsBuilder()
		.append(this.filename, aUploadFile.filename)
		.append(this.uploadDate, aUploadFile.uploadDate)
		.append(this.uploadType, aUploadFile.uploadType)
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.filename)
		.append(this.uploadDate)
		.append(this.uploadType)
		.toHashCode();	
	}
	

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id", this.id)
	   	.append("filename", this.filename)
	   	.append("uploadDate", this.uploadDate)
	   	.append("uploadType", this.uploadType)
	   	.toString();	
	}

	
	/**
	 * Adds a sales record instance to upload file. Handles the bi-directional
	 * relation.
	 * @param aSalesRecord The instance to add
	 */
	public void addSalesRecord(HistoricSales aSaleRecord){
		
		if (aSaleRecord == null){
			throw new IllegalArgumentException("Null aSaleRecord passed in as parameter");
		}
		
		if (aSaleRecord.getUploadFile() != null){
			aSaleRecord.getUploadFile().removeSalesRecord(aSaleRecord);
		}
		
		aSaleRecord.setUploadFile(this);
		getSalesRecords().add(aSaleRecord);
	}
	
	/**
	 * Removes a sales record. Handles the bi-directional
	 * relation.
	 * @param aSalesRecord The instance to remove
	 */
	public void removeSalesRecord(HistoricSales aSaleRecord){
		
		if (aSaleRecord == null){
			throw new IllegalArgumentException("Null aSaleRecord passed in as parameter");
		}
				
		getSalesRecords().remove(aSaleRecord);
		
		if (aSaleRecord.getUploadFile() != null){
			aSaleRecord.setUploadFile(null);
		}
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the salesRecords
	 */
	public Set<HistoricSales> getSalesRecords() {
		if (salesRecords == null){
			salesRecords = new HashSet<HistoricSales>();
		}
		return salesRecords;
	}

	/**
	 * @param salesRecords the salesRecords to set
	 */
	@SuppressWarnings("unused")
	private void setSalesRecords(Set<HistoricSales> salesRecords) {
		this.salesRecords = salesRecords;
	}


}
