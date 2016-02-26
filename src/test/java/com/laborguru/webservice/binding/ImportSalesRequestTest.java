package com.laborguru.webservice.binding;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

import com.laborguru.util.CalendarUtils;
import com.laborguru.webservice.sales.binding.ImportSalesRequest;

import junit.framework.TestCase;

public class ImportSalesRequestTest extends TestCase {
	
	public ImportSalesRequestTest(String name) {
		super(name);
	}
	
	public void testMarshall() throws Exception{
		
		Document document = DocumentHelper.createDocument();
		Namespace namespace = DocumentHelper.createNamespace("sal", "http://www.laborguru.com/webservices/sales");
		Element salesElement = document.addElement("importSalesRequest");
		Element storeCodeElement = salesElement.addElement("storeCode");
		Element storeLocationElement = salesElement.addElement("storeLocation");
		Element salesDateElement = salesElement.addElement("salesDate");
		Element salesItemListElement = salesElement.addElement("salesItemList");
		Element salesItemElement = salesItemListElement.addElement("salesItem");
		
		salesElement.add(namespace);
		storeCodeElement.add(namespace);
		storeLocationElement.add(namespace);
		salesDateElement.add(namespace);
		salesItemListElement.add(namespace);
		salesItemElement.add(namespace);
		storeCodeElement.addText("ABP_1");
		storeLocationElement.addText("Boston");
		salesDateElement.addText("05/04/2009");
		
		Element halfHourElement = salesItemElement.addElement("halfHour");
		Element mainValueElement = salesItemElement.addElement("mainValue");
		halfHourElement.add(namespace);
		mainValueElement.add(namespace);
		halfHourElement.addText("12:24 a");
		mainValueElement.addText("14.34");
		
		salesItemElement = salesItemListElement.addElement("salesItem");
		halfHourElement = salesItemElement.addElement("halfHour");
		mainValueElement = salesItemElement.addElement("mainValue");
		halfHourElement.add(namespace);
		mainValueElement.add(namespace);
		halfHourElement.addText("12:25 a");
		mainValueElement.addText("20.46");
		
		
		IBindingFactory bfact = BindingDirectory.getFactory(ImportSalesRequest.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        StringReader reader = new StringReader(salesElement.asXML());
        ImportSalesRequest importSales = (ImportSalesRequest)uctx.unmarshalDocument(reader);
        
        assertEquals("ABP_1", importSales.getStoreCode());
        assertEquals("Boston", importSales.getStoreLocation());
        assertEquals(CalendarUtils.stringToDate("05/04/2009", "mm/dd/yyyy"), importSales.getSalesDate());
        assertEquals(2, importSales.getSalesItemList().getSalesItemLists().size());
        
        IMarshallingContext mctx = bfact.createMarshallingContext();
        
        mctx.setOutput(System.out, null);
        mctx.marshalDocument(importSales);
        
	}
	

}
