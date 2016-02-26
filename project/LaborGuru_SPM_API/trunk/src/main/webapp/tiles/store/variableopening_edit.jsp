<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
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
		<td id="subtitleBar" nowrap><s:text name="store.laborallowance.title" /> - <s:text name="store.laborallowance.variableopening.title" /></td>
	</tr>

	<tr>
		<td align="center">
              <s:form action="storeVariableOpening_save" theme="simple">
              <s:hidden name="store.id" theme="simple"/>
		
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<s:iterator id="dayPart" value="store.dayParts" status="dpStatus">
								<td><s:property value="name"/>
								</s:iterator>
							</tr>
							<s:iterator id="position" value="store.positions" status="pStatus">
								<tr>
									<td class="editorTableFirstColumn"><s:property value="name"/></td>
									<s:iterator id="dayPart" value="store.dayParts" status="dpStatus">
										<td class="editorTable<s:if test="#pStatus.even">Even</s:if><s:else>Odd</s:else>Row">
										<s:textfield name="dayPartValues[%{#pStatus.index}][%{#dpStatus.index}]" size="8" maxlength="10"/>
										</td>
									</s:iterator>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="<s:property value="%{store.dayParts.size + 1}"/>" class="infoMessage"><s:text name="store.laborallowance.variableopening.message" /></td>		
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
			</s:form>
		</td>
	</tr>
</table>