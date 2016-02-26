<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td>
				<!-- Header tables -->
				<table border="0" cellpadding="2" cellspacing="2" width="100%">
					<tr>
						<td valign="top">
							<!-- Left column -->
							<table id="fullHeightTable" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr valign="top">
									<td>
										<table id="windowTable">
											<tr>
												<td>
													<center><img id="currentNextWeeksSummaryIndicator" style="display: none;" src="<s:url value="/images/wait.gif" includeParams="none"/>" alt="<s:text name="wait.message"/>" title="<s:text name="wait.message"/>" border="0"/></center>
													<s:url id="currentNextWeeksSummaryUrl" action="showCurrentNextWeeksSummary" namespace="/home" includeParams="none" />
													<s:div id="currentNextWeeksSummaryFrame" showErrorTransportText="false" executeScripts="true" theme="ajax" href="%{currentNextWeeksSummaryUrl}" indicator="currentNextWeeksSummaryIndicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
												</td>
											</tr>
											<tr>
												<td>
													<center><img id="pastWeeksSummaryIndicator" style="display: none;" src="<s:url value="/images/wait.gif" includeParams="none"/>" alt="<s:text name="wait.message"/>" title="<s:text name="wait.message"/>" border="0"/></center>
													<s:url id="pastWeeksSummaryUrl" action="showPastWeeksSummary" namespace="/home" includeParams="none" />
													<s:div id="pastWeeksSummaryFrame" showErrorTransportText="false" executeScripts="true" theme="ajax" href="%{pastWeeksSummaryUrl}" indicator="pastWeeksSummaryIndicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<!-- Left column -->
						</td>
						
						<td valign="top" align="right">
							<!-- Right column -->
							<table id="windowTable"><tr><td>
							<center><img id="currentWeekPositionSummaryIndicator" style="display: none;" src="<s:url value="/images/wait.gif" includeParams="none"/>" alt="<s:text name="wait.message"/>" title="<s:text name="wait.message"/>" border="0"/></center>
							<s:url id="currentWeekPositionSummaryUrl" action="showCurrentWeekPositionSummary" namespace="/home" includeParams="none" />
							<s:div id="currentWeekPositionSummaryFrame" showErrorTransportText="false" executeScripts="true" theme="ajax" href="%{currentWeekPositionSummaryUrl}" indicator="currentWeekPositionSummaryIndicator" cssClass="waitMessage" loadingText='%{getText("wait.message")}'/>
							</td></tr></table>
							<!-- Right column -->
						</td>
					</tr>
				</table>
				<!-- Header tables -->
			</td>
		</tr>
	</table>

	<script language="javascript" type="text/javascript">
		djConfig.searchIds.push("currentNextWeeksSummaryFrame");	
		djConfig.searchIds.push("pastWeeksSummaryFrame");	
		djConfig.searchIds.push("currentWeekPositionSummaryFrame");	
	</script>
