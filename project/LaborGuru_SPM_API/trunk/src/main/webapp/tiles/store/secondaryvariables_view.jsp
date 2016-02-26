<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<table border="0" cellspacing="0" align="center">
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
								
								<s:if test="%{isSecondaryVariablePresent(1)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable2Opening}"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable2Flexible}"/>
									</td>
								</s:if>
								
								<s:if test="%{isSecondaryVariablePresent(2)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable3Opening}"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable3Flexible}"/>
									</td>
								</s:if>

								<s:if test="%{isSecondaryVariablePresent(3)}">
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable4Opening}"/>
									</td>
									<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:property value="%{storePositions[#pStatus.index].variable4Flexible}"/>
									</td>
								</s:if>
								
							</tr>
							</s:iterator>											
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
