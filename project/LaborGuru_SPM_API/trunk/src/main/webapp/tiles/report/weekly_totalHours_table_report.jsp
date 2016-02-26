<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />

<table border="0" cellspacing="0" align="center">
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.weeklytotalhours.title" />
    </td>
   </tr>
    <tr>
      <td>
		<table id="windowReportTable" cellspacing="0">
		  <tr>
			<td class="tableLabel">
			  <b><s:text name="report.weeklytotalhours.totalhours.label" /></b>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder"><s:text name="report.weeklytotalhours.dateformat"><s:param value="day"/></s:text></td>
			</s:iterator>
			<td class="greyTableLabelWithLeftBorder"><s:text name="report.weeklytotalhours.totalweek.label" /></td>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.weeklytotalhours.checks.label"><s:param value="%{getVariableNames().get(0)}"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="sales"/></s:text>
			  </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
				<s:text name="currency"><s:param value="totalSales"/></s:text>
			</td>
		  </tr>

		  <s:if test="%{isSecondaryVariablesConfigured(1)}">
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.checks.label"><s:param value="%{getVariableNames().get(1)}"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="variable2"/></s:text>
			  </td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
				<s:text name="currency"><s:param value="totalVariable2"/></s:text>
			</td>
		  </tr>
		  </s:if>

		  <s:if test="%{isSecondaryVariablesConfigured(2)}">
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.checks.label"><s:param value="%{getVariableNames().get(2)}"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="variable3"/></s:text>
			  </td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
				<s:text name="currency"><s:param value="totalVariable3"/></s:text>
			</td>
		  </tr>
		  </s:if>

		  <s:if test="%{isSecondaryVariablesConfigured(3)}">
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.checks.label"><s:param value="%{getVariableNames().get(3)}"/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="variable4"/></s:text>
			  </td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
				<s:text name="currency"><s:param value="totalVariable4"/></s:text>
			</td>
		  </tr>		  
		  </s:if>
		  
		  <s:if test="secondaryVariablesConfigured">
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.weeklytotalhours.total.label"/>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="total"/></s:text>
			  </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
				<s:text name="currency"><s:param value="total"/></s:text>
			</td>
		  </tr>
		  </s:if>
		  
		  <tr>
			<td class="tableLabelWithBottomBorder">
			  <s:text name="report.weeklytotalhours.scheduled.label" />
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBottomBorder">
			   <s:text name="report.total.hours"><s:param value="schedule"/></s:text></td>
			</s:iterator>
			<td class="greyTableValueWithLeftBottomBorder">
			   <s:text name="report.total.hours"><s:param value="totalSchedule"/></s:text></td>
		  </tr>
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.target.label" />
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			   <s:text name="report.total.hours"><s:param value="target"/></s:text>
        </td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
			   <s:text name="report.total.hours"><s:param value="totalTarget"/></s:text>
      		</td>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.weeklytotalhours.difference.label" />
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			   <s:text name="report.total.hours"><s:param value="difference"/></s:text>
        </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
			   <s:text name="report.total.hours"><s:param value="totalDifference"/></s:text>
      </td>
		  </tr>
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.weeklytotalhours.percentage.label" />
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			     <s:text name="report.percentage"><s:param value="percentage"/></s:text>
        </td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
			     <s:text name="report.percentage"><s:param value="totalPercentage"/></s:text>
      </td>
		  </tr>
		</table>
      </td>
    </tr>
    
	<tr>
		<td>&nbsp;</td>
	</tr>

	<!-- TPLH Report -->
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.txperlabourhr.title"><s:param value='mainVariableName'/><s:param value='mainVariableInitials'/></s:text>
    </td>
   </tr>
    <tr>
      <td>
		<table id="windowReportTable" cellspacing="0">
		  <tr>
			<td class="tableLabel">&nbsp;</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder"><s:text name="report.weeklytotalhours.dateformat"><s:param value="day"/></s:text></td>
			</s:iterator>
			<td class="greyTableLabelWithLeftBorder"><s:text name="report.weeklytotalhours.totalweek.label" /></td>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.txperlabourhr.scheduled.label"><s:param value='mainVariableInitials'/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			  	<s:text name="decimal"><s:param value="vplhSchedule"/></s:text>
			  </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
				<s:text name="decimal"><s:param value="totalVplhSchedule"/></s:text>
			</td>
		  </tr>
		  
		  <tr>
			<td class="tableLabel">
				<s:text name="report.txperlabourhr.target.label"><s:param value='mainVariableInitials'/></s:text>
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			   <s:text name="decimal"><s:param value="vplhTarget"/></s:text></td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
			   <s:text name="decimal"><s:param value="totalVplhTarget"/></s:text></td>
		  </tr>

		</table>
      </td>
    </tr>
	<!--  End new report table -->
	
	<tr>
		<td>&nbsp;</td>
	</tr>

	<!-- New report table -->
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.laborpercentsales.title" />
    </td>
   </tr>
    <tr>
      <td>
		<table id="windowReportTable" cellspacing="0">
		  <tr>
			<td class="tableLabel">&nbsp;</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder"><s:text name="report.weeklytotalhours.dateformat"><s:param value="day"/></s:text></td>
			</s:iterator>
			<td class="greyTableLabelWithLeftBorder"><s:text name="report.weeklytotalhours.totalweek.label" /></td>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.laborpercentsales.projected.label" />
			</td>
			<s:iterator id="txPerLaborHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			  	<s:text name="currency"><s:param value="ProjectedSales"/></s:text>
			  </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
				<s:text name="currency"><s:param value="totalProjectedSales"/></s:text>
			</td>
		  </tr>
		  <tr>
			<td class="tableLabel">
			  <s:text name="report.laborpercentsales.scheduled.label" />
			</td>
			<s:iterator id="totalHour" value="totalHours" status="itTotalHours">
			  <td class="tableValueWithLeftBorder">
			   <s:text name="decimal"><s:param value="scheduleLaborPercentage"/></s:text>%</td>
			</s:iterator>
			<td class="greyTableValueWithLeftBorder">
			   <s:text name="decimal"><s:param value="totalScheduleLaborPercentage"/></s:text>%</td>
		  </tr>
		  <tr>
			<td class="greyTableLabel">
			  <s:text name="report.laborpercentsales.target.label" />
			</td>
			<s:iterator id="txPerLaborHour" value="totalHours" status="itTotalHours">
			  <td class="greyTableValueWithLeftBorder">
			  	<s:text name="decimal"><s:param value="targetLaborPercentage"/></s:text>%
			  </td>
			</s:iterator>
			<td class="tableValueWithLeftBorder">
				<s:text name="decimal"><s:param value="totalTargetLaborPercentage"/></s:text>%
			</td>
		  </tr>
		</table>
      </td>
    </tr>
    
    <!-- End of Report -->

	<tr>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>
			<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="600" height="350" id="Column3D" >
		   	<param name="movie" value="<s:url value='/fusionCharts/FCF_MSColumn3DLineDY.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" />
   			<param name="FlashVars" value="&dataXML=<s:property value="xmlValues"/>">
   			<param name="quality" value="high" />
   			<embed src="<s:url value='/fusionCharts/FCF_MSColumn3DLineDY.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" flashVars="&dataXML=<s:property value="xmlValues"/>" quality="high" width="600" height="300" name="Column3D" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
			</object>
		</td>
	</tr>
</table>
