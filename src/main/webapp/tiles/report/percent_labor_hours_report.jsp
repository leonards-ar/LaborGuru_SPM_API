<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
                    			
<table id="windowReportTable" cellspacing="0">
	<tr>
		<td class="greyTableLabel">&nbsp;</td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.percentlabor.schedule.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="report.percentlabor.target.label" /></td>
	</tr>

	<tr>
		<td class="tableLabel"><s:text name="report.percentlabor.tplh.label"><s:param value='mainVariableInitials'/></s:text></td>
		<td class="tableValueWithLeftBorder"><s:text name="decimal"><s:param value="vplhSchedule"/></s:text></td>
		<td class="tableValueWithLeftBorder"><s:text name="decimal"><s:param value="vplhTarget"/></s:text></td>
	<tr>
	<tr>
		<td class="greyTableLabel"><s:text name="report.percentlabor.percentlabor.label" /></td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="percentage"><s:param value="laborPercentageSchedule"/></s:text>%</td>
		<td class="greyTableLabelWithLeftBorder"><s:text name="percentage"><s:param value="laborPercentageTarget"/></s:text>%</td>
	</tr>
</table>