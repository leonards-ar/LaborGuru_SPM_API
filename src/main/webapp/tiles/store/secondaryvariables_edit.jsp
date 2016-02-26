<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form action="storeSecondaryVariables_save" theme="simple">
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
		<td>
			<table border="0" align="center" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborallowance.title" /> - <s:text name="store.laborallowance.secondaryvariables.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td><s:text name="store.laborallowance.secondaryvariables.position.label" /></td>
								<s:if test="%{isSecondaryVariablePresent(1)}">
									<td><s:text name="store.laborallowance.secondaryvariables.opening.label"><s:param value="secondaryVariableNames[1]"></s:param></s:text></td>
									<td><s:text name="store.laborallowance.secondaryvariables.flexible.label"><s:param value="secondaryVariableNames[1]"></s:param></s:text></td>
								</s:if>
								<s:if test="%{isSecondaryVariablePresent(2)}">
									<td><s:text name="store.laborallowance.secondaryvariables.opening.label"><s:param value="secondaryVariableNames[2]"></s:param></s:text></td>
									<td><s:text name="store.laborallowance.secondaryvariables.flexible.label"><s:param value="secondaryVariableNames[2]"></s:param></s:text></td>
								</s:if>
								<s:if test="%{isSecondaryVariablePresent(3)}">
									<td><s:text name="store.laborallowance.secondaryvariables.opening.label"><s:param value="secondaryVariableNames[3]"></s:param></s:text></td>
									<td><s:text name="store.laborallowance.secondaryvariables.flexible.label"><s:param value="secondaryVariableNames[3]"></s:param></s:text></td>
								</s:if>
							</tr>			
							<s:iterator id="position" value="storePositions" status="pStatus">
							<tr>
								<td class="editorTableFirstColumn"><s:property value="name"/></td>
								<s:hidden name="storePositions[%{#pStatus.index}].id" />
								<s:hidden name="storePositions[%{#pStatus.index}].name" />
								
								<s:if test="%{isSecondaryVariablePresent(1)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable2Opening" size="8" maxlength="10"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable2Flexible" size="8" maxlength="10"/>
									</td>
								</s:if>
								<s:else>
									<s:hidden name="storePositions[%{#pStatus.index}].variable2Opening" value="0" />
									<s:hidden name="storePositions[%{#pStatus.index}].variable2Flexible" value="0" />
								</s:else>
								
								<s:if test="%{isSecondaryVariablePresent(2)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable3Opening" size="8" maxlength="10"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable3Flexible" size="8" maxlength="10"/>
									</td>
								</s:if>
								<s:else>
									<s:hidden name="storePositions[%{#pStatus.index}].variable3Opening" value="0" />
									<s:hidden name="storePositions[%{#pStatus.index}].variable3Flexible" value="0" />
								</s:else>

								<s:if test="%{isSecondaryVariablePresent(3)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable4Opening" size="8" maxlength="10"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="storePositions[%{#pStatus.index}].variable4Flexible" size="8" maxlength="10"/>
									</td>
								</s:if>
								<s:else>
									<s:hidden name="storePositions[%{#pStatus.index}].variable4Opening" value="0" />
									<s:hidden name="storePositions[%{#pStatus.index}].variable4Flexible" value="0" />
								</s:else>
								
							</tr>
							</s:iterator>											
							<tr>
								<td colspan="<s:property value="%{totalSecondaryVariablesColumns}"/>" class="infoMessage"><s:text name="store.laborallowance.secondaryvariables.message" /></td>		
							</tr>						
						</table>
					</td>
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