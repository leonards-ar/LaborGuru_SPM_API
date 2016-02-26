<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.schedule.title" /></td>
	</tr>

	<tr>
		<td align="left">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.storeoperations.firstdayofweek.label" /></td>
                    <td align="left" class="value">
                    	<s:if test="store.firstDayOfWeekAsInteger != null">
                    		<s:text name="%{'dayofweek.'+store.firstDayOfWeekAsInteger}" />
                    	</s:if>
                    </td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.hoursofoperation.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td>&nbsp;</td>
								<td><s:text name="dayofweek.0" /></td>
								<td><s:text name="dayofweek.1" /></td>
								<td><s:text name="dayofweek.2" /></td>
								<td><s:text name="dayofweek.3" /></td>
								<td><s:text name="dayofweek.4" /></td>
								<td><s:text name="dayofweek.5" /></td>
								<td><s:text name="dayofweek.6" /></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.hoursofoperation.open" /></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[0]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[1]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[2]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[3]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[4]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[5]"/></td>
								<td class="editorTableOddRow"><s:property value="weekOperationTimeOpen[6]"/></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.hoursofoperation.close" /></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[0]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[1]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[2]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[3]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[4]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[5]"/></td>
								<td class="editorTableEvenRow"><s:property value="weekOperationTimeClose[6]"/></td>
							</tr>	
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.opening_extra_hours.label" /></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[0]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[1]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[2]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[3]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[4]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[5]"/></td>
								<td class="editorTableOddRow"><s:property value="openingExtraHours[6]"/></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.closing_extra_hours.label" /></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[0]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[1]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[2]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[3]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[4]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[5]"/></td>
								<td class="editorTableEvenRow"><s:property value="closingExtraHours[6]"/></td>
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
