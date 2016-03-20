package com.laborguru.controller.customer;

import com.laborguru.controller.BaseController;
import com.laborguru.frontend.dto.CustomerDto;
import com.laborguru.model.Customer;
import com.laborguru.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 3/20/16.
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController
        extends BaseController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDto> getAllCustomers() {

        List<CustomerDto> customersDto = new LinkedList<>();

        for(Customer customer: customerService.findAll()) {
            customersDto.add(getDtoMapper().toTinyDto(customer));
        }
        return customersDto;
    }

    @RequestMapping(value="/{customerId}", method = RequestMethod.GET)
    public CustomerDto getCustomer(@PathVariable Integer customerId){

        Customer tmp = new Customer();
        tmp.setId(customerId);

        return getDtoMapper().toDto(customerService.getCustomerById(tmp));
    }

}
