package com.laborguru.frontend.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , id)
                .append("name",name)
                .append("area", area)
                .toString();
    }
}
