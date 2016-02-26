/*****************************************
 * COMMON VARIABLES
 *****************************************/
var TOTAL_COLS = 0;
var TOTAL_ROWS = [0];
var POSITION_IDS;
var ONE_DAY_MINUTES = 24 * 60;
/*****************************************
 * DAILY SCHEDULE VARIABLES
 *****************************************/
/*
   Possible Actions are:
     1: set working time
	 2: set break
	 3: delete
*/
var scheduleAction = 1;

/*
   Possible States are:
     1: Ready to set working time
	 2: Setting working time
	 3: Ready to set break
	 4: Setting break
	 5: Ready to delete
	 6: Deleting
*/
var scheduleState = 1;

var fromColumn;
var toColumn;
var currentRow;
var currentScheduleId;
var previousClassName;
var onMouseOutEnabled = true;
var TIME_INTERVAL = 15;
var BREAK = null;
var CANNOT_CHANGE_ROW_MSG = null;
var END_TIME_MSG = null;
var START_TIME_MSG = null;
var SCHEDULE_IDS = [''];


/*****************************************
 * COMMON FUNCTIONS
 *****************************************/


function addPositionId(index, positionId) {
	POSITION_IDS[index] = positionId;
}

function addScheduleTotalRows(index, totalRows) {
	TOTAL_ROWS[index] = totalRows;
}

/*****************************************
 * DAILY SCHEDULE FUNCTIONS
 *****************************************/
function initialize(totalCols, breakTxt, cannotChangeRowMsg, startTimeMsg, endTimeMsg, positionsQty, schedulesQty) {
	TOTAL_COLS = totalCols;
	TOTAL_ROWS = new Array(schedulesQty);
	BREAK = breakTxt;
	CANNOT_CHANGE_ROW_MSG = cannotChangeRowMsg;
	START_TIME_MSG = startTimeMsg;
	END_TIME_MSG = endTimeMsg;
	POSITION_IDS = new Array(positionsQty);
}

function initializeMultiSchedule(schedulesQty) {
	SCHEDULE_IDS = new Array(schedulesQty);
	for(var i=0; i < schedulesQty; i++) {
		SCHEDULE_IDS[i] = i;
	}
}

function getTotalRows(scheduleId) {
	if(scheduleId == '' || isNaN(scheduleId)) {
		return TOTAL_ROWS[0];
	} else {
		scheduleId = toInt(scheduleId);
		return TOTAL_ROWS[scheduleId];
	}
}

function scheduleClick(tdObj, rowNum, colNum, defaultPositionId, scheduleId)
{
	setMessage('', scheduleId);
	if(scheduleState == 1 || scheduleState == 3 || scheduleState == 5) {
		currentScheduleId = scheduleId;
		currentRow = rowNum;
		fromColumn = colNum;
		toColumn = null;
		tdObj.className = 'scheduleStartSelection';
		onMouseOutEnabled = false;
		scheduleState += 1;
		setMessage(END_TIME_MSG, scheduleId);
	} else if(scheduleState == 2 || scheduleState == 4 || scheduleState == 6) {
		if(currentRow != rowNum || currentScheduleId != scheduleId) {
			setMessage(CANNOT_CHANGE_ROW_MSG, scheduleId);
		} else {
			tdObj.className = 'scheduleEndSelection';
			if(colNum < fromColumn) {
				toColumn = fromColumn;
				fromColumn = colNum;
			} else {
				toColumn = colNum;
			}
			
			for(var i = fromColumn; i <= toColumn; i++) {
				var cell = getObjectByID(scheduleId + 'cell_' + currentRow + '_' + i);
				cell.className = getClassName();
				var inputObj = getObjectByID(scheduleId + 'schedule_' + rowNum + '_' + i);
				if(scheduleState == 2) {
					var positionId = getPositionId(rowNum, scheduleId);
					if(positionId) {
						inputObj.value = positionId;
					} else {
						inputObj.value = defaultPositionId;
					}
				} else if(scheduleState == 4) {
					inputObj.value = BREAK;
				} else {
					inputObj.value = '';
				}
				
			}
			refreshRow(rowNum, scheduleId);

			fromColumn = null;
			toColumn = null;
			onMouseOutEnabled = false;

			changeAction(scheduleState - 1, scheduleId);
		}
	}
	
}

