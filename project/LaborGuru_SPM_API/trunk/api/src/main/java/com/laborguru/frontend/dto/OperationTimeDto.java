package com.laborguru.frontend.dto;

import com.laborguru.model.Store;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 4/10/16.
 */
public class OperationTimeDto {

    private List<String> openHour;
    private List<String> closeHour;
    private List<Integer> openingExtraHours;
    private List<Integer> closingExtraHours;

    public OperationTimeDto() {
        openHour = new LinkedList<>();
        closeHour = new LinkedList<>();
        openingExtraHours = new LinkedList<>();
        closingExtraHours = new LinkedList<>();

    }

    public List<String> getOpenHour() {
        return openHour;
    }

    public List<String> getCloseHour() {
        return closeHour;
    }

    public List<Integer> getOpeningExtraHours() {
        return openingExtraHours;
    }

    public List<Integer> getClosingExtraHours() {
        return closingExtraHours;
    }

    public void addOpenHour(String openHour){
        this.openHour.add(openHour);
    }

    public void addCloseHour(String closeHour){
        this.closeHour.add(closeHour);
    }

    public void addOpeningExtraHours(Integer openingExtraHours){
        this.openingExtraHours.add(openingExtraHours);
    }

    public void addClosingExtraHours(Integer closingExtraHours){
        this.closingExtraHours.add(closingExtraHours);
    }
}
