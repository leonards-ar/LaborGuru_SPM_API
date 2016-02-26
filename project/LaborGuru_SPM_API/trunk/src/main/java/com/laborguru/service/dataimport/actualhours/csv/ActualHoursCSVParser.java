package com.laborguru.service.dataimport.actualhours.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.laborguru.exception.FileParserException;
import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.service.dataimport.actualhours.ActualHoursFileParser;

public abstract class ActualHoursCSVParser implements ActualHoursFileParser {

	protected static final Logger log = Logger.getLogger(ActualHoursCSVParser.class);
	
	protected int allLinesCounter;
	protected int validLinesCounter;
	protected int errorLinesCounter;
	protected CSVReader csvReader;
	protected String filename;


	/**
	 * This method initializes the parser with file we are going to process.
	 * @param fileToParse
	 * @return
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#assembleSalesFileParser(java.io.File)
	 */
	public ActualHoursFileParser assembleFileParser(File fileToParse, int ignoreLines) {
	
		FileReader inReader;
	
		try {
			inReader = new FileReader(fileToParse);
		} catch (FileNotFoundException e) {
			String message = "File to parse:"+fileToParse.getName()+ "is not found";
			log.error(message);
			throw new InvalidUploadFileException(e, message);			
		}
		
		this.filename = fileToParse.getName();
		this.csvReader = new CSVReader(inReader);
		this.allLinesCounter = 0;
		this.errorLinesCounter = 0;
		this.validLinesCounter = 0;
		
		if (ignoreLines > 0){
			for (int i=0; i < ignoreLines; i++){
				try {
					
					csvReader.readNext();
					allLinesCounter++;
					
				} catch (IOException e) {
					String message = "Error reading the line:"+allLinesCounter+ " on the file:"+this.filename;
					log.error(message);
					throw new FileParserException(e, message);			
				}
			}
		}
		
		return this;
	}

	/**
	 * Close underlying reading and release reources.
	 * Don't forget to call this method before you finish to preocess the upload.
	 * @throws IOException 
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#close()
	 */
	public void close() {
		try {
			this.csvReader.close();
		} catch (IOException e) {
			String message = "Error - Trying to close csv reader:"+this.csvReader+ " - IO reader for the file:"+this.filename+"could not be closed";
			log.error(message);
			throw new FileParserException(e, message);			
		}
	}

	/**
	 * @return the allLinesCounter
	 */
	public int getAllLinesCounter() {
		return allLinesCounter;
	}

	/**
	 * @return the validLinesCounter
	 */
	public int getValidLinesCounter() {
		return validLinesCounter;
	}

	/**
	 * @return the allLinesCounter
	 */
	public int getErrorLinesCounter() {
		return errorLinesCounter;
	}

}