function getClassName() {
	if(scheduleState == 2) {
		return 'scheduleSelected';
	} else if(scheduleState == 4) {
		return 'scheduleBreak';
	} else if(scheduleState == 6) {
		return 'scheduleEmpty';
	}
}

function setScheduleActionImage(actionCode, scheduleId) {
	if(actionCode == 1 || actionCode == 3 || actionCode == 5) {
		var shiftImg = getObjectByID(scheduleId + 'scheduleShiftImage');
		var breakImg = getObjectByID(scheduleId + 'scheduleBreakImage');
		var deleteImg = getObjectByID(scheduleId + 'scheduleDeleteImage');
		
		if(actionCode == 1) {
			if(shiftImg) {shiftImg.className = 'scheduleActionImageOn';}
			if(breakImg) {breakImg.className = 'scheduleActionImageOff';}
			if(deleteImg) {deleteImg.className = 'scheduleActionImageOff';}
		} else if(actionCode == 3) {
			if(shiftImg) {shiftImg.className = 'scheduleActionImageOff';}
			if(breakImg) {breakImg.className = 'scheduleActionImageOn';}
			if(deleteImg) {deleteImg.className = 'scheduleActionImageOff';}
		} else if(actionCode == 5) {
			if(shiftImg) {shiftImg.className = 'scheduleActionImageOff';}
			if(breakImg) {breakImg.className = 'scheduleActionImageOff';}
			if(deleteImg) {deleteImg.className = 'scheduleActionImageOn';}		}
	}
}

function changeAction(actionCode, scheduleId)
{
	setScheduleActionImage(actionCode, scheduleId);
	
	if(fromColumn != null && scheduleState != actionCode) {
		var cell = getObjectByID(scheduleId + 'cell_' + currentRow + '_' + fromColumn);
		cell.className = 'scheduleEmpty';
	}
	fromColumn = null;
	toColumn = null;
	scheduleState = actionCode;
	setMessage(START_TIME_MSG, scheduleId);
}

function setMessage(msgTxt, scheduleId) {
	var msg = getObjectByID(scheduleId + 'actionMessage');
	if(msg) {
		msg.innerHTML = msgTxt;
	}
}

function scheduleOnMouseOver(tdObj) {
	previousClassName = tdObj.className;
	tdObj.className = 'scheduleSelectingOver';
}

function scheduleOnMouseOut(tdObj) {
	if(onMouseOutEnabled) {
		tdObj.className = previousClassName;
	}
	onMouseOutEnabled = true;
	previousColor = null;
}

function refreshRows(scheduleId) {
	for(var i=0; i < getTotalRows(scheduleId); i++) {
		refreshRowImpl(i, scheduleId, false);
	}
	initTotalHours(scheduleId);
	refreshStaffing(scheduleId);
}

function setTotalHours(scheduleId, totalHours) {
	var totalHoursObj = getObjectByID(scheduleId + 'total_cell');
	if(totalHoursObj) {
		totalHoursObj.innerHTML = minutesToTime(totalHours);
	}
}

function updateSummaryTotals(scheduleId) {
	updateProjectionTotals();
	updatePositionTotals();
}

