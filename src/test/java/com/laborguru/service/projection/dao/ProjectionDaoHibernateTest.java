package com.laborguru.service.projection.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.Store;
import com.laborguru.model.helpers.StoreTestHelper;
import com.laborguru.service.dao.SpmDaoHibernateTest;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectionDaoHibernateTest extends SpmDaoHibernateTest {

    @Autowired    
    private ProjectionDao projectionDao;

	/**
	 * Returns the dataset to be used on the test.
	 * The dataset will loaded/deleted on the database before and after each.
	 * 
	 * @return IDataSet the dataset
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected IDataSet getDataSet() throws IOException, DataSetException {
		return new FlatXmlDataSet(this.getClass().getResourceAsStream("dataset_projection.xml"));
	}
	
	@Test
	public void getAvgHalfHourProjectionTest() throws ParseException {
		Store store = StoreTestHelper.getStore("balvanera", 100);
		Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse("2008-08-20");
		Integer numberOfWeeks = new Integer(4);
		
		List<HalfHourProjection> projections = projectionDao.getAvgHalfHourProjection(numberOfWeeks, store, selectedDate);
		
		assertNotNull(projections);
		
		assertTrue(projections.size() > 0);
		
		for(HalfHourProjection aProjection: projections){
			System.out.println("halfHourProjection: " + aProjection);
		}
		
	}
    

}
