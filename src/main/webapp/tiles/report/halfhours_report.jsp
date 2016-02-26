<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form id="dailyReport_form" name="dailyReport_form" theme="simple">
	<s:hidden id="selectedDate" name="selectedDate"/>
	<s:hidden id="selectedWeekDay" name="selectedWeekDay"/>
	
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td id="titleBar"><s:text name="report.dailyHours.title" /></td>
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
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); dailyReport_form.action='halfHourReport_changeWeek.action'; dailyReport_form.selectedDate.value='<s:text name='report.weekdayselector.weekdayselector.input.dateformat'><s:param value='prevDate'/></s:text>'; dailyReport_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='report.weekdayselector.availableweek.dateformat'><s:param value='prevDate'/></s:text>
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
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); dailyReport_form.action='halfHourReport_changeWeek.action'; dailyReport_form.selectedDate.value='<s:text name='report.weekdayselector.weekdayselector.input.dateformat'><s:param value='weekDaySelector.previousStartingWeekDay'/></s:text>'; dailyReport_form.submit();"><img src="<s:url value="/images/cal_prev.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td class="calendarSelectedWeekText" nowrap="nowrap"><s:text name='schedule.addshift.weekdayselector.selectedweek.dateformat'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  						<td><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); dailyReport_form.action='halfHourReport_changeWeek.action'; dailyReport_form.selectedDate.value='<s:text name='report.weekdayselector.weekdayselector.input.dateformat'><s:param value='weekDaySelector.nextStartingWeekDay'/></s:text>'; dailyReport_form.submit();"><img src="<s:url value="/images/cal_next.png" includeParams="none"/>" border="0"/></a></td>
			                  						<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
			                  					</tr>
			                  				</table>
			                  			</td>
			                  			<td class="calendarTableColumn"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
                  						<s:iterator id="nextDate" value="weekDaySelector.nextStartingWeekDays" status="itNextDate">
			                  				<td class="calendarTableColumn" width="10%" nowrap="nowrap">
			                  					<a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); dailyReport_form.action='halfHourReport_changeWeek.action'; dailyReport_form.selectedDate.value='<s:text name='report.weekdayselector.weekdayselector.input.dateformat'><s:param value='nextDate'/></s:text>'; dailyReport_form.submit();" class="calendarUnselectedWeekLink">
			                  						<s:text name='report.weekdayselector.availableweek.dateformat'><s:param value='nextDate'/></s:text>
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
                  						<td width="12%" class="selectedWeekDay"><s:text name='report.weekdayselector.weekday.dateformat'><s:param value='weekDay'/></s:text></td>
                  						</s:if>
                  						<s:else>
                  						<td width="12%" class="availableWeekDay"><a href="<s:url value="#" includeParams="none"/>" onclick="showWaitSplash(); dailyReport_form.action='halfHourReport_changeDay.action'; dailyReport_form.selectedWeekDay.value='<s:text name='report.dailyHours.weekdayselector.daytooltip.dateformat'><s:param value='weekDay'/></s:text>'; dailyReport_form.submit();" class="availableWeekDayLink">
                  						<s:text name='report.weekdayselector.weekday.dateformat'><s:param value='weekDay'/></s:text>
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
				<table align="center" id="selectTable" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
				<tr>
     				<td align="right" class="form_label"><s:text name="report.reportGrouping.label"/></td>
				    <td align="left"><s:select id="selectGrouping" name="selectedGrouping" list="groupingMap" listKey="key" listValue="%{getText(value)}" onchange="controller.changed()" theme="simple" /></td>
	 				<td>&nbsp;</td>
					<s:url id="positionAutoCompleter" action="positionAutocompleter" namespace="/report" includeParams="none"/>
	 				<td align="left"><s:div  id="selectItemDaily" formId="dailyReport_form" href="%{positionAutoCompleter}" theme="ajax" listenTopics="/Changed" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/></td>						
				</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td align="center">
				<s:url id="halfHourReportUrl" action="halfHourReport_showReport" namespace="/report" includeParams="none"/>
			
			
				<s:div id="tableFrame" theme="ajax" 
							href="%{halfHourReportUrl}" 
							formId="dailyReport_form"
							listenTopics="/refresh"
							indicator="indicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
			</td>
		</tr>
	</table>
</s:form>
<center><img id="indicator"
	style="display: none;"
	src="<s:url value="/images/wait.gif" includeParams="none"/>"
	alt="<s:text name="wait.message"/>"
	title="<s:text name="wait.message"/>" border="0" />
</center>
	

<script language="javascript" type="text/javascript">

controller = {
		refresh : function(){
				},
		changed : function(){
				}
 	};
 
dojo.event.topic.registerPublisher("/refresh", controller, "refresh");
dojo.event.topic.registerPublisher("/Changed", controller, "changed");

djConfig.searchIds.push("selectItemDaily");	
djConfig.searchIds.push("tableFrame");

</script>
					