function updatePositionTotals() {
	var totalSchedule = 0;
	var totalTarget = 0;
	var totalDiff = 0;

	var positionSchedule = 0;
	var positionTarget = 0;
	var positionTargetObj;
	var diff;
	
	for(var i=0; i < POSITION_IDS.length; i++) {
		positionSchedule = 0;
		
		for(var k=0; k < SCHEDULE_IDS.length; k++) {
			for(var j=0; j < getTotalRows(SCHEDULE_IDS[k]); j++) {
				var rowPositionId = getPositionId(j, SCHEDULE_IDS[k]);
				if(rowPositionId == POSITION_IDS[i]) {
					var rowTotalObj = getObjectByID(SCHEDULE_IDS[k] + 'totalHours_' + j);
					
					if(rowTotalObj) {
						var posRowTotal = timeInMinutes(rowTotalObj.innerHTML);
						positionSchedule += posRowTotal;
						totalSchedule += posRowTotal;
					}
				}
			}
		}
		
		positionTargetObj = getObjectByID(POSITION_IDS[i] + '_position_target_total');
		if(positionTargetObj) {
			positionTarget = timeInMinutes(positionTargetObj.innerHTML);
			totalTarget += positionTarget;
		}

		diff = positionSchedule - positionTarget;
		
		var diffObj = getObjectByID(POSITION_IDS[i] + '_position_diff');
		if(diffObj) {
			if(diff < 0) {
				diffObj.innerHTML = '-' + minutesToTime(-1 * diff);
			} else {
				diffObj.innerHTML = minutesToTime(diff);
			}
		}		
		
		var positionScheduleObj = getObjectByID(POSITION_IDS[i] + '_position_schedule_total');
		if(positionScheduleObj) {
			positionScheduleObj.innerHTML = minutesToTime(positionSchedule);
		}
	}
	
	var totalScheduleObj = getObjectByID('position_schedule_total');
	if(totalScheduleObj) {
		totalScheduleObj.innerHTML = minutesToTime(totalSchedule);
	}
	
	var totalTargetObj = getObjectByID('position_target_total');
	if(totalTargetObj) {
		totalTargetObj.innerHTML = minutesToTime(totalTarget);
	}		
	
	totalDiff = totalSchedule - totalTarget;
	var totalDiffObj = getObjectByID('position_diff');
	if(totalDiffObj) {
		if(totalDiff < 0) {
			totalDiffObj.innerHTML = '-' + minutesToTime(-1 * totalDiff);
		} else {
			totalDiffObj.innerHTML = minutesToTime(totalDiff);
		}
	}	
}

function getHiddenScheduledTotal() {
	var hiddenTotalMins = 0;
	var hiddenTotalObj = getObjectByID('hiddenTotalScheduledInMinutes');
	if(hiddenTotalObj) {
		hiddenTotalMins = toInt(hiddenTotalObj.value);
	}
	return hiddenTotalMins;
}

function updateProjectionTotals() {
	var totalHours = 0;
	var target = 0;
	var diff = 0;
	
	for(var i = 0; i < SCHEDULE_IDS.length; i++) {
		var totalHoursObj = getObjectByID(SCHEDULE_IDS[i] + 'total_cell');
		if(totalHoursObj) {
			totalHours += timeInMinutes(totalHoursObj.innerHTML);
		}
	}
	var totalObj = getObjectByID('projection_schedule_total');
	if(totalObj) {
		totalObj.innerHTML = minutesToTime(totalHours + getHiddenScheduledTotal());
	}
	
	var targetObj = getObjectByID('projection_target_total');
	if(targetObj) {
		target = timeInMinutes(targetObj.innerHTML);
	}

	diff = totalHours - target;
	
	var diffObj = getObjectByID('projection_diff');
	if(diffObj) {
		if(diff < 0) {
			diffObj.innerHTML = '-' + minutesToTime(-1 * diff);
		} else {
			diffObj.innerHTML = minutesToTime(diff);
		}
	}
	
	var diffPercentObj = getObjectByID('projection_diff_percent');
	if(diffPercentObj) {
		if(target != 0) {
			diffPercentObj.innerHTML = ((diff) / target * 100).toFixed(2) + '%';
		} else {
			diffPercentObj.innerHTML = '0%';
		}
	}
}

