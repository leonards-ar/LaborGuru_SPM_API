<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>
<s:form id="halfhour_form" name="halfhour_form" action="halfhour_save" theme="simple">
<s:hidden id="selectedDate" name="selectedDate"/>
<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>

	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="projection.halfhour.title" />
			      </td>
        	  </tr>
              
              <tr>
              	<td class="errorMessage" align="center">
	              	<table border="0" align="center" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
              			<tr>
              				<td>
				              	<s:fielderror theme="simple"/>
				              	<s:actionerror theme="simple"/>
			              	</td>
            		  	</tr>
              		</table>
              	</td>
              </tr>
              <s:if test="%{!projectionError}">
              <tr>
              	<td align="center">
                  	<!-- Start week table -->
                  	<table align="center" id="calendarTable" width="100%" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  		<tr>
                  			<td align="center">
                  				<table align="center" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
                  					<tr>
                  						<s:iterator id="prevDate" value="weekDaySelector.previousStartingWeekDays" status="itPrevDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="halfhour_form.action='halfhour_changeWeek.action'; halfhour_form.selectedDate.value='<s:text name='projection.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; halfhour_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='projection.weekdayselector.availableweek.dateformat'><s:param value='prevDate'/></s:text>
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
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="halfhour_form.action='halfhour_changeWeek.action'; halfhour_form.selectedDate.value='<s:text name='projection.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; halfhour_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='projection.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="halfhour_form.action='halfhour_changeWeek.action'; halfhour_form.selectedDate.value='<s:text name='projection.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; halfhour_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="halfhour_form.action='halfhour_changeWeek.action'; halfhour_form.selectedDate.value='<s:text name='projection.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; halfhour_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='projection.weekdayselector.availableweek.dateformat'><s:param value='nextDate'/></s:text>
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
                  						<td width="13%" class="selectedWeekDay"><s:text name='projection.halfhour.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
                  						</s:if>
                  						<s:else>
                  						<td width="13%" class="availableWeekDay"><a href="<s:url value="#" includeParams="none"/>" onclick="halfhour_form.action='halfhour_changeDay.action';halfhour_form.selectedWeekDay.value='<s:text name='projection.weekdayselector.input.dateformat'><s:param value='weekDay'/></s:text>'; halfhour_form.submit();" class="availableWeekDayLink">
                  						<s:text name='projection.halfhour.weekday.dateformat'><s:param value='weekDay'/></s:text>
                  						</a>
                  						</td>                  						
                  						</s:else>
                  						<s:if test="!#itWeekDay.last">
                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						</s:if>
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
              	<td>              
             		<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
						<s:if test="%{getDisplayWeekUsed()}">
							<tr class="editFormEvenRow">
			                    <td width="15%" align="right" class="form_label" nowrap="nowrap"><s:text name="projection.halfhour.weeksused.label" /></td>
			                    <td width="25%" align="left" class="value">						
									<s:select name="usedWeeks" list="usedWeeksMap" listKey="key" listValue="%{getText(value)}" theme="simple" disabled="%{!isEditable()}" onchange="halfhour_form.action='halfhour_reviseUsedWeeks.action';halfhour_form.submit();" />
								</td>
								<td width="60%" align="left"><s:text name='projection.weekdayselector.selectedday.dateformat.long'><s:param value="weekDaySelector.selectedDay"/></s:text></td>
			                </tr>
						</s:if>
		              	<tr class="editFormOddRow">
                    		<td width="100%" align="center" colspan="3">
                    			<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0" align="center">
                    				<tr>
                    					<td>
			                    			<!-- Half Hour Projection -->
											<table border="0" cellpadding="3" cellspacing="1" colspan="0" cellspan="0" align="center">
												<tr class="editorTableHeader">
													<td><s:text name="projection.halfhour.hour.label" /></td>
													<td><s:text name="projection.halfhour.projection.label" /></td>
													<td><s:text name="projection.halfhour.changes.label" /></td>
													<td><s:text name="projection.halfhour.revisedprojection.label" /></td>
												</tr>
  												<tr>
													<s:hidden id="totalProjectedValues" name="totalProjectedValues"/>
													<s:hidden id="totalAdjustedValues" name="totalAdjustedValues"/>
													<s:hidden id="totalRevisedValues" name="totalRevisedValues"/>
													<td class="editorTableFirstColumn"><b><s:text name="projection.halfhour.daytotal.label" /></b></td>
  													<td class="editorTableOddRow"><div id="totalProjectionValues"><b><s:text name="currency"><s:param value="totalProjectedValues"/></s:text></b></div></td>
													<td class="editorTableOddRow"><div id="totalAdjustedValues"><b><s:text name="currency"><s:param value="totalAdjustedValues"/></s:text></b></div></td>
													<td class="editorTableOddRow"><div id="totalRevisedValues"><b><s:text name="currency"><s:param value="totalRevisedValues"/></s:text></b></div></td>
													<s:hidden name="totalProjectionValues"/>
													<s:hidden name="totalAdjustedValues"/>
													<s:hidden name="totalRevisedValues"/>
												</tr>
												<!-- Iterate for each half hour from open to close hour -->
												<s:iterator id="halfhourElement" value="projectionElements" status="itHalfHourProjection">
												<s:if test="%{isEditable()}">
													<s:if test="%{isHalfHourVisible(#halfhourElement)}">
														<tr>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].id"/>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].hour"/>
		  												    <td class="editorTableFirstColumn"><s:property value="hour"/></td>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].projectedValue"/>
															<td class="editorTableEvenRow"><s:text name="currency"><s:param value="projectedValue" /></s:text></td>
															<td class="editorTableEvenRow"><s:textfield name="projectionElements[%{#itHalfHourProjection.index}].adjustedValue" size="10" theme="simple" /></td>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].revisedValue"/>
															<td class="editorTableEvenRow"><s:text name="currency"><s:param value="revisedValue"/></s:text></td>
														</tr>
													</s:if>
													<s:else>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].id"/>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].hour"/>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].projectedValue"/>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].adjustedValue"/>
															<s:hidden name="projectionElements[%{#itHalfHourProjection.index}].revisedValue"/>
													</s:else>
												</s:if>
												<s:else>
													<s:if test="%{isHalfHourVisible(#halfhourElement)}">
														<tr>
		  												    <td class="editorTableFirstColumn"><s:property value="hour"/></td>
															<td class="editorTableEvenRow"><s:text name="currency"><s:param value="projectedValue" /></s:text></td>
															<td class="editorTableEvenRow">&nbsp;</td>
															<td class="editorTableEvenRow"><s:text name="currency"><s:param value="revisedValue"/></s:text></td>
														</tr>
													</s:if>
												</s:else>
												</s:iterator>												
												<!-- End Iterate for each half hour from open to close hour -->							
											</table>                    			
			                    			<!-- End Half Hour Projection -->
                    					</td>
	                    					<td valign="top">
				                    			<!-- Action buttons -->
				                    			<br/>
				                    			<br/>
							                    <table border="0" cellpadding="5" cellspacing="5" colspan="0" cellspan="0">
													<s:if test="%{isEditable() && !hasErrors()}">
									                    <tr>
									                		<td align="center"><s:submit id="reviseButton" key="projection.halfhour.revise.button" theme="simple" cssClass="button" action="halfhour_reviseProjections"/></td>
									                	</tr>
														<s:if test="%{isTotalRevisedValuesGreaterThanZero()}">
									                    <tr>
									                		<td align="center"><s:submit id="saveButton" onclick="return showWaitSplash();" key="save.button" theme="simple" cssClass="button"/></td>
									                	</tr>
														</s:if>
									                	<tr>
									                    	<td align="center"><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
									                    </tr>
													</s:if>
								                    <tr>
								                		<td align="center"><s:submit id="backDailyButton" key="projection.halfhour.backDaily.button" theme="simple" cssClass="button" action="daily_edit"/></td>
								                    </tr>
							                    </table>                    
				                    			<!-- End Action buttons -->
	                    					</td>
                    				</tr>
                    			</table>
                    		</td>
		                </tr>
 
              		</table>
	              </td>
              </tr>
			</s:if>
          </table>
</s:form>