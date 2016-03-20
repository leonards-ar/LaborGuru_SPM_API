package com.laborguru.frontend.mapper;

import com.laborguru.frontend.dto.CustomerDto;
import com.laborguru.frontend.dto.RegionDto;
import com.laborguru.model.Customer;
import com.laborguru.model.Region;

/**
 * Created by federicobarreraoro on 3/20/16.
 */
public class DomainMapper {

    public Customer toDomain(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setCode(customerDto.getCode());

        for(RegionDto regionDto: customerDto.getRegionDto()){

            customer.addRegion(toDomain(regionDto));
        }

        return customer;

    }

    public Region toDomain(RegionDto regionDto){
        Region region = new Region();

        region.setId(regionDto.getId());
        region.setName(regionDto.getName());

        return region;
    }
}
