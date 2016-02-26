<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step4.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step4.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepFive_execute" theme="simple">
              <s:hidden name="sourceDemoStoreId" theme="simple"/>
              <s:hidden name="actionButton" theme="simple"/>
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
								<td colspan="6" class="infoMessage"><s:text name="store.storeoperations.hoursofoperation.message" /></td>		
							</tr>						
						</table>
					</td>
				</tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="backButton" id="backButton" key="back.button" action="stepThree_execute" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='back';"/></td>
		                		<td><s:submit name="nextButton" id="nextButton" key="next.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='next';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepFour_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
		      <tr>
			      <td colspan="2" class="infoMessage"><s:text name="info.mandatory.fields" /></td>
        	  </tr>	  
              </table>
              </s:form>

              </td>
              </tr>
          </table>