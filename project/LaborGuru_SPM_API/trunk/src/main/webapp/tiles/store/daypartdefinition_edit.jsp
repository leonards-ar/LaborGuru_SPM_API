<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<br />
<s:form id="dayPartDefinition_form" action="storeDayPartDefinition_save" theme="simple">
<s:hidden name="store.id" theme="simple"/>
<table border="0" cellspacing="0" align="center">
	
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.daypartdefinition.title" /></td>
	</tr>
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
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr>
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<td><s:text name="store.storeoperations.daypartdefinition.daypart.label" /></td>
								<td><s:text name="store.storeoperations.daypartdefinition.starttime.label" /></td>
								<td>&nbsp;</td>
							</tr>
							<s:iterator value="dayParts" status="itDayPart">
							<s:hidden name="dayParts[%{#itDayPart.index}].id" value="%{id}" theme="simple"/>
							<s:hidden name="dayParts[%{#itDayPart.index}].positionIndex" value="%{positionIndex}" theme="simple"/>
							<tr class="editorTable<s:if test="#itDayPart.even">Even</s:if><s:else>Odd</s:else>Row">
								<td>
									<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
										<tr>
											<td><a href="#" onclick="dayPartDefinition_form.action='storeDayPartDefinition_oneUp.action?index=<s:property value='#itDayPart.index'/>'; dayPartDefinition_form.submit();"><img src="<s:url value="/images/up.png" includeParams="none"/>"/></a></td>
											<td><a href="#" onclick="dayPartDefinition_form.action='storeDayPartDefinition_oneDown.action?index=<s:property value='#itDayPart.index'/>'; dayPartDefinition_form.submit();"><img src="<s:url value="/images/down.png" includeParams="none"/>"/></a></td>
										</tr>
									</table>
								</td>							
								<td class="value"><s:textfield name="dayParts[%{#itDayPart.index}].name" value="%{name}" size="25" maxlength="100" theme="simple" /></td>
								<td class="value"><s:textfield name="dayParts[%{#itDayPart.index}].startHour" value="%{startHour}" size="5" onchange="updateTime(this);" maxlength="8" theme="simple" /></td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
										<tr>
											<td><a href="#" onclick="dayPartDefinition_form.action='storeDayPartDefinition_remove.action?index=<s:property value='#itDayPart.index'/>'; dayPartDefinition_form.submit();"><img src="<s:url value="/images/delete.png" includeParams="none"/>"/></a></td>
										</tr>
									</table>
								</td>
							</tr>
							</s:iterator>
							<tr class="editorTableEvenRow">
								<td>
									<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</td>							

								<td class="value"><s:textfield name="newDayPart.name" size="25" maxlength="100" theme="simple" /></td>
								<td class="value"><s:textfield name="newDayPart.startHour" size="5" maxlength="5" theme="simple" /></td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
										<tr>
											<td><a href="#" onclick="dayPartDefinition_form.action='storeDayPartDefinition_add.action'; dayPartDefinition_form.submit();"><img src="<s:url value="/images/add.png" includeParams="none"/>"/></a></td>
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