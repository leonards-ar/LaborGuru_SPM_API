<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
              <s:form action="storeUtilizationLimits_save" theme="simple">
              <s:hidden name="storeId" theme="simple"/>
<table border="0" cellspacing="0" align="center">


              <tr>
              	<td class="errorMessage" align="center">
	              	<table border="0" align="center" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
              			<tr>
              				<td>
				              	<s:fielderror theme="simple"/>
				              	<s:actionerror theme="simple"/>
			              	</td>
            		  	</tr>
              		</table>
              	</td>
              </tr>	
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.utilizationlimits.guest.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<td colspan="2"><s:text name="store.laborassumptions.utilizationlimits.guest.label" /></td>
							</tr>
							<tr class="editorTableHeader">
								<td><s:text name="store.laborassumptions.utilizationlimits.guest.position.label" /></td>
								<td><s:text name="store.laborassumptions.utilization.min.label" /></td>
								<td><s:text name="store.laborassumptions.utilization.max.label" /></td>
							</tr>							
							<s:iterator id="position" value="storePositions" status="pStatus">
								<s:hidden name="storePositions[%{#pStatus.index}].name" value="%{name}" disabled="true" theme="simple"/>
								<s:hidden name="storePositions[%{#pStatus.index}].id" />
								<tr>
									<td class="editorTableFirstColumn">
										<s:property value="name"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].utilizationMinimum" value="%{utilizationMinimum}" theme="simple" size="6" maxlength="6"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].utilizationMaximum" value="%{utilizationMaximum}" theme="simple" size="6" maxlength="6"/>
									</td>
								</tr>
							</s:iterator>											
							<tr>
								<td colspan="3" class="infoMessage"><s:text name="store.laborassumptions.utilizationlimits.guest.message" /></td>		
							</tr>						
						</table>
					</td>
				</tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="store_edit" theme="simple" cssClass="button"/></td>		                    
		                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
		                    </tr>
	                    </table>                    
                    </td>
                </tr>

			</table>
		</td>
	</tr>
</table>
</s:form>