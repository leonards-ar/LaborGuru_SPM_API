<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script language="JavaScript">
<!--

// -->
</script>

<br />
<s:form id="printweeklytotalhoursbyposition_form" name="printweeklytotalhoursbyposition_form" action="printweeklytotalhoursbyposition_view" theme="simple">
	<s:hidden id="selectedDate" name="selectedDate"/>
	<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>
	
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td id="titleBar"><s:text name="schedule.printshift.weekly.byposition.title" /></td>
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
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="printweeklytotalhoursbyposition_form.action='printweeklytotalhoursbyposition_changeWeek.action'; printweeklytotalhoursbyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; printweeklytotalhoursbyposition_form.submit();" class="calendarUnselectedWeekLink">
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
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printweeklytotalhoursbyposition_form.action='printweeklytotalhoursbyposition_changeWeek.action'; printweeklytotalhoursbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; printweeklytotalhoursbyposition_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='schedule.printshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printweeklytotalhoursbyposition_form.action='printweeklytotalhoursbyposition_changeWeek.action'; printweeklytotalhoursbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; printweeklytotalhoursbyposition_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printweeklytotalhoursbyposition_form.action='printweeklytotalhoursbyposition_changeWeek.action'; printweeklytotalhoursbyposition_form.selectedDate.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; printweeklytotalhoursbyposition_form.submit();" class="calendarUnselectedWeekLink">
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
			              <!-- 
			              <tr class="calendarWeekDayTableRowSeparator">
			              	<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			              </tr>
			              <tr>
			              	<td align="center" class="calendarWeekDayTableColumn">
								<table align="center" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  					<tr>
                  						<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itWeekDay">
	                  						<td width="12%" class="availableWeekDay"><a title="<s:text name='schedule.printshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>" href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); printweeklytotalhoursbyposition_form.action='printweeklytotalhoursbyposition_changeDay.action'; printweeklytotalhoursbyposition_form.selectedWeekDay.value='<s:text name='schedule.printshift.weekdayselector.input.dateformat'><s:param value='weekDay'/></s:text>'; printweeklytotalhoursbyposition_form.submit();" class="availableWeekDayLink">
	                  						<s:text name='schedule.printshift.weekday.dateformat'><s:param value='weekDay'/></s:text>
	                  						</a>
	                  						</td>                  						
	                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						</s:iterator>
                  						<td width="12%" class="selectedWeekDay"><s:text name="schedule.printshift.weekselector.week"/></td>
                  					</tr>
                  				</table>			              		
			              	</td>
			              </tr>
			               -->
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
						<!-- Iterate week days -->
						<s:iterator id="weekDay" value="weekDaySelector.weekDays">
							<td class="printScheduleCellHeader"><s:text name='schedule.printshift.weekly.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
						</s:iterator>
						<!-- End Iterate week days -->		
						<td class="printScheduleCellHeader"><s:text name="schedule.printshift.week_total_hours"/></td>		    
					</tr>
					<!-- Header -->
					
					<s:iterator id="pos" value="weeklySchedulePositions" status="itPosStatus">
						<tr><td  class="printSchedulePositionTitle" colspan="9"><s:property value="name"/></td></tr>
						
						<s:iterator id="employee" value="getWeeklyScheduleEmployeesFor(#pos)" status="itEmployeeStatus">
						<tr>
							<td class="printScheduleValueCell"><s:property value="fullName"/></td>
							<s:iterator id="weekDay" value="weekDaySelector.weekDays" status="itDayStatus">
							<td class="printScheduleValueCell" align="center">
							<s:property escape="false" value="%{getTotalHoursFor(#pos, #employee, #itDayStatus.index)}"/>
							</td>
							</s:iterator>
							<td class="printScheduleValueCell"><s:property value="%{getWeekTotalHoursFor(#pos, #employee)}"/></td>
						</tr>
						</s:iterator>
					</s:iterator>
				</table>			
			<!-- Schedule selection table -->
			</td>
		</tr>
	</table>
</s:form>
