<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>
<s:form id="settings_form" name="settings_form" action="settings_save" theme="simple">
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="projection.settings.title" />
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
             		<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
			            <tr class="editFormEvenRow">
		                    <td align="right" class="form_label" nowrap><s:text name="projections.settings.dailyWeeksUsedDefault.label" /></td>
		                    <td align="left" class="value"><s:textfield name="dailyWeeksUsedDefault" size="10" theme="simple"/></td>
		                </tr>
		                
		                <tr class="editFormOddRow">
		                    <td align="right" class="form_label" nowrap><s:text name="projections.settings.halfHourWeeksUsedDefault.label" /></td>
		                    <td  align="left" class="value"><s:textfield name="halfHourWeeksUsedDefault" size="10" theme="simple"/></td>
		                </tr>

					<s:iterator id="variableAverage" value="storeVariablesAverages" status="itVariableDefinition">
						<tr class="editForm<s:if test="#itVariableDefinition.even">Odd</s:if><s:else>Even</s:else>Row">
		                    <td align="right" class="form_label" nowrap><s:text name="projections.settings.averageVariable.label"><s:param value="%{getMainVariable(#itVariableDefinition.index)}"/></s:text></td>
		                    <td align="left" class="value"><s:textfield name="storeVariablesAverages[%{#itVariableDefinition.index}]" value="%{variableAverage}" size="10" theme="simple"/></td>
						</tr>
					</s:iterator>
 
		 		       	<tr class="editForm<s:if test="storeVariablesAverages.size() % 2 == 0">Even</s:if><s:else>Odd</s:else>Row">
		                    <td width="100%" align="right" colspan="2">
			                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
				                    <tr>
				                		<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
				                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
				                    	<td><s:submit id="cancelButton" key="cancel.button" action="daily_edit" theme="simple" cssClass="button"/></td>		                    
				                    </tr>
			                    </table>                    
		                    </td>
		                </tr>
              		</table>
	              </td>
              </tr>
          </table>
</s:form>