function setDifferenceTotal(scheduleId) {
	var totalHoursObj = getObjectByID(scheduleId + 'total_cell');
	var totalStaffingHoursObj = getObjectByID(scheduleId + 'total_staffing_cell');
	var differenceTotalObj = getObjectByID(scheduleId + 'total_difference_cell');
	
	var total = 0;
	var staffingTotal = 0;
	
	if(totalHoursObj) {
		total = timeInMinutes(totalHoursObj.innerHTML);
	}
	if(totalStaffingHoursObj) {
		staffingTotal = timeInMinutes(totalStaffingHoursObj.innerHTML);
	}
	
	if(differenceTotalObj) {
		differenceTotalObj.innerHTML = minutesToTime(Math.abs(total - staffingTotal));
	}
}

function refreshEmployeeDayTotalHours(scheduleId, rowNum) {
	var rowTotalObj = getObjectByID(scheduleId + 'totalHours_' + rowNum);
	if(rowTotalObj) {
		var totalTimeInMinutes = timeInMinutes(rowTotalObj.innerHTML);
		checkEmployeeDayTotalHours(scheduleId, totalTimeInMinutes, rowNum, rowTotalObj);
	}
}

function checkEmployeeDayTotalHours(scheduleId, totalTimeInMinutes, rowNum, rowTotalObj) {
	var maxDayHoursObj = getObjectByID(scheduleId + 'scheduleEmployeeMaxWeekHours_' + rowNum);
	var value = null;
	if(maxDayHoursObj) {
		value = toInt(maxDayHoursObj.value) * 60;
	}

	if(value != null && value != '' && value > 0) {
		if(totalTimeInMinutes > value && rowTotalObj) {
			rowTotalObj.className = 'scheduleEmployeeTotalHoursExceeded';
		} else {
			rowTotalObj.className = 'scheduleValueCell';
		}
	}
}

function initTotalHours(scheduleId) {
	var totalHours = 0;
	var rowTotalTimeInMins = 24 * 60;
	for(var i=0; i < getTotalRows(scheduleId); i++) {
		var rowTotalObj = getObjectByID(scheduleId + 'totalHours_' + i);
		if(rowTotalObj)	{
			rowTotalTimeInMins = timeInMinutes(rowTotalObj.innerHTML);
			totalHours += rowTotalTimeInMins;
		}
		var totalObj = getObjectByID(scheduleId + 'total_cell_' + i);
		if(totalObj) {
			totalObj.innerHTML = '0';
		}
		checkEmployeeDayTotalHours(scheduleId, rowTotalTimeInMins, i, rowTotalObj); 
	}
	
	setTotalHours(scheduleId, totalHours);
	setDifferenceTotal(scheduleId);
}

function updateTotalHours(scheduleId, oldTotalRowHours, newTotalRowHours, rowNum, rowTotalObj) {
	var totalHoursObj = getObjectByID(scheduleId + 'total_cell');
	var totalHours = 0;
	var rowTotalTimeInMins = 0;
	
	for(var i=0; i < getTotalRows(scheduleId); i++) {
		var rowTotObj = getObjectByID(scheduleId + 'totalHours_' + i);
		if(rowTotObj)	{
			rowTotalTimeInMins = timeInMinutes(rowTotObj.innerHTML);
			totalHours += rowTotalTimeInMins;
		}
	}	
	
	if(totalHoursObj) {
		totalHoursObj.innerHTML = minutesToTime(totalHours);
	}
	
	// :TODO: Improve performance?
	refreshStaffing(scheduleId);
	setDifferenceTotal(scheduleId);

	checkEmployeeDayTotalHours(scheduleId, newTotalRowHours, rowNum, rowTotalObj);
}

function getPositionId(rowNum, scheduleId) {
	var selectObj = getObjectByID(scheduleId + 'scheduleposition_' + rowNum);
	var positionId = null;
	if(selectObj) {
		positionId = selectObj.value;
	}
	return positionId;
}

