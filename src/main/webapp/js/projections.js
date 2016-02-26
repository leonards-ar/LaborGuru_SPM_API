/*****************************************
 * Functions used to handle total in daily projections and actuals page
 *****************************************/
	function updateProjectionRowValue(objectId, variableName, totalId){
		truncateDailyProjectionValue(objectId, 'i');
		updateTotalRow(variableName, totalId, 'i');
	}

	
	
	function updateDoubleRowValue(objectId, variableName, totalId){
		truncateDailyProjectionValue(objectId, 'd');
		updateTotalRow(variableName, totalId,'d');
	}
	
	function getNumberFromObject(objectId, numberType){
		
		var number = getObjectByIDValue(objectId,0).replace(/\,/, "");
		var truncatedValue = 0;
		if (numberType == 'i'){
			truncatedValue = toInt(number);
		}else{
			//Setting only 2 decimal for the number
			var auxNumber = toDouble(number).toFixed(2);
			//As toFixed returns an string we need to convert the string back to a number
			truncatedValue = (+auxNumber);
		}
		
		if(isNaN(truncatedValue)) {
			truncatedValue = 0;
		}

		return truncatedValue;
	}
	
	function truncateDailyProjectionValue(objectId, numberType){
		var truncatedValue = getNumberFromObject(objectId, numberType);
		setObjectByIDValue(objectId, truncatedValue);				
	}
		
	function updateTotalRow(variableName, totalId, numberType){
		var totalValue = 0;
		for (projectionIndexRow=0; projectionIndexRow < 7; projectionIndexRow++){
			var dailyValue = getNumberFromObject(variableName+'['+projectionIndexRow+']', numberType);
			totalValue += dailyValue;		
		}
		
		if (numberType != 'i')
		{
			totalValue = totalValue.toFixed(2);
		}
		
		setObjectByIDValueAndClass(totalId, totalValue, null);
		setObjectByIDValue(totalId+'Input', totalValue, null);		
	}
		