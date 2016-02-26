<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
              <s:form action="storeOperationTimes_save" theme="simple">
              <s:hidden name="storeId" theme="simple"/>
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
                    	<s:select name="firstDayOfWeek" list="{'0','1','2','3','4','5','6'}" listValue="%{getText('dayofweek.'+toString())}"/>
                    </td>
				</tr>
			</table>
		</td>
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
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[0]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[1]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[2]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[3]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[4]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[5]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableOddRow"><s:textfield name='weekOperationTimeOpen[6]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.hoursofoperation.close" /></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[0]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[1]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[2]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[3]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[4]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[5]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
								<td class="editorTableEvenRow"><s:textfield name='weekOperationTimeClose[6]' onchange="updateTime(this);" size="4" maxlength="8"/></td>
							</tr>						
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.opening_extra_hours.label" /></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[0]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[1]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[2]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[3]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[4]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[5]' size="2" maxlength="2"/></td>
								<td class="editorTableOddRow"><s:textfield name='openingExtraHours[6]' size="2" maxlength="2"/></td>
							</tr>
							<tr>
								<td class="editorTableFirstColumn"><s:text name="store.storeoperations.closing_extra_hours.label" /></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[0]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[1]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[2]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[3]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[4]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[5]' size="2" maxlength="2"/></td>
								<td class="editorTableEvenRow"><s:textfield name='closingExtraHours[6]' size="2" maxlength="2"/></td>
							</tr>						

							<tr>
								<td colspan="6" class="infoMessage"><s:text name="store.storeoperations.hoursofoperation.message" /></td>		
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