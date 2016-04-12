package com.laborguru.frontend.dto;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public class StoreDto {

    private Integer id;
    private String clientName;
    private String code;
    private String name;
    private Boolean isDemo;

    private RegionDto region;
    private AreaDto area;



    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }
}
