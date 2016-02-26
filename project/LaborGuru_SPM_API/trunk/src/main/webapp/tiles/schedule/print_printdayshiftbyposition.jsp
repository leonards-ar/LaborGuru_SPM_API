<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<center>
	<h4><s:if test="!inTimeOnly"><s:text name="schedule.printshift.daily.byposition.title" /></s:if><s:else><s:text name="schedule.printshift.daily.byposition_in_time.title" /></s:else>&nbsp;-&nbsp;<s:text name='schedule.printshift.daily.weekday.dateformat'><s:param value='weekDaySelector.selectedDay'/></s:text></h4>
	<table class="printScheduleMainTable" id="printScheduleMainTable" cellpadding="1" cellspacing="0">
	    <!-- Header -->
	    <tr class="printScheduleMainTableHeader">
			<td class="printScheduleCellHeader"><s:text name="schedule.printshift.employee"/></td>
			<td class="printScheduleCellHeader"><s:text name="schedule.printshift.schedule"/></td>
			<td class="printScheduleCellHeader"><s:text name="schedule.printshift.break_schedule"/></td>
			<td class="printScheduleCellHeader"><s:text name="schedule.printshift.break"/></td>
			<td class="printScheduleCellHeader"><s:text name="schedule.printshift.comments"/></td>
		</tr>
		<!-- Header -->
		
		<s:iterator id="pos" value="schedulePositionsForSelectedDate" status="itPosStatus">
			<tr>
				<td class="printSchedulePositionTitle"><s:property value="name"/></td>
				<td class="printSchedulePositionTotalHours"><s:property value="%{getDayTotalHoursForSelectedDate(#pos)}"/></td>
				<td class="printSchedulePositionTotalHours" colspan="3"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			</tr>
			
			<s:iterator id="employee" value="getScheduleEmployeesForSelectedDate(#pos)" status="itEmployeeStatus">
			<tr>
				<td class="printScheduleValueCell"><s:property value="fullName"/></td>
				<td class="printScheduleValueCell" align="center">
				<s:set id="shifts" name="shifts" value="%{getCompleteShiftsForSelectedDate(#pos, #employee)}"/>
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
				
				<td class="printScheduleValueCell" align="center">
				<s:set id="breaks" name="breaks" value="%{getBreaksForSelectedDate(#employee)}"/>
				<s:if test="#breaks.size() > 0">
						<table border="0" cellpadding="1" cellspacing="0" align="center">
						<s:iterator id="shift" value="breaks" status="itShiftStatus">
							<tr>
								<td class="printScheduleValueTextCell"><s:property value="fromHourAsString"/></td>
								<td class="printScheduleValueTextCell">-</td>
								<td class="printScheduleValueTextCell"><s:property value="toHourAsString"/></td>
							</tr>
						</s:iterator>
						</table>
				</s:if>
				<s:else>
				&nbsp;
				</s:else>
				</td>

				<td class="printScheduleValueCell" align="center">
					&nbsp;&nbsp;15&nbsp;&nbsp;30&nbsp;&nbsp;60&nbsp;&nbsp;
				</td>

				<td class="printScheduleValueCell" align="center">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			</s:iterator>
		</s:iterator>
		<tr>
			<td class="printSchedulePositionTitle"><s:text name="schedule.printshift.total_hours"/></td>
			<td class="printSchedulePositionTotalHours"><s:property value="%{getDayTotalHoursForSelectedDate()}"/></td>			
			<td class="printSchedulePositionTotalHours" colspan="3"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>

		</tr>
	</table>
</center>
