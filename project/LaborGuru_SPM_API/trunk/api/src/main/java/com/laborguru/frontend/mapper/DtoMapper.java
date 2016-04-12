package com.laborguru.frontend.mapper;

import com.laborguru.frontend.dto.*;
import com.laborguru.model.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public class DtoMapper extends Mapper {

    public EmployeeDto toDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setAddress(employee.getAddress());
        dto.setAddress2(employee.getAddress2());
        dto.setCity(employee.getCity());
        dto.setComments(employee.getComments());
        dto.setUserName(employee.getUserName());
        dto.setEmail(employee.getEmail());
        dto.setUserName(employee.getUserName());
        dto.setSurname(employee.getSurname());

        return dto;
    }

    public StoreDto toDto(Store store) {
        StoreDto dto = new StoreDto();
        dto.setId(store.getId());
        Area area = store.getArea();
        dto.setArea(toDto(area));
        Region region = area.getRegion();
        dto.setRegion(toTinyDto(region));
        Customer customer = region.getCustomer();
        dto.setClientName(customer.getName());
        dto.setCode(store.getCode());
        dto.setName(store.getName());

        return dto;
    }

    public StoreDto toTinyDto(Store store){
        StoreDto dto = new StoreDto();
        dto.setId(store.getId());
        dto.setClientName(store.getArea().getRegion().getCustomer().getName());
        dto.setCode(store.getCode());
        dto.setName(store.getName());

        return dto;
    }

    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setCode(customer.getCode());

        Set<RegionDto> regionsDto = new HashSet();
        for(Region region: customer.getRegions()){

            regionsDto.add(toDto(region));

        }

        customerDto.setRegionDto(regionsDto);

        return customerDto;
    }

    public CustomerDto toTinyDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setCode(customer.getCode());

        return customerDto;
    }

    public RegionDto toTinyDto(Region region){
        RegionDto regionDto = new RegionDto();

        regionDto.setId(region.getId());
        regionDto.setName(region.getName());

        return regionDto;
    }

    public RegionDto toDto(Region region){
        RegionDto regionDto = new RegionDto();

        regionDto.setId(region.getId());
        regionDto.setName(region.getName());

        Set<AreaDto> areasDto = new HashSet();
        for(Area area: region.getAreas()){
            areasDto.add(toDto(area));
        }

        regionDto.setArea(areasDto);

        return regionDto;
    }

    public AreaDto toDto(Area area){
        AreaDto areaDto = new AreaDto();

        areaDto.setId(area.getId());
        areaDto.setName(area.getName());

        return areaDto;
    }

    public OperationTimeDto toDto(List<OperationTime> otList){

        OperationTimeDto oDto = new OperationTimeDto();
        for(OperationTime ot: otList) {
            oDto.addCloseHour(formatHour(ot.getCloseHour()));
            oDto.addClosingExtraHours(ot.getClosingExtraHours());
            oDto.addOpenHour(formatHour(ot.getOpenHour()));
            oDto.addOpeningExtraHours(ot.getOpeningExtraHours());
        }
        return oDto;
    }

}
