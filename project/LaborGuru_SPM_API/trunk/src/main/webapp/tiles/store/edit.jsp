<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      <s:if test="store.id == null">
			      	<s:text name="store.create.title" />
			      </s:if>
			      <s:else>
			      	<s:text name="store.update.title" />
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
              <td align="center">              
              <s:form name="store_form" id="store_form" action="store_save" theme="simple">
              <s:hidden name="store.id" theme="simple"/>
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="store.customer.label" /></td>
                    <td align="left" class="value"><s:select onchange="store_form.action='store_selectCustomer.action';store_form.submit();" name="storeCustomer.id" list="customers" listKey="id" listValue="name" headerKey="" headerValue="%{getText('store.customer.header.label')}" theme="simple"/></td>
				</tr>
                                  	
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.code.label" /></td>
                    <td align="left" class="value"><s:textfield name="store.code" size="10" theme="simple"/></td>
                </tr>
                
                <tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="store.name.label" /></td>
                    <td  align="left" class="value"><s:textfield name="store.name" size="20" theme="simple"/></td>
                </tr>
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="store.region.label" /></td>
                    <td align="left" class="value"><s:select onchange="store_form.action='store_selectRegion.action';store_form.submit();" name="storeRegion.id" list="regions" listKey="id" listValue="name" headerKey="" headerValue="%{getText('store.region.header.label')}" theme="simple"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="store.area.label" /></td>
                    <td align="left" class="value"><s:select name="storeArea.id" list="areas" listKey="id" listValue="name" headerKey="" headerValue="%{getText('store.area.header.label')}" theme="simple"/></td>
				</tr>
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.isdemo.label" /></td>
                    <td align="left" class="value"><s:checkbox name="store.demo" theme="simple"/></td>
				</tr>					
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.intimeonly.label" /></td>
                    <td align="left" class="value"><s:checkbox name="store.inTimeOnly" theme="simple"/></td>
				</tr>
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                		<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="store_list" theme="simple" cssClass="button"/></td>		                    
		                    	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
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
        	  
        	  <s:if test="store.id != null"> 
        	  
        	  <tr>
        	  	<td width="100%">
        	  		<table border="0" cellpadding="6" cellspacing="6" colspan="0" cellspan="0" width="100%">
					  <tr>
					    <td width="33%" valign="top" align="left">
					    	<!-- Store Operations -->
					    		<table id="actionsTable" align="right">
					    			<tr class="actionsTableHeader"><td nowrap><s:text name="store.storeoperations.title" /></td></tr>
					    			<tr class="actionsTableValue">
						    			<td>
						    				<table border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeOperationTimes_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.hoursofoperation.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeDayPartDefinition_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.daypartdefinition.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storePositionNames_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.positionnames.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="positionGroup_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.positiongroupnames.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="projectionSettings_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storesettings.projections.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableDefinitions_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.variabledefinition.title" /></a></td></tr>
						    					
						    				</table>
						    			</td>
					    			</tr>
					    		</table>
					    	<!-- End Store Operations -->					    
					    </td>
					    <td width="34%" valign="top" align="center">
					    	<!-- Labor Allowances -->
					    		<table id="actionsTable" align="left">
					    			<tr class="actionsTableHeader"><td nowrap><s:text name="store.laborallowance.title" /></td></tr>
					    			<tr class="actionsTableValue">
					    			<td>
					    				<table border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
												<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeWeekdayGuestServ_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.weekdayguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeWeekendGuestServ_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.weekendguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableFlexible_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.variableflexible.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableOpening_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.variableopening.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedFlexible_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedflexible.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedOpening_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedopening.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedPostRush_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedpostrush.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedClosing_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedclosing.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedGuestServ_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeSecondaryVariables_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.secondaryvariables.title" /></a></td></tr>
					    				</table>
					    			</td>
					    			</tr>
					    		</table>
					    	<!-- End Allowances -->					    
					    </td>
					    <td width="33%" valign="top" align="right">
					    	<!-- Labor Assumptions -->
					    		<table id="actionsTable" align="center">
					    			<tr class="actionsTableHeader"><td nowrap><s:text name="store.laborassumptions.title" /></td></tr>
					    			<tr class="actionsTableValue">
						    			<td>
						    				<table border="0" cellpadding="3" cellspacing="0" colspan="0" cellspan="0">
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeUtilization_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.utilization.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeUtilizationLimits_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.utilizationlimits.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeMinimumStaffing_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.minimumstaffing.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeActivitySharing_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.activitysharing.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeOtherFactors_edit" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.otherfactors.title" /></a></td></tr>
				    						</table>
						    			</td>
					    			</tr>
					    		</table>
					    	<!-- End Labor Assumptions -->
					    </td>
					  </tr>
					</table>
        	  	</td>
        	  </tr>     
        	  </s:if>    
          </table>