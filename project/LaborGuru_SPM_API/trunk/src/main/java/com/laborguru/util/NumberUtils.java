package com.laborguru.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * Utility class to handle number related methods
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class NumberUtils {

	private final static Logger log = Logger.getLogger(NumberUtils.class);

	public static final String DECIMAL_FORMAT = "#,##0.###";
	
	//Used in projections
	public static final String CURRENCY_FORMAT = "#,##0";
	
	public static final Double EMPTY_DOUBLE = null;
	
	/**
	 *
	 */
	private NumberUtils() {
	}
	
	/**
	 * Parse a number with a specific format passed as parameter.
	 * 
	 * @param the number to parser
	 * @return
	 */
	public static Number stringToNumber(String number, String format) {
		try {
			return new DecimalFormat(format).parse(number);
		} catch (ParseException ex) {
			log.error("Cannot parse number:" + number, ex);
			return null;
		}
	}

	/**
	 * Parse an integer with a specific format passed as parameter.
	 * 
	 * @param the number to parser
	 * @return
	 */
	public static Integer stringToInteger(String number) {
		if(number == null) {
			return null;
		} else {
			try {
				return new Integer(number);
			} catch (Throwable ex) {
				log.error("Cannot parse number:" + number, ex);
				return null;
			}
		}
	}
	
	/**
	 * Returns the string representation for a given number and format
	 * @param time
	 * @return
	 */
	public static String numberToString(Number number, String format) {
			return new DecimalFormat(format).format(number);
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static double getDoubleValue(Double d) {
		return d != null && !d.isNaN() && !d.isInfinite() ? d.doubleValue() : 0.0;
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static double getDoubleValue(BigDecimal bd) {
		return bd != null ? bd.doubleValue() : 0.0;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public static int getIntegerValue(Integer i) {
		return i != null ? i.intValue() : 0;
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static boolean isValid(Double d) {
		return d != null && !d.isNaN() && !d.isInfinite();
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public static boolean isValid(Integer i) {
		return i != null;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public static boolean isValidInteger(String number) {
		try {
			new Integer(number);
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}
	
	/**
	 * Used at projection pages (front end)
	 * @param bd
	 * @return
	 */
	public static int bigDecimalToInt(BigDecimal bd){
		if (bd != null)
		{
			float floatResult = bd.floatValue();
			return Math.round(floatResult);
		}
		
		return 0;
	}
}
