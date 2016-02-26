<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step1.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step1.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepTwo_execute" theme="simple">
              <s:hidden name="actionButton" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="demo.wizard.baseDemoStore.label" /></td>
                    <td align="left" class="value"><s:select name="sourceDemoStoreId" list="demoStores" listKey="id" listValue="name" theme="simple"/></td>
				</tr>
           
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                		<td><s:submit name="nextButton" id="nextButton" key="next.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='next';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepOne_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
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