function refreshStaffing(scheduleId) {
	var rowTotal;
	
	for(var j=0; j < TOTAL_COLS; j++) {
		rowTotal = 0;
		for(var i=0; i < getTotalRows(scheduleId); i++) {
			var inputObj = getObjectByID(scheduleId + 'schedule_' + i + '_' + j);
			if(inputObj) {
				var value = inputObj.value;
				if(value != null && value != '' && value != BREAK) {
					rowTotal += 1;
				}
			}			
		}
		var totalObj = getObjectByID(scheduleId + 'total_cell_' + j);
		if(totalObj) {
			totalObj.innerHTML = rowTotal;
		}
		var minimunStaffingObj = getObjectByID(scheduleId + 'staffing_div_' + j);
		var minimunStaffing = 0;
		if(minimunStaffingObj) {
			minimunStaffing = minimunStaffingObj.innerHTML;
		}
		
		var diffObj = getObjectByID(scheduleId + 'difference_cell_' + j);
		var difference = rowTotal - minimunStaffing;
		if(diffObj) {
			if(difference == 0) {
				diffObj.innerHTML = difference;
				diffObj.className = 'scheduleStaffingDifferenceEqual';
			} else if(difference < 0) {
				diffObj.innerHTML = -1 * difference;
				diffObj.className = 'scheduleStaffingDifferenceNegative';
			} else {
				diffObj.innerHTML = difference;
				diffObj.className = 'scheduleStaffingDifferencePositive';
			}
		}
	}
}

function refreshRow(rowNum, scheduleId) {
	refreshRowImpl(rowNum, scheduleId, true);
}

function refreshRowImpl(rowNum, scheduleId, refreshTotals) {
	var inHour = null;
	var outHour = null;
	var totalHours = 0;
	var newTotalRowHours = 0;
	
	for(var i=0; i < TOTAL_COLS; i++) {
		var inputObj = getObjectByID(scheduleId + 'schedule_' + rowNum + '_' + i);
		var inputHourObj = getObjectByID(scheduleId + 'schedulehour_' + rowNum + '_' + i);
		
		var value = null;
		var currentHour = null;
		
		if(inputObj) {
			value = inputObj.value;
		}
		
		if(inputHourObj) {
			currentHour = inputHourObj.value;
		}
		
		if(value != null && value != '' && value != BREAK) {
			inputObj.value = getPositionId(rowNum, scheduleId);			
			
			if(inHour == null) {
				inHour = currentHour;
			}

			// Out hour is the last selected hour			
			outHour = currentHour;
			
			totalHours += 15;
		}
	}
	newTotalRowHours = totalHours;
	
	
	if(totalHours > 0) {
		totalHours = minutesToTime(totalHours);
	} else {
		totalHours = '-';
	}
	
	if(outHour != null && outHour != '') {
		outHour = timeInMinutes(outHour) + 15;
		outHour = minutesToTime(outHour);
	} else {
		outHour = '-';
	}
	
	if(inHour == null) {
		inHour = '-';
	}
	
	
	var o1 = getObjectByID(scheduleId + 'inHourInput_' + rowNum);
	var o2 = getObjectByID(scheduleId + 'outHourInput_' + rowNum);
	var o3 = getObjectByID(scheduleId + 'totalHoursInput_' + rowNum);
	
	if(o1) {
		o1.value = inHour;
	}
	
	if(o2) {
		o2.value = outHour;
	}
	if(o3) {
		o3.value = totalHours;
	}

	var o4 = getObjectByID(scheduleId + 'inHour_' + rowNum);
	var o5 = getObjectByID(scheduleId + 'outHour_' + rowNum);
	var o6 = getObjectByID(scheduleId + 'totalHours_' + rowNum);
	
	
	if(o4) {
		o4.innerHTML = inHour;
	}
	if(o5) {
		o5.innerHTML = outHour;
	}
	
	var oldTotalRowHours = '';

	if(o6) {
		oldTotalRowHours = o6.innerHTML;
		o6.innerHTML = totalHours;
	}		
	
	updateTotalHours(scheduleId, timeInMinutes(oldTotalRowHours), newTotalRowHours, rowNum, o6);
	if(refreshTotals) {
		updateSummaryTotals(scheduleId);
	}
}

