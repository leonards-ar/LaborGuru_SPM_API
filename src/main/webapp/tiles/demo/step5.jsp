<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step5.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step5.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepSix_execute" theme="simple">
              <s:hidden name="sourceDemoStoreId" theme="simple"/>
              <s:hidden name="index" theme="simple"/>
              <s:hidden name="actionButton" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				
				<tr class="editorTableHeader">
					<td >&nbsp;</td>
					<td><s:text name="store.storeoperations.variabledefinition.index.label" /></td>
					<td><s:text name="store.storeoperations.variabledefinition.name.label" /></td>
					<td >&nbsp;</td>
				</tr>

				<s:iterator id="varDefName" value="variableDefinitionNames" status="itVariableDefinition">
					<tr class="editorTable<s:if test="#itVariableDefinition.even">Even</s:if><s:else>Odd</s:else>Row">
						<td>
						<s:if test="#itVariableDefinition.index != 0">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td width="16px"><a href="#" onclick="demowizard_form.action='stepFive_variableDefinitionOneUp.action'; demowizard_form.index.value='<s:property value='#itVariableDefinition.index'/>'; demowizard_form.submit();"><img src="<s:url value="/images/up.png" includeParams="none"/>"/></a></td>
									<td width="16px">
									<s:if test="#itVariableDefinition.last == false">
									<a href="#" onclick="demowizard_form.action='stepFive_variableDefinitionOneDown.action'; demowizard_form.index.value='<s:property value='#itVariableDefinition.index'/>'; demowizard_form.submit();"><img src="<s:url value="/images/down.png" includeParams="none"/>"/></a>
									</s:if>
									<s:else>
									&nbsp;
									</s:else>
									</td>
								</tr>
							</table>
						</s:if>
						<s:else>&nbsp;</s:else>
						</td>			
						<td align="left">
							<s:property value="%{#itVariableDefinition.index + 1}"/><s:if test="#itVariableDefinition.first">*</s:if>
						</td>
						<td class="value">
							<s:textfield name="variableDefinitionNames[%{#itVariableDefinition.index}]" value="%{varDefName}" size="25" maxlength="100" theme="simple" />
						</td>
						<td>
							<s:if test="#itVariableDefinition.index != 0">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="demowizard_form.action='stepFive_removeVariableDefinition.action'; demowizard_form.index.value='<s:property value='#itVariableDefinition.index'/>'; demowizard_form.submit();"><img
										src="<s:url value="/images/delete.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
							</s:if>
							<s:else>&nbsp;</s:else>							
						</td>
					</tr>
				</s:iterator>
				<s:if test="!maximumVariableDefinitionsReached">
					<tr class="editorTableEvenRow">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="value">
						<s:textfield name="newVariableDefinitionName" size="25" maxlength="100" theme="simple" />
						</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="demowizard_form.action='stepFive_addVariableDefinition.action'; demowizard_form.submit();"><img
										src="<s:url value="/images/add.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:if>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="4">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="backButton" id="backButton" key="back.button" action="stepFour_execute" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='back';"/></td>
		                		<td><s:submit name="nextButton" id="nextButton" key="next.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='next';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepFive_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
		      <tr>
			      <td colspan="4" class="infoMessage"><s:text name="info.mandatory.fields" /></td>
        	  </tr>	  
              </table>
              </s:form>

              </td>
              </tr>
          </table>