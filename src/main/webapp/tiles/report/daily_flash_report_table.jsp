<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>
<script type="text/javascript">
    var url = '<s:url value="/report/saveDailyFlashReport.action" includeParams="none"/>';
    var calulateUrl='<s:url value="/report/calculateIdealDailyFlashReport.action" includeParams="none"/>';
    var projectedOpenningHours='<s:property value="projectedOpeningHours"/>';
    var projectedFlexHours='<s:property value="projectedFlexibleHours"/>';
</script>
<!-- 

There is a plugin restriction to use the SUM function, I need to use excel names.
That's why each field has a letter as a name.
This is a reminder

A - Actual Hours
B - Actual Sales
C - Cumulative Actual Sales 
D - Sales Difference
E - Schedule Hours
F - Target Hours
G - Partial Cumulative Projected Sales (this is used by Forecast)
H - Cumul Schedule Hours
I - Cumul Target Hours
J - New Projections
K - Ideal Hours
L - Cumul Ideal Hours
M - Adjustes calculus

 -->
 
<div id="result" style="position:absolute; top:200px; z-index:10; display:none" align="center">
  <table width="200px" class="waitTable" border="0" cellpadding="2" cellspacing="0">
    <tr>
      <td align="center" valign="middle">
        <table width="100%" align="center">
			    <tr>
			      <td align="center">&nbsp;</td>
			    </tr>
          <tr>
            <td id="message" align="center" valign="middle" class="waitMessage">&nbsp;</td>
          </tr>
			    <tr>
			      <td align="center" valign="middle" class="waitMessage">&nbsp;</td>
			    </tr> 
        </table>
      </td>
    </tr>
  </table>
</div>