function reloadEmployeeMaxHoursDay(scheduleId, rowNum) {
	var autoCompleter = dojo.widget.byId(scheduleId + 'scheduleEmployee_' + rowNum);
	var employeeId = null;
	
	if(autoCompleter) {
		employeeId = autoCompleter.getSelectedValue();
	}
	
	if(employeeId == null) {
		var originalEmployeeIdObj = get(scheduleId + 'scheduleOriginalEmployeeId_' + rowNum);
		if(originalEmployeeIdObj) {
			employeeId = originalEmployeeIdObj.value;
		}
	}
	
	var urlToInvoke = '<s:url action="scheduleemployeemaxhsday" includeParams="none"/>?employeeId=' + employeeId;
	alert(urlToInvoke);
	
	var kw = {
	        url: urlToInvoke,
	        load: function(data){
					var maxDayHoursObj = getObjectByID(scheduleId + 'scheduleEmployeeMaxWeekHours_' + rowNum);
					if(maxDayHoursObj) {
						maxDayHoursObj.value = trim(data);
					}	       
				  },
	        error: function(data){
	                alert("An error occurred: " + data);
	        },
	        timeout: 2000
	};
	
	dojo.xhrGet(kw);
	 
	refreshEmployeeDayTotalHours(scheduleId, rowNum);
}

/*****************************************
 * DAILY SCHEDULE FUNCTIONS
 *****************************************/
function wsInitialize(totalCols, positionsQty) {
	TOTAL_COLS = totalCols;
	TOTAL_ROWS = new Array(1);
	POSITION_IDS = new Array(positionsQty);
}

function wsRefreshTotalHours(rowNum, columnNum) {
	var inHourMin = getObjectValueAsTimeInMinutes('weeklyScheduleInHour_' + rowNum + '_' + columnNum, null);
	var outHourMin = getObjectValueAsTimeInMinutes('weeklyScheduleOutHour_' + rowNum + '_' + columnNum, null);
	var totalHourMin = 0;
	
	if(hasValue(inHourMin) && hasValue(outHourMin)) {
		if(outHourMin > inHourMin || (inHourMin == 0 && outHourMin == 0)) {
			totalHourMin = outHourMin - inHourMin;
		} else {
			totalHourMin = ONE_DAY_MINUTES + (outHourMin - inHourMin);
		}
		
		if(totalHourMin >= 0) {
			setObjectByIDValue('weeklyScheduleTotalHours_' + rowNum + '_' + columnNum, minutesToTime(totalHourMin));
		}
		wsRefreshRowTotals(rowNum);
		wsUpdateSummaryTotals();
	}
}

function wsRefreshRowTotals(rowNum) {
	var total = 0;
	var colTotal;
	var totalDays = 0;
	var className;
	var maxHoursDay = toInt(getObjectByIDValue('scheduleEmployeeMaxHoursDay_' + rowNum, '24')) * 60;
	var maxHoursWeek = toInt(getObjectByIDValue('scheduleEmployeeMaxHoursWeek_' + rowNum, '168')) * 60;
	var maxDaysWeek = toInt(getObjectByIDValue('scheduleEmployeeMaxDaysWeek_' + rowNum, '7'));
	
	for(var i=0; i < TOTAL_COLS; i++) {
		colTotal = getObjectValueAsTimeInMinutes('weeklyScheduleTotalHours_' + rowNum + '_' + i, '00:00');
		if(colTotal > maxHoursDay) {
			setObjectByIDClass('weeklyScheduleTotalHours_' + rowNum + '_' + i, 'weekScheduleEmployeeTotalHoursExceeded');
		} else {
			setObjectByIDClass('weeklyScheduleTotalHours_' + rowNum + '_' + i, 'weekScheduleTimeInputCellText');
		}
		if(colTotal > 0) {
			totalDays++;
		}
		total += colTotal;
	}
	
	if(total > maxHoursWeek) {
		className = 'weekScheduleEmployeeTotalWeekHoursExceeded';
	} else {
		className = 'weekScheduleNameCell';
	}
	setObjectByIDValueAndClass('scheduleWeeklyTotal_' + rowNum, minutesToTime(total), className);

	for(var i=0; i < TOTAL_COLS; i++) {
		if(totalDays > maxDaysWeek && colTotal > 0) {
			className = 'weekScheduleEmployeeTotalWeekDaysExceeded';
		} else {
			className = 'weekScheduleValueCell';
		}
		setObjectByIDClass('scheduleHours_' + rowNum + '_' + i, className);
	}	
}

