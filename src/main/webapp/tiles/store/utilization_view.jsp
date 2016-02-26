<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.utilization.guest.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<td colspan="2"><s:text name="store.laborassumptions.utilization.guest.label" /></td>
							</tr>
							<tr class="editorTableHeader">
								<td><s:text name="store.laborassumptions.utilization.guest.position.label" /></td>
								<td><s:text name="store.laborassumptions.utilization.bottom.label" /></td>
								<td><s:text name="store.laborassumptions.utilization.top.label" /></td>
							</tr>							
							<s:iterator id="position" value="storePositions" status="pStatus">
								<tr>
									<td class="editorTableFirstColumn"><s:property value="name"/></td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:if test="utilizationBottom != null">
										<s:text name="percentage"><s:param name="value" value="utilizationBottom"/></s:text>%
										</s:if>			
										</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
									<s:if test="utilizationTop != null">
										<s:text name="percentage"><s:param name="value" value="utilizationTop"/></s:text>%		
									</s:if>							
										</td>
								</tr>
							</s:iterator>							
						</table>
					</td>
				</tr>

	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.utilization.nonguest.title" /></td>
	</tr>

	<tr>
		<td align="left">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<td><s:text name="store.laborassumptions.utilization.nonguest.label" /></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.laborassumptions.utilization.nonguestallpos.title" /></td>
								<td class="editorTableOddRow">
								<s:if test="allPositionsUtilization != null">
										<s:text name="percentage"><s:param name="value" value="allPositionsUtilization"/></s:text>%
								</s:if>
								</td>
							</tr>
					
						</table>					
					</td>
				</tr>
			</table>
		</td>
	</tr>
		
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
						<s:form action="store_show" theme="simple">
              			<s:hidden name="store.id" theme="simple"/>                    
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
								<td><s:submit action="store_show" key="back.button" theme="simple" cssClass="button"/></td>
		                    </tr>
	                    </table>   
	                    </s:form>                 
                    </td>
                </tr>

			</table>
		</td>
	</tr>
</table>
