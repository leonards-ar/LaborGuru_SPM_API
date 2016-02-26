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
                    		<s:text name="user.remove.title" />
                    	</s:if>
                    	<s:else>
                    		<s:text name="user.view.title" />
                    	</s:else>                    
			      </td>
        	  </tr>
              <tr>
              <td>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.firstname.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="user.name"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.surname.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="user.surname"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.username.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="user.userName"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.email.label" /></td>
                    <td width="35%" align="left" class="value"><s:property value="user.email"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.status.label" /></td>
                    <td width="35%" align="left" class="value"><s:text name="%{statusMap[user.status.toString()]}"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>&nbsp;</td>
                    <td width="35%" align="left" class="value">&nbsp;</td>
                </tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="4">
                    <s:form theme="simple"> 
                    <s:hidden name="user.id"/>
                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0"><tr>                  
                    <td>
                    	<s:if test="removePage">
                    		<s:submit action="user_list" key="cancel.button" theme="simple" cssClass="button"/>      
                  			<s:submit action="user_delete" key="remove.button" theme="simple" cssClass="button"/>
                    	</s:if>
                    	<s:else>
                    		<s:submit action="user_list" key="back.button" theme="simple" cssClass="button"/>
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