function wsRefreshAllRowsTotals() {
	for(var i=0; i < getTotalRows(''); i++) {
		wsRefreshRowTotals(i);
	}
}

function wsUpdateProjectionTotals() {
	var totalHours = 0;
	var target = 0;
	var diff = 0;

	for(var i = 0; i < getTotalRows(''); i++) {
		totalHours += getObjectValueAsTimeInMinutes('scheduleWeeklyTotal_' + i, '00:00');
	}
	
	setObjectByIDValue('projection_schedule_total', minutesToTime(totalHours + getHiddenScheduledTotal()));

	target = getObjectValueAsTimeInMinutes('projection_target_total', '00:00');

	diff = totalHours - target;
	
	if(diff < 0) {
		setObjectByIDValue('projection_diff', '-' + minutesToTime(-1 * diff));
	} else {
		setObjectByIDValue('projection_diff', minutesToTime(diff));
	}	

	if(target != 0) {
		setObjectByIDValue('projection_diff_percent', ((diff) / target * 100).toFixed(2) + '%');
	} else {
		setObjectByIDValue('projection_diff_percent', '0%');
	}	
}

function wsUpdatePositionTotals() {
	var totalSchedule = 0;
	var totalTarget = 0;
	var totalDiff = 0;

	var positionSchedule = 0;
	var positionTarget = 0;
	var diff;
	for(var i=0; i < POSITION_IDS.length; i++) {
		positionSchedule = 0;
		for(var j = 0; j < getTotalRows(''); j++) {
			var rowPositionId = getObjectByIDValue('scheduleposition_' + j, null);
			if(toInt(rowPositionId) == toInt(POSITION_IDS[i])) {
				var posRowTotal = getObjectValueAsTimeInMinutes('scheduleWeeklyTotal_' + j, '00:00');
				positionSchedule += posRowTotal;
				totalSchedule += posRowTotal;			
			}
		}

		positionTarget = getObjectValueAsTimeInMinutes(POSITION_IDS[i] + '_position_target_total', '00:00');
		totalTarget += positionTarget;		

		diff = positionSchedule - positionTarget;

		if(diff < 0) {
			setObjectByIDValue(POSITION_IDS[i] + '_position_diff', '-' + minutesToTime(-1 * diff));
		} else {
			setObjectByIDValue(POSITION_IDS[i] + '_position_diff', minutesToTime(diff));
		}

		setObjectByIDValue(POSITION_IDS[i] + '_position_schedule_total', minutesToTime(positionSchedule));
	
	}

	setObjectByIDValue('position_schedule_total', minutesToTime(totalSchedule));
	setObjectByIDValue('position_target_total', minutesToTime(totalTarget));

	if(totalDiff < 0) {
		setObjectByIDValue('position_diff', '-' + minutesToTime(-1 * totalDiff));
	} else {
		setObjectByIDValue('position_diff', minutesToTime(totalDiff));
	}
}

function wsUpdateSummaryTotals() {
	wsUpdateProjectionTotals();
	wsUpdatePositionTotals();
}

function confirmCopySchedule(obj, message) {
	var c = confirmAndWaitSpash('confirm', obj, message);
	
	if(c) {
		setObjectByIDValue('copySchedule', 'true');
	}
	return c;
}

function confirmPostSchedule(obj, message) {
	var c = confirmAndWaitSpash('confirm', obj, message);
	
	if(c) {
		setObjectByIDValue('postSchedule', 'true');
	}
	return c;
}
