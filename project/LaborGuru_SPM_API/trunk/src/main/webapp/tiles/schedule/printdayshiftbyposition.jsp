<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>

<script language="JavaScript">
<!--

// -->
</script>

<br />
<s:form id="printdailyshiftbyposition_form" name="printdailyshiftbyposition_form" action="printdailyshiftbyposition_view" theme="simple">
	<s:hidden id="selectedDate" name="selectedDate"/>
	<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>
	<s:hidden id="postSchedule" name="postSchedule"/>
	<s:hidden id="inTimeOnly" name="inTimeOnly"/>
	
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td>
				<table border="0" cellspacing="0" align="center">
					<tr>
						<td align="right" class="form_label"><s:text name="schedule.printshift.view_selector.label"/></td>	
						<td align="left"><s:select name="selectedView" list="viewMap" listKey="key" listValue="%{getText(value)}" onchange="printdailyshiftbyposition_form.action=printdailyshiftbyposition_form.selectedView.value; printdailyshiftbyposition_form.submit()" theme="simple" /></td>				
						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>	
						<td align="center">
							<s:url id="printUrl" action="printdailyshiftbyposition_print" escapeAmp="false" includeParams="none">
								<s:param name="selectedDate"><s:property value="selectedDate"/></s:param>
								<s:param name="selectedWeekDay"><s:property value="selectedWeekDay"/></s:param>
								<s:param name="selectedView"><s:property value="selectedView"/></s:param>
								<s:param name="inTimeOnly"><s:property value="inTimeOnly"/></s:param>
							</s:url>						
							<s:submit onclick="return openPopup('%{printUrl}', 'print_window');" id="printButton" key="schedule.printshift.daily.print.button" theme="simple" cssClass="button"/>
						</td>
					</tr>				
				</table>
			</td>
		</tr>

		<tr>
			<td id="titleBar">
				<s:if test="!inTimeOnly"><s:text name="schedule.printshift.daily.byposition.title" /></s:if><s:else><s:text name="schedule.printshift.daily.byposition_in_time.title" /></s:else>
				&nbsp;-&nbsp;
				<s:text name='schedule.printshift.daily.weekday.dateformat'><s:param value='weekDaySelector.selectedDay'/></s:text>
			</td>
		</tr>

		<tr>
			<td align="center">
                  	<!-- Start week table -->
                  	<table align="center" id="calendarTable" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  		<tr>
                  			<td align="center">
                  				<table align="center" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  					<tr>
                  						<s:iterator id="prevDate" value="weekDaySelector.previousStartingWeekDays" status="itPrevDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="printdailyshiftbyposition_form.action='printdailyshiftbyposition_changeWeek.action'; printdailyshiftbyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; printdailyshiftbyposition_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='schedule.printshift.weekdayselector.availableweek.dateformat'><s:param value='prevDate'/></s:text>
			                  					</a>
			                  				</td>
			                  			
				                  			<s:if test="!#itPrevDate.last">
				                  			<td class="calendarTableColumn" nowrap="nowrap"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>|<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
	                  						</s:if>
                  						</s:iterator>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  			<td class="calendarTableColumn" nowrap="nowrap">
			                  				<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
			                  					<tr>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printdailyshiftbyposition_form.action='printdailyshiftbyposition_changeWeek.action'; printdailyshiftbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; printdailyshiftbyposition_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='schedule.printshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printdailyshiftbyposition_form.action='printdailyshiftbyposition_changeWeek.action'; printdailyshiftbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; printdailyshiftbyposition_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printdailyshiftbyposition_form.action='printdailyshiftbyposition_changeWeek.action'; printdailyshiftbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; printdailyshiftbyposition_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='schedule.printshift.weekdayselector.availableweek.dateformat'><s:param value='nextDate'/></s:text>
			                  					</a>
			                  				</td>
			                  			
				                  			<s:if test="!#itNextDate.last">
											<td class="calendarTableColumn" nowrap="nowrap"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>|<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
											</s:if>
                  						</s:iterator>			                  			
			                  		</tr>
			                  	</table>
			                 </td>
			              </tr>
			              <tr class="calendarWeekDayTableRowSeparator">
			              	<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			              </tr>
			              <tr>
			              	<td align="center" class="calendarWeekDayTableColumn">
								<table align="center" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  					<tr>
                  						<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itWeekDay">
                  						<s:if test="%{weekDaySelector.isSelectedWeekDay(#weekDay)}">
                  						<td width="12%" class="selectedWeekDay" title="<s:text name='schedule.addshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>"><s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
                  						</s:if>
                  						<s:else>
                  						<td width="12%" class="availableWeekDay"><a title="<s:text name='schedule.addshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>" href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printdailyshiftbyposition_form='printdailyshiftbyposition_changeDay.action'; printdailyshiftbyposition_form.selectedWeekDay.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDay'/></s:text>'; printdailyshiftbyposition_form.submit();" class="availableWeekDayLink">
                  						<s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDay'/></s:text>
                  						</a>
                  						</td>                  						
                  						</s:else>
                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						</s:iterator>
                  					</tr>
                  				</table>			              		
			              	</td>
			              </tr>
                  	</table>
                  	<!-- End week table -->  			
			</td>
		</tr>
		
		<tr>
			<td align="center">
			<table border="0" align="center" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
				<tr>
					<td class="errorMessage">
						<s:fielderror theme="simple" />
						<s:actionerror theme="simple" />
					</td>
				</tr>
				<tr>
					<td class="actionMessage">
						<s:actionmessage theme="simple"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>	
		
		<tr>
			<td align="center">
			<!-- Schedule selection table -->
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
			<!-- Schedule selection table -->
			</td>
		</tr>
	</table>
</s:form>
