package com.laborguru.service.dataimport.actualhours;

import com.laborguru.model.ActualHours;
import com.laborguru.model.Customer;

public interface ActualHoursAssembler {

	Customer getCustomer(String[] line);

	ActualHours getActualHours(String[] line);

}