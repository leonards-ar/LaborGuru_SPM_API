<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="totalHistoricalTable" style="display:<s:if test="dataType=='absolute'">block</s:if><s:else>none</s:else>">
<s:if test="totalHistoricalHours != null">
	<table border="0" width="100%" cellspacing="0" align="center">
		<tr>
			<td>
			<table id="windowReportTable" cellspacing="0">
				<tr>
					<td id="titleBar"><s:property value="%{getText(reportTitle)}" />
					</td>
				</tr>
				<tr>
					<td>
					<table  border="0" width="100%" cellspacing="0" align="center">
						<tr>
							<td class="greyCellLabel">&nbsp;</td>
							<td class="cellLabel">
							 <s:property value="%{getText(scheduleHeader)}" />
              </td>
							<td class="cellLabel">
							 <s:property value="%{getText(targetHeader)}" />
              </td>
							<td class="greyCellLabel">
							 <s:text name="report.historicalComparison.difference.label" />
              </td>
							<td class="cellLabel">
                <s:text name="report.historicalComparison.difference.percentage" />
              </td>
              <td class="cellLabel">
                   <s:property value="%{getText(scheduleTrendHeader)}"/>
              </td>
              <td class="cellLabel">
                   <s:property value="%{getText(targetTrendHeader)}"/>
              </td>
						</tr>
						<s:iterator id="totalHistoricalHour" value="totalHistoricalHours" status="itTotalHour">
							<tr>
								<td class="greyCellValue">
								  <s:text name="report.historicalComparison.date">
									 <s:param value="totalHour.day" />
								  </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="currency">
  									<s:param value="totalHour.schedule" />
	 							 </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="currency">
									 <s:param value="totalHour.target" />
								  </s:text>
                </td>
								<td class="greyCellValue">
								  <s:text name="currency">
									 <s:param value="totalHour.difference" />
								  </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="report.percentage">
									 <s:param value="totalHour.percentage" />
								  </s:text>
                </td>
                
                				<td class="cellValue">
                				  <s:if test="actualTrend != null">
                				    <s:text name="currency">
               				          <s:param value="actualTrend"/>
                				    </s:text>
                				  </s:if>
                				  <s:else>
                				  	&nbsp;
                				  </s:else>
                </td>
                                
                				<td class="cellValue">
                				  <s:if test="idealTrend != null">
                 				    <s:text name="currency">
                				      <s:param value="idealTrend"/>
                				    </s:text>
                				  </s:if>
                				  <s:else>
                				  	&nbsp;
                				  </s:else>
                </td>
                
                
							</tr>
						</s:iterator>
					</table>
					</td>
				</tr>
			</table>
			</td>
			<td>
			<table border="0" width="100%" cellspacing="0" align="center">
				<tr>
					<td></td>
					<td><OBJECT
						classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
						codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0
						" width="600" height="350" id="Column3D">
						<param name="movie"
							value="<s:url value='/fusionCharts/FCF_MSLine.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" />
						<param name="FlashVars"
							value="&dataXML=<s:property value="xmlValues"/>">
						<param name="quality" value="high" />
						<embed
							src="<s:url value='/fusionCharts/FCF_MSLine.swf?chartWidth=600&chartHeight=300' includeParams="none"/>"
							flashVars="&dataXML=<s:property value="xmlValues"/>"
							quality="high" width="600" height="300" name="Column3D"
							type="application/x-shockwave-flash"
							pluginspage="http://www.macromedia.com/go/getflashplayer" /></object></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:if>
</div>
<div id="totalHistoricalPercentageTable" style="display:<s:if test="dataType=='percentage'">block</s:if><s:else>none</s:else>">
<s:if test="totalHistoricalHours != null">
	<table border="0" width="100%" cellspacing="0" align="center">
		<tr>
			<td>
			<table id="windowReportTable" cellspacing="0">
				<tr>
					<td id="titleBar"><s:property value="%{getText(reportTitle)}" />
					</td>
				</tr>
				<tr>
					<td>
					<table  border="0" width="100%" cellspacing="0" align="center">
						<tr>
							<td class="greyCellLabel">&nbsp;</td>
							<td class="cellLabel">
							 <s:property value="%{getText(scheduleHeader)}" />
              </td>
							<td class="cellLabel">
							 <s:property value="%{getText(targetHeader)}" />
              </td>
							<td class="greyCellLabel">
							 <s:text name="report.historicalComparison.difference.label" />
              </td>
							<td class="cellLabel">
                <s:text name="report.historicalComparison.difference.percentage" />
              </td>
              <td class="cellLabel">
                   <s:text name="report.historicalComparison.difference.trendpercentage"/>
              </td>
						</tr>
						<s:iterator id="totalHistoricalHour" value="totalHistoricalHours" status="itTotalHour">
							<tr>
								<td class="greyCellValue">
								  <s:text name="report.historicalComparison.date">
									 <s:param value="totalHour.day" />
								  </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="currency">
  									<s:param value="totalHour.schedule" />
	 							 </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="currency">
									 <s:param value="totalHour.target" />
								  </s:text>
                </td>
								<td class="greyCellValue">
								  <s:text name="currency">
									 <s:param value="totalHour.difference" />
								  </s:text>
                </td>
								<td class="cellValue">
								  <s:text name="report.percentage">
									 <s:param value="percentage" />
								  </s:text>
                </td>
                
                				<td class="cellValue">
                				  <s:if test="idealTrend != null">
                				    <s:text name="report.percentage">
               				          <s:param value="trendPercentage"/>
                				    </s:text>
                				  </s:if>
                				  <s:else>
                				  	&nbsp;
                				  </s:else>
                </td>
							</tr>
						</s:iterator>
					</table>
					</td>
				</tr>
			</table>
			</td>
			<td>
			<table border="0" width="100%" cellspacing="0" align="center">
				<tr>
					<td></td>
					<td><OBJECT
						classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
						codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0
						" width="600" height="350" id="Column3D">
						<param name="movie"
							value="<s:url value='/fusionCharts/FCF_MSLine.swf?chartWidth=600&chartHeight=300' includeParams="none"/>" />
						<param name="FlashVars"
							value="&dataXML=<s:property value="xmlPercentValues"/>">
						<param name="quality" value="high" />
						<embed
							src="<s:url value='/fusionCharts/FCF_MSLine.swf?chartWidth=600&chartHeight=300' includeParams="none"/>"
							flashVars="&dataXML=<s:property value="xmlPercentValues"/>"
							quality="high" width="600" height="300" name="Column3D"
							type="application/x-shockwave-flash"
							pluginspage="http://www.macromedia.com/go/getflashplayer" /></object></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:if>
</div>