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
                    <td width="35%" align="left" class="value"><s:property value="manager.name"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.surname.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="manager.surname"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.username.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="manager.userName"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.email.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="manager.email"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="employee.status.label" /></td>
                    <td width="35%" align="left" class="value"><s:text name="%{statusMap[manager.status.toString()]}" /></td>
                    <td width="15%" align="right" class="form_label" nowrap>&nbsp;</td>
                    <td width="35%" align="left" class="value">&nbsp;</td>

                </tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="4">
                    <s:form theme="simple">
                    <s:hidden name="paramId"/> 
                    <s:hidden name="userId"/>
                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0"><tr>                  
                    <td>
                    	<s:if test="removePage">
                    		<s:submit action="%{getText(actionName)}_list" key="cancel.button" theme="simple" cssClass="button"/>      
                  			<s:submit action="%{getText(actionName)}_delete" key="remove.button" theme="simple" cssClass="button"/>
                    	</s:if>
                    	<s:else>
                    		<s:submit action="%{getText(actionName)}_list" key="back.button" theme="simple" cssClass="button"/>
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
