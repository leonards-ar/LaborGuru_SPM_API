package com.laborguru.service.store.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;

/**
 * Hibernate implementation for Store Dao 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreDaoHibernate extends SpmHibernateDao implements StoreDao {

	private static final Logger log = Logger.getLogger(StoreDaoHibernate.class);	
	
	private static final String STORE_ID_NULL = "the store id passed in as parameter is null";
	private static final String STORE_NULL = "the store passed in as parameter is null";
	
	
	/**
	 * @param store
	 * @see com.laborguru.service.store.dao.StoreDao#delete(com.laborguru.model.Store)
	 */
	public void delete(Store store) {
		
		if (store == null){
			throw new IllegalArgumentException(STORE_NULL);
		}
		
		if (store.getId() == null){
			throw new IllegalArgumentException(STORE_ID_NULL);
		}
		
		getHibernateTemplate().delete(store);
	}

	/**
	 * @param storeFilter
	 * @return
	 * @see com.laborguru.service.store.dao.StoreDao#filterStore(com.laborguru.model.filter.SearchStoreFilter)
	 */
	public List<Store> applyFilter(SearchStoreFilter storeFilter) {
		
		if (storeFilter == null){
			throw new IllegalArgumentException("The storeFilter passed in as paremeter is null.");
		}
		
		String hql = getHql(storeFilter);
		
		log.debug("In applyFilter with sql:"+ hql);
		
		return (List<Store>)getHibernateTemplate().find(hql);	
	}

	private String getHql(SearchStoreFilter storeFilter) {
		
		List<String> hqlParams = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder("from Store store");
		
		if (includeInFilter(storeFilter.getCustomerId())) {
			hqlParams.add("store.area.region.customer.id=" + storeFilter.getCustomerId());
		}

		//The code comparison matchs exact value. This behaviour It's required by the uploader. Do not modify without looking at
		//the consequences in the historic sales upload process. 
		if (includeInFilter(storeFilter.getCustomerCode())) {
			hqlParams.add("store.area.region.customer.code like '" + storeFilter.getCustomerCode()+"'");
		}		
		
		if (includeInFilter(storeFilter.getCode())){
			hqlParams.add("store.code like '%"+storeFilter.getCode()+"%'");
		}
		
		if (includeInFilter(storeFilter.getName())) {
			hqlParams.add("store.name like '%"+storeFilter.getName()+"%'");
		}
		
		int i=0;
		for (String param: hqlParams){
			if (i==0){
				sb.append(" where " + param);
			}else {
				sb.append(" and " + param);
			}
			
			i++;
		}
		
		return sb.toString();
	}
	
	/**
	 * @param store
	 * @return
	 * @see com.laborguru.service.store.dao.StoreDao#getStoreById(com.laborguru.model.Store)
	 */
	public Store getStoreById(Store store) {
		
		if (store == null){
			throw new IllegalArgumentException(STORE_NULL);
		}
		
		if (store.getId() == null){
			throw new IllegalArgumentException(STORE_ID_NULL);
		}		
		
		return (Store)getHibernateTemplate().get(Store.class, store.getId());
	}

	/**
	 * @param store
	 * @return
	 * @see com.laborguru.service.store.dao.StoreDao#save(com.laborguru.model.Store)
	 */
	public Store save(Store store) {

		if (store == null){
			throw new IllegalArgumentException(STORE_NULL);
		}
		
		getHibernateTemplate().saveOrUpdate(store);
		
		return store;
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.store.dao.StoreDao#findAll()
	 */
	public List<Store> findAll() {
		return (List<Store>)getHibernateTemplate().find("from Store");
	}

	/**
	 * @return
	 * @see com.laborguru.service.store.dao.StoreDao#findAllDemo()
	 */
	public List<Store> findAllDemo() {
		// Check Hibernate property hibernate.query.substitutions for "TRUE" sustitution
		return (List<Store>) getHibernateTemplate().find("from Store where demo = true");
	}
	
	/**
	 * Retrieves the number of stores
	 * @return the number of stores in the system.
	 * @see com.laborguru.service.store.dao.StoreDao#getNumberOfStores()
	 */
	public Integer getNumberOfStores() {
		List<Long> results = (List<Long>)getHibernateTemplate().find("select count(*) from Store");
		Long retVal = results.get(0);
		
		return Integer.valueOf(retVal.intValue());
	}


	/**
	 * Retrieves the n oldest/latest updated stores in the system 
	 * @param n The number of stores to retrieve
	 * @param order The order (latest/oldest)
	 * @return the list of stores
	 */
	public List<Store> findNStores(Integer n, OrderFindN order) {
		
		String orderFlag = "desc";
		
		
		if(OrderFindN.OLDEST.equals(order)){
			orderFlag = "asc";
		}
		
		List<Store> results = (List<Store>) getSession().createQuery("from Store order by creationDate "+orderFlag).setFirstResult(0).setMaxResults(n).list();
		
		return results;
	}
}
