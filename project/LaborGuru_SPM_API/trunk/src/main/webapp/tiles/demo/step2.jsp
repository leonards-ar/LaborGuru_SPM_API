<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<script language="javascript" type="text/javascript">
	<!--
		function displayMessage() {
			return confirm('<s:text name="demo.wizard.looseChanges.message" />');
		}
	//-->
	</script>
	
          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step2.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step2.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepThree_execute" theme="simple">
              <s:hidden name="sourceDemoStoreId" theme="simple"/>
              <s:hidden name="actionButton" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.customer.label" /></td>
                    <td align="left" class="value"><s:property value="demoStore.area.region.customer.name"/></td>
				</tr>
                                  	
                <tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="store.name.label" /></td>
                    <td  align="left" class="value"><s:textfield name="demoStore.name" size="20" theme="simple"/></td>
                </tr>

              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.code.label" /></td>
                    <td align="left" class="value"><s:textfield name="demoStore.code" size="10" theme="simple"/></td>
                </tr>

              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.region.label" /></td>
                    <td align="left" class="value"><s:property value="demoStore.area.region.name"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.area.label" /></td>
                    <td align="left" class="value"><s:property value="demoStore.area.name"/></td>
				</tr>
           
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="backButton" id="backButton" key="back.button" action="stepOne_execute" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='back'; return displayMessage();"/></td>
		                		<td><s:submit name="nextButton" id="nextButton" key="next.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='next';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepTwo_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
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