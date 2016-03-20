package com.laborguru.service.historicsales;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.laborguru.model.DailyHistoricSales;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Store;
import com.laborguru.model.UploadFile;
import com.laborguru.service.historicsales.dao.HistoricSalesDao;

/**
 * Handles historic sales services
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface HistoricSalesService {
	
	/**
	 * Returns a DailyHistoricSales object populated with the sales information for the date passed as parameter.
	 * 
	 * @param store
	 * @param date
	 * @return The DailyHistoricSales
	 */
	DailyHistoricSales getDailyHistoricSales(Store store, Date date);

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	BigDecimal getTotalHistoricSalesForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 * @param dao
	 */
	void setHistoricSalesDao(HistoricSalesDao dao);
	
	/**
	 * @param historicSales
	 * @return
	 */
	int saveAll(List<HistoricSales> historicSales, UploadFile uploadFile);

	/**
	 * @param dailyHistoricSales
	 * @param projectionAmount
	 * @return
	 */
	DailyHistoricSales calculateHistoricSalesStaticProjection(DailyHistoricSales dailyHistoricSales, BigDecimal projectionAmount);

	/**
	 * @param dailyHistoricSales
	 */
	void saveDailyHistoricSales(DailyHistoricSales dailyHistoricSales);

	/**
	 * @param historicSales
	 */
	HistoricSales createOrReplace(HistoricSales historicSales);
	
}
