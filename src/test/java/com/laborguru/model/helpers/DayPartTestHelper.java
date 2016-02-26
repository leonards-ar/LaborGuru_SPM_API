package com.laborguru.model.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.model.DayPart;

/**
 * DayPart Test Helper
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
public class DayPartTestHelper {

	public static DayPart getDayPart(String param) {
		return getDayPart(param, 1);
	}

	public static DayPart getDayPart(String param, int i) {
		DayPart dayPart = new DayPart();

		dayPart.setId(i);
		dayPart.setPositionIndex(i);
		dayPart.setName("name:" + param);
		dayPart.setStore(StoreTestHelper.getStore(param));
		dayPart.setStartHour(new Date());

		return dayPart;
	}

	public static List<DayPart> getDayPartList(String param, int size) {
		List<DayPart> list = new ArrayList<DayPart>();

		for (int i = 0; i < size; i++) {
			list.add(getDayPart("dayPart" + i, i));
		}

		return list;
	}
}
