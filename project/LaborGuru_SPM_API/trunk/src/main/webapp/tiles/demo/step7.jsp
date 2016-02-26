<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step7.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step7.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepEight_execute" theme="simple">
              <s:hidden name="sourceDemoStoreId" theme="simple"/>
              <s:hidden name="actionButton" theme="simple"/>

              <table id="editFormTable" border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="employee.firstname.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.name" size="20" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="employee.surname.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.surname" size="20" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="employee.username.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.userName" size="15" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.email.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.email" size="20" theme="simple"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.password.label" /></td>
                    <td width="35%" align="left" class="value"><s:password name="employee.password" showPassword="true" size="20" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.confirm.password.label" /></td>
                    <td width="35%" align="left" class="value"><s:password name="passwordConfirmation" showPassword="true" size="20" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.phone.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.phone" size="15" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.phone2.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.phone2" size="15" theme="simple"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.address.label" /></td>
                    <td width="85%" align="left" class="value" colspan="3"><s:textfield name="employee.address" size="60" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.address2.label" /></td>
                    <td width="85%" align="left" class="value" colspan="3"><s:textfield name="employee.address2" size="60" theme="simple"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.city.label" /></td>
              		<td colspan="3" width="85%" align="left">
						<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0"><tr>
						<td align="left" class="value"><s:textfield name="employee.city" size="20" theme="simple"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
                    	<td align="right" class="form_label" nowrap><s:text name="employee.state.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:select name="employee.state" list="statesList" theme="simple" headerKey="" headerValue="%{getText('employee.state.header.label')}"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.zip.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:textfield name="employee.zip" size="5" theme="simple"/></td>
						</tr></table>
					</td>              		
                </tr>                

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="4">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="backButton" id="backButton" key="back.button" action="stepSix_execute" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='back';"/></td>
		                		<td><s:submit name="saveButton" id="saveButton" key="save.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='save';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepSeven_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
              </table>
              </s:form>

              </td>
              </tr>
          </table>