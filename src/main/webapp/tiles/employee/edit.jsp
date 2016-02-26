<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      <s:if test="employee.id == null">
			      	<s:text name="employee.create.title" />
			      </s:if>
			      <s:else>
			      	<s:text name="employee.update.title" />
			      </s:else>
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
              <td>              
              <s:form action="employee_save" theme="simple">
              <s:hidden name="employee.id" theme="simple"/>
              <s:hidden name="employee.store.id" theme="simple"/>              
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
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.employeeid.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.employeeId" size="10" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="employee.wage.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="employee.wage" size="4" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
	              	<td width="15%" align="right" class="form_label" nowrap><s:text name="employee.maxdaysweek.label" /></td>
	              	<td colspan="3" width="85%" align="left">
						<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0"><tr>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:textfield name="employee.maxDaysWeek" size="2" theme="simple"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.maxhoursday.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:textfield name="employee.maxHoursDay" size="2" theme="simple"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.maxhoursweek.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:textfield name="employee.maxHoursWeek" size="3" theme="simple"/></td>
						</tr></table>
	              	</td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.defaultposition.label" /></td>
                    <td width="35%" align="left" class="value">
                    <s:select name="employee.defaultPosition.id" list="positions" listKey="id" listValue="name" theme="simple"/>
                    </td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.status.label" /></td>
                    <td width="35%" align="left" class="value">
                    <s:select name="employee.status" list="statusMap" listKey="key" listValue="%{getText(value)}" theme="simple"/>
                    </td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.ismanager.label" /></td>
                    <td width="35%" align="left" class="value"><s:checkbox name="employee.manager" fieldValue="true" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.hiredate.label" /></td>
                    <td width="35%" align="left" class="value">
                    	<s:datetimepicker id="hireDate" type="date" formatLength="short" displayFormat="%{getText('datepicker.format.date')}" disabled="true" name="employee.hireDate" theme="simple"/>
                    	<script>djConfig.searchIds.push("hireDate");</script>
                    </td>
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
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.comments.label" /></td>
                    <td width="85%" align="left" class="value" colspan="3"><s:textfield name="employee.comments" size="60" theme="simple"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="100%" align="right" colspan="4">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                		<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="employee_list" theme="simple" cssClass="button"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
              </table>
              </s:form>
              </td>
              </tr>
		      <tr>
			      <td class="infoMessage"><s:text name="info.mandatory.fields" /></td>
        	  </tr>	              
          </table>