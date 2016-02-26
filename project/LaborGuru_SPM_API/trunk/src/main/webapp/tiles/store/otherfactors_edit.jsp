<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<s:form action="storeOtherFactors_save" theme="simple">
<s:hidden name="storeId" theme="simple"/>
	<table border="0" cellspacing="0" align="center">
		<tr>
			<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.otherfactors.title" /></td>
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
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.scheduleinefficiency.label" /></td>
                    <td align="left" class="value">
						<s:if test="scheduleInefficiency != null">
							<s:text name="percentage" id="ub">
								<s:param name="value" value="scheduleInefficiency"/>
							</s:text>
						</s:if>
						<s:else>
							<s:set name="ub" value=""/>
						</s:else>
						<s:textfield name="scheduleInefficiency" value="%{#ub}" size="6" maxlength="6"/>%
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.fillnefficiency.label" /></td>
                    <td align="left" class="value">
						<s:if test="fillInefficiency != null">
							<s:text name="percentage" id="ub">
								<s:param name="value" value="fillInefficiency"/>
							</s:text>
						</s:if>
						<s:else>
							<s:set name="ub" value=""/>
						</s:else>
						<s:textfield name="fillInefficiency" value="%{#ub}" size="6" maxlength="6"/>%
                    </td>
				</tr>
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.trainingfactor.label" /></td>
                    <td align="left" class="value">
						<s:if test="trainingFactor != null">
							<s:text name="percentage" id="ub">
								<s:param name="value" value="trainingFactor"/>
							</s:text>
						</s:if>
						<s:else>
							<s:set name="ub" value=""/>
						</s:else>
						<s:textfield name="trainingFactor" value="%{#ub}" size="6" maxlength="6"/>%
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.earnedbreakfactor.label" /></td>
                    <td align="left" class="value">
						<s:if test="earnedBreakFactor != null">
							<s:text name="percentage" id="ub">
								<s:param name="value" value="earnedBreakFactor"/>
							</s:text>
						</s:if>
						<s:else>
							<s:set name="ub" value=""/>
						</s:else>
						<s:textfield name="earnedBreakFactor" value="%{#ub}" size="6" maxlength="6"/>%
                    </td>
				</tr>
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.floorManagementFactor.label" /></td>
                    <td align="left" class="value">
						<s:if test="floorManagementFactor != null">
							<s:text name="percentage" id="ub">
								<s:param name="value" value="floorManagementFactor"/>
							</s:text>
						</s:if>
						<s:else>
							<s:set name="ub" value=""/>
						</s:else>
						<s:textfield name="floorManagementFactor" value="%{#ub}" size="6" maxlength="6"/>%
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.minimumfloormanagementhours.label" /></td>
                    <td align="left" class="value">
						<s:textfield name="minimumFloorManagementHours" size="2" maxlength="2"/>
                    </td>
				</tr>				
			</table>
		</td>
	</tr>

            	<tr class="editFormOddRow">
                  <td width="100%" align="right" colspan="2">
                   <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
                    <tr>
                    	<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
                    	<td><s:submit id="cancelButton" key="cancel.button" action="store_edit" theme="simple" cssClass="button"/></td>		                    
                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
                    </tr>
                   </table>                    
                  </td>
              </tr>

			</table>

</s:form>