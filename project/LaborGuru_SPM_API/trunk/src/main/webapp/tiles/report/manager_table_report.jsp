<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="totalManagerHours != null">
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
							<table border="0" width="100%" cellspacing="0" align="center">
								<tr>
								  <s:if test='selectView == "scheduleExecutionEfficiency"'>
								    <td class="cellLabel">&nbsp;</td>
								    <td class="cellLabel" colspan="2">
								      <s:text name="report.manager.sales"/>
								    </td>
								  </s:if>
								  <s:else>
                    <td class="cellLabel" <s:if test='selectView != "forecastEfficiency"'>colspan="2"</s:if>>&nbsp;</td>
								  </s:else>
									<td class="greyCellLabel" colspan="4"><s:if
											test='selectView != "forecastEfficiency"'>
											<s:text name="report.manager.laborHours" />
										</s:if> <s:else>
											<s:text name="report.manager.sales" />
										</s:else></td>
									<s:if test='selectView != "forecastEfficiency"'>
										<td class="cellLabel" colspan="2"><s:text
												name="report.manager.variable1PMH" />
										</td>
										<td class="greyCellLabel" colspan="2"><s:text
												name="report.manager.laborPercentage" />
										</td>
									</s:if>
								</tr>
								<tr>
									<td class="cellLabel"><s:text name="%{getText(reportObject)}" />
									</td>
									<s:if test='selectView != "forecastEfficiency"'>
                    <s:if test='selectView == "scheduleExecutionEfficiency"'>
                    <td class="greyCellLabel">
                      <s:text name="report.manager.schedule.sales" />
                    </td>
                    <td class="greyCellLabel">
                      <s:text name="report.manager.actual.sales" />
                    </td>                 
                   </s:if>
                   <s:else>
                    <td class="greyCellLabel">
                      <s:text name="report.manager.sales" />
                    </td>
                   </s:else>                 
									</s:if>
									<td class="cellLabel"><s:property
											value="%{getText(scheduleHeader)}" /></td>
									<td class="cellLabel"><s:property
											value="%{getText(targetHeader)}" /></td>
									<td class="cellLabel"><s:text
											name="report.manager.difference.label" /></td>
									<td class="cellLabel"><s:text
											name="report.manager.difference.percentage" /></td>
									<s:if test='selectView != "forecastEfficiency"'>
										<td class="greyCellLabel"><s:property
												value="%{getText(scheduleHeader)}" /></td>
										<td class="greyCellLabel"><s:property
												value="%{getText(targetHeader)}" /></td>
										<td class="cellLabel"><s:property
												value="%{getText(scheduleHeader)}" /></td>
										<td class="cellLabel"><s:property
												value="%{getText(targetHeader)}" /></td>
									</s:if>
								</tr>
								<s:iterator id="totalManagerHour" value="totalManagerHours"
									status="itTotalManagerHour">
									<tr>
										<td class="cellValue"><s:property value="name" /></td>
										<s:if test='selectView != "forecastEfficiency"'>
	                    <s:if test='selectView == "scheduleExecutionEfficiency"'>
	                    <td class="greyCellLabel">
	                      <s:text name="currency">
                            <s:param value="projections" />
                        </s:text>
	                    </td>
	                    <td class="greyCellLabel">
	                      <s:text name="currency">
                            <s:param value="sales" />
                        </s:text>
	                    </td>                 
	                   </s:if>
	                   <s:else>										
												<td class="greyCellValue"><s:text name="currency">
														<s:param value="sales" />
													</s:text></td>
										 </s:else>
										</s:if>
										<td class="cellValue"><s:text name="currency">
												<s:param value="schedule" />
											</s:text></td>
										<td class="cellValue"><s:text name="currency">
												<s:param value="target" />
											</s:text></td>
										<td class="greyCellValue"><s:text name="currency">
												<s:param value="difference" />
											</s:text></td>
										<td class="cellValue"><s:text name="report.percentage">
												<s:param value="percentage" />
											</s:text></td>
										<s:if test='selectView != "forecastEfficiency"'>
											<td class="greyCellValue"><s:text name="currency">
													<s:param value="scheduleMPH" />
												</s:text></td>
											<td class="greyCellValue"><s:text name="currency">
													<s:param value="targetMPH" />
												</s:text></td>
											<td class="cellValue">
											 <s:text name="decimal"><s:param value="scheduleLaborPercentage"/></s:text>%
											 </td>
											<td class="cellValue">
											 <s:text name="decimal"><s:param value="targetLaborPercentage"/></s:text>%
											</td>
										</s:if>
									</tr>
								</s:iterator>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</s:if>
