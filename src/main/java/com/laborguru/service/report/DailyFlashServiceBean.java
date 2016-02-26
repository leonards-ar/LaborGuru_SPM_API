package com.laborguru.service.report;

import java.util.Date;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.DailyFlash;
import com.laborguru.model.Store;
import com.laborguru.service.report.dao.DailyFlashDao;

public class DailyFlashServiceBean implements DailyFlashService {

	DailyFlashDao dailyFlashDao;
	
	
	public DailyFlashDao getDailyFlashDao() {
		return dailyFlashDao;
	}

	public void setDailyFlashDao(DailyFlashDao dailyFlashDao) {
		this.dailyFlashDao = dailyFlashDao;
	}

	public DailyFlash getDailyFlashById(DailyFlash dailyFlash) {
		return getDailyFlashDao().getDailyFlashById(dailyFlash);
	}

	public DailyFlash getDailyFlashByDate(Store store, Date date) {
		return getDailyFlashDao().getDailyFlashByDate(date, store);
	}

	public DailyFlash save(DailyFlash dailyFlash) throws SpmCheckedException {
		
		return getDailyFlashDao().save(dailyFlash);
	}

}
