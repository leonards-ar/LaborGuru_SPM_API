package com.laborguru.service.report.dao;

import java.util.Date;

import com.laborguru.model.DailyFlash;
import com.laborguru.model.Store;

public interface DailyFlashDao {
	
	DailyFlash getDailyFlashById(DailyFlash dailyFlash);
	
	DailyFlash getDailyFlashByDate(Date date, Store store);
	
	DailyFlash save(DailyFlash dailyFlash);

}
