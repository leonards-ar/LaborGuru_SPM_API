<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />

<table border="0" cellspacing="0" align="center">
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.dailyHoursTable.title" />
    </td>
   </tr>
    <tr>
      <td>
		<table id="windowReportTable" cellspacing="0">
		  <tr>
			<td class="tableLabel">&nbsp;</td>
			<td class="tableLabelWithLeftBorder"><s:text name="report.dailyHours.total.label" /></td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
				<s:if test="#itTotalHours.odd">
			  		<td class="tableValueWithLeftBorder" colspan="2"><s:text name="report.dailyHours.dateformat"><s:param value="day"/></s:text></td>
				</s:if>
			</s:iterator>
			
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.weeklytotalhours.sales.label" />
			</td>
			<td class="greyTableValueWithLeftBorder"><s:text name="currency"><s:param value="totalSales"/></s:text></td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder"><s:text name="currency"><s:param value="sales"/></s:text></td>
			</s:iterator>

		  </tr>

		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.scheduled.label" />
			</td>
			<td class="tableValueWithLeftBorder">
			  <s:text name="report.total.hours"><s:param value="totalSchedule"/></s:text></td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			  	<s:text name="report.total.hours"><s:param value="schedule"/></s:text>
			  </td>
			</s:iterator>
		  </tr>
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.target.label" />
			</td>
			<td class="tableValueWithLeftBorder">
			  <s:text name="report.total.hours"><s:param value="totalTarget"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			  	<s:text name="report.total.hours"><s:param value="target"/></s:text>
			  </td>
			</s:iterator>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.weeklytotalhours.difference.label" />
			</td>
			<td class="greyTableValueWithLeftBorder">
			  <s:text name="report.total.hours"><s:param value="totalDifference"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="<s:if test="difference < 0">differenceNegativeWithLeftBorder</s:if><s:elseif test="difference > 0">differencePositiveWithLeftBorder</s:elseif><s:else>greyTableValueWithLeftBorder</s:else>">
			    <s:text name="report.total.hours"><s:param value="difference"/></s:text>
			  </td>
			</s:iterator>
		  </tr>
		</table>
      </td>
    </tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>
			<table border="0"  width="100%" cellspacing="0" align="center">
			<tr>
				<td>
					<table border="0" width="100%" cellspacing="0" align="center">
					<!-- New Labor % table -->
					<tr><td>
						<s:url id="percentLaborUrl" action="percentLaborReport_showReport" namespace="/report" includeParams="none"/>
						<s:div id="tableFrame" theme="ajax" 
								href="%{percentLaborUrl}" 
								formId="dailyReport_form"
								listenTopics="/refresh"
								indicator="indicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
					</td></tr>
					<tr><td>&nbsp;</td></tr>					
					<!-- End New Labor % table -->
					<tr><td>
						<s:url id="fixedLaborUrl" action="fixedLaborReport_showReport" namespace="/report" includeParams="none"/>
			
			
						<s:div id="tableFrame" theme="ajax" 
								href="%{fixedLaborUrl}" 
								formId="dailyReport_form"
								listenTopics="/refresh"
								indicator="indicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
					</td></tr>
					</table>
				</td>
				<td>
					<table border="0" width="100%" cellspacing="0" align="center">
					<tr>
						<td>
							
						</td>
						<td>
							<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="600" height="350" id="Column3D" >
						   	<param name="movie" value="<s:url value='/fusionCharts/FCF_MSColumn2DLineDY.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" />
				   			<param name="FlashVars" value="&dataXML=<s:property value="xmlValues"/>">
				   			<param name="quality" value="high" />
				   			<embed src="<s:url value='/fusionCharts/FCF_MSColumn2DLineDY.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" flashVars="&dataXML=<s:property value="xmlValues"/>" quality="high" width="600" height="300" name="Column3D" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
							</object>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
</table>
