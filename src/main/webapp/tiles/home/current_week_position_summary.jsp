<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table border="0" cellpadding="0" cellspacing="0"">
	<tr>
		<td class="windowTableHeader"><s:text name='home.position.summary.title'><s:param value='weekDaySelector.startingWeekDay'/></s:text></td>
	</tr>				
	
	<tr height="3px">
		<td height="3px"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
	</tr>

	<tr>
		<td>
			<table border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td class="windowGreyTableTitle"><s:text name="home.position.label"/></td>
					<td class="windowGreyTableTitle"><s:text name="home.scheduled.label"/></td>
					<td class="windowGreyTableTitle"><s:text name="home.target.label"/></td>
					<td class="windowGreyTableTitle"><s:text name="home.difference.label"/></td>
				</tr>
				
				<!--  For each position -->
				<s:iterator value="currentDayPositionSummary" id="row">
				<tr>
					<td class="windowTableValue"><s:property value="position.name"/></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="projectedScheduled"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="projectedTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="projectedDifference"/></s:text></td>
				</tr>
				</s:iterator>
				<!-- For each position -->
				
				<tr>
					<td class="windowTableValue"><b><s:text name="home.total.label"/></b></td>
					<td class="windowTableValue"><b><s:text name="total.hours"><s:param value="totalProjectedScheduled"/></s:text></b></td>
					<td class="windowTableValue"><b><s:text name="total.hours"><s:param value="totalProjectedTarget"/></s:text></b></td>
					<td class="windowTableValue"><b><s:text name="total.hours"><s:param value="totalProjectedDifference"/></s:text></b></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script language="javascript" type="text/javascript">
	setObjectByIDClass('currentDayPositionSummaryFrame', '');
</script>