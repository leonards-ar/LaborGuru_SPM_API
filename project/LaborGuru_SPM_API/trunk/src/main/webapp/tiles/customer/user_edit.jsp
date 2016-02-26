<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      <s:if test="user == null">
			      	<s:text name="user.create.title" />
			      </s:if>
			      <s:else>
			      	<s:text name="user.update.title" />
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
              <s:form action="%{getText(actionName)}_save" theme="simple">
              <s:hidden name="user.id" theme="simple"/>
			  <s:hidden name="paramId" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.firstname.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="user.name" size="20" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.surname.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="user.surname" size="20" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.username.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="user.userName" size="15" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap><s:text name="user.email.label" /></td>
                    <td width="35%" align="left" class="value"><s:textfield name="user.email" size="20" theme="simple"/></td>
                </tr>

              	<tr class="editFormEvenRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.password.label" /></td>
                    <td width="35%" align="left" class="value"><s:password name="user.password" showPassword="true" size="20" theme="simple"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.confirm.password.label" /></td>
                    <td width="35%" align="left" class="value"><s:password name="passwordConfirmation" showPassword="true" size="20" theme="simple"/></td>
                </tr>

              	<tr class="editFormOddRow">
                    <td width="15%" align="right" class="form_label" nowrap>* <s:text name="user.status.label" /></td>
                    <td width="35%" align="left" class="value"><s:select name="user.status" list="statusMap" listKey="key" listValue="%{getText(value)}"/></td>
                    <td width="15%" align="right" class="form_label" nowrap>&nbsp;</td>
                    <td width="35%" align="left" class="value">&nbsp;</td>
                </tr>

              	<tr class="editFormEvenRow">
                    <td width="100%" align="right" colspan="4">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                		<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="%{getText(actionName)}_list" theme="simple" cssClass="button"/></td>		                    
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