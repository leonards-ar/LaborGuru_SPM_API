<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<center>
<h4><s:if test="!inTimeOnly"><s:text name="schedule.printshift.weekly.byposition.title" /></s:if><s:else><s:text name="schedule.printshift.weekly.byposition_in_time.title" /></s:else> - <s:text name='schedule.printshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></h4>

<table class="printScheduleMainTable" id="printScheduleMainTable" cellpadding="1" cellspacing="0">
    <!-- Header -->
    <tr class="printScheduleMainTableHeader">
		<td class="printScheduleCellHeader"><s:text name="schedule.printshift.employee"/></td>    
		<!-- Iterate week days -->
		<s:iterator id="weekDay" value="weekDaySelector.weekDays">
			<td class="printScheduleCellHeader"><s:text name='schedule.printshift.weekly.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
		</s:iterator>
		<!-- End Iterate week days -->		
		<td class="printScheduleCellHeader"><s:text name="schedule.printshift.week_total_hours"/></td>		    
	</tr>
	<!-- Header -->
	
	<s:iterator id="pos" value="weeklySchedulePositions" status="itPosStatus">
		<tr>
			<td class="printSchedulePositionTitle"><s:property value="name"/></td>
			<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itDayStatus">
			<td class="printSchedulePositionTotalHours"><s:property value="%{getDayTotalHoursFor(#pos, #itDayStatus.index)}"/></td>			
			</s:iterator>							
			<td class="printSchedulePositionTotalHours"><s:property value="%{getWeekTotalHoursFor(#pos)}"/></td>
		</tr>
		
		<s:iterator id="employee" value="getWeeklyScheduleEmployeesFor(#pos)" status="itEmployeeStatus">
		<tr>
			<td class="printScheduleValueCell"><s:property value="fullName"/></td>
			<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itDayStatus">
			<td class="printScheduleValueCell" align="center">
			<s:set id="shifts" name="shifts" value="%{getCompleteShiftsFor(#pos, #employee, #itDayStatus.index)}"/>
			<s:if test="#shifts.size() > 0">
					<table border="0" cellpadding="1" cellspacing="0" align="center">
					<s:iterator id="shift" value="shifts" status="itShiftStatus">
						<tr>
							<td class="printScheduleValueTextCell"><s:property value="fromHourAsString"/></td>
							<s:if test="!inTimeOnly">
							<td class="printScheduleValueTextCell">-</td>
							<td class="printScheduleValueTextCell"><s:property value="toHourAsString"/></td>
							</s:if>
						</tr>
					</s:iterator>
					</table>
			</s:if>
			<s:else>
			&nbsp;
			</s:else>
			</td>
			</s:iterator>
			<td class="printScheduleValueCell"><s:property value="%{getWeekTotalHoursFor(#pos, #employee)}"/></td>
		</tr>
		</s:iterator>
	</s:iterator>
	<tr>
		<td class="printSchedulePositionTitle"><s:text name="schedule.printshift.total_hours"/></td>
		<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itDayStatus">
		<td class="printSchedulePositionTotalHours"><s:property value="%{getDayTotalHours(#itDayStatus.index)}"/></td>			
		</s:iterator>
		<td class="printSchedulePositionTotalHours"><s:property value="%{getWeekTotalHours()}"/></td>
	</tr>
</table>
</center>
