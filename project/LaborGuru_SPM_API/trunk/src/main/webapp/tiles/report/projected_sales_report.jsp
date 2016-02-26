<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>



<table id="dailyFlashTable" border="0" cellspacing="0" align="center">
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.dailyFlashReport.title" />
    </td>
   </tr>
    <tr>
      <td>
        <input type="hidden" id="storeId" value="<s:property value="store.id"/>"/>
		<table id="windowReportTable" cellspacing="0">
		  <tr>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.daypart.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.time.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.projectedSales.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.projectedSales.cum.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.actualSales.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.actualhour.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.actualSale.cum.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.difference.label" /></td>
			<td class="yellowTableHeader"><s:text name="report.dailyFlashReport.difference.cum.label" /></td>
		  </tr>
		  <tr>
			  <td class="tableValueWithLeftBottomBorder">Pre-Open</td>
			  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			  <td id="projectedSales0" class="greyTableValueWithLeftBottomBorder">$-</td>
			  <td id="projectedSalesCumul0" class="greyTableValueWithLeftBottomBorder">$-</td>
			  <td id="actualSales0" class="tableValueWithLeftBottomBorder"><input type="text" id="actualSale0" data-format="0,0" value="" size="10" theme="simple" /></td>
			  <td id="actualHours0" class="tableValueWithLeftBottomBorder"><input type="text" id="A0" data-format="0" value="<s:property value="dailyFlash.openHours"/>" size="10" theme="simple" /></td>
 		  	  <td id="actualSalesCumul0" class="greyTableValueWithLeftBottomBorder">$-</td>
 		  	  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>

		  </tr>

		  <s:set name="index" value="-1"/>
		  
 		  <s:iterator id="dailyFlashHour" value="dailyFlashHours" status="itTotalHours">
 		    <s:if test="%{isEquals(day)}" >
 		      <s:set name="index" value="#itTotalHours.count"/>
 		      <tr id="row<s:property value='#itTotalHours.count' />" row="<s:property value='#itTotalHours.count' />" class="yellow">
 		    </s:if>
 		    <s:else>
 		      <tr id="row<s:property value='#itTotalHours.count'/>" row="<s:property value='#itTotalHours.count' />">
 		    </s:else>
			  <td class="selectableTableValueWithLeftBottomBorder"><s:property value="dayPart.name"/></td>
			  <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.hours.format"><s:param value="day"/></s:text></td>
			  <td id="projectedSales<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder">$<s:text name="currency"><s:param value="sales"/></s:text></td>
			  <td id="projectedSalesCumul<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="( $projectedSales<s:property value='#itTotalHours.count'/> + $projectedSalesCumul<s:property value='#itTotalHours.count - 1'/> ) "/></td>
			  <td id="actualSales<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><input type="text" id="actualSale<s:property value='#itTotalHours.count'/>" data-format="0[.]0" value="<s:property value="actualSale"/>" size="10" theme="simple" /></td>
			  <td id="actualHours<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><input type="text" id="A<s:property value='#itTotalHours.count'/>" data-format="0" value="<s:property value="actualHour"/>" size="10" theme="simple" /></td>
			  <td id="actualSalesCumul<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="( $actualSale<s:property value='#itTotalHours.count'/> + $actualSalesCumul<s:property value='#itTotalHours.count - 1'/> ) "></td>
			  <td id="diff<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="($actualSale<s:property value='#itTotalHours.count'/> - $projectedSales<s:property value='#itTotalHours.count'/>)"></td>
			  <td id="cumulDiff<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="($diff<s:property value='#itTotalHours.count'/> + $cumulDiff<s:property value='#itTotalHours.count - 1'/>)"></td>
			</tr>
		  </s:iterator>
			  <td class="tableValueWithLeftBottomBorder">Close</td>
			  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			  <td id="closeProjectedSales" class="greyTableValueWithLeftBottomBorder">$-</td>
			  <td id="closeSaleCumul0" class="greyTableValueWithLeftBottomBorder">$-</td>
			  <td id="closeSales" class="tableValueWithLeftBottomBorder"><input type="text" id="actualSale0" data-format="0,0" value="" size="10" theme="simple" /></td>
			  <td id="closeHour" class="tableValueWithLeftBottomBorder"><input type="text" id="A<s:property value="dailyFlashHours.size() + 1"/>" data-format="0" value="<s:property value="dailyFlash.closeHours"/>" size="10" theme="simple" /></td>
			  <td id="closeActualSalesCumul" class="greyTableValueWithLeftBottomBorder">$-</td>
 		  	  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			  <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
		  
		  <tr>
		    <td class="tableValueWithLeftBottomBorder">&nbsp;</td>
		  	<td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.total.label"/></td>
		  	<td id="totalSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$projectedSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
		  	<td id="totalCumulSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$projectedSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
		  	<td id="totalActualSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$actualSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
		  	<td id="totalPartialHours" class="greyTableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($A0,$A<s:property value='dailyFlashHours.size() + 1'/>)"></td>
		  	<td id="totalActualSalesCumul" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$actualSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
		  	<td id="totalDiff" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="$cumulDiff<s:property value='dailyFlashHours.size()'/>"></td>
		  	<td id="totalCumulDiff" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="$cumulDiff<s:property value='dailyFlashHours.size()'/>"></td>  	
		  </tr>
		</table>
		</td>
	</tr>
	<tr>
	  <td>&nbsp;</td>
	</tr>
	<tr>
		<td>
		<table width="100%">
		  <tr>
		    <td>
				<table class="windowReportTable" cellspacing="0" border="1">
					<s:if test="%{#index > 0}">
					<tr>
					  <td class="cellLabel"><s:text name="report.dailyFlashReport.forecastChange.label"/></td>
					  <td id="forecast" class="greyCellValue" data-format="0%" data-formula="($cumulDiff<s:property value="#index"/>/$projectedSalesCumul<s:property value="#index"/>)"></td>
					</tr>
					<tr>
 					  <td class="cellLabel"><s:text name="report.dailyFlashReport.percentOfDayLeft.label"/></td>
					  <td id="percentOfDay" class="greyCellValue" data-format="0%" data-formula="1 - ($projectedSalesCumul<s:property value="#index"/> / $totalCumulSales)"></td>
					</tr>
					</s:if>
					<s:else>
					<tr>
					  <td class="cellLabel"><s:text name="report.dailyFlashReport.forecastChange.label"/></td>
					  <td id="forecast" class="greyCellValue" data-format="0%" data-formula="($totalCumulDiff/$totalSales)"></td>
					</tr>
					<tr>
 					  <td class="cellLabel"><s:text name="report.dailyFlashReport.percentOfDayLeft.label"/></td>
					  <td id="percentOfDay" class="greyCellValue" data-format="0%" data-formula="1 - ($totalSales / $totalCumulSales)"></td>
					</tr>
					</s:else>
				</table>
			</td>
			<td>
				<table class="windowReportTable" cellspacing="0">
				<tr>
					<td class="cellLabel">&nbsp;</td>
					<td class="greyCellLabel" colspan="2"><s:text name="report.dailyFlashReport.soFar.label" /></td>
					<td class="greyCellLabel" colspan="2"><s:text name="report.dailyflashReport.restOfDay.label" /></td>
				</tr>
				<tr>
					<td class="cellLabel">&nbsp;</td>
					<td class="greyCellLabel"><s:text name="report.dailyFlashReport.hours.label"/></td>
					<td class="greyCellLabel"><s:text name="report.dailyFlashReport.difference.label"/></td>
					<td class="greyCellLabel"><s:text name="report.dailyFlashReport.hours.label"/></td>
					<td class="greyCellLabel"><s:text name="report.dailyFlashReport.difference.label"/></td>
				</tr>
				<tr>
					<td class="cellLabel"><s:text name="report.dailyFlashReport.actualHours.label"/></td>
					<td id="actualHours" class="greyCellValue" data-format="0" data-formula="$totalPartialHours" ></td>
					<td class="darkGreyCellValue">&nbsp;</td>
					<td class="darkGreyCellValue">&nbsp;</td>
					<td class="darkGreyCellValue">&nbsp;</td>
				</tr>
				<tr>
					<td class="cellLabel"><s:text name="report.dailyFlashReport.scheduledHours.label"/></td>
					<td id="partialHours" class="greyCellValue"><s:text name="report.total.hours"><s:param value="partialScheduleHour"/></s:text></td>
					<td id="diffPartialHours" class="greyCellValue" data-format="(0)" data-formula="( $actualHours - $partialHours )"></td>
					<td class="greyCellValue"><s:text name="report.total.hours"><s:param value="restTotalScheduleHour"/></s:text></td>
					<td class="darkGreyCellValue">&nbsp;</td>
				</tr>
			    </table>
			  </td>
		    </tr>
		  </table>
	    </td>	
	  </tr>
    </table>


