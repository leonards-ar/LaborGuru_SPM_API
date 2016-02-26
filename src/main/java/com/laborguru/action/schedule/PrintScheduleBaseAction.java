/**
 * 
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.model.CompleteShift;
import com.laborguru.model.Employee;
import com.laborguru.model.EmployeeSchedule;
import com.laborguru.model.Manager;
import com.laborguru.model.Position;
import com.laborguru.model.Shift;
import com.laborguru.model.StoreSchedule;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.email.EmailService;
import com.laborguru.service.manager.ManagerService;
import com.laborguru.service.store.StoreService;
import com.laborguru.util.CalendarUtils;

/**
 * @author mariano
 *
 */
public abstract class PrintScheduleBaseAction extends PrintShiftBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PrintScheduleBaseAction.class);
	private static final String ZERO_TIME = "-";
	
	private List<StoreSchedule> storeSchedules = null;
	private List<Date> weekDays = null;
	private Map<String,String> viewMap;
	private boolean inTimeOnly = false;
	private String selectedView;
	private EmailService emailService;
	private ReferenceDataService referenceDataService;
	private ManagerService managerService;
	private boolean postSchedule;
	private StoreService storeService;
	
	/**
	 * 
	 */
	public PrintScheduleBaseAction() {
	}

	/**
	 * @return the storeSchedule
	 */
	protected List<StoreSchedule> getStoreSchedules() {
		if(storeSchedules == null) {
			storeSchedules = new ArrayList<StoreSchedule>();
			try {
				StoreSchedule aSchedule;
				//:TODO: Probably should retrieve the whole week from the database
				for(Date aDate : getWeekDays()) {
					aSchedule = getStoreSchedule(aDate);
					storeSchedules.add(aSchedule);
				}
			} catch(RuntimeException ex) {
				log.error("Could not retrieve schedule for week days " + getWeekDays() + " for store " + getEmployeeStore(), ex);
			}

		}
		return storeSchedules;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	protected StoreSchedule getStoreSchedule(Date date) {
		StoreSchedule aSchedule = getScheduleService().getStoreScheduleByDate(getEmployeeStore(), date);
		if(aSchedule == null) {
			aSchedule = new StoreSchedule();
			aSchedule.setStore(getEmployeeStore());
			aSchedule.setDay(date);
		}					
		return aSchedule;
	}
	
	/**
	 * @return the weekDays
	 */
	public List<Date> getWeekDays() {
		if(weekDays == null) {
			this.weekDays = getWeekDaySelector().getWeekDays();
		}
		return weekDays;
	}
	
	/**
	 * @param weekDays the weekDays to set
	 */
	public void setWeekDays(List<Date> weekDays) {
		this.weekDays = weekDays;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Position> getWeeklySchedulePositions() {
		Set<Position> positions = new HashSet<Position>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			positions.addAll(schedule.getSchedulePositions());
		}
		
		return new ArrayList<Position>(positions);
	}

	/**
	 * 
	 * @return
	 */
	public List<Position> getSchedulePositionsForSelectedDate() {
		return new ArrayList<Position>(getStoreSchedule(getWeekDaySelector().getSelectedDay()).getSchedulePositions());
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Employee> getWeeklyScheduleEmployees() {
		Set<Employee> employees = new HashSet<Employee>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			employees.addAll(schedule.getScheduleEmployees());
		}
		
		return sortEmployees(new ArrayList<Employee>(employees));
	}

	/**
	 * 
	 * @return
	 */
	public List<Employee> getScheduleEmployeesForSelectedDate() {
		return sortEmployees(new ArrayList<Employee>(getStoreSchedule(getWeekDaySelector().getSelectedDay()).getScheduleEmployees()));
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Employee> getWeeklyScheduleEmployeesFor(Position position) {
		Set<Employee> employees = new HashSet<Employee>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			employees.addAll(schedule.getScheduleEmployeesFor(position));
		}
		
		return sortEmployees(new ArrayList<Employee>(employees));		
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Employee> getScheduleEmployeesForSelectedDate(Position position) {
		return sortEmployees(new ArrayList<Employee>(getStoreSchedule(getWeekDaySelector().getSelectedDay()).getScheduleEmployeesFor(position)));		
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 */
	public List<Position> getWeeklySchedulePositionsFor(Employee employee) {
		Set<Position> positions = new HashSet<Position>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			positions.addAll(schedule.getSchedulePositionsFor(employee));
		}
		
		return sortPositions(new ArrayList<Position>(positions));		
	}	

	/**
	 * 
	 * @param employee
	 * @return
	 */
	public List<Position> getSchedulePositionsForSelectedDate(Employee employee) {
		return sortPositions(new ArrayList<Position>(getStoreSchedule(getWeekDaySelector().getSelectedDay()).getSchedulePositionsFor(employee)));		
	}
	
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @param dayIndex
	 * @return
	 */
	public List<CompleteShift> getCompleteShiftsFor(Position position, Employee employee, int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		EmployeeSchedule schedule = storeSchedule.getEmployeeSchedule(employee);
		if(schedule != null) {
			return schedule.getUnreferencedCompleteShiftsFor(position);
		} else {
			return new ArrayList<CompleteShift>();
		}
	}
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @return
	 */
	public List<CompleteShift> getCompleteShiftsForSelectedDate(Position position, Employee employee) {
		StoreSchedule storeSchedule = getStoreSchedule(getWeekDaySelector().getSelectedDay());
		EmployeeSchedule schedule = storeSchedule.getEmployeeSchedule(employee);
		if(schedule != null) {
			return schedule.getUnreferencedCompleteShiftsFor(position);
		} else {
			return new ArrayList<CompleteShift>();
		}
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 */
	public List<Shift> getBreaksForSelectedDate(Employee employee) {
		StoreSchedule storeSchedule = getStoreSchedule(getWeekDaySelector().getSelectedDay());
		EmployeeSchedule schedule = storeSchedule.getEmployeeSchedule(employee);
		if(schedule != null) {
			return schedule.getBreakShifts();
		} else {
			return new ArrayList<Shift>();
		}
	}
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @param dayIndex
	 * @return
	 */
	public List<Shift> getShiftsFor(Position position, Employee employee, int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		EmployeeSchedule schedule = storeSchedule.getEmployeeSchedule(employee);
		if(schedule != null) {
			return schedule.getUnreferencedShiftsFor(position);
		} else {
			return new ArrayList<Shift>();
		}
	}
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @return
	 */
	public String getWeekTotalHoursFor(Position position, Employee employee) {
		double total = 0.0;
		for(int i=0; i < getWeekDays().size(); i++) {
			for(Shift shift : getShiftsFor(position, employee, i)) {
				if(!shift.isReferencedShift()) {
					total += shift.getTotalShiftHoursWithContiguous().doubleValue();
				}
			}
		}
		return CalendarUtils.hoursToTime(new Double(total), ZERO_TIME);
	}

	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	public String getDayTotalHours(int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		return CalendarUtils.hoursToTime(storeSchedule.getTotalShiftHoursWithContiguous(), ZERO_TIME);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDayTotalHoursForSelectedDate() {
		StoreSchedule storeSchedule = getStoreSchedule(getWeekDaySelector().getSelectedDay());
		return CalendarUtils.hoursToTime(storeSchedule.getTotalShiftHoursWithContiguous(), ZERO_TIME);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWeekTotalHours() {
		double total = 0.0;
		StoreSchedule storeSchedule = null;
		for(int i=0; i < getWeekDays().size(); i++) {
			storeSchedule = getStoreSchedules().get(i);
			total += storeSchedule.getTotalShiftHoursWithContiguous().doubleValue();
		}		
		return CalendarUtils.hoursToTime(new Double(total), ZERO_TIME);		
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public String getWeekTotalHoursFor(Position position) {
		double total = 0.0;
		StoreSchedule storeSchedule = null;
		for(int i=0; i < getWeekDays().size(); i++) {
			storeSchedule = getStoreSchedules().get(i);
			total += storeSchedule.getTotalShiftHoursWithContiguous(position).doubleValue();
		}		
		return CalendarUtils.hoursToTime(new Double(total), ZERO_TIME);
	}	

	/**
	 * 
	 * @param position
	 * @return
	 */
	public String getWeekTotalHoursFor(Employee employee) {
		double total = 0.0;
		StoreSchedule storeSchedule = null;
		EmployeeSchedule employeeSchedule = null;
		for(int i=0; i < getWeekDays().size(); i++) {
			storeSchedule = getStoreSchedules().get(i);
			employeeSchedule = storeSchedule.getEmployeeSchedule(employee);
			if(employeeSchedule != null) {
				total += employeeSchedule.getTotalShiftHoursWithContiguous().doubleValue();
			}
		}		
		return CalendarUtils.hoursToTime(new Double(total), ZERO_TIME);
	}	
	
	/**
	 * 
	 * @param position
	 * @param dayIndex
	 * @return
	 */
	public String getDayTotalHoursFor(Position position, int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		return CalendarUtils.hoursToTime(storeSchedule.getTotalShiftHoursWithContiguous(position), ZERO_TIME);
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public String getDayTotalHoursForSelectedDate(Position position) {
		StoreSchedule storeSchedule = getStoreSchedule(getWeekDaySelector().getSelectedDay());
		return CalendarUtils.hoursToTime(storeSchedule.getTotalShiftHoursWithContiguous(position), ZERO_TIME);
	}

	/**
	 * 
	 * @param position
	 * @param dayIndex
	 * @return
	 */
	public String getDayTotalHoursForSelectedDate(Employee employee) {
		StoreSchedule storeSchedule = getStoreSchedule(getWeekDaySelector().getSelectedDay());
		EmployeeSchedule employeeSchedule = storeSchedule.getEmployeeSchedule(employee);
		return CalendarUtils.hoursToTime(employeeSchedule != null ? employeeSchedule.getTotalShiftHoursWithContiguous() : null, ZERO_TIME);
	}

	/**
	 * 
	 * @param position
	 * @param dayIndex
	 * @return
	 */
	public String getDayTotalHoursFor(Employee employee, int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		EmployeeSchedule employeeSchedule = storeSchedule.getEmployeeSchedule(employee);
		return CalendarUtils.hoursToTime(employeeSchedule != null ? employeeSchedule.getTotalShiftHoursWithContiguous() : null, ZERO_TIME);
	}
	
	public Map<String, String> getViewMap() {
		if(viewMap == null || viewMap.size() <=0 ) {
			setViewMap(getReferenceDataService().getPrintScheduleViews());
		}
		return viewMap;
	}

	public void setViewMap(Map<String, String> viewMap) {
		this.viewMap = viewMap;
	}

	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}

	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	public boolean isInTimeOnly() {
		return inTimeOnly;
	}

	public void setInTimeOnly(boolean inTimeOnly) {
		this.inTimeOnly = inTimeOnly;
	}

	public String getSelectedView() {
		return selectedView;
	}

	public void setSelectedView(String selectedView) {
		this.selectedView = selectedView;
	}
	
	public String print() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		return SpmActionResult.PRINT.getResult();
	}

	public String save() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

		getEmployeeStore().setInTimeOnly(isInTimeOnly());
		
		getStoreService().save(getEmployeeStore());		
		
		return SpmActionResult.SUCCESS.getResult();
	}	
	
	public String post() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		List<Object> args =  new ArrayList<Object>();
		args.add(getWeekDaySelector().getStartingWeekDay());
		String weekDay = getText("schedule.printshift.weekdayselector.selectedweek.dateformat", args);
		
		Set<Employee> employees = new HashSet<Employee>(getWeeklyScheduleEmployees());
		Set<Manager> areaManagers = new HashSet<Manager>(getManagerService().getAreaUsersByArea(getEmployeeStore().getArea()));

		Set<Integer> sentIds = new HashSet<Integer>(employees.size() + areaManagers.size());
		int sentEmails = 0;
		int failEmails = 0;
		
		for(Employee employee : employees) {
			if(!StringUtils.isBlank(employee.getEmail())) {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("employee", employee);
				model.put("weekStartDay", weekDay);
				sentIds.add(employee.getId());
				try {
					getEmailService().sendHtmlEmail(new String[] {employee.getEmail()}, null, getText("schedule.post.schedule.email.subject"), getText("schedule.post.schedule.email.template"), model, null);
					sentEmails++;
				} catch(MailException ex) {
					log.error("Could not send email to [" + employee.getEmail() + "]", ex);
					failEmails++;
				}
			} else {
				log.warn("Employee " + employee.getFullName() + " does not have an email address");
			}
		}

		for(Manager areaManager : areaManagers) {
			if(!sentIds.contains(areaManager.getId())) {
				if(!StringUtils.isBlank(areaManager.getEmail())) {
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("employee", areaManager);
					model.put("weekStartDay", weekDay);
					
					try {
						getEmailService().sendHtmlEmail(new String[] {areaManager.getEmail()}, null, getText("schedule.post.schedule.email.subject"), getText("schedule.post.schedule.email.template"), model, null);
						sentEmails++;
					} catch(MailException ex) {
						log.error("Could not send email to [" + areaManager.getEmail() + "]", ex);
						failEmails++;
					}
				} else {
					log.warn("Area manager " + areaManager.getFullName() + " does not have an email address");
				}
			} else {
				log.debug("Already sent email to area manager " + areaManager.getFullName() + " when sending email to employees");
			}
		}
		
		if(failEmails > 0) {
			addActionError(new ErrorMessage("schedule.post.failure", new String[] {String.valueOf(sentEmails), String.valueOf(failEmails), String.valueOf(sentEmails + failEmails)}));
		} else {
			addActionMessage(getText("schedule.post.success", new String[] {String.valueOf(sentEmails)}));
		}

		return SpmActionResult.SUCCESS.getResult();		
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public ManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	public boolean isPostSchedule() {
		return postSchedule;
	}

	public void setPostSchedule(boolean postSchedule) {
		this.postSchedule = postSchedule;
	}
	
	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService the storeService to set
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}	
}