<table id="dailyFlashTable" border="0" cellspacing="0" align="center">
  <tr>
    <td>
    	<table id="ProjectionTable" border="0" cellspacing="0" align="center">
  <tr>
    <td class="windowTableHeader">
      <s:text name="report.dailyFlashReport.title" />
    </td>
   </tr>
    <tr>
      <td>
        <input type='hidden' id='storeId' value='<s:property value="store.id"/>'/>
        <table id="scheduleReportTable" cellspacing="0">
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
			      <td class="yellowTableHeader"><s:text name="report.dailyFlashReport.scheduledHours.label" /></td>
			      <td class="yellowTableHeader"><s:text name="report.dailyFlashReport.cumulScheduledHours.label" /></td>
			      <td class="yellowTableHeader"><s:text name="report.dailyFlashReport.targetHours.label" /></td>
			      <td class="yellowTableHeader"><s:text name="report.dailyFlashReport.cumulTargetHours.label" /></td>
			      <td class="yellowTableHeader">New Projection</td>
			      <td class="yellowTableHeader">Partial</td>
			      <td class="yellowTableheader">Ideal Hours</td>
			      <td class="yellowTableheader">Partial Ideal Hours</td>
			      
			      </tr>
			      <tr>
			        <td class="tableValueWithLeftBottomBorder">Pre-Open</td>
			        <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			        <td id="projectedSales0" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td id="projectedSalesCumul0" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td id="actualSales0" class="tableValueWithLeftBottomBorder"><input type="text" readonly="true" id="B0" data-format="0,0" value="" size="10" theme="simple" /></td>
			        <td id="actualHours0" class="tableValueWithLeftBottomBorder"><input type="text" id="A0" data-format="0" value="<s:property value="dailyFlash.openHours"/>" size="10" theme="simple" /></td>
			        <td id="C0" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			        <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			        <td id="E0" class="tableValueWithLeftBottomBorder"><s:property value="scheduleOpeningHours"/></td>
			        <td id="H0" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($A0 > 0,$E0,0)"></td>
              <td id="F0" class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="projectedOpeningHours"/></td>
              <td id="I0" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($A0 > 0,$F0,0)"></td>
              <td id="J0" class="tableValueWithLeftBottomBorder">&nbsp;</td>			
              <td id="G0" class="tableValueWithLeftBottomBorder">&nbsp;</td>
              <td id="K0" class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="projectedOpeningHours"/></td>
              <td id="L0" class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="projectedOpeningHours"/></td>

            </tr>

			      <s:iterator id="dailyFlashHour" value="dailyFlashHours" status="itTotalHours">
 	          <tr id="row<s:property value='#itTotalHours.count'/>" row="<s:property value='#itTotalHours.count' />">
			        <td class="selectableTableValueWithLeftBottomBorder"><s:property value="dayPart.name"/></td>
			        <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.hours.format"><s:param value="day"/></s:text></td>
			        <td id="projectedSales<s:property value='#itTotalHours.count'/>" data-format="$0,0" class="greyTableValueWithLeftBottomBorder">$<s:text name="currency"><s:param value="sales"/></s:text></td>
			        <td id="projectedSalesCumul<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="( $projectedSales<s:property value='#itTotalHours.count'/> + $projectedSalesCumul<s:property value='#itTotalHours.count - 1'/>) "/></td>
			        <td id="actualSales<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><input type="text" id="B<s:property value='#itTotalHours.count'/>" data-format="0[.]0" value="<s:property value="actualSale"/>" size="10" theme="simple" /></td>
			        <td id="actualHours<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><input type="text" id="A<s:property value='#itTotalHours.count'/>" data-format="0" value="<s:property value="actualHour"/>" size="10" theme="simple" /></td>
			        <td id="C<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,($B<s:property value='#itTotalHours.count'/> + $C<s:property value='#itTotalHours.count - 1'/>),0)"></td>
			        <td id="D<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,($B<s:property value='#itTotalHours.count'/> - $projectedSales<s:property value='#itTotalHours.count'/>),0)"></td>
			        <td id="cumulDiff<s:property value='#itTotalHours.count'/>" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,($D<s:property value='#itTotalHours.count'/> + $cumulDiff<s:property value='#itTotalHours.count - 1'/>),0)"></td>
              <td id="E<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><s:property value="scheduleHour"/></td>
              <td id="H<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,$E<s:property value='#itTotalHours.count'/>,0)"></td>
              <td id="F<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder"><s:property value="targetHour"/></td>
              <td id="I<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,$F<s:property value='#itTotalHours.count'/>,0)"></td>
              <td id="J<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="$0,0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,$B<s:property value='#itTotalHours.count'/>,$projectedSales<s:property value='#itTotalHours.count'/> * (1 + $forecast))"></td>
              <td id="G<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="$0,0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,$projectedSales<s:property value='#itTotalHours.count'/>,0)"></td>
              <td><input type="text" id="K<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="0" value="<s:property value="idealHour"/>" readonly="true"/></td>
              <td><input type="text" id="L<s:property value='#itTotalHours.count'/>" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($B<s:property value='#itTotalHours.count'/> > 0,$K<s:property value='#itTotalHours.count'/>,0)" readonly="true"/></td>
              
			      </tr>
			      </s:iterator>
			        <td class="tableValueWithLeftBottomBorder">Close</td>
			        <td class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			        <td id="closeProjectedSales" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td id="closeSaleCumul0" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td id="closeSales" class="tableValueWithLeftBottomBorder"><input type="text" readonly="true" data-format="0,0" value="" size="10" theme="simple"/></td>
			        <td id="closeHour" class="tableValueWithLeftBottomBorder"><input type="text" id="A<s:property value='dailyFlashHours.size() + 1'/>" data-format="0" value="<s:property value='dailyFlash.closeHours'/>" size="10" theme="simple" /></td>
			        <td id="closeActualSalesCumul" class="greyTableValueWithLeftBottomBorder">$-</td>
			        <td id="closeDiff" class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
			        <td id="closeCumulDiff" class="greyTableValueWithLeftBottomBorder">&nbsp;</td>
              <td id="closeSchedule" class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="scheduleCloseHours"/></td>
              <td id="closeCumulSchedule" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($A<s:property value='dailyFlashHours.size() + 1'/> > 0, <s:property value="scheduleCloseHours"/>,0)"></td>
              <td id="closeTarget"class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="projectedCloseHours"/></td>
              <td id="closeCumulTarget" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($A<s:property value='dailyFlashHours.size() + 1'/> > 0, <s:property value="projectedCloseHours"/>,0)"></td>
              <td id="closeNewProjection" class="tableValueWithLeftBottomBorder">&nbsp;</td>
              <td id="closePartial" class="tableValueWithLeftBottomBorder">&nbsp;</td>
              <td id="closeIdealHours" class="tableValueWithLeftBottomBorder" data-format="0"><s:property value="projectedCloseHours"/></td>
              <td id="closeCumulIdealHours" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="IF($A<s:property value='dailyFlashHours.size() + 1'/> > 0, <s:property value="projectedCloseHours"/>,0)"></td>
			      </tr>
			      <tr>
			        <td class="tableValueWithLeftBottomBorder">&nbsp;</td>
			        <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.total.label"/></td>
			        <td id="totalSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$projectedSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
			        <td id="totalCumulSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="$projectedSalesCumul<s:property value='dailyFlashHours.size()'/>"></td>
			        <td id="totalActualSales" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="SUM($B1,$B<s:property value='dailyFlashHours.size()'/>)"></td>
			        <td id="totalPartialHours" class="greyTableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($A1,$A<s:property value='dailyFlashHours.size() + 1'/>)/2 + $A0"></td>
			        <td id="totalActualSalesCumul" class="greyTableValueWithLeftBottomBorder" data-format="$0,0" data-formula="SUM($B1,$B<s:property value='dailyFlashHours.size()'/>)"></td>
			        <td id="totalDiff" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="SUM($D1,$D<s:property value='dailyFlashHours.size()'/>)"></td>
			        <td id="totalCumulDiff" class="greyTableValueWithLeftBottomBorder" data-format="$(0,0)" data-formula="$totalDiff"></td>
              <td id="totalSchedule" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($E1,$E<s:property value='dailyFlashHours.size()'/>)/2 + $E0 + $closeSchedule"></td>
              <td id="totalCumulSchedule" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($H1,$H<s:property value='dailyFlashHours.size()'/>)/2 + $H0 + $closeCumulSchedule"></td>
              <td id="totalTarget" class="tableValueWithLeftBottomBorder"data-format="0" data-formula="SUM($F1,$F<s:property value='dailyFlashHours.size()'/>)/2 + $F0 + $closeTarget"></td>
              <td id="totalCumulTarget" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($I1,$I<s:property value='dailyFlashHours.size()'/>)/2 + $I0 + $closeCumulTarget"></td>
              <td id="totalNewProjection" class="tableValueWithLeftBottomBorder" data-format="0,0" data-formula="SUM($J1,$J<s:property value='dailyFlashHours.size()'/>)"></td>
              <td id="totalPartialProjectedSales" class="tableValueWithLeftBottomBorder" data-format="$0,0" data-formula="SUM($G1,$G<s:property value='dailyFlashHours.size()'/>)"></td>   
              <td id="totalIdealHours" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($K1,$K<s:property value='dailyFlashHours.size()'/>)/2 + $K0 + $closeIdealHours"></td>
              <td id="totalCumulIdealHours" class="tableValueWithLeftBottomBorder" data-format="0" data-formula="SUM($L1,$L<s:property value='dailyFlashHours.size()'/>)/2 + $L0 + $closeCumulIdealHours"></td>
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
			        <td align="left">
			        <table class="windowReportTable" cellspacing="0" border="1">
			          <tr>
			            <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.forecastChange.label"/></td>
			            <td id="forecast" class="greyFlashCellValue" data-format="0%" data-formula="($totalDiff/$totalPartialProjectedSales)"></td>
			          </tr>
			          <tr>
			            <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.percentOfDayLeft.label"/></td>
			            <td id="percentOfDay" class="greyFlashCellValue" data-format="0%" data-formula="1 - ($totalPartialProjectedSales/$totalSales)"></td>
			          </tr>
			        </table>
			      </td>
			      <td align="right">
			        <table class="windowReportTable" cellspacing="0">
			        <tr>
			          <td>&nbsp;</td>
			          <td class="greyFlashCellLabel" colspan="2"><s:text name="report.dailyFlashReport.soFar.label" /></td>
			          <td class="greyFlashCellLabel" colspan="2"><s:text name="report.dailyflashReport.restOfDay.label" /></td>
			        </tr>
			        <tr>
			          <td>&nbsp;</td>
			          <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.hours.label"/></td>
			          <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.difference.label"/></td>
			          <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.hours.label"/></td>
			          <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.difference.label"/></td>
			        </tr>
			        <tr>
			          <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.actualHours.label"/></td>
                <td id="actualHours" class="greyFlashCellValue" data-format="0" data-formula="$totalPartialHours" ></td>
			          <td class="darkGreyCellValue">&nbsp;</td>
			          <td class="darkGreyCellValue">&nbsp;</td>
			          <td class="darkGreyCellValue">&nbsp;</td>
			        </tr>
			        <tr>
			          <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.scheduledHours.label"/></td>
                <td id="partialScheduleHours" class="greyFlashCellValue" data-format="0" data-formula="$totalCumulSchedule"></td>
			          <td id="diffPartialHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $actualHours - $partialScheduleHours )"></td>
			          <td id="soFarScheduleHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $totalSchedule - $totalCumulSchedule )"></td>
			          <td class="darkGreyCellValue">&nbsp;</td>
			         </tr>
              <tr>
                <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.targetHours.label"/></td>
                <td id="partialTargetHours" class="greyFlashCellValue" data-format="0" data-formula='$totalCumulTarget + (1 - $percentOfDay)*<s:property value="projectedFlexibleHours"/>'></td>
                <td id="diffTargetHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $actualHours - $partialTargetHours )"></td>
                <td id="soFarTargetHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $totalTarget + <s:property value="projectedFlexibleHours"/> - $partialTargetHours )"></td>
                <td id="soFarTargetDiff" class="greyFlashCellValue" data-format="(0)" data-formula="$soFarTargetHours - $soFarScheduleHours"></td>
               </tr>
              <tr>
                <td class="cellFlashLabel"><s:text name="report.dailyFlashReport.idealHours.label"/></td>
                <td id="partialIdealHours" class="greyFlashCellValue" data-format="0" data-formula='$totalCumulIdealHours + (1 - $percentOfDay)*(<s:property value="projectedFlexibleHours"/> * (1 + $forecast))'></td>
                <td id="diffIdealHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $actualHours - $partialIdealHours )"></td>
                <td id="soFarIdealHours" class="greyFlashCellValue" data-format="(0)" data-formula="( $totalIdealHours + <s:property value="projectedFlexibleHours"/> * (1 + $forecast) - $partialIdealHours )"></td>
                <td id="soFarIdealHoursDiff" class="greyFlashCellValue" data-format="(0)" data-formula="$soFarIdealHours - $soFarScheduleHours "></td>
               </tr>
			        </table>
			      </td>
			      </tr>
			      <s:if test="%{isSecondaryVariablesConfigured(1)}">
			      <tr>
			        <td>
               <table class="windowReportTable" cellspacing="0" align="left">
                  <tr>
                    <td colspan="5">
                      <s:text name="report.weeklytotalhours.checks.label"><s:param value="%{getVariableNames().get(1)}"/></s:text>
                    </td>
                  </tr>       
                  <tr>
                    <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.status.label"/></td>
                    <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.projectedSales.label"/></td>
                    <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.actualSales.label"/></td>
                    <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.actualSale.cum.label"/></td>
                    <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.percent.label"/></td>
                  </tr>
                  <tr>
                    <td class="greyFlashCellValue"><s:text name="report.dailyFlashReport.delivered.label"/></td>
                    <td id="deliveredProjSales" class="greyFlashCellValue" ><s:property value="dailyFlashObject.variable2"/></td>
                    <td class="tableValueWithLeftBottomBorder"><input type="text" id="deliveredActualSales" data-format="$0,0" value="<s:property value="dailyFlash.delivered"/>" size="10" theme="simple" /></td>
                    <td id="deliveredActualSalesCumulative" class="greyTableValueWithLeftBottomBorder" data-format="(0,0)" data-formula="$deliveredActualSales - $deliveredProjSales"/>
                    <td id="deliveredPercent" class="greyTableValueWithLeftBottomBorder" data-format="0%" data-formula="IF($deliveredProjSales > 0,$deliveredActualSales/$deliveredProjSales,1)"/>
                  </tr>
                  <tr>
                    <td class="greyCellValue"><s:text name="report.dailyFlashReport.planned.label"/></td>
                    <td id="plannedProjSales" class="greyCellValue" ></td>
                    <td class="tableValueWithLeftBottomBorder"><input type="text" id="plannedActualSales" data-format="$0,0" value="<s:property value="dailyFlash.planned"/>" size="10" theme="simple" /></td>
                    <td id="plannedActualSalesCumulative" class="greyTableValueWithLeftBottomBorder" data-format="(0,0)" data-formula="$plannedActualSales + $deliveredActualSalesCumulative"/>
                    <td id="plannedPercent" class="greyTableValueWithLeftBottomBorder" data-format="0%" data-formula="IF($deliveredProjSales,$plannedActualSales/$deliveredProjSales,1)"/>
                  </tr>
                </table>
              </td>
              <td align="right">
                <table class="windowReportTable" cellspacing="0" align="right">
                  <tr>
                    <td colspan="2"><s:text name="report.dailyFlashReport.catering.hours.label"/></td>
                  </tr>
                  <tr>
                    <td class="cellFlashValue"><s:text name="report.dailyFlashReport.projected.label"/></td>
                    <td id="schedule_catering" class="greyFlashCellValue" data-format="0" data-formula='$deliveredProjSales * <s:property value="secondVariableHours"/>'></td> <!-- TODO Ver de donde sacar la información -->
                  </tr>
                  <tr>
                    <td class="cellFlashValue"><s:text name="report.dailyFlashReport.delivered.label"/></td>
                    <td id="delivered_catering" class="greyFlashCellValue" data-format='0' data-formula='$deliveredActualSales * <s:property value="secondVariableHours"/>'></td>
                  </tr>
                  <tr>
                    <td class="cellFlashValue"><s:text name="report.dailyFlashReport.planned.label"/></td>
                    <td id="planned_catering" class="greyFlashCellValue" data-format='0' data-formula='$plannedActualSales * <s:property value="secondVariableHours"/>'></td>
                  </tr>
                </table>
						 </td>
					 </tr>
					 <tr>
					   <td colspan="2">&nbsp;</td>
					 </tr>
					 <tr>
					   <td>
              <table class="windowReportTable" cellspacing="0" border="1">
                <tr>
                  <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.laborHours.label"/></td>
                  <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.restaurant.label"/></td>
                  <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.catering.label"/></td>
                  <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.total.label"/></td>
                 </tr>
                 <tr>
                   <td class="greyFlashCellLabel"><s:text name="report.dailyFlashReport.adjustment.label"/></td>
                   <td id="M0" data-format="0" data-formula="$soFarIdealHoursDiff"></td> 
                   <td id="M1" data-format="0" data-formula="$planned_catering"></td> 
                   <td id="totalAdj" data-format="0" data-formula="$M0+$M1"></td> 
                 </tr>
               </table>  
		         </td>
		         <td align="right" valign="middle"><input id="saveFlashReport" type="submit" value="<s:text name="save.button"/>" class="button" /></td>
		       </tr>
		       </s:if>
		       <s:else>
		        <tr>
		          <td align="right" colspan="2" valign="middle"><input id="saveFlashReport" type="submit" value="<s:text name="save.button"/>" class="button" /></td>
		        </tr>
		       </s:else>	      
			    </table>
			    </td> 
			   </tr>
		    </table>
       </td>
      </tr>
 </table>
   

