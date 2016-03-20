package com.laborguru.frontend.dto;

import com.laborguru.model.Position;
import com.laborguru.model.Store;

import java.util.Date;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public class EmployeeDto extends UserDto {

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

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getMaxHoursWeek() {
        return maxHoursWeek;
    }

    public void setMaxHoursWeek(Integer maxHoursWeek) {
        this.maxHoursWeek = maxHoursWeek;
    }

    public Integer getMaxDaysWeek() {
        return maxDaysWeek;
    }

    public void setMaxDaysWeek(Integer maxDaysWeek) {
        this.maxDaysWeek = maxDaysWeek;
    }

    public Integer getMaxHoursDay() {
        return maxHoursDay;
    }

    public void setMaxHoursDay(Integer maxHoursDay) {
        this.maxHoursDay = maxHoursDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
