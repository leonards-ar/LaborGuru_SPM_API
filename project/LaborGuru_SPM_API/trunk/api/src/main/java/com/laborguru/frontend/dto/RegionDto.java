package com.laborguru.frontend.dto;

import java.util.Set;

/**
 * Created by federicobarreraoro on 3/20/16.
 */
public class RegionDto {

    Integer id;
    String name;
    Set<AreaDto> area;

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

    public Set<AreaDto> getArea() {
        return area;
    }

    public void setArea(Set<AreaDto> area) {
        this.area = area;
    }
}
