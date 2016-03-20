package com.laborguru.frontend.dto;

import java.util.Set;

/**
 * Created by federicobarreraoro on 3/20/16.
 */
public class CustomerDto {

    Integer id;
    String name;
    String code;
    Set<RegionDto> regionDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<RegionDto> getRegionDto() {
        return regionDto;
    }

    public void setRegionDto(Set<RegionDto> regionDto) {
        this.regionDto = regionDto;
    }
}
