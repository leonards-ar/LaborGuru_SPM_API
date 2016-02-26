<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form name="positionGroup_form" action="positionGroup_save" theme="simple">
	<s:hidden name="store.id" theme="simple" />
	<s:iterator id="removePositonGroup" value="removePositionGroups" status="itRemovePosition">
		<s:hidden name="removePositionGroups[%{#itRemovePosition.index}].id" theme="simple"/>
		<s:hidden name="removePositionGroups[%{#itRemovePosition.index}].name" theme="simple"/>
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
			<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.positiongroupnames.title" /></td>
		</tr>
		<tr>
			<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editorTableHeader">
					<td colspan="2"><s:text name="store.storeoperations.positiongroupnames.label" /></td>
				</tr>

				<s:iterator id="positionGroup" value="positionGroups" status="itPosition">
					<tr class="editorTable<s:if test="#itPosition.even">Even</s:if><s:else>Odd</s:else>Row">
						<td class="value">
						  <s:hidden name="positionGroups[%{#itPosition.index}].id"/>
						  <s:textfield name="positionGroups[%{#itPosition.index}].name" value="%{name}" size="25" maxlength="150" theme="simple" />
						</td>
						<td>
						  <table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
							<tr>
								<td>
									<a href="<s:url value="#" includeParams="none"/>" onclick="positionGroup_form.action='positionGroup_removePositionGroup.action?index=<s:property value='#itPosition.index'/>'; positionGroup_form.submit();"><img
									src="<s:url value="/images/delete.png" includeParams="none"/>" /></a>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</s:iterator>
					<tr class="editorTableEvenRow">
						<td class="value">
						<s:textfield name="newPositionGroup" size="25" maxlength="150" theme="simple" /></td>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
							<tr>
								<td>
									<a href="<s:url value="#" includeParams="none"/>" onclick="positionGroup_form.action='positionGroup_addPositionGroup.action'; positionGroup_form.submit();"><img
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