<table id="cateringTable" class="windowReportTable" cellspacing="0" border="1">
	<tr>
	  <td>
	    <table class="windowReportTable" cellspacing="0" border="1">
			  <tr>
			    <td colspan="5">
			     <s:text name="report.dailyFlashReport.catering.label"/>
			    </td>
			  </tr>	      
	      <tr>
				 <td class="greyCellValue"><s:text name="report.dailyFlashReport.status.label"/></td>
				 <td class="greyCellValue"><s:text name="report.dailyFlashReport.projectedSales.label"/></td>
				 <td class="greyCellValue"><s:text name="report.dailyFlashReport.actualSales.label"/></td>
				 <td class="greyCellValue"><s:text name="report.dailyFlashReport.actualSale.cum.label"/></td>
				 <td class="greyCellValue"><s:text name="report.dailyFlashReport.percent.label"/></td>
	      </tr>
	      <tr>
		     <td class=" class="greyCellValue""><s:text name="report.dailyFlashReport.delivered.label"/></td>
	       <td id="deliveredProjSales" class="greyCellValue" >$ 550</td><!-- TODO: Ver de donde sacar el valor -->
	       <td class="tableValueWithLeftBottomBorder"><input type="text" id="deliveredActualSales" data-format="$0,0" value="<s:property value="dailyFlash.deliveredActualSales"/>" size="10" theme="simple" /></td>
	       <td id="cateringActualSalesCumulative" class="greyTableValueWithLeftBottomBorder" data-format="(0,0)" data-formula="$deliveredActualSales - $deliveredProjSales"/>
	       <td id="deliveredPercent" class="greyTableValueWithLeftBottomBorder" data-format="0%" data-formula="$deliveredActualSales/$deliveredProjSales"/>
		    </tr>
	    </table>
	  </td>
  </tr>
  <tr>
    <td>
      <table class="windowReportTable" cellspacing="0" border="1">
        <tr>
          <td colspan="2"><s:text name="report.dailyFlashReport.catering.label"/></td>
        </tr>
        <tr>
          <td class="tableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.projected.label"/></td>
          <td>2</td> <!-- TODO Ver de donde sacar la información -->
        </tr>
        <tr>
          <td class="tableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.delivered.label"/></td>
          <td>15</td> <!-- TODO: Ver de donde sacar la información -->
        </tr>
        <tr>
          <td class="tableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.planned.label"/></td>
          <td>3</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
      <table class="windowReportTable" cellspacing="0" border="1">
        <tr>
          <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.laborHours.label"/></td>
          <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.restaurant.label"/></td>
          <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.catering.label"/></td>
          <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.total.label"/></td>
        </tr>
        <tr>
          <td class="greyTableValueWithLeftBottomBorder"><s:text name="report.dailyFlashReport.adjustment.label"/></td>
          <td>-5</td> <!-- TODO: Ver como hacer para obtener el valor -->
          <td>+3</td> <!-- TODO: Ver como hacer para obtener el valor -->
          <td>-2</td> <!-- TODO: Ver como hacer para obtener el valor -->
        </tr>
    </td>
  </tr>
</table>