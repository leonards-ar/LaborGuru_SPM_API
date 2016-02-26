<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td>&nbsp;</td>
        	  </tr>
		      <tr>
			      <td id="titleBar">
                    	<s:if test="removePage">
                    		<s:text name="employee.remove.title" />
                    	</s:if>
                    	<s:else>
                    		<s:text name="employee.view.title" />
                    	</s:else>                    
			      </td>
        	  </tr>
              <tr>
              <td>
              <table id="editFormTable" border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.firstname.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.name"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.surname.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.surname"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.username.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.userName"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.email.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.email"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.phone.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.phone"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.phone2.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.phone2"/></td>
                </tr>
                
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.employeeid.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.employeeId"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.wage.label" /></td>
                    <td width="35%" align="left" class="value"><s:text name="currency"><s:param value="employee.wage"/></s:text></td>
                </tr>
                                
              	<tr class="editFormEvenRow">
	              	<td width="15%" align="right" class="form_label" nowrap><s:text name="employee.maxdaysweek.label" /></td>
	              	<td colspan="3" width="85%" align="left">
						<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0"><tr>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:property value="employee.maxDaysWeek"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.maxhoursday.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:property value="employee.maxHoursDay"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.maxhoursweek.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:property value="employee.maxHoursWeek"/></td>
						</tr></table>
              		</td>
              	</tr>

              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.defaultposition.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.defaultPosition.name"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.status.label" /></td>
                    <td width="35%" align="left" class="value"><s:text name="%{statusMap[employee.status.toString()]}" /></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.ismanager.label" /></td>
                    <td width="35%" align="left" class="value">
						<s:if test="%{employee.manager}">
							<img src="<s:url value="/images/check.png" includeParams="none"/>"/>
						</s:if>
						<s:else>
							<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
						</s:else>
                    </td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.hiredate.label" /></td>
                    <td width="35%" align="left" class="value"><s:if test="employee.hireDate !=  null"><s:text name="shortdate"><s:param value="employee.hireDate"/></s:text></s:if><s:else>&nbsp;</s:else></td>
                </tr>

              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.address.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.address"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>&nbsp;</td>
                    <td width="35%" align="left" class="value">&nbsp;</td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.address2.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="employee.address2"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>&nbsp;</td>
                    <td width="35%" align="left" class="value">&nbsp;</td>
                </tr>

              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.city.label" /></td>
              		<td colspan="3" width="85%" align="left">
						<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0"><tr>
						<td align="left" class="value"><s:property value="employee.city"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
                    	<td align="right" class="form_label" nowrap><s:text name="employee.state.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:property value="employee.state"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="right" class="form_label" nowrap><s:text name="employee.zip.label" /></td>
	                    <td><img src="<s:url value="/images/transp6x1.gif" includeParams="none"/>"/></td>
	                    <td align="left" class="value"><s:property value="employee.zip"/></td>
						</tr></table>
					</td>              		
                </tr>   
              	
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.comments.label" /></td>
                    <td width="85%" align="left" class="value" colspan="3"><s:property value="employee.comments"/></td>
                </tr>
                
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="4">
                    <s:form theme="simple">
                    <s:hidden name="storeId"/> 
                    <s:hidden name="employee.id"/>
                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0"><tr>                  
                    <td>
                    	<s:if test="removePage">
                    		<s:submit action="employeeStore_list" key="cancel.button" theme="simple" cssClass="button"/>      
                  			<s:submit action="employeeStore_delete" key="remove.button" theme="simple" cssClass="button"/>
                    	</s:if>
                    	<s:else>
                    		<s:submit action="employeeStore_list" key="back.button" theme="simple" cssClass="button"/>
                    	</s:else>                    
                    </td>
                    </tr></table>
                   	</s:form>                    
                    </td>
                </tr>
              </table>
              </td>
              </tr>
          </table>
