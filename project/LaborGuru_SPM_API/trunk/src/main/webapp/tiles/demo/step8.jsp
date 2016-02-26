<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step8.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step8.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepEight_login" theme="simple">
              <s:hidden name="actionButton" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.name.label" /></td>
                    <td align="left" class="value"><s:property value="storeName"/></td>
				</tr>
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="employee.username.label" /></td>
                    <td align="left" class="value"><s:property value="employeeUserName"/></td>
				</tr>
           
              	<tr class="editFormEvenRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="loginButton" id="loginButton" key="login.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='login';"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
              </table>
              </s:form>
              </td>
              </tr>
          </table>