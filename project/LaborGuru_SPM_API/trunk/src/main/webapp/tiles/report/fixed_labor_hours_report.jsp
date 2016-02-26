<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
                    			
<table id="windowReportTable" cellspacing="0">
	<tr>
		<td class="greyTableLabel">&nbsp;</td>
		<td class="tableLabelWithLeftBorder"><s:text name="report.fixedlabor.total.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.fixedlabor.service.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.fixedlabor.opening.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.fixedlabor.flexible.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.fixedlabor.closing.label" /></td>
	</tr>

	<tr>
		<td class="tableLabelWithBottomBorder"><s:text name="report.fixedlabor.schedule.label" /></td>
		<td class="greyTableValueWithLeftBottomBorder">
		  <s:text name="report.total.hours"><s:param value="fixedLaborHoursReport.totalSchedule" /></s:text>
		</td>
		<s:push value="fixedLaborHoursReport.schedule">
			<td class="tableValueWithLeftBottomBorder"><s:text name="report.total.hours"><s:param value="serviceHours"/></s:text></td>
			<td class="tableValueWithLeftBottomBorder"><s:text name="report.total.hours"><s:param value="openHours"/></s:text></td>
			<td class="tableValueWithLeftBottomBorder"><s:text name="report.total.hours"><s:param value="flexHours"/></s:text></td>
			<td class="tableValueWithLeftBottomBorder"><s:text name="report.total.hours"><s:param value="closeHours"/></s:text></td>		
		</s:push>
	<tr>
	<tr>
		<td class="tableLabel"><s:text name="report.fixedlabor.target.label" /></td>
		<td class="greyTableValueWithLeftBorder">
		  <s:text name="report.total.hours"><s:param value="fixedLaborHoursReport.totalTarget" /></s:text>
		</td>
		<s:push value="fixedLaborHoursReport.target">
			<td class="tableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="serviceHours"/></s:text></td>
			<td class="tableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="openHours"/></s:text></td>
			<td class="tableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="flexHours"/></s:text></td>
			<td class="tableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="closeHours"/></s:text></td>		
		</s:push>
	</tr>
	<tr>
		<td class="greyTableLabel"><s:text name="report.fixedlabor.difference.label" /></td>
		<td class="tableValueWithLeftBorder">
		  <s:text name="report.total.hours"><s:param value="fixedLaborHoursReport.totalDifference" /></s:text>
		</td>
		<s:push value="fixedLaborHoursReport.difference">
			<td class="greyTableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="serviceHours"/></s:text></td>
			<td class="greyTableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="openHours"/></s:text></td>
			<td class="greyTableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="flexHours"/></s:text></td>
			<td class="greyTableValueWithLeftBorder"><s:text name="report.total.hours"><s:param value="closeHours"/></s:text></td>		
		</s:push>		
	</tr>
</table>