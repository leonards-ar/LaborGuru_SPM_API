<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="showTable">

<table id="windowReportTable" width="100%" cellspacing="0" align="center">
<tr>
	<td class="greyTableLabel">&nbsp;</td>
	<s:iterator id="totalHourByPosition" value="totalHoursByPosition" status="itTotalHours">
		<td class="greyTableLabelWithLeftBorder">
			<s:property value="position.name"/>
	</s:iterator>
</tr>
<tr>
	<td class="tableLabelWithBottomBorder"><s:text name="report.weeklytotalhours.scheduled.label"/></td>
	<s:iterator id="totalHourByPosition" value="totalHoursByPosition" status="itTotalHours">
		<td class="tableValueWithLeftBottomBorder">
			<s:text name="report.total.hours"><s:param value="totalHour.schedule"/></s:text>
		</td>
	</s:iterator>
</tr>
<tr>
	<td class="tableLabel"><s:text name="report.weeklytotalhours.target.label"/></td>
	<s:iterator id="totalHourByPosition" value="totalHoursByPosition" status="itTotalHours">
		<td class="tableValueWithLeftBorder">
			<s:text name="report.total.hours"><s:param value="totalHour.target"/></s:text>
		</td>
	</s:iterator>
</tr>
<tr>
	<td class="greyTableLabel"><s:text name="report.weeklytotalhours.difference.label"/></td>
	<s:iterator id="totalHourByPosition" value="totalHoursByPosition" status="itTotalHours">
		<td class="greyTableValueWithLeftBorder">
			<s:text name="report.total.hours"><s:param value="totalHour.difference"/></s:text></td>
	</s:iterator>
</tr>
<tr>
	<td class="tableLabel"><s:text name="report.weeklytotalhours.percentage.label"/></td>
	<s:iterator id="totalHourByPosition" value="totalHoursByPosition" status="itTotalHours">
		<td class="tableValueWithLeftBorder">
			<s:text name="report.percentage"><s:param value="totalHour.percentage"/></s:text></td>
	</s:iterator>
</tr>
</table>
</s:if>