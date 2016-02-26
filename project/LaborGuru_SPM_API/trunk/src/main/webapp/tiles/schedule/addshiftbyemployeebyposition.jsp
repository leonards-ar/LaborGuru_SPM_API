<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form id="addshiftbyemployeebyposition_form" name="addshiftbyemployeebyposition_form" action="addshiftbyemployeebyposition_save" theme="simple">
	<s:hidden id="selectedDate" name="selectedDate"/>
	<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>
	<s:hidden id="dailyVolume" name="dailyVolume"/>
	<s:hidden id="hiddenTotalScheduledInMinutes" name="hiddenTotalScheduledInMinutes"/>
	<s:hidden id="scheduleDataIndex" name="scheduleDataIndex"/>
	<s:hidden id="copySchedule" name="copySchedule"/>
	
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td id="titleBar"><s:text name="schedule.addshift.byemployeebyposition.title" /></td>
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
												<td class="windowTableLabel"><s:text name="schedule.addshift.daily_volume"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.schedule"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.target"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.diff"/></td>
												<td class="windowTableLabel"><s:text name="schedule.addshift.diffpercent"/></td>
											</tr>
											
											<tr>
												<td class="windowTableValue"><s:text name="currency"><s:param value="dailyVolume"/></s:text></td>
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
													<s:select id="selectView" name="selectView" onchange="showWaitSplash(); addshiftbyemployeebyposition_form.action=addshiftbyemployeebyposition_form.selectView.value; addshiftbyemployeebyposition_form.submit();" theme="simple" list="scheduleViewsMap" listKey="key" listValue="%{getText(value)}"/></td>
												<td align="right" class="form_label"><s:text name="schedule.addshift.positions"/></td>
												<td align="left">
													<s:select onchange="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_selectPosition.action'; addshiftbyemployeebyposition_form.submit();" name="positionSelectId" list="positions" listKey="uniqueId" listValue="name" theme="simple" headerKey="" headerValue="%{getText('schedule.addshift.positions.header.label')}">
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
												<s:datetimepicker id="copy_target_day" type="date" formatLength="short" displayFormat="%{getText('datepicker.format.date')}" disabled="true" name="copyTargetDay" theme="simple"/>
												<script>djConfig.searchIds.push("copy_target_day");</script>
												</td>
												<td align="right"><s:submit onclick="return confirmCopySchedule(this, '%{getText('schedule.addshift.copy.schedule.confirm.msg')}');" id="copyButton" key="schedule.addshift.copy.button" action="addshiftbyemployeebyposition_copySchedule" theme="simple" cssClass="button"/></td>
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
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_changeWeek.action'; addshiftbyemployeebyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; addshiftbyemployeebyposition_form.submit();" class="calendarUnselectedWeekLink">
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
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_changeWeek.action'; addshiftbyemployeebyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; addshiftbyemployeebyposition_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='schedule.addshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_changeWeek.action'; addshiftbyemployeebyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; addshiftbyemployeebyposition_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_changeWeek.action'; addshiftbyemployeebyposition_form.selectedDate.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; addshiftbyemployeebyposition_form.submit();" class="calendarUnselectedWeekLink">
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
                  						<s:if test="%{weekDaySelector.isSelectedWeekDay(#weekDay)}">
                  						<td width="12%" class="selectedWeekDay" title="<s:text name='schedule.addshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>"><s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
                  						</s:if>
                  						<s:else>
                  						<td width="12%" class="availableWeekDay"><a title="<s:text name='schedule.addshift.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>" href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_changeDay.action'; addshiftbyemployeebyposition_form.selectedWeekDay.value='<s:text name='schedule.addshift.weekdayselector.input.dateformat'><s:param value='weekDay'/></s:text>'; addshiftbyemployeebyposition_form.submit();" class="availableWeekDayLink">
                  						<s:text name='schedule.addshift.weekday.dateformat'><s:param value='weekDay'/></s:text>
                  						</a>
                  						</td>                  						
                  						</s:else>
                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						</s:iterator>
                  						<td width="12%" class="availableWeekDay"><a href="#" onclick="showWaitSplash(); addshiftbyemployeebyposition_form.action='addweeklyshiftbyposition_edit.action'; addshiftbyemployeebyposition_form.submit();" class="availableWeekDayLink"><s:text name="schedule.addshift.weekselector.week"/></a></td>
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
		
