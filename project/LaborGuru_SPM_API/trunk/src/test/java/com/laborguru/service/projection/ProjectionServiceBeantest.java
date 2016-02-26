package com.laborguru.service.projection;

import static org.easymock.EasyMock.createMock;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.laborguru.service.projection.dao.ProjectionDao;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ProjectionServiceBeantest {

	private ProjectionService projectionService;
	private ProjectionDao projectionDao;
	
	@Before
	public void setUp(){
		projectionService = new ProjectionServiceBean();
		
		projectionDao = createMock(ProjectionDao.class);
		projectionService.setProjectionDao(projectionDao);
	}
	
	@Test
	public void calculateFixedDistribution_bigDecimal(){
	/*	List<BigDecimal> values = new ArrayList<BigDecimal>();
		
		BigDecimal totalSales = new BigDecimal("5500").setScale(2);
		BigDecimal totalFixed = new BigDecimal("700").setScale(2);
		BigDecimal totalOldProjects = new BigDecimal("255").setScale(2);
		values.add(new BigDecimal("6.00").setScale(2));
		values.add(new BigDecimal("162.00").setScale(2));
		values.add(new BigDecimal("318.00").setScale(2));
		values.add(new BigDecimal("445.00").setScale(2));
		values.add(new BigDecimal("514.00").setScale(2));
		values.add(new BigDecimal("481.00").setScale(2));
		values.add(new BigDecimal("344.00").setScale(2));
		values.add(new BigDecimal("292.00").setScale(2));
		values.add(new BigDecimal("256.00").setScale(2));
		values.add(new BigDecimal("215.00").setScale(2));
		values.add(new BigDecimal("183.00").setScale(2));
		values.add(new BigDecimal("179.00").setScale(2));
		values.add(new BigDecimal("209.00").setScale(2));
		values.add(new BigDecimal("250.00").setScale(2));
		values.add(new BigDecimal("299.00").setScale(2));
		values.add(new BigDecimal("390.00").setScale(2));
		values.add(new BigDecimal("348.00").setScale(2));
		values.add(new BigDecimal("298.00").setScale(2));
		values.add(new BigDecimal("179.00").setScale(2));
		values.add(new BigDecimal("76.00").setScale(2));
		values.add(new BigDecimal("56.00").setScale(2));
		
		values = projectionService.calculateFixedDistribution(totalSales, totalFixed, totalOldProjects, values);
		
		//Setting fixed values
		values.set(18, new BigDecimal("500.00").setScale(2));
		values.set(19, new BigDecimal("200.00").setScale(2));
		
		BigDecimal totalNewValues = sumatory(values).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		if(totalNewValues.subtract(totalSales).abs().doubleValue() <= 0.01) {
			totalNewValues = totalNewValues.add(new BigDecimal("0.01"));
		}
		assertEquals(totalSales, totalNewValues);
		*/
	}
	
	
	private BigDecimal sumatory(List<BigDecimal> values) {
		BigDecimal total = new BigDecimal("0");
		for(BigDecimal value: values) {
			System.out.println("value: " + value);
			total = total.add(value);
		}
		return total;
	}
	

}
