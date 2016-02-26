<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form name="storePositions_form" action="storePositionNames_save" theme="simple">
	<s:hidden name="store.id" theme="simple" />
	<s:iterator id="removePositon" value="removePositions" status="itRemovePosition">
		<s:hidden name="removePositions[%{#itRemovePosition.index}].id" theme="simple"/>
		<s:hidden name="removePositions[%{#itRemovePosition.index}].name" theme="simple"/>
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
			<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.positionnames.title" /></td>
		</tr>
		<tr>
			<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editorTableHeader">
					<td >&nbsp;</td>
					<td><s:text name="store.storeoperations.positionnames.label" /></td>
					<td><s:text name="store.storeoperations.position.ismanager.label" /></td>
					<td><s:text name="store.storeoperations.position.isguestservice.label" /></td>
					<td >&nbsp;</td>
				</tr>

				<s:iterator id="position" value="positions" status="itPosition">
					<tr class="editorTable<s:if test="#itPosition.even">Even</s:if><s:else>Odd</s:else>Row">
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td><a href="#" onclick="storePositions_form.action='storePositionNames_oneUp.action?index=<s:property value='#itPosition.index'/>'; storePositions_form.submit();"><img src="<s:url value="/images/up.png" includeParams="none"/>"/></a></td>
									<td><a href="#" onclick="storePositions_form.action='storePositionNames_oneDown.action?index=<s:property value='#itPosition.index'/>'; storePositions_form.submit();"><img src="<s:url value="/images/down.png" includeParams="none"/>"/></a></td>
								</tr>
							</table>
						</td>							
						<td class="value">
							<s:hidden name="positions[%{#itPosition.index}].id"/>
							<s:hidden name="positions[%{#itPosition.index}].positionIndex"/>
							<s:textfield name="positions[%{#itPosition.index}].name" value="%{name}" size="25" maxlength="150" theme="simple" />
						</td>
						<td align="center">
							<s:checkbox name="positions[%{#itPosition.index}].manager" fieldValue="true" theme="simple"/>				
						</td>
						<td align="center">
							<s:checkbox name="positions[%{#itPosition.index}].guestService" fieldValue="true" theme="simple"/>				
						</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="storePositions_form.action='storePositionNames_removePosition.action?index=<s:property value='#itPosition.index'/>'; storePositions_form.submit();"><img
										src="<s:url value="/images/delete.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:iterator>
					<tr class="editorTableEvenRow">
						<td>&nbsp;</td>
						<td class="value">
						<s:textfield name="newPositionName" size="25" maxlength="150" theme="simple" />
						</td>
						<td><s:checkbox name="newPositionManager" fieldValue="true" theme="simple"/></td>
						<td><s:checkbox name="newPositionGuestService" fieldValue="true" theme="simple"/></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="storePositions_form.action='storePositionNames_addPosition.action'; storePositions_form.submit();"><img
										src="<s:url value="/images/add.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
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