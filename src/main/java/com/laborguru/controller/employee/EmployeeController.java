package com.laborguru.controller.employee;

import com.laborguru.frontend.dto.EmployeeDto;
import com.laborguru.frontend.mapper.DtoMapper;
import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.service.employee.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by federicobarreraoro on 3/17/16.
 */

@RestController
public class EmployeeController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final DtoMapper dtoMapper = new DtoMapper();
    @Autowired
    private EmployeeService employeeService;

    //-------------------Retrieve All Employees--------------------------------------------------------
    @RequestMapping(value="/api/employee", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDto>> getEmployees() throws Exception{


        logger.debug("Hey!!! I am here returning a String !!!!!!!.");

        Store store = new Store();
        store.setId(1);
        List<Employee> employees = employeeService.getEmployeesByStore(store);
        return new ResponseEntity<List<EmployeeDto>>(dtoMapper.toDto(employees), HttpStatus.OK);
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
