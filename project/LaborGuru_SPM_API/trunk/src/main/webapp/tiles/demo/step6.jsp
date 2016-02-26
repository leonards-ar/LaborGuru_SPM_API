<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

          <br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="demo.wizard.step6.title" />
			      </td>
        	  </tr>
              
              <tr>
			      <td class="wizardMessage"><s:text name="demo.wizard.step6.message" /></td>
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
              <s:form name="demowizard_form" id="demowizard_form" action="stepSeven_execute" theme="simple">
              <s:hidden name="sourceDemoStoreId" theme="simple"/>
              <s:hidden name="index" theme="simple"/>
              <s:hidden name="actionButton" theme="simple"/>

              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				
				<tr class="editorTableHeader">
					<td >&nbsp;</td>
					<td><s:text name="store.storeoperations.positionnames.label" /></td>
					<td><s:text name="store.storeoperations.position.ismanager.label" /></td>
					<td><s:text name="store.storeoperations.position.isguestservice.label" /></td>
					<td >&nbsp;</td>
				</tr>

				<s:iterator id="varDefName" value="positions" status="itPosition">
					<tr class="editorTable<s:if test="#itPosition.even">Even</s:if><s:else>Odd</s:else>Row">
						<td>
						<s:if test="#itPosition.index != 0">
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td width="16px"><a href="#" onclick="demowizard_form.action='stepSix_positionOneUp.action'; demowizard_form.index.value='<s:property value='#itPosition.index'/>'; demowizard_form.submit();"><img src="<s:url value="/images/up.png" includeParams="none"/>"/></a></td>
									<td width="16px">
									<s:if test="#itPosition.last == false">
									<a href="#" onclick="demowizard_form.action='stepSix_positionOneDown.action'; demowizard_form.index.value='<s:property value='#itPosition.index'/>'; demowizard_form.submit();"><img src="<s:url value="/images/down.png" includeParams="none"/>"/></a>
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
						<td class="value">
							<s:textfield name="positions[%{#itPosition.index}].name" value="%{name}" size="25" maxlength="150" theme="simple" />
						</td>
						<td align="center">
							<s:checkbox name="positions[%{#itPosition.index}].manager" fieldValue="true" theme="simple"/>				
						</td>
						<td align="center">
							<s:checkbox name="positions[%{#itPosition.index}].guestService" fieldValue="true" theme="simple"/>				
						</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="demowizard_form.action='stepSix_removePosition.action', demowizard_form.index.value='<s:property value='#itPosition.index'/>'; demowizard_form.submit();"><img
										src="<s:url value="/images/delete.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:iterator>
					<tr class="editorTableEvenRow">
						<td>&nbsp;</td>
						<td class="value">
						<s:textfield name="newPosition.name" size="25" maxlength="150" theme="simple" />
						</td>
						<td><s:checkbox name="newPosition.manager" fieldValue="true" theme="simple"/></td>
						<td><s:checkbox name="newPosition.guestService" fieldValue="true" theme="simple"/></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
								<tr>
									<td>
										<a href="<s:url value="#" includeParams="none"/>" onclick="demowizard_form.action='stepSix_addPosition.action'; demowizard_form.submit();"><img
										src="<s:url value="/images/add.png" includeParams="none"/>" /></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="5">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<td><s:submit name="backButton" id="backButton" key="back.button" action="stepFive_execute" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='back';"/></td>
		                		<td><s:submit name="nextButton" id="nextButton" key="next.button" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='next';"/></td>
		                    	<td><s:submit name="cancelButton" id="cancelButton" key="cancel.button" action="stepSix_cancel" theme="simple" cssClass="button" onclick="demowizard_form.actionButton.value='cancel';"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>
              </table>
              </s:form>

              </td>
              </tr>
          </table>