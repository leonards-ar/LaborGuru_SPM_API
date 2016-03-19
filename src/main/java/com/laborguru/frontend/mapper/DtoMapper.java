package com.laborguru.frontend.mapper;

import com.laborguru.frontend.dto.EmployeeDto;
import com.laborguru.model.Employee;

import java.util.LinkedList;
import java.util.List;

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

    public List<EmployeeDto> toDto(List<Employee> employees){

        List<EmployeeDto> employeeDto = new LinkedList<EmployeeDto>();

        for(Employee employee: employees){
            employeeDto.add(toDto(employee));
        }

        return employeeDto;
    }
}
