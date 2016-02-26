package com.laborguru.service.report.dao;

import java.io.IOException;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.laborguru.model.helpers.ReportTestHelper;
import com.laborguru.model.report.TotalHour;
import com.laborguru.service.dao.SpmDaoHibernateTest;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SqlMapReportDaoTesting extends SpmDaoHibernateTest {

	@Autowired
	private ReportDao reportDao;
	
	/**
	 * Returns the dataset to be used on the test.
	 * The dataset will loaded/deleted on the database before and after each.
	 * 
	 * @return IDataSet the dataset
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected IDataSet getDataSet() throws IOException, DataSetException {
		return new FlatXmlDataSet(this.getClass().getResourceAsStream("dataset_report.xml"));
	}
	
    @Test
	public void testFindAll_happyPath() throws Exception {
    	List<TotalHour> totalHours = reportDao.getScheduleWeeklyTotalHour(ReportTestHelper.getStore(), ReportTestHelper.getStartDate(), ReportTestHelper.getEndDate());
    	
    	System.out.println(totalHours.toString());
    	
    	
	}


}
