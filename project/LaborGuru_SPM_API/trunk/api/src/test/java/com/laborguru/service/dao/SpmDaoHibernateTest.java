package com.laborguru.service.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations={"/applicationContext-dao.xml"})
public abstract class SpmDaoHibernateTest {

    private IDatabaseTester databaseTester;
    
    @Autowired
    private DataSource myDataSourcePool;
    
    /**
     * This methods run before the execution of each test.
     * Sets the dataset and the operations to be executed on the database
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        databaseTester = new DataSourceDatabaseTester(myDataSourcePool);
        databaseTester.setSchema("spm");
        
        // initialize your dataset here
        IDataSet dataSet = getDataSet();
               
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
        
        //It will call default setUpOperation
        databaseTester.onSetup();
    }

    /**
     * This method runs after the execution of each test.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
    	//It will call default tearDownOperation
        databaseTester.onTearDown();
    }   
    
	/**
	 * Returns the dataset to be used on the test.
	 * The dataset will loaded/deleted on the database before and after each.
	 * 
	 * @return IDataSet the dataset
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected abstract IDataSet getDataSet() throws IOException, DataSetException ;
    
	/**
	 * @return the myDataSourcePool
	 */
	public DataSource getMyDataSourcePool() {
		return myDataSourcePool;
	}

	/**
	 * @param myDataSourcePool the myDataSourcePool to set
	 */
	public void setMyDataSourcePool(DataSource myDataSourcePool) {
		this.myDataSourcePool = myDataSourcePool;
	}    
}
