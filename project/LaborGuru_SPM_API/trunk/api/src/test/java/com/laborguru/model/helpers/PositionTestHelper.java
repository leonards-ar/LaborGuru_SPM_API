package com.laborguru.model.helpers;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.model.Position;

/**
 * Position Test Helper
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionTestHelper {

	public static Position getPosition(String param){
		return getPosition(param, 0);
	}

	public static Position getPosition(String param, int index) {
		Position position = new Position();
		
		position.setId(index);
		position.setName("name_"+param+"_"+index);
		
		return position;
	}
	
	public static List<Position> getPositions(String param, int size){
		List<Position> positions = new ArrayList<Position>(size);
		
		for (int i=0; i < size; i++){
			positions.add(getPosition(param, i));
		}
		return positions;
	}
}
