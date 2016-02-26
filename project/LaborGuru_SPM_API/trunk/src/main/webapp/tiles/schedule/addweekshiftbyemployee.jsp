<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script language="JavaScript">
<!--

// -->
</script>

<br />
<s:form id="addweeklyshiftbyemployee_form" name="addweeklyshiftbyemployee_form" action="addweeklyshiftbyemployee_save" theme="simple">
	<s:hidden id="selectedDate" name="selectedDate"/>
	<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>
	<s:hidden id="weeklyVolume" name="weeklyVolume"/>
	<s:hidden id="hiddenTotalScheduledInMinutes" name="hiddenTotalScheduledInMinutes"/>
	<s:hidden id="copySchedule" name="copySchedule"/>
	
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td id="titleBar"><s:text name="schedule.addshift.weekly.byemployee.title" /></td>
		</tr>

		<tr>
			<td>
				<!-- Header tables -->
				<table border="0" cellpadding="2" cellspacing="2" width="100%">
					<tr>
						<td valign="top" align="right" width="30%">
							<!-- Left column -->
							<table id="fullHeightTable" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr valign="top">
									<td>
										<table id="windowTable">
											<tr>
												<td colspan="6" class="windowTableHeader"><s:text name="schedule.addshift.projection"/></td>
											</tr>
											<tr>
												<td class="windowTableLabel"><s:text name="schedule.addshift.weekly_volume"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.schedule"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.target"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.diff"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.diffpercent"/></td>
											</tr>
											
											<tr>
												<td class="windowTableValue"><s:text name="currency"><s:param value="weeklyVolume"/></s:text></td>
												<td class="windowTableValue" id="projection_schedule_total">&nbsp;</td>
												<td class="windowTableValue"><a href="#"><span id="projection_target_total"><s:property value="totalTarget"/></span></a></td>
												<td class="windowTableValue" id="projection_diff">&nbsp;</td>
												<td class="windowTableValue" id="projection_diff_percent">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								
								<tr valign="bottom">
									<td>
										<table border="0" cellpadding="1" cellspacing="2">
											<tr>
												<td align="right" class="form_label"><s:text name="schedule.addshift.view"/></td>
												<td align="left">
													<s:select id="selectView" name="selectView" onchange="showWaitSplash(); addweeklyshiftbyemployee_form.action=addweeklyshiftbyemployee_form.selectView.value; addweeklyshiftbyemployee_form.submit();" theme="simple" list="scheduleViewsMap" listKey="key" listValue="%{getText(value)}"/>
												</td>
												<td align="right" class="form_label"><s:text name="schedule.addshift.positions"/></td>
												<td align="left">
													<s:select onchange="showWaitSplash(); addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_selectPosition.action'; addweeklyshiftbyemployee_form.submit();" name="positionSelectId" list="positions" listKey="uniqueId" listValue="name" theme="simple" headerKey="" headerValue="%{getText('schedule.addshift.positions.header.label')}">
														<s:optgroup label="%{getText('schedule.addshift.position_groups.header.label')}" list="positionGroups" listKey="uniqueId" listValue="name"/>
													</s:select>					
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr valign="bottom">
									<td>
										<table border="0" cellpadding="1" cellspacing="2">
											<tr>
												<td align="right" class="form_label"><s:text name="schedule.addshift.copy.label"/></td>
												<td align="left">
													<s:datetimepicker id="copy_target_day" type="date" formatLength="short" displayFormat="%{getText('datepicker.format.date')}" disabled="true" name="copyTargetDay" theme="simple" />
													<script>djConfig.searchIds.push("copy_target_day");</script>
												</td>
												<td align="right"><s:submit onclick="return confirmCopySchedule(this, '%{getText('schedule.addshift.copy.schedule.confirm.msg')}');" id="copyButton" key="schedule.addshift.copy.button" action="addweeklyshiftbyemployee_copySchedule" theme="simple" cssClass="button"/></td>
											</tr>
										</table>
									</td>
								</tr>								
							</table>
							<!-- Left column -->
						</td>
						
						<td width="10%">&nbsp;</td>
						
						<td valign="top" width="30%" align="center">
							<!-- Center column -->
							<table id="windowTable">
								<tr>
									<td class="windowTableHeader"><s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDaySelector.selectedDay'/></s:text></td>
									<td class="windowTableHeader"><s:text name="schedule.addshift.schedule"/></td>
									<td class="windowTableHeader"><s:text name="schedule.addshift.target"/></td>
									<td class="windowTableHeader"><s:text name="schedule.addshift.diff"/></td>
								</tr>
								
								<!--  For each position -->
								<s:iterator value="selectedPositions" id="pos">
								<tr>
									<td class="windowTableValue"><s:property value="name"/></td>
									<td class="windowTableValue" id="<s:property value="id"/>_position_schedule_total">&nbsp;</td>
									<td class="windowTableValue" id="<s:property value="id"/>_position_target_total"><s:property value="%{getTotalPositionTarget(#pos)}"/></td>
									<td class="windowTableValue" id="<s:property value="id"/>_position_diff">&nbsp;</td>
								</tr>
								
								</s:iterator>
								<!-- For each position -->
								
								<tr>
									<td class="windowTableValue"><b><s:text name="schedule.addshift.total"/></b></td>
									<td class="windowTableValue" id="position_schedule_total"><b>&nbsp;</b></td>
									<td class="windowTableValue" id="position_target_total"><b>&nbsp;</b></td>
									<td class="windowTableValue" id="position_diff"><b>&nbsp;</b></td>
								</tr>
								
							</table>
							<!-- Center column -->
						</td>
						
						<td width="10%">&nbsp;</td>
						
						<td valign="top" width="20%" align="left">
							<!-- Right column -->
							<table id="windowTable">	
								<tr>
									<td></td>
									<td class="windowTableHeader"><s:text name="schedule.addshift.schedule"/></td>
									<td class="windowTableHeader"><s:text name="schedule.addshift.target"/></td>
								</tr>							
								<tr>
									<td class="windowTableLabel" nowrap="nowrap"><s:text name="schedule.addshift.vplh"><s:param value='mainVariableInitials'/></s:text></td>
									<td class="windowTableValue" id="vplh_schedule">
										<s:if test="vplhSchedule != null"><s:text name="decimal"><s:param value="vplhSchedule"/></s:text></s:if>
										<s:else><s:text name="schedule.addshift.emptyvalue"/></s:else>
									</td>
									<td class="windowTableValue" id="vplh_target">
										<s:if test="vplhTarget != null"><s:text name="decimal"><s:param value="vplhTarget"/></s:text></s:if>
										<s:else><s:text name="schedule.addshift.emptyvalue"/></s:else>									
									</td>
								</tr>							
								<tr>
									<td class="windowTableLabel" nowrap="nowrap"><s:text name="schedule.addshift.labor_percent"/></td>
									<td class="windowTableValue" id="labor_schedule">
										<s:if test="laborPercentageSchedule != null"><s:text name="percentage"><s:param value="laborPercentageSchedule"/></s:text>%</s:if>
										<s:else><s:text name="schedule.addshift.emptyvalue"/></s:else>									
									</td>
									<td class="windowTableValue" id="labor_target">
										<s:if test="laborPercentageTarget != null"><s:text name="percentage"><s:param value="laborPercentageTarget"/></s:text>%</s:if>
										<s:else><s:text name="schedule.addshift.emptyvalue"/></s:else>									
									</td>
								</tr>							
							</table>
							<!-- Right column -->
						</td>							
					</tr>
				</table>
				<!-- Header tables -->
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
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_changeWeek.action'; addweeklyshiftbyemployee_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; addweeklyshiftbyemployee_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='schedule.addshift.weekdayselector.availableweek.dateformat'><s:param value='prevDate'/></s:text>
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
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_changeWeek.action'; addweeklyshiftbyemployee_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; addweeklyshiftbyemployee_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='schedule.addshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_changeWeek.action'; addweeklyshiftbyemployee_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; addweeklyshiftbyemployee_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_changeWeek.action'; addweeklyshiftbyemployee_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; addweeklyshiftbyemployee_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='schedule.addshift.weekdayselector.availableweek.dateformat'><s:param value='nextDate'/></s:text>
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
	                  						<td width="12%" class="availableWeekDay"><a title="<s:text name='schedule.addshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>" href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addweeklyshiftbyemployee_form.action='addshiftbyemployee_changeDay.action'; addweeklyshiftbyemployee_form.selectedWeekDay.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDay'/></s:text>'; addweeklyshiftbyemployee_form.submit();" class="availableWeekDayLink">
	                  						<s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDay'/></s:text>
	                  						</a>
	                  						</td>                  						
	                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						</s:iterator>
                  						<td width="12%" class="selectedWeekDay"><s:text name="schedule.addshift.weekselector.week"/></td>
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
				<table class="weekScheduleMainTable" id="weekScheduleMainTable" cellpadding="1" cellspacing="0">
				    <!-- Header -->
				    <tr class="weekScheduleMainTableHeader">
						<td class="weekScheduleCellHeader"><s:text name="schedule.addshift.employee"/></td>    
						<td class="weekScheduleCellHeader"><s:text name="schedule.addshift.position"/></td>    
						<td class="weekScheduleCellHeader" width="45px"><s:text name="schedule.addshift.total_hours"/></td>
						<!-- Iterate week days -->
						<s:iterator id="weekDay" value="weekDaySelector.weekDays">
							<td class="weekScheduleCellHeader"><s:text name='schedule.addshift.weekly.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
						</s:iterator>
						<!-- End Iterate week days -->				    
					</tr>
				    <!-- Header -->
				    
				    <!-- Employees -->
				    <s:iterator id="dataRow" value="weeklyScheduleData.scheduleData" status="itScheduleData">
				    <tr class="scheduleMainTableEmployeeRow">
						<s:hidden id="scheduleIsFirstRow_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].firstRow"/>
						<s:hidden id="scheduleOriginalEmployeeId_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].originalEmployeeId"/>
						<s:hidden id="scheduleEmployeeMaxHoursDay_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeMaxHoursDay"/>
						<s:hidden id="scheduleEmployeeMaxHoursWeek_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeMaxHoursWeek"/>
						<s:hidden id="scheduleEmployeeMaxDaysWeek_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeMaxDaysWeek"/>
						<s:hidden id="scheduleEmployeeWage_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeWage"/>
						<s:hidden id="scheduleGroupById_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].groupById"/>
						<s:hidden id="scheduleOrderByEmployee_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].orderByEmployee"/>
						<s:hidden id="schedulePositionIndex_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].positionIndex"/>
				    	<s:if test="#dataRow.firstRow">
				    	<td class="weekScheduleNameCell" id="scheduleEmployee_<s:property value="#itScheduleData.index"/>" rowspan="<s:property value="weeklyScheduleData.getCountFor(#dataRow.employeeId)"/>" valign="top">
				    		<s:if test="%{editable}">
							<!--s:url id="employeeList" action="scheduleemployeeautocomplete" includeParams="none"/-->
							<!--s:autocompleter id="scheduleEmployee_%{#itScheduleData.index}" onchange="XXXreloadEmployeeMaxHoursDay('', %{#itScheduleData.index}); return true;" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeName" keyName="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeId" loadMinimumCount="3" forceValidOption="true" theme="ajax" href="%{employeeList}" dataFieldName="storeEmployees" autoComplete="true" searchType="substring"/-->
							<!--script>djConfig.searchIds.push("scheduleEmployee_<s:property value="#itScheduleData.index"/>");</script-->
							<s:hidden id="scheduleEmployeeName_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeName"/>
							<s:select id="scheduleEmployee_%{#itScheduleData.index}" onchange="setValueWithSelectedText('scheduleEmployee_%{#itScheduleData.index}', 'scheduleEmployeeName_%{#itScheduleData.index}'); return true;" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeId" list="employees" listKey="id" listValue="fullName" theme="simple"/>
							</s:if>
							<s:else>
							<s:property value="#dataRow.employeeName"/>
							</s:else>
				    	</td>
				    	</s:if>
				    	<s:else>
				    		<s:hidden id="scheduleEmployee_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].employeeName"/>
				    	</s:else>
						<td class="weekScheduleNameCell" id="schedulePositionTD_<s:property value="#itScheduleData.index"/>">
							<s:if test="%{editable}">
					    		<s:if test="%{selectedPositions.size() != 1}">
					    			<s:select id="scheduleposition_%{#itScheduleData.index}" onchange="wsUpdatePositionTotals();" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].positionId" list="selectedPositions" listKey="id" listValue="name" theme="simple"/>
					    		</s:if>
					    		<s:else>
					    			<s:property value="selectedPositions[0].name"/>
					    			<s:hidden id="scheduleposition_%{#itScheduleData.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].positionId" value="%{selectedPositions[0].id}"/>
					    		</s:else>
				    		</s:if>
				    		<s:else>
				    		<s:property value="#dataRow.positionName"/>
				    		<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].positionId"/>						
				    		</s:else>
				    		<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].positionName"/>						
						</td>    
						<td class="weekScheduleNameCell" id="scheduleWeeklyTotal_<s:property value="#itScheduleData.index"/>">&nbsp;</td>
						
						<s:iterator id="dayDataEntry" value="weeklySchedule" status="itDataEntry">
						<td class="weekScheduleValueCell" id="scheduleHours_<s:property value="#itScheduleData.index"/>_<s:property value="#itDataEntry.index"/>">
							<table border="0" cellpadding="1" cellspacing="0" align="<s:if test="%{editable}">left</s:if><s:else>center</s:else>" id="weekScheduleTimeInputTable">
								<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].day"/>
								<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].multipleShifts"/>
								<tr>
									<s:if test="%{editable}">
									<td><s:textfield cssClass="timeInput" id="weeklyScheduleInHour_%{#itScheduleData.index}_%{#itDataEntry.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].inHourAsString" onchange="updateTime(this); wsRefreshTotalHours(%{#itScheduleData.index}, %{#itDataEntry.index});" maxlength="8"/></td>
									<td><s:textfield cssClass="timeInput" id="weeklyScheduleOutHour_%{#itScheduleData.index}_%{#itDataEntry.index}" name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].outHourAsString" onchange="updateTime(this); wsRefreshTotalHours(%{#itScheduleData.index}, %{#itDataEntry.index});" maxlength="8"/></td>
									</s:if>
									<s:else>
									<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].inHourAsString"/>
									<s:hidden name="weeklyScheduleData.scheduleData[%{#itScheduleData.index}].weeklySchedule[%{#itDataEntry.index}].outHourAsString"/>
									<s:if test="shift">
									<td class="weekScheduleTimeInputCellText"><s:property value="inHourAsString"/></td>
									<td class="weekScheduleTimeInputCellText">-</td>
									<td class="weekScheduleTimeInputCellText"><s:property value="outHourAsString"/></td>
									</s:if>
									</s:else>
									<td class="weekScheduleTimeInputCellText" id="weeklyScheduleTotalHours_<s:property value="#itScheduleData.index"/>_<s:property value="#itDataEntry.index"/>">
									<s:property value="totalHoursAsString"/>
									</td>
									<s:if test="%{multipleShifts}">
									<td class="weekScheduleTimeInputCellText">
									<span title="| <s:iterator id="h" value="shiftHours"><s:property/> | </s:iterator>"><s:text name="schedule.addshift.weekly.has_multiple_shifts" /></span>
									</td>
									</s:if>
								</tr>
							</table>
						</td>
						</s:iterator>
				    </tr>
				    </s:iterator>
				    <!-- Employees -->

					<s:if test="%{editable}">
				    <!-- New Employee -->
				    <tr>
				    	<td class="weekScheduleNameCell">
							<!--s:url id="employeeList" action="scheduleemployeeautocomplete" includeParams="none"/-->
							<!--s:autocompleter id="newEmployeeName" name="newEmployeeName" loadMinimumCount="3" keyName="newEmployeeId" forceValidOption="true" theme="ajax" href="%{employeeList}" dataFieldName="storeEmployees" autoComplete="true" searchType="substring" /-->
							<!--script>djConfig.searchIds.push("newEmployeeName");</script-->
							<s:hidden id="newEmployeeName" name="newEmployeeName" value="%{newEmployeeName}"/>
							<s:select id="newEmployeeId" name="newEmployeeId" onchange="setValueWithSelectedText('newEmployeeId', 'newEmployeeName'); return true;" list="employees" listKey="id" listValue="fullName" theme="simple"/>
				    	</td>
				    	<td class="weekScheduleNameCell">
				    		<s:if test="%{selectedPositions.size() != 1}">
								<s:select name="newEmployeePositionId" list="selectedPositions" listKey="id" listValue="name" theme="simple"/>
				    		</s:if>
				    		<s:else>
				    			<s:property value="selectedPositions[0].name"/>
				    			<s:hidden name="newEmployeePositionId" value="%{selectedPositions[0].id}"/>
				    		</s:else>				    	
				    	</td>
						<td class="weekScheduleNameCell" align="center"><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); setValueWithSelectedText('newEmployeeId', 'newEmployeeName'); addweeklyshiftbyemployee_form.action='addweeklyshiftbyemployee_addEmployee.action'; addweeklyshiftbyemployee_form.submit();"><img src="<s:url value="/images/add.png" includeParams="none"/>" /></a></td>
						<td class="weekScheduleValueCell" colspan="7">&nbsp;</td>
				    </tr>				    
				    <!-- New Employee -->
				    </s:if>
				</table>			
			<!-- Schedule selection table -->
			</td>
		</tr>

		<tr>
			<td width="100%" align="left">
				<table border="0" cellpadding="1" cellspacing="2">
					<tr>
						<td class="infoMessage"><s:text name="schedule.addshift.weekly.has_multiple_shif.label" /></td>
					</tr>
				</table>
			</td>
		</tr>

		<s:if test="%{editable}">
		<tr>
			<td width="100%" align="right">
				<table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
					<tr>
						<td><s:submit onclick="return showWaitSplash();" id="saveButton" name="saveSchedule" key="save.button" theme="simple" cssClass="button"/></td>
						<td><s:submit id="cancelButton" key="cancel.button" action="addweeklyshiftbyemployee_cancel" theme="simple" cssClass="button"/></td>		                    
      				</tr>
     			</table>                    
    		</td>
		</tr>		
		</s:if>
	</table>
</s:form>
<script language="javascript" type="text/javascript">
<!--
wsInitialize(<s:property value="weekDays.size()"/>, <s:property value="positions.size()"/>);
<s:iterator id="pos" value="positions" status="posStatus">
	addPositionId(<s:property value="#posStatus.index"/>, '<s:property value="id"/>');
</s:iterator>
addScheduleTotalRows(0, <s:property value="totalScheduleRows"/>);
wsRefreshAllRowsTotals();
wsUpdateSummaryTotals();
// -->
</script>