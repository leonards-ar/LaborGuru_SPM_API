/**
 * 
 */
package com.laborguru.service.employee.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Region;
import com.laborguru.model.Store;
import com.laborguru.util.PoiUtils;

/**
 * @author mariano
 *
 */
public class EmployeeDefinitionFileParserBean implements EmployeeDefinitionFileParser {
	private static final Logger log = Logger.getLogger(EmployeeDefinitionFileParserBean.class);

	/**
	 * 
	 */
	public EmployeeDefinitionFileParserBean() {
	}

	/**
	 * 
	 * @param employeesToUpload
	 * @return
	 * @see com.laborguru.service.employee.file.EmployeeDefinitionFileParser#parseEmployees(java.io.File)
	 */
	public List<Employee> parseEmployees(File employeesToUpload) {
		List<Employee> employees = new ArrayList<Employee>();
		
		try {

			 Iterator<Row> rit = PoiUtils.getFirstSheetRows(employeesToUpload);
			 			 
			 //Ignoring the header
			 rit.next();
			 
			 while(rit.hasNext()) {
				Row row = rit.next();
				employees.add(buildEmployee(row));
			}
			 
			 return employees;			 

		} catch (FileNotFoundException exceptionFNF) {
			String message = "File to parse: " + employeesToUpload.getName()+ " not found";
			log.error(message);
			throw new InvalidUploadFileException(exceptionFNF, message);
		} catch (IOException e) {
			String msg = "The file " + employeesToUpload.getName() + " passed in as parameter cannot be read.";
			log.error(msg);
			throw new InvalidUploadFileException(msg);		
		} catch(Throwable ex) {
			String msg = "The file " + employeesToUpload.getName() + " passed in as parameter cannot be processed: " + ex.getMessage();
			log.error(msg);
			throw new InvalidUploadFileException(msg);		
		}
	}

	private Employee buildEmployee(Row row) {
		Employee employee = new Employee();
		Store store = new Store();
		Customer customer = new Customer();
		Area area = new Area();
		Region region = new Region();
		
		region.setCustomer(customer);
		area.setRegion(region);
		store.setArea(area);
		employee.setStore(store);
		
		// Client Code
		customer.setCode(PoiUtils.getStringValue(row.getCell(0)));
		// Store Code
		store.setCode(PoiUtils.getStringValue(row.getCell(1)));
		// First Name
		employee.setName(PoiUtils.getStringValue(row.getCell(2)));
		// Last Name
		employee.setSurname(PoiUtils.getStringValue(row.getCell(3)));
		// Username
		employee.setUserName(PoiUtils.getStringValue(row.getCell(4)));
		// e-mail
		employee.setEmail(PoiUtils.getStringValue(row.getCell(5)));
		// Password
		employee.setPassword(PoiUtils.getStringValue(row.getCell(6)));
		//$ Wage
		employee.setWage(PoiUtils.getDoubleValue(row.getCell(7)));
		//Phone
		employee.setPhone(PoiUtils.getStringValue(row.getCell(8)));
		// Alternate Phone
		employee.setPhone2(PoiUtils.getStringValue(row.getCell(9)));
		// Max hours per week
		employee.setMaxHoursWeek(PoiUtils.getIntegerValue(row.getCell(10)));
		// Max hours per day
		employee.setMaxHoursDay(PoiUtils.getIntegerValue(row.getCell(11)));
		// Max days per week
		employee.setMaxDaysWeek(PoiUtils.getIntegerValue(row.getCell(12)));
		// Default position
		String posName = PoiUtils.getStringValue(row.getCell(13));
		if(StringUtils.isNotBlank(posName)) {
			Position defaultPosition = new Position();
			defaultPosition.setName(posName);
			employee.setDefaultPosition(defaultPosition);
		} else {
			employee.setDefaultPosition(null);
		}
		// Status
		employee.setStatus(PoiUtils.getIntegerValue(row.getCell(14)));
		// Manager (1 yes, 0 No)
		employee.setManager(PoiUtils.getBooleanValue(row.getCell(15)));
		// Hire Date
		employee.setHireDate(PoiUtils.getDateValue(row.getCell(16)));
		// Address
		employee.setAddress(PoiUtils.getStringValue(row.getCell(17)));
		//Address 2
		employee.setAddress2(PoiUtils.getStringValue(row.getCell(18)));
		// City
		employee.setCity(PoiUtils.getStringValue(row.getCell(19)));
		//State
		employee.setState(PoiUtils.getStringValue(row.getCell(20)));
		//Zip
		employee.setZip(PoiUtils.getStringValue(row.getCell(21)));
		//Comments
		employee.setComments(PoiUtils.getStringValue(row.getCell(22)));
		
		return employee;
	}
}
