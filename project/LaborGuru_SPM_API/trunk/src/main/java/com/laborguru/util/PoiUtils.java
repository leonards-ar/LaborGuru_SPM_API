package com.laborguru.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PoiUtils {

	/**
	 * Private constructor - Enforces non instanciation of the utility class
	 */
	private PoiUtils(){
		
	}
	
	/**
	 * @param cell
	 * @return
	 */
	public static String getStringValue(Cell cell){
		if ( (cell!= null) && (cell.getCellType() == Cell.CELL_TYPE_STRING)){
			return cell.getStringCellValue().trim();
		} else {
			Object val = getIntegerValue(cell);
			if(val != null) {
				return val.toString();
			}
			val = getDoubleValue(cell);
			if(val != null) {
				return val.toString();
			}
			val = getDateValue(cell);
			if(val != null) {
				return val.toString();
			}
		}
		
		return null;
	}
	
	/**
	 * @param cell
	 * @return
	 */
	public static Date getDateValue(Cell cell){
		if ( (cell != null) && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) && (HSSFDateUtil.isCellDateFormatted(cell))){
			return cell.getDateCellValue();
		}
		
		return null;
	}


	/**
	 * @param cell
	 * @return
	 */
	public static Double getDoubleValue(Cell cell){
		if ((cell != null) && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) && !(HSSFDateUtil.isCellDateFormatted(cell))){
			return cell.getNumericCellValue();
		}
		
		return null;
	}
	
	/**
	 * @param cell
	 * @return
	 */
	public static Integer getIntegerValue(Cell cell){
		if ((cell != null) && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) && !(HSSFDateUtil.isCellDateFormatted(cell))){
			return new Integer(((int)cell.getNumericCellValue()));
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param cell
	 * @return
	 */
	public static boolean getBooleanValue(Cell cell) {
		Object o;
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if((o = getIntegerValue(cell)) !=  null) {
			return ((Integer ) o).intValue() == 1;
		} else if((o = getStringValue(cell)) !=  null) {
			return o.toString().equalsIgnoreCase("y") || o.toString().equalsIgnoreCase("yes") || o.toString().equalsIgnoreCase("t") || o.toString().equalsIgnoreCase("true") || o.toString().equalsIgnoreCase("1");
		}
		return false;
	}
	
	public static Iterator<Row> getFirstSheetRows(File file) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(in);

			HSSFSheet sheet = wb.getSheetAt(0);

			return sheet.rowIterator();
		} catch (OfficeXmlFileException ex) {
			close(in);
			in = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(in);
			XSSFSheet sheet = wb.getSheetAt(0);

			return sheet.rowIterator();
		} finally {
			close(in);
		}
	}
	
	private static void close(InputStream is) {
		if(is != null) {
			try {
				is.close();
			} catch(Throwable ex) {
				
			}
		}
	}
}
