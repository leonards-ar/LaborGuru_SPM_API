package com.laborguru.action.employee;

import java.util.List;
import java.util.Map;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Profile;
import com.laborguru.model.filter.SearchEmployeeFilter;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.employee.EmployeeService;
import com.laborguru.service.position.PositionService;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public abstract class EmployeeBaseAction extends SpmAction implements Preparable{

	private Employee employee;
	
	private EmployeeService employeeService;
	private ReferenceDataService referenceDataService;
	private PositionService positionService;
	
	private List<Employee> storeEmployees;
	private List<Position> positions;
	private Map<String, String> statusMap;
	private List<String> statesList;

	private SearchEmployeeFilter searchEmployee;
	private String passwordConfirmation;	
	private Integer employeeId;
	private boolean removePage;
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		//It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * Prepare data to be used to display employee data
	 */
	public void prepareShow() {
		this.setStatusMap(getReferenceDataService().getStatus());
	}

	/**
	 * Prepare data to be used to edit employee data
	 */
	public void prepareEdit() {
		loadListsForAddEditPage();
	}
	
	/**
	 * Prepare data to be used in case it goes back to a page that may need it.
	 */
	public void prepareList() {
		//loadListsForAddEditPage();
	}	
	
	/**
	 * Prepare the data to be used on the add page
	 * Loads position and status list
	 * @throws Exception
	 */
	public void prepareAdd(){
		loadListsForAddEditPage();
	}

	/**
	 * Prepare data to be used to display employee data
	 * before removal.
	 */
	public void prepareRemove() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare the data to be used on the add page
	 * We should preload the list needed to render the add page.
	 * When a validation fails the application goes back to the add page and this data is needed.
	 * Loads position and status list
	 * @throws Exception
	 */
	public void prepareSave(){
		loadListsForAddEditPage();
	}

	/**
	 * Prepares the view page
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {	
		loadEmployeeFromId();
		setExtraInformation();
		return SpmActionResult.SHOW.getResult();
	}
		
	/**
	 * Retrieves all the employees that belong to the user's store.
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		
		this.setStoreEmployees(getEmployees());
		setExtraInformation();
		return SpmActionResult.LIST.getResult();
	}	

	/**
	 * Prepares the add page
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		setExtraInformation();
		setPositionsForSelect();
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Prepare removes page
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception {

		//Getting employee
		loadEmployeeFromId();
		setExtraInformation();
		this.setRemovePage(true);
		return SpmActionResult.SHOW.getResult();
	}
	
	/**
	 * Stores an employee on the DB
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {		
		//Getting employee
		Employee auxEmployee = getEmployeeService().getEmployeeById(getEmployee());		
		getEmployeeService().logicalDelete(auxEmployee);
		setExtraInformation();
		return SpmActionResult.LISTACTION.getResult();
	}

	/**
	 * Prepares the edit page
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		loadEmployeeFromId();
		setExtraInformation();	
		setPositionsForSelect();
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * 
	 */
	protected void setPositionsForSelect() {
		if(getPositions() == null || getPositions().isEmpty()) {
			setPositions(retrievePositions());
		}
	}
	
	/**
	 * TODO Performs an Employee Search
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {
		
		//Setting the store on the filter
		addFilters();
		setExtraInformation();
		this.setStoreEmployees(getEmployeeService().filterEmployee(this.searchEmployee));
		
		return SpmActionResult.LIST.getResult();
	}
	
	/**
	 * Stores an employee on the DB
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {		
		
		if (getEmployee().getId() == null){
			setAssociation();
		}
		
		try {
			getEmployee().setProfile(getEmployeeProfile());
			getEmployeeService().save(getEmployee());
			setExtraInformation();
			return SpmActionResult.LISTACTION.getResult();

		} catch (SpmCheckedException e) {
			addActionError(e.getErrorMessage());
		}
		
		return SpmActionResult.INPUT.getResult();
	}
	
	/**
	 *  Load full employee from the property employeeId
	 */
	protected void loadEmployeeFromId() {
		Employee tmpEmployee = new Employee();
		tmpEmployee.setId(getEmployeeId());
		this.setEmployee(getEmployeeService().getEmployeeById(tmpEmployee));
		setPasswordConfirmation(getEmployee().getPassword());
	}	
	
	/**
	 * Load position and status list
	 */
	protected void loadListsForAddEditPage() {
		this.setStatusMap(getReferenceDataService().getStatus());
		//:TODO: Add country support
		this.setStatesList(getReferenceDataService().getStates("us"));
		
		setPositionsForSelect();
	}

	/**
	 * 
	 * @return
	 */
	protected abstract List<Position> retrievePositions();
	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the employeeService
	 */
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * @return the referenceDataService
	 */
	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}
	
	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * @param positions the positions to set
	 */
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	/**
	 * @return the statusMap
	 */
	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * @param statusMap the statusMap to set
	 */
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * @return the statesList
	 */
	public List<String> getStatesList() {
		return statesList;
	}

	/**
	 * @param statesList the statesList to set
	 */
	public void setStatesList(List<String> statesList) {
		this.statesList = statesList;
	}

	/**
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the storeEmployees
	 */
	public List<Employee> getStoreEmployees() {
		return storeEmployees;
	}

	/**
	 * @param storeEmployees the storeEmployees to set
	 */
	public void setStoreEmployees(List<Employee> storeEmployees) {
		this.storeEmployees = storeEmployees;
	}

	/**
	 * @return the removePage
	 */
	public boolean isRemovePage() {
		return removePage;
	}

	/**
	 * @param removePage the removePage to set
	 */
	public void setRemovePage(boolean removePage) {
		this.removePage = removePage;
	}

	
	/**
	 * @return the searchEmployee
	 */
	public SearchEmployeeFilter getSearchEmployee() {
		return searchEmployee;
	}

	/**
	 * @param searchEmployee the searchEmployee to set
	 */
	public void setSearchEmployee(SearchEmployeeFilter searchEmployee) {
		this.searchEmployee = searchEmployee;
	}

	/**
	 * Retrieves the store that will be associated to the store
	 * @return
	 */
	protected abstract List<Employee> getEmployees();
	
	/**
	 * Retrieves the Profile that he user should have.
	 * @return
	 * @see com.laborguru.action.employee.EmployeeBaseAction#getEmployeeProfile()
	 */
	protected abstract Profile getEmployeeProfile();

	/**
	 * Adds additional filters to the {@link com.laborguru.action.employee.SearchEmployeeFilter} Object
	 */
	protected abstract void addFilters();

	/**
	 * Adds a Store, Area, Region or Customer association to an employee 
	 */
	protected abstract void setAssociation();
	
	/**
	 * Sets extra information for List employees
	 */
	protected abstract void setExtraInformation();

}
