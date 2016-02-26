package com.laborguru.action.report;

import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.model.DailyFlashSales;
import com.laborguru.model.DailyStaffing;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyFlashStaffing;
import com.laborguru.model.report.HalfHourIdealSales;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.service.store.StoreService;
import com.opensymphony.xwork2.Action;

public class DailyFlashIdealHoursAction extends SpmAction {
	
	private static final long serialVersionUID = 1L;
	
	private StoreService storeService;
	private StaffingService staffingService;
	
	
	private transient List<HalfHourIdealSales> inputSales;
	private transient String storeId;
	
	private DailyStaffing dailyStaffing;
	
	private String responseMessage;
	
	public String execute() {
		
		Date today = new Date();
		Store searchStore = new Store();
		searchStore.setId(Integer.parseInt(getStoreId()));
		
		Store store = getStoreService().getStoreById(searchStore);
		DailyFlashSales dailySalesValue = new DailyFlashSales();
		
		for(HalfHourIdealSales idealSales: getSales()) {
			dailySalesValue.addHalfHourFlashSales(idealSales.getHalfHourFlashSales());
		}

		dailySalesValue.setSalesDate(today);
		dailySalesValue.setStore(store);
		
		StoreDailyFlashStaffing dailyFlashStaffing = staffingService.getDailyFlashSalesStaffingByDate(store,today, dailySalesValue);
		
		String idealHours = "";
		for(int i=0; i < getSales().size();i++){
			idealHours+= (i==0?"":",")  + dailyFlashStaffing.getHalfHourStaffing(getSales().get(i).getTime());
		}
		
		setResponseMessage(idealHours);
		
		return Action.SUCCESS;
		
	}
	
	public List<HalfHourIdealSales> getSales() {
		return inputSales;
	}

	public void setSales(List<HalfHourIdealSales> sales) {
		this.inputSales = sales;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public DailyStaffing getDailyStaffing() {
		return dailyStaffing;
	}

	public void setDailyStaffing(DailyStaffing dailyStaffing) {
		this.dailyStaffing = dailyStaffing;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public StaffingService getStaffingService() {
		return staffingService;
	}

	public void setStaffingService(StaffingService staffingService) {
		this.staffingService = staffingService;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}	
	
}
