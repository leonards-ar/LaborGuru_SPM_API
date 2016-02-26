package com.laborguru.service.report;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.Customer;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.Region;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.model.StoreSchedule;
import com.laborguru.model.report.TotalCustomerManagerHour;
import com.laborguru.model.report.TotalRegionManagerHour;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.region.RegionService;
import com.laborguru.service.report.dao.ReportDao;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class ReportCustomerServiceBean implements ReportCustomerService {
	public static Logger log = Logger.getLogger(ReportCustomerServiceBean.class);
	
	private ReportDao reportDao;
	private StaffingService staffingService;
	private CustomerService customerService;
	private RegionService regionService;
	private ProjectionService projectionService;
	private ScheduleService scheduleService;

	

	public List<TotalCustomerManagerHour> getPerformanceEfficiencyReport(Customer customer, Date start, Date end) {

		try{
			
			List<TotalCustomerManagerHour> actualSales = reportDao.getActualSalesByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> actualHours = reportDao.getActualHoursByCustomer(customer, start, end);			
			List<TotalCustomerManagerHour> minimumStaffing = getActualMinimumStaffing(customer, start, end);
			List<TotalCustomerManagerHour> wages = getStoreWages(customer, start, end);
		
			return merge(actualSales, actualHours, minimumStaffing, wages, null);
		
		} catch(SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
		
	}
	
	public List<TotalCustomerManagerHour> getWeeklyTotalHours(Customer customer, Date start, Date end) {

		try {
			List<TotalCustomerManagerHour> actualSales = reportDao.getActualSalesByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> scheduleTotalHours = reportDao.getScheduleTotalHourByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> targetTotalHours = reportDao.getTargetTotalHourByCustomer(customer, start, end);
	        List<TotalCustomerManagerHour> wages = getStoreWages(customer, start, end);
			
			return merge(actualSales, scheduleTotalHours, targetTotalHours, wages, null); 
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}

	public List<TotalCustomerManagerHour> getScheduleExecutionEfficiencyReport(Customer customer, Date start, Date end) {
		try{

			List<TotalCustomerManagerHour> actualSales = reportDao.getActualSalesByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> scheduleTotalHours = reportDao.getScheduleTotalHourByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> actualHours = reportDao.getActualHoursByCustomer(customer, start, end);
            List<TotalCustomerManagerHour> wages = getStoreWages(customer, start, end);	
            List<TotalCustomerManagerHour> projections = getDailyProjections(customer, start, end);
			return merge(actualSales, scheduleTotalHours, actualHours, wages, projections);
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}		
	}
	
	public List<TotalCustomerManagerHour> getForecastEfficiencyReport(Customer customer, Date start, Date end){

		try{
			List<TotalCustomerManagerHour> actualSales = reportDao.getActualSalesByCustomer(customer, start, end);
			List<TotalCustomerManagerHour> projections = getDailyProjections(customer, start, end);
			
			return mergeForecast(actualSales, projections);
		}catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	/**
	 * Returns the minimum staffing for each region. This is the sum of all the stores of each region
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<TotalCustomerManagerHour> getActualMinimumStaffing(Customer customer, Date startDate, Date endDate){
		List<TotalCustomerManagerHour> totalHours = new ArrayList<TotalCustomerManagerHour>();
		
		Customer tmpCustomer = customerService.getCustomerById(customer);
		for(Region region: tmpCustomer.getRegions()) {
			TotalCustomerManagerHour totalManagerHour = new TotalCustomerManagerHour();
			totalManagerHour.setRegion(region);
			totalHours.add(totalManagerHour);
			BigDecimal targetRegion = SpmConstants.BD_ZERO_VALUE;
			for(Store store: region.getStores()) {
				BigDecimal targetStores = SpmConstants.BD_ZERO_VALUE;
				for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
					StoreDailyHistoricSalesStaffing saleStaffing = getStaffingService().getDailyHistoricSalesStaffingByDate(store, date);
					targetStores = targetStores.add(new BigDecimal(saleStaffing.getTotalDailyTarget()));
				}
				targetRegion = targetRegion.add(targetStores);
			}
			totalManagerHour.setSchedule(targetRegion);
		}

		return totalHours;
	}

	private List<TotalCustomerManagerHour> getDailyProjections(Customer customer, Date startDate, Date endDate){
		List<TotalCustomerManagerHour> totalHours = new ArrayList<TotalCustomerManagerHour>();
		
		Customer tmpCustomer = customerService.getCustomerById(customer);
		for(Region region: tmpCustomer.getRegions()) {
			TotalCustomerManagerHour totalManagerHour = new TotalCustomerManagerHour();
			totalManagerHour.setRegion(region);
			totalHours.add(totalManagerHour);
			BigDecimal targetRegion = SpmConstants.BD_ZERO_VALUE;
			for(Store store: region.getStores()) {
				BigDecimal targetStores = SpmConstants.BD_ZERO_VALUE;
				for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
					DailyProjection dailyProjection = getProjectionService().getDailyProjection(store, date);
					targetStores = targetStores.add(dailyProjection != null? dailyProjection.getDailyProjectionValue() :  SpmConstants.BD_ZERO_VALUE);
				}
				targetRegion = targetRegion.add(targetStores);
			}
			totalManagerHour.setSchedule(targetRegion);
		}
		return totalHours;
	}
	
    private List<TotalCustomerManagerHour> getStoreWages(Customer customer, Date startDate, Date endDate) {
        List<TotalCustomerManagerHour> totalHours = new ArrayList<TotalCustomerManagerHour>();
        
        Customer tmpCustomer = getCustomerService().getCustomerById(customer);
        for(Region region: tmpCustomer.getRegions()) {
            TotalCustomerManagerHour totalManagerHour = new TotalCustomerManagerHour();
            totalManagerHour.setRegion(region);
            totalHours.add(totalManagerHour);
            
            Double storeWages[] = new Double[2];
            storeWages[0] = SpmConstants.DOUBLE_ZERO_VALUE;
            storeWages[1] = SpmConstants.DOUBLE_ZERO_VALUE;

            for(Store store: region.getStores()) {
                for(Date date = startDate; endDate.compareTo(date) >= 0; date = CalendarUtils.addOrSubstractDays(date,1)){
                    StoreSchedule schedule = getScheduleService().getStoreScheduleByDate(store, date);
                    if(schedule != null) {
                        storeWages[0] += schedule.getAverageWage();
                        storeWages[1] += schedule.getTotalWage();
                    } else {
                        storeWages[0] += new Double(0.0);
                        storeWages[1] += new Double(0.0);
                    }
                }
                
            }
            
            totalManagerHour.setAverageWage(storeWages[0]);
            totalManagerHour.setTotalWage(storeWages[1]);
        }
    
        return totalHours;
    }
	
	private List<TotalCustomerManagerHour> merge(List<TotalCustomerManagerHour> actualSales, List<TotalCustomerManagerHour> scheduleHours, List<TotalCustomerManagerHour> targetHours, List<TotalCustomerManagerHour> wages, List<TotalCustomerManagerHour> projections) {
		
		List<TotalCustomerManagerHour> totalHours = new ArrayList<TotalCustomerManagerHour>();
		for(TotalCustomerManagerHour total: actualSales) {
			if(total.getSales() == null) total.setSales(SpmConstants.BD_ZERO_VALUE);
			TotalCustomerManagerHour scheduleHour = getTotalHour(total.getRegion(), scheduleHours); 
			total.setSchedule((scheduleHour.getSchedule() != null)? scheduleHour.getSchedule(): SpmConstants.BD_ZERO_VALUE);
			TotalCustomerManagerHour targetHour = getTotalHour(total.getRegion(), targetHours);
			total.setTarget(((targetHour.getSchedule() != null)? targetHour.getSchedule(): SpmConstants.BD_ZERO_VALUE));
			totalHours.add(total);
            if(wages != null) {
                TotalCustomerManagerHour wage = getTotalHour(total.getRegion(), wages);
                if(wage != null) {
                    total.setAverageWage(wage.getAverageWage());
                    total.setTotalWage(wage.getTotalWage());
                } else {
                    total.setAverageWage(SpmConstants.DOUBLE_ZERO_VALUE);
                    total.setTotalWage(SpmConstants.DOUBLE_ZERO_VALUE);
                }
                
                Region tmpRegion = getRegionService().getRegionById(total.getRegion());
                
                total.setAverageVariable(tmpRegion.getAverageVariable());
            }
            
            if(projections != null) {
                TotalCustomerManagerHour projectionHour = getTotalHour(total.getRegion(), projections);
                total.setProjections(((projectionHour.getSales() != null)? projectionHour.getSales() : SpmConstants.BD_ZERO_VALUE));
            }           
			
		}
		
		return totalHours;
	}
	
    private List<TotalCustomerManagerHour> mergeForecast(List<TotalCustomerManagerHour> actualHours, List<TotalCustomerManagerHour> scheduleHours) {

        List<TotalCustomerManagerHour> totalHours = new ArrayList<TotalCustomerManagerHour>();
        for(TotalCustomerManagerHour total: actualHours) {
            TotalCustomerManagerHour scheduleHour = getTotalHour(total.getRegion(), scheduleHours); 
            total.setSchedule((scheduleHour.getSchedule() != null)? scheduleHour.getSchedule(): SpmConstants.BD_ZERO_VALUE);
            total.setTarget(((total.getSales() != null)? total.getSales(): SpmConstants.BD_ZERO_VALUE));
            
            totalHours.add(total);
        }
        
        return totalHours;      
        
    }	
	
	private TotalCustomerManagerHour getTotalHour(Region region, List<TotalCustomerManagerHour> totalManagerHours) {
		
		for(TotalCustomerManagerHour totalManagerHour: totalManagerHours) {
			if(region.getId().equals(totalManagerHour.getRegion().getId())) {
				return totalManagerHour;
			}
		}
		
		return null;
	}
	/**
	 * @return the reportDao
	 */
	public ReportDao getReportDao() {
		return reportDao;
	}

	/**
	 * @param reportDao the reportDao to set
	 */
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	/**
	 * @return the staffingService
	 */
	public StaffingService getStaffingService() {
		return staffingService;
	}

	/**
	 * @param staffingService the staffingService to set
	 */
	public void setStaffingService(StaffingService staffingService) {
		this.staffingService = staffingService;
	}


	/**
	 * @return the customerService
	 */
	public CustomerService getCustomerService() {
		return customerService;
	}


	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return the projectionService
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}

	/**
	 * @param projectionService the projectionService to set
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}

    /**
     * @return the scheduleService
     */
    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    /**
     * @param scheduleService the scheduleService to set
     */
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * @return the regionService
     */
    public RegionService getRegionService() {
        return regionService;
    }

    /**
     * @param regionService the regionService to set
     */
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }
	
    
}

