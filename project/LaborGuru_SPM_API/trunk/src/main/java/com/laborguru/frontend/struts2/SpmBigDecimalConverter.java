package com.laborguru.frontend.struts2;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.laborguru.util.NumberUtils;
import com.opensymphony.xwork2.util.TypeConversionException;

/**
 * Struts converter that handles the String-BigDecimal conversion for values
 * in SPM. It is used on the projections module.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SpmBigDecimalConverter extends StrutsTypeConverter {

	private final static Logger log = Logger.getLogger(SpmBigDecimalConverter.class);

	/**
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	public Object convertFromString(Map context, String[] values, Class toClass) {
		String value = values[0];
		
		if (value == null || "".equals(value.trim())){
			return value;
		}
					
		Number auxValue = NumberUtils.stringToNumber(value.trim(), NumberUtils.DECIMAL_FORMAT);
		
		if (auxValue != null)
			return  new BigDecimal(auxValue.toString());
		
		log.error("Not number valid expression [" + value + "]");
		throw new TypeConversionException("Not number valid expression [" + value + "]");					
	}


	/**
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	public String convertToString(Map context, Object arg1) {
		return NumberUtils.numberToString((BigDecimal)arg1, NumberUtils.DECIMAL_FORMAT);
	}	
}
