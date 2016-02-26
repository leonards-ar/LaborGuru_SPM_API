<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />

<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.otherfactors.title" /></td>
	</tr>

	<tr>
		<td align="left">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.scheduleinefficiency.label" /></td>
                    <td align="left" class="value">
	                    <s:text name="percentage">
	                    	<s:param name="value" value="store.scheduleInefficiency"/>
	                    </s:text>%
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.fillnefficiency.label" /></td>
                    <td align="left" class="value">
	                    <s:text name="percentage">
	                    	<s:param name="value" value="store.fillInefficiency"/>
	                    </s:text>%                    
                    </td>
				</tr>
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.trainingfactor.label" /></td>
                    <td align="left" class="value">
	                    <s:text name="percentage">
	                    	<s:param name="value" value="store.trainingFactor"/>
	                    </s:text>%                    
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.earnedbreakfactor.label" /></td>
                    <td align="left" class="value">
	                    <s:text name="percentage">
	                    	<s:param name="value" value="store.earnedBreakFactor"/>
	                    </s:text>%
                    </td>
				</tr>
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.floorManagementFactor.label" /></td>
                    <td align="left" class="value">
	                    <s:text name="percentage">
	                    	<s:param name="value" value="store.floorManagementFactor"/>
	                    </s:text>%
                    </td>
				</tr>
				<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.laborassumptions.otherfactors.minimumfloormanagementhours.label" /></td>
                    <td align="left" class="value">
                    	<s:property value="store.minimumFloorManagementHours"/>
                    </td>
				</tr>				
			</table>
		</td>
	</tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right">
		              <s:form action="store_show" theme="simple">
		              <s:hidden name="store.id" theme="simple"/>                    
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
								<td><s:submit action="store_show" key="back.button" theme="simple" cssClass="button"/></td>
		                    </tr>
	                    </table>   
	                    </s:form>                   
                    </td>
                </tr>

			</table>

