package com.laborguru.service.store.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.laborguru.model.Area;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.model.helpers.StoreTestHelper;
import com.laborguru.service.area.dao.AreaDao;
import com.laborguru.service.dao.SpmDaoHibernateTest;

/**
 * StoreDaoHibernate Test
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StoreDaoHibernateTest extends SpmDaoHibernateTest {
    
    @Autowired    
    private StoreDao storeDao;

    @Autowired    
    private AreaDao areaDao;
    
	/**
	 * Returns the dataset to be used on the test.
	 * The dataset will loaded/deleted on the database before and after each.
	 * 
	 * @return IDataSet the dataset
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected IDataSet getDataSet() throws IOException, DataSetException {
		return new FlatXmlDataSet(this.getClass().getResourceAsStream("dataset_store.xml"));
	}
    
    @Test
	public void testFindAll_happyPath(){
    	List<Store> stores = storeDao.findAll();
    	assertEquals("incorrect number of stores",4, stores.size());
	}

    @Test
	public void testGetById_happyPath(){
    	Store store = StoreTestHelper.getStore("microcentro");
    	
    	Store retStore = storeDao.getStoreById(store);
    	
    	assertEquals("Stores are different",StoreTestHelper.getStore("microcentro"), retStore);
	}

    @Test(expected = IllegalArgumentException.class)
	public void testGetById_StoreIdNull(){
    	Store store = new Store();
    	
    	storeDao.getStoreById(store);    	
	}    
    
    @Test
	public void testApplyFilter_happyPathCustomer(){
    	SearchStoreFilter storeFilter = new SearchStoreFilter();
    	storeFilter.setCustomerId(1);
    	
    	List<Store> stores = storeDao.applyFilter(storeFilter);
    	assertEquals("incorrect number of stores",4, stores.size());
	}    

    @Test
	public void testApplyFilter_happyPathName(){
    	SearchStoreFilter storeFilter = new SearchStoreFilter();
    	storeFilter.setName("caba");
    	
    	List<Store> stores = storeDao.applyFilter(storeFilter);
    	assertEquals("incorrect number of stores",1, stores.size());
	}
    
    
    @Test
	public void testApplyFilter_EmptyFilter(){
    	SearchStoreFilter storeFilter = new SearchStoreFilter();
    	
    	List<Store> stores = storeDao.applyFilter(storeFilter);
    	assertEquals("incorrect number of stores",4, stores.size());
	}
    
    @Test
	public void testSaveRemove_happyPath(){
    	Store store = StoreTestHelper.getStore("China Town");
    	Area area = new Area();
    	area.setId(1);
    	
    	store.setArea(areaDao.getAreaById(area));
    	store.setId(null);
    	
    	Store retStore = storeDao.save(store);
    	
    	assertNotNull("Store id null", retStore.getId());

    	SearchStoreFilter storeFilter = new SearchStoreFilter();
    	storeFilter.setName("China");
    	
    	List<Store> stores = storeDao.applyFilter(storeFilter);
    	assertEquals("incorrect number of stores",1, stores.size());
    	
    	storeDao.delete(retStore);
    	
    	List<Store> emptyStores = storeDao.applyFilter(storeFilter);
    	assertEquals("incorrect number of stores",0, emptyStores.size());    	
	}   
}