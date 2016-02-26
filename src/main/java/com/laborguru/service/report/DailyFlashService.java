package com.laborguru.service.report;

import java.util.Date;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.DailyFlash;
import com.laborguru.model.Store;
import com.laborguru.service.Service;

public interface DailyFlashService extends Service{
	
	/**
	 * Retrieves a given dailyFlash
	 * @param dailyFlash The dailyFlash to retrieve (holds the dailyFlash id)
	 * @return
	 */
	DailyFlash getDailyFlashById(DailyFlash dailyFlash);
	
	/**
	 * Retrieves a given dailyFlash
	 * @param Date The date
	 * @param Store store
	 * @return
	 */
	DailyFlash getDailyFlashByDate(Store store, Date date);
	
	/**
	 * Saves or updates a dailyFlash
	 * @param region region to save or update
	 * @return the region updated.
	 * @throws SpmCheckedException In case there is any error during save
	 */
	DailyFlash save(DailyFlash dailyFlash) throws SpmCheckedException;	

}
