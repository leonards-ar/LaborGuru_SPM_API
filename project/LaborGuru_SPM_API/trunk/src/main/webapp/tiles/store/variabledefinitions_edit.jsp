<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form name="variableDefinitions_form" action="storeVariableDefinitions_save" theme="simple">
	<s:hidden name="store.id" theme="simple" />
	<s:iterator id="removeVariableDefinition" value="removeVariableDefinitions" status="itRemoveVariableDefinition">
		<s:hidden name="removeVariableDefinitions[%{#itRemoveVariableDefinition.index}].id" theme="simple"/>
		<s:hidden name="removeVariableDefinitions[%{#itRemoveVariableDefinition.index}].name" theme="simple"/>
	</s:iterator>
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
			<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.variabledefinition.title" /></td>
		</tr>
		<tr>
			<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editorTableHeader">
					<td >&nbsp;</td>
					<td><s:text name="store.storeoperations.variabledefinition.index.label" /></td>
					<td><s:text name="store.storeoperations.variabledefinition.name.label" /></td>
					<td >&nbsp;</td>
				</tr>

				<s:iterator id="variableDefinition" value="variableDefinitions" status="itVariableDefinition">
					<tr class="editorTable<s:if test="#itVariableDefinition.even">Even</s:if><s:else>Odd</s:else>Row">
						<td>
						<s:if test="#itVariableDefinition.index != 0">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td><a href="#" onclick="variableDefinitions_form.action='storeVariableDefinitions_oneUp.action?index=<s:property value='#itVariableDefinition.index'/>'; variableDefinitions_form.submit();"><img src="<s:url value="/images/up.png" includeParams="none"/>"/></a></td>
									<td><a href="#" onclick="variableDefinitions_form.action='storeVariableDefinitions_oneDown.action?index=<s:property value='#itVariableDefinition.index'/>'; variableDefinitions_form.submit();"><img src="<s:url value="/images/down.png" includeParams="none"/>"/></a></td>
								</tr>
							</table>
						</s:if>
						<s:else>&nbsp;</s:else>
						</td>							
						<td align="center">
							<s:hidden name="variableDefinitions[%{#itVariableDefinition.index}].id"/>
							<s:hidden name="variableDefinitions[%{#itVariableDefinition.index}].variableIndex"/>
							<s:property value="%{variablePosition}"/>
						</td>
						<td class="value">
							<s:textfield name="variableDefinitions[%{#itVariableDefinition.index}].name" value="%{name}" size="25" maxlength="100" theme="simple" />
						</td>
						<td>
							<s:if test="#itVariableDefinition.index != 0">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="variableDefinitions_form.action='storeVariableDefinitions_removeVariableDefinition.action?index=<s:property value='#itVariableDefinition.index'/>'; variableDefinitions_form.submit();"><img
										src="<s:url value="/images/delete.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
							</s:if>
							<s:else>&nbsp;</s:else>							
						</td>
					</tr>
				</s:iterator>
				<s:if test="!maximumVariableDefinitionsReached">
					<tr class="editorTableEvenRow">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="value">
						<s:textfield name="newVariableDefinitionName" size="25" maxlength="100" theme="simple" />
						</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="variableDefinitions_form.action='storeVariableDefinitions_addVariableDefinition.action'; variableDefinitions_form.submit();"><img
										src="<s:url value="/images/add.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:if>
			</table>
			</td>
		</tr>

		<tr class="editFormOddRow">
			<td width="100%" align="right">
			<table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
				<tr>
					<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button" /></td>
					<td><s:submit id="cancelButton" key="cancel.button" action="store_edit" theme="simple" cssClass="button" /></td>
					<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button" /></td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
</s:form>