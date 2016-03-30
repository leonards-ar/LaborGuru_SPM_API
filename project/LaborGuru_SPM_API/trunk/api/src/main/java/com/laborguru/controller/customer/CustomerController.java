package com.laborguru.controller.customer;

import com.laborguru.controller.BaseController;
import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.frontend.dto.CustomerDto;
import com.laborguru.model.Customer;
import com.laborguru.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 3/20/16.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController
        extends BaseController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDto> getAllCustomers() {

        List<CustomerDto> customersDto = new LinkedList<>();

        for(Customer customer: customerService.findAll()) {
            customersDto.add(getDtoMapper().toTinyDto(customer));
        }
        return customersDto;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){

        Customer customer = getDomainMapper().toDomain(customerDto);

        try{
            customerService.save(customer);
        } catch (Exception e){

        }

        return new ResponseEntity(getDtoMapper().toDto(customer), HttpStatus.CREATED);

    }

    @RequestMapping(value="/{customerId}", method = RequestMethod.GET)
    public CustomerDto getCustomer(@PathVariable Integer customerId){

        Customer tmp = new Customer();
        tmp.setId(customerId);

        return getDtoMapper().toDto(customerService.getCustomerById(tmp));
    }

    @RequestMapping(value="/{customerId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto){

        Customer customer = getDomainMapper().toDomain(customerDto);
            try{
                customerService.save(customer);
            } catch (Exception e){
                throw new SpmUncheckedException(e.getCause(), e.getMessage(), ErrorEnum.GENERIC_ERROR);
            }

            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}


