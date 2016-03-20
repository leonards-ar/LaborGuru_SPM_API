package com.laborguru.model;

import java.util.Date;

public class Employee extends User {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Store store;
	private Position defaultPosition;
	private boolean manager;
	private String phone;
	private String phone2;
	private Date hireDate;
	private Integer maxHoursWeek;
	private Integer maxDaysWeek;
	private Integer maxHoursDay;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String comments;
	private Double wage;
	private String employeeId;
	
	/**
	 * 
	 */
	public Employee() {
		this(null);
	}
	
	/**
	 * 
	 * @param employeeId
	 */
	public Employee(Integer employeeId) {
		super();
		setId(employeeId);
	}
	
	/**
	 * 
	 * @return
	 */
	public Store getStore() {
		return store;
	}
	
	/**
	 * 
	 * @param store
	 */
	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isManager() {
		return manager;
	}
	
	/**
	 * 
	 * @param manager
	 */
	public void setManager(boolean manager) {
		this.manager = manager;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * 
	 * @return
	 */
	public Date getHireDate() {
		return hireDate;
	}
	
	/**
	 * 
	 * @param hireDate
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getMaxHoursWeek() {
		return maxHoursWeek;
	}
	
	/**
	 * 
	 * @param maxHoursWeek
	 */
	public void setMaxHoursWeek(Integer maxHoursWeek) {
		this.maxHoursWeek = maxHoursWeek;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getMaxDaysWeek() {
		return maxDaysWeek;
	}
	
	/**
	 * 
	 * @param maxDaysWeek
	 */
	public void setMaxDaysWeek(Integer maxDaysWeek) {
		this.maxDaysWeek = maxDaysWeek;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getMaxHoursDay() {
		return maxHoursDay;
	}
	
	/**
	 * 
	 * @param maxHoursDay
	 */
	public void setMaxHoursDay(Integer maxHoursDay) {
		this.maxHoursDay = maxHoursDay;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAddress2() {
		return address2;
	}
	
	/**
	 * 
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * 
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * 
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return the defaultPosition
	 */
	public Position getDefaultPosition() {
		return defaultPosition;
	}
	
	/**
	 * @param defaultPosition the defaultPosition to set
	 */
	public void setDefaultPosition(Position defaultPosition) {
		this.defaultPosition = defaultPosition;
	}
	
	/**
	 * @return the wage
	 */
	public Double getWage() {
		return wage;
	}
	
	/**
	 * @param wage the wage to set
	 */
	public void setWage(Double wage) {
		this.wage = wage;
	}
	
	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
