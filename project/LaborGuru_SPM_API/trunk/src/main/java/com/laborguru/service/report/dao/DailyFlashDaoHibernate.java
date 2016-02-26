package com.laborguru.service.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.DailyFlash;
import com.laborguru.model.Shift;
import com.laborguru.model.Store;

public class DailyFlashDaoHibernate extends HibernateDaoSupport implements DailyFlashDao {

	private static Logger log = Logger.getLogger(DailyFlashDaoHibernate.class);
	
	public DailyFlash getDailyFlashById(DailyFlash dailyFlash) {
		if(log.isDebugEnabled()) {
			log.debug("Searching dailyFlash [" + dailyFlash + "]");
		}
		List<DailyFlash> dailyFlashList = (List<DailyFlash>)getHibernateTemplate().findByNamedParam("from DailyFlash d where d.id = :dailyFlashId", new String[]{"dailyFlashId"}, new Object[]{dailyFlash.getId()});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (dailyFlashList != null ? dailyFlashList.size() : "null") + "]");
		}
		return dailyFlashList != null && dailyFlashList.size() > 0 ? dailyFlashList.get(0) : null;		

	}

	public DailyFlash getDailyFlashByDate(Date date, Store store) {
		if(log.isDebugEnabled()){
			log.debug("Searching dailyFlash by [" + date + ", " + store + "]");
		}
		
		List<DailyFlash> dailyFlashList = (List<DailyFlash>)getHibernateTemplate().findByNamedParam("from DailyFlash d where d.date = :dailyDate and d.store = :store", new String[]{"dailyDate", "store"}, new Object[]{date, store});

		if(log.isDebugEnabled()) {
			log.debug("Found [" + (dailyFlashList != null ? dailyFlashList.size() : "null") + "]");
		}
		
		return dailyFlashList != null && dailyFlashList.size() > 0 ? dailyFlashList.get(0) : null;
	}

	public DailyFlash save(DailyFlash dailyFlash) {
		getHibernateTemplate().saveOrUpdate(dailyFlash);
		
		return dailyFlash;
	}

}
