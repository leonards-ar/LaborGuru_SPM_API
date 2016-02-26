package com.laborguru.webservice.sales.endpoint;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.UploadFile;
import com.laborguru.service.historicsales.HistoricSalesService;
import com.laborguru.service.store.StoreService;
import com.laborguru.service.uploadfile.UploadEnumType;
import com.laborguru.webservice.sales.binding.ImportSalesRequest;
import com.laborguru.webservice.sales.binding.ImportSalesResponse;

/**
 *
 * @author <a href="fbarreraoro">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
@Endpoint
public class SalesEndPoint {
	private static Logger logger = Logger.getLogger(SalesEndPoint.class);
	
	private StoreService storeService;
	private HistoricSalesService historicSalesService;
		
	@PayloadRoot(localPart = "importSalesRequest", namespace = "http://www.laborguru.com/webservices/sales")
	public ImportSalesResponse importSales(ImportSalesRequest request) {
		
		if(logger.isDebugEnabled()) logger.debug("request: " + request);
		try{
			List<HistoricSales> historicSales = request.bindDb(getStoreService());
			int processed = getHistoricSalesService().saveAll(historicSales, getUploadFile(request));
			ImportSalesResponse response = new ImportSalesResponse(historicSales.size(), processed);
			
			if(logger.isDebugEnabled()) logger.debug("response: " + response);
			return response;
		} catch(Exception e) {
			throw new SpmUncheckedException(e.getMessage(), ErrorEnum.GENERIC_ERROR);
		}
	}
	
	private UploadFile getUploadFile(ImportSalesRequest request) {
		UploadFile uploadFile = new UploadFile();
		DateTime dateTime = new DateTime();
		String fileName = "WS_" + request.getStoreCode() + "_" + request.getStoreLocation() + "_" +
			dateTime.getHourOfDay() + dateTime.getMinuteOfDay() + dateTime.getSecondOfDay();
		uploadFile.setUploadType(UploadEnumType.WEBSERVICE);
		uploadFile.setFilename(fileName);
		uploadFile.setUploadDate(dateTime.toDate());
		return uploadFile;
	}
	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService the storeService to set
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * @return the service
	 */
	public HistoricSalesService getHistoricSalesService() {
		return historicSalesService;
	}

	/**
	 * @param service the service to set
	 */
	public void setHistoricSalesService(HistoricSalesService historicSalesService) {
		this.historicSalesService = historicSalesService;
	}

}
