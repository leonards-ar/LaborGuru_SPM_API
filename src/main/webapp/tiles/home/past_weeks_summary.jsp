<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr class="windowGreyTableTitleRow">
		<td class="windowGreyTableTitle"><s:text name="home.previous_week.title"/></td>
	</tr>			

	<tr>
		<td>
			<table border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td class="windowTableLabel">&nbsp;</td>
					<td class="windowGreyTableLabel" colspan="3"><s:text name="home.projection_labor.label"/></td>
					<td class="windowGreyTableLabel" colspan="3"><s:text name="home.actual_labor.label"/></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="home.week.label"/></td>
					<td class="windowTableLabel"><s:text name="home.volume.label"/></td>
					<td class="windowTableLabel"><s:text name="home.target.label"/></td>
					<td class="windowTableLabel"><s:text name="home.scheduled.label"/></td>
					<td class="windowTableLabel"><s:text name="home.volume.label"/></td>
					<td class="windowTableLabel"><s:text name="home.target.label"/></td>
					<td class="windowTableLabel"><s:text name="home.actual.label"/></td>
				</tr>
				
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[0].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="currency"><s:param value="pastWeeksSummary[0].projectedVolume"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].projectedTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].projectedScheduled"/></s:text></td>
					<td class="windowTableValue"><s:text name="currency"><s:param value="pastWeeksSummary[0].actualVolume"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].actualTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].actualScheduled"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[1].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="currency"><s:param value="pastWeeksSummary[1].projectedVolume"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].projectedTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].projectedScheduled"/></s:text></td>
					<td class="windowTableValue"><s:text name="currency"><s:param value="pastWeeksSummary[1].actualVolume"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].actualTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].actualScheduled"/></s:text></td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td class="windowTableHeader"><s:text name="home.key_performance_indicators.title"/></td>
	</tr>				
	
	<tr height="3px">
		<td height="3px"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
	</tr>
	
	<tr class="windowGreyTableTitleRow">
		<td class="windowGreyTableTitle"><s:text name="home.previous_week.title"/></td>
	</tr>

	<tr>
		<td>
			<table border="0" cellpadding="2" cellspacing="2">

			
				<tr>
					<td class="windowTableLabel"><s:text name="home.week.label"/></td>
					<td class="windowTableLabel"><s:text name="home.performance.label"/></td>
					<td class="windowTableLabel"><s:text name="home.scheduled.label"/></td>
					<td class="windowTableLabel"><s:text name="home.projection.label"/></td>
					<td class="windowTableLabel"><s:text name="home.execution.label"/></td>
				</tr>
							
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[0].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].actualDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].projectedDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].projectionDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].executionDifferencePercentage"/></s:text>%</td>
				</tr>
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[1].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].actualDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].projectedDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].projectionDifferencePercentage"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].executionDifferencePercentage"/></s:text>%</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td class="windowTableHeader"><s:text name="home.key_performance_ratios.title"/></td>
	</tr>				
	
	<tr height="3px">
		<td height="3px"><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
	</tr>
	
	<tr class="windowGreyTableTitleRow">
		<td class="windowGreyTableTitle"><s:text name="home.previous_week.title"/></td>
	</tr>
				
	<tr>
		<td>
			<table border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td class="windowTableLabel">&nbsp;</td>
					<td class="windowGreyTableLabel" colspan="2"><s:text name="home.volume_labor.label"/></td>
					<td class="windowGreyTableLabel" colspan="2"><s:text name="home.volume_sales.label"/></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="home.week.label"/></td>
					<td class="windowTableLabel"><s:text name="home.target.label"/></td>
					<td class="windowTableLabel"><s:text name="home.actual.label"/></td>
					<td class="windowTableLabel"><s:text name="home.ideal.label"/></td>
					<td class="windowTableLabel"><s:text name="home.actual.label"/></td>
				</tr>
				
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[0].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].volumeLaborTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[0].volumeLaborActual"/></s:text></td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].laborPercentageOfSalesIdeal"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[0].laborPercentageOfSalesActual"/></s:text>%</td>
				</tr>
				<tr>
					<td class="windowTableValue"><s:text name="home.weekday.performance.dateformat"><s:param value="pastWeeksSummary[1].date"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].volumeLaborTarget"/></s:text></td>
					<td class="windowTableValue"><s:text name="total.hours"><s:param value="pastWeeksSummary[1].volumeLaborActual"/></s:text></td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].laborPercentageOfSalesIdeal"/></s:text>%</td>
					<td class="windowTableValue"><s:text name="percentage"><s:param value="pastWeeksSummary[1].laborPercentageOfSalesActual"/></s:text>%</td>
				</tr>
			</table>
		</td>
	</tr>	
</table>
<script language="javascript" type="text/javascript">
	setObjectByIDClass('pastWeeksSummaryFrame', '');
</script>