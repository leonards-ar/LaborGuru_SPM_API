package com.laborguru.service.store.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.model.Store;
import com.laborguru.util.PoiUtils;

/**
 * Parses a store definition file
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreDefinitionFileParserBean implements StoreDefinitionFileParser {

	private static final Logger log = Logger.getLogger(StoreDefinitionFileParserBean.class);

	/**
	 * @param storeToUpload
	 * @return
	 * @see com.laborguru.service.store.file.StoreDefinitionFileParser#parseStore(java.io.File)
	 */
	public Store parseStore(File storeToUpload) {
		
		try {
			 
			 StoreAssembler storeAssembler = StoreAssembler.getStoreAssembler();

			 Iterator<Row> rit = PoiUtils.getFirstSheetRows(storeToUpload);
			 			 
			 //Ignoring the header
			 rit.next();
			 
			 while(rit.hasNext()) {
				Row row = rit.next();
				storeAssembler.addToStore(row);
			}
			 
			 return storeAssembler.assemble();			 

		} catch (FileNotFoundException exceptionFNF) {
			String message = "File to parse:"+storeToUpload.getName()+ "is not found";
			log.error(message);
			throw new InvalidUploadFileException(exceptionFNF, message);
		} catch (IOException e) {
			String msg = "The file " + storeToUpload.getName() +" passed in as parameter cannot be read.";
			log.error(msg);
			throw new InvalidUploadFileException(msg);			
		}
	}
}