<!-- Start of schedule -->		
		<s:iterator id="aSchedule" value="positionScheduleData" status="itSchedule">
		<s:hidden name="positionScheduleData[%{#itSchedule.index}].position.id"/>
		<s:hidden name="positionScheduleData[%{#itSchedule.index}].position.name"/>
		
		<tr>
			<td align="left" class="subtitleBar"><s:property value="position.name"/></td>
		</tr>
		<s:if test="%{!weekDaySelector.isSelectedDateBeforeToday()}">
		<tr>
			<td align="center" width="100%">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="center" width="100%">
							<!-- Schedule selection action table -->
							<table id="scheduleActionsTable" border="0" cellpadding="0" cellspacing="0">
								<tr>
							    	<td nowrap><a href="#" onclick="changeAction(1, '<s:property value="#itSchedule.index"/>');"><img title="<s:text name="schedule.addshift.shift.alt"/>" alt="<s:text name="schedule.addshift.shift.alt"/>" id="<s:property value="#itSchedule.index"/>scheduleShiftImage" class="scheduleActionImageOff" border="0" src="<s:url value="/images/add_shift.gif" includeParams="none"/>"/></a></td>
							    	<td class="scheduleActionLabel" valign="middle">&nbsp;</td>
							    	<td class="scheduleActionLabel" valign="middle"><a href="#" class="scheduleActionLink" onclick="changeAction(1, '<s:property value="#itSchedule.index"/>');"><s:text name="schedule.addshift.shift.alt"/></a></td>
							        <td class="scheduleActionLabel" valign="middle">&nbsp;|&nbsp;</td>
							    	<td nowrap><a href="#" onclick="changeAction(3, '<s:property value="#itSchedule.index"/>');"><img title="<s:text name="schedule.addshift.break.alt"/>" alt="<s:text name="schedule.addshift.break.alt"/>" id="<s:property value="#itSchedule.index"/>scheduleBreakImage" class="scheduleActionImageOff" border="0" src="<s:url value="/images/add_break.gif" includeParams="none"/>"/></a></td>
							    	<td class="scheduleActionLabel" valign="middle">&nbsp;</td>
							    	<td class="scheduleActionLabel" valign="middle"><a href="#" class="scheduleActionLink" onclick="changeAction(3, '<s:property value="#itSchedule.index"/>');"><s:text name="schedule.addshift.break.alt"/></a></td>
							        <td class="scheduleActionLabel" valign="middle">&nbsp;|&nbsp;</td>
							    	<td nowrap><a href="#" onclick="changeAction(5, '<s:property value="#itSchedule.index"/>');"><img title="<s:text name="schedule.addshift.free.alt"/>" alt="<s:text name="schedule.addshift.free.alt"/>" id="s<s:property value="#itSchedule.index"/>cheduleDeleteImage" class="scheduleActionImageOff" border="0" src="<s:url value="/images/del_shift.gif" includeParams="none"/>"/></a></td>
							    	<td class="scheduleActionLabel" valign="middle">&nbsp;</td>
							    	<td class="scheduleActionLabel" valign="middle"><a href="#" class="scheduleActionLink" onclick="changeAction(5, '<s:property value="#itSchedule.index"/>');"><s:text name="schedule.addshift.free.alt"/></a></td>
							        <td class="scheduleActionMessage">&nbsp;&nbsp;</td>
									<td id="<s:property value="#itSchedule.index"/>actionMessage" class="scheduleActionMessage"></td>							    
							    </tr>
							</table>				
							<!-- Schedule selection action table -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</s:if>
		
		<tr>
			<td align="center">
			<!-- Schedule selection table -->
				<table class="scheduleMainTable" id="scheduleMainTable" cellpadding="0" cellspacing="0">
				    <!-- Header -->
				    <tr class="scheduleMainTableHeader">
						<td class="scheduleCellHeader"><s:text name="schedule.addshift.employee"/></td>    
						<td class="scheduleCellHeader" width="45px"><s:text name="schedule.addshift.in_hour"/></td>    
						<td class="scheduleCellHeader" width="45px"><s:text name="schedule.addshift.out_hour"/></td>
						<td class="scheduleCellHeader" width="45px"><s:text name="schedule.addshift.total_hours"/></td>
						<s:iterator id="hourLabel" value="scheduleLabelHours" status="itHourLabel">     
							<td class="scheduleCellHeader" colspan="<s:property value="colspan"/>"><s:text name='schedule.addshift.hour.dateformat'><s:param value='hour'/></s:text></td>
						</s:iterator>         
				    </tr>
				    <!-- Header -->
				    
				    <!-- Employees -->
				    <s:iterator id="data" value="scheduleData" status="itScheduleData">
					    <tr class="scheduleMainTableEmployeeRow">
			    			<s:hidden id="%{#itSchedule.index}scheduleposition_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].positionId" value="%{position.id}"/>
				    		<s:hidden name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].positionName"/>
							<td class="scheduleNameCell">
							<s:if test="%{weekDaySelector.isSelectedDateBeforeToday()}">
					    		<s:property value="#data.employeeName"/>
							</s:if>
							<s:else>
								<s:hidden id="%{#itSchedule.index}scheduleOriginalEmployeeId_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].originalEmployeeId"/>
								<s:hidden id="%{#itSchedule.index}scheduleEmployeeMaxWeekHours_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeMaxHoursDay"/>
								<s:hidden id="%{#itSchedule.index}scheduleEmployeeWage_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeWage"/>
								<!--s:url id="employeeList" action="scheduleemployeeautocomplete" includeParams="none"/-->
								<!--s:autocompleter id="%{#itSchedule.index}scheduleEmployee_%{#itScheduleData.index}" onchange="reloadEmployeeMaxHoursDay('%{#itSchedule.index}', %{#itScheduleData.index}); return true;" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeName" keyName="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeId" loadMinimumCount="3" forceValidOption="true" theme="ajax" href="%{employeeList}" dataFieldName="storeEmployees" autoComplete="true" searchType="substring"/-->
								<!--script>djConfig.searchIds.push("<s:property value="#itSchedule.index"/>scheduleEmployee_<s:property value="#itScheduleData.index"/>");</script-->
								<s:hidden id="%{#itSchedule.index}scheduleEmployeeName_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeName" value="%{positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeName}"/>
								<s:select id="%{#itSchedule.index}scheduleEmployee_%{#itScheduleData.index}" onchange="setValueWithSelectedText('%{#itSchedule.index}scheduleEmployee_%{#itScheduleData.index}', '%{#itSchedule.index}scheduleEmployeeName_%{#itScheduleData.index}'); return true;" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].employeeId" list="employees" listKey="id" listValue="fullName" theme="simple"/>
							</s:else> 
							<s:hidden id="%{#itSchedule.index}inHourInput_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].inHour"/>
							<s:hidden id="%{#itSchedule.index}outHourInput_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].outHour"/>
							<s:hidden id="%{#itSchedule.index}totalHoursInput_%{#itScheduleData.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].totalHours"/>
							</td>    
							<td class="scheduleValueCell" id="<s:property value="#itSchedule.index"/>inHour_<s:property value="#itScheduleData.index"/>"><s:property value="#data.inHour"/></td>    
							<td class="scheduleValueCell" id="<s:property value="#itSchedule.index"/>outHour_<s:property value="#itScheduleData.index"/>"><s:property value="#data.outHour"/></td>
							<td class="scheduleValueCell" id="<s:property value="#itSchedule.index"/>totalHours_<s:property value="#itScheduleData.index"/>"><s:property value="#data.totalHours"/></td>            
							<s:iterator id="hour" value="scheduleIndividualHours" status="itHour">
								<s:if test="#itHour.first">
									<s:iterator id="startToIgnore" value="scheduleIndividualStartHoursToIgnore">
										<td class="scheduleUnavailable">
											&nbsp;
										</td>
									</s:iterator>
								</s:if>  
								<td id='<s:property value="#itSchedule.index"/>cell_<s:property value="#itScheduleData.index"/>_<s:property value="#itHour.index"/>' <s:if test="%{!weekDaySelector.isSelectedDateBeforeToday()}">onclick="scheduleClick(this, <s:property value="#itScheduleData.index"/>,<s:property value="#itHour.index"/>,'<s:property value="#data.positionId"/>', '<s:property value="#itSchedule.index"/>');" onMouseOver="scheduleOnMouseOver(this);" onMouseOut="scheduleOnMouseOut(this);"</s:if> class='<s:if test="%{#data.isBreakShift(#itHour.index)}">scheduleBreak</s:if><s:else><s:if test="%{#data.isFreeShift(#itHour.index)}">scheduleEmpty</s:if><s:else>scheduleSelected</s:else></s:else>'>
									&nbsp;
									<s:hidden id="%{#itSchedule.index}schedulehour_%{#itScheduleData.index}_%{#itHour.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].hours[%{#itHour.index}]"/>
									<s:hidden id="%{#itSchedule.index}schedule_%{#itScheduleData.index}_%{#itHour.index}" name="positionScheduleData[%{#itSchedule.index}].scheduleData[%{#itScheduleData.index}].schedule[%{#itHour.index}]"/>
								</td>            
								<s:if test="#itHour.last">
									<s:iterator id="startToIgnore" value="scheduleIndividualEndHoursToIgnore">
										<td class="scheduleUnavailable">
											&nbsp;
										</td>
									</s:iterator>
								</s:if> 
							</s:iterator>
					    </tr>
					</s:iterator>
					
					<s:if test="%{!weekDaySelector.isSelectedDateBeforeToday()}">
					<!-- Add new employee to schedule -->
				    <tr class="scheduleMainTableEmployeeRow">
		    			<s:hidden name="positionScheduleData[%{#itSchedule.index}].newEmployeePositionId" value="%{positionScheduleData[%{#itSchedule.index}].position.id}"/>
						<td class="scheduleNameCell">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<!--s:url id="employeeList" action="scheduleemployeeautocomplete" includeParams="none"/-->
										<!--s:autocompleter id="%{#itSchedule.index}newEmployeeName" name="positionScheduleData[%{#itSchedule.index}].newEmployeeName" loadMinimumCount="3" keyName="positionScheduleData[%{#itSchedule.index}].newEmployeeId" forceValidOption="true" theme="ajax" href="%{employeeList}" dataFieldName="storeEmployees" autoComplete="true" searchType="substring" /-->
										<!--script>djConfig.searchIds.push("<s:property value="#itSchedule.index"/>newEmployeeName");</script-->
										<s:hidden id="%{#itSchedule.index}newEmployeeName" name="positionScheduleData[%{#itSchedule.index}].newEmployeeName" value="%{positionScheduleData[%{#itSchedule.index}].newEmployeeName}"/>
										<s:select id="%{#itSchedule.index}newEmployeeId" onchange="setValueWithSelectedText('%{#itSchedule.index}newEmployeeId', '%{#itSchedule.index}newEmployeeName'); return true;" name="positionScheduleData[%{#itSchedule.index}].newEmployeeId" list="employees" listKey="id" listValue="fullName" theme="simple"/>
									</td>
									<td>
										&nbsp;
									</td>
									<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); setValueWithSelectedText('%{#itSchedule.index}newEmployeeId', '%{#itSchedule.index}newEmployeeName'); addshiftbyemployeebyposition_form.action='addshiftbyemployeebyposition_addEmployee.action'; addshiftbyemployeebyposition_form.scheduleDataIndex.value='<s:property value="#itSchedule.index"/>' ;addshiftbyemployeebyposition_form.submit();"><img src="<s:url value="/images/add.png" includeParams="none"/>" /></a></td>
								</tr>
							</table>
						</td>    
						<td class="scheduleValueCell">&nbsp;</td>    
						<td class="scheduleValueCell">&nbsp;</td>
						<td class="scheduleValueCell">&nbsp;</td>            
						<s:iterator id="hour" value="scheduleIndividualHours" status="itHour">
							<s:if test="#itHour.first">
								<s:iterator id="startToIgnore" value="scheduleIndividualStartHoursToIgnore">
									<td class="scheduleUnavailable">&nbsp;</td>
								</s:iterator>
							</s:if>  
							<td class="scheduleUnavailable">&nbsp;</td>
							<s:if test="#itHour.last">
								<s:iterator id="startToIgnore" value="scheduleIndividualEndHoursToIgnore">
									<td class="scheduleUnavailable">&nbsp;</td>
								</s:iterator>
							</s:if> 
						</s:iterator>
				    </tr>					
				    <!-- Employees -->
				    </s:if>
				    
				    <!-- Staffing -->
				    <tr class="scheduleMainTableEmployeeRow">
				    	<td colspan="3" class="scheduleStaffingNameCell"><s:text name="schedule.addshift.staffing.total"/>&nbsp;&nbsp;</td>
				    	<td class="scheduleStaffingValueCell" id='<s:property value="#itSchedule.index"/>total_cell'>&nbsp;</td>
							<s:iterator id="staffing" value="minimumStaffing" status="itStaffing">
								<s:if test="#itStaffing.first">
									<s:iterator id="startToIgnore" value="scheduleIndividualStartHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if>
								<td id='<s:property value="#itSchedule.index"/>total_cell_<s:property value="#itStaffing.index"/>' class="scheduleTotalValueCell">&nbsp;</td>
								<s:if test="#itStaffing.last">
									<s:iterator id="startToIgnore" value="scheduleIndividualEndHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if> 
							</s:iterator>				    	
				    </tr>
				    
				    <tr class="scheduleMainTableEmployeeRow">
				    	<td colspan="3" class="scheduleStaffingNameCell"><s:text name="schedule.addshift.staffing.minimum_staffing"/>&nbsp;&nbsp;</td>
				    	<td class="scheduleStaffingValueCell" id='<s:property value="#itSchedule.index"/>total_staffing_cell'><s:property value='totalMinimutStaffingTime'/></td>
							<s:iterator id="staffing" value="minimumStaffing" status="itStaffing">
								<s:if test="#itStaffing.first">
									<s:iterator id="startToIgnore" value="scheduleIndividualStartHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if>
								<td id='<s:property value="#itSchedule.index"/>staffing_cell_<s:property value="#itStaffing.index"/>' class="scheduleStaffingCalculated">
									<div id='<s:property value="#itSchedule.index"/>staffing_div_<s:property value="#itStaffing.index"/>'><s:property/></div>
									<s:hidden name="positionScheduleData[%{#itSchedule.index}].minimumStaffing[%{#itStaffing.index}]"/>
								</td>
								<s:if test="#itStaffing.last">
									<s:iterator id="startToIgnore" value="scheduleIndividualEndHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if> 
							</s:iterator>				    	
				    </tr>	

				    <tr class="scheduleMainTableEmployeeRow">
				    	<td colspan="3" class="scheduleStaffingNameCell"><s:text name="schedule.addshift.staffing.difference"/>&nbsp;&nbsp;</td>
				    	<td class="scheduleStaffingValueCell" id='<s:property value="#itSchedule.index"/>total_difference_cell'>&nbsp;</td>
							<s:iterator id="staffing" value="minimumStaffing" status="itStaffing">
								<s:if test="#itStaffing.first">
									<s:iterator id="startToIgnore" value="scheduleIndividualStartHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if>
								<td id='<s:property value="#itSchedule.index"/>difference_cell_<s:property value="#itStaffing.index"/>' class="scheduleStaffingDifferenceEqual">&nbsp;</td>
								<s:if test="#itStaffing.last">
									<s:iterator id="startToIgnore" value="scheduleIndividualEndHoursToIgnore">
										<td class="scheduleStaffingUnavailable">&nbsp;</td>
									</s:iterator>
								</s:if> 
							</s:iterator>				    	
				    </tr>				    			    
				    <!--  Staffing -->
				</table>			
			<!-- Schedule selection table -->
			</td>
		</tr>
				
		<s:if test="!#itSchedule.last">
		<tr>
			<td width="100%" align="left">&nbsp;</td>
		</tr>
		</s:if>
		</s:iterator>
		<!-- End of schedule -->

		<tr>
			<td width="100%" align="left">
				<!-- Caption -->
				<table border="0" cellpadding="1" cellspacing="2">
					<tr>
						<td class="scheduleSelectedCaption"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
						<td class="captionText"><s:text name="schedule.addshift.shift.caption"/></td>
						<td>&nbsp;</td>
						<td class="scheduleBreakCaption"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
						<td class="captionText"><s:text name="schedule.addshift.break.caption"/></td>
						<td>&nbsp;</td>
						<td class="scheduleEmptyCaption"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
						<td class="captionText"><s:text name="schedule.addshift.free.caption"/></td>
					</tr>
				</table>
				<!-- Caption -->			
			</td>
		</tr>
				
		<s:if test="%{!weekDaySelector.isSelectedDateBeforeToday()}">
		<tr>
			<td width="100%" align="right">
				<table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
					<tr>
						<td><s:submit onclick="return showWaitSplash();" id="saveButton" name="saveSchedule" key="save.button" theme="simple" cssClass="button"/></td>
						<td><s:submit id="cancelButton" key="cancel.button" action="addshiftbyemployeebyposition_cancel" theme="simple" cssClass="button"/></td>		                    
      				</tr>
     			</table>                    
    		</td>
		</tr>
		</s:if>
	</table>
</s:form>

<script language="javascript" type="text/javascript">
initialize(<s:property value="totalIndividualHours"/>, '<s:property value="breakId"/>', '<s:text name="schedule.addshift.cannot_change_row_message"/>', '<s:text name="schedule.addshift.start_time_message"/>', '<s:text name="schedule.addshift.end_time_message"/>', <s:property value="positions.size()"/>, <s:property value="positionScheduleData.size()"/>);
initializeMultiSchedule(<s:property value="positionScheduleData.size()"/>);
<s:iterator id="pos" value="positions" status="posStatus">
	addPositionId(<s:property value="#posStatus.index"/>, '<s:property value="id"/>');
</s:iterator>
<s:iterator id="aSchedule" value="positionScheduleData" status="itSchedule">
addScheduleTotalRows(<s:property value="#itSchedule.index"/>, <s:property value="scheduleRows"/>);
refreshRows('<s:property value="#itSchedule.index"/>');
changeAction(1, '<s:property value="#itSchedule.index"/>');
updateSummaryTotals('<s:property value="#itSchedule.index"/>');
</s:iterator>
</script>
