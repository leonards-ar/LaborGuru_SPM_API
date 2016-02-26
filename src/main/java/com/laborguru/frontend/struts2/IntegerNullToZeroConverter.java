package com.laborguru.frontend.struts2;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.util.TypeConversionException;

/**
 * Struts converter that handles the Integer conversion setting a default value 0, when 
 * receives null or empty.
 * It is used by Utilization Limit CRUD.
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class IntegerNullToZeroConverter extends StrutsTypeConverter {

	/**
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		String number = values[0];

		if((number == null) || ("".equals(number.trim()))){
			return new Integer(0);
		}
		
		try{
			return new Integer(number); 
		}catch(NumberFormatException ex){
			throw new TypeConversionException("Cannot parse integer [" + number + "]", ex);
		}
		
	}

	/**
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@Override
	public String convertToString(Map map, Object object) {
		if (object != null)
			return ((Integer)object).toString();
		
		return null;
	}

}
