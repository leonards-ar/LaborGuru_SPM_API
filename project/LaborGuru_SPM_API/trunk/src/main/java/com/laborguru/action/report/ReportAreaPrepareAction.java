package com.laborguru.action.report;

import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.model.report.TotalAreaManagerHour;
import com.laborguru.service.report.ReportAreaService;

public class ReportAreaPrepareAction extends ReportManagerBaseAction {

    private static final long serialVersionUID = 1L;

    private ReportAreaService reportAreaService;
    
    private List<TotalAreaManagerHour> totalManagerHours;
    
    private static String reportObject = "report.manager.store"; 
    
    private Area area;    
    @Override
    protected void performanceEfficiency() {
        setTotalManagerHours(reportAreaService.getPerformanceEfficiencyReport(getArea(), getStartDate(), getEndDate()));
    }

    @Override
    protected void schedulingEfficiency() {
        setTotalManagerHours(reportAreaService.getWeeklyTotalHours(getArea(), getStartDate(), getEndDate()));
    }

    @Override
    protected void scheduleExecutionEfficiency() {
        setTotalManagerHours(reportAreaService.getScheduleExecutionEfficiencyReport(getArea(), getStartDate(), getEndDate()));
    }

    @Override
    protected void forecastEfficiency() {
        setTotalManagerHours(reportAreaService.getForecastEfficiencyReport(getArea(), getStartDate(), getEndDate()));
    }

    /**
     * @param reportAreaService the reportAreaService to set
     */
    public void setReportAreaService(ReportAreaService reportAreaService) {
        this.reportAreaService = reportAreaService;
    }

    
    /**
     * @return the totalManagerHours
     */
    public List<TotalAreaManagerHour> getTotalManagerHours() {
        return totalManagerHours;
    }

    /**
     * @param totalManagerHours the totalManagerHours to set
     */
    public void setTotalManagerHours(List<TotalAreaManagerHour> totalManagerHours) {
        this.totalManagerHours = totalManagerHours;
    }

    /**
     * @return the region
     */
    public Area getArea() {
        if(area == null) {
            return super.getArea();
        }
        return area;
    }

    /**
     * @param region the region to set
     */
    public void setArea(Area area) {
        this.area= area;
    }
    
    /**
     * @return the reportObject
     */
    public String getReportObject() {
        return reportObject;
    }

}
