<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
                    	<s:if test="removePage">
                    		<s:text name="store.remove.title" />
                    	</s:if>
                    	<s:else>
                    		<s:text name="store.view.title" />
                    	</s:else>   
			      </td>
        	  </tr>
              
              <tr>                            
              <td align="center">              
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.customer.label" /></td>
                    <td align="left" class="value"><s:property value="store.area.region.customer.name"/></td>
				</tr>

              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.code.label" /></td>
                    <td align="left" class="value"><s:property value="store.code"/></td>
                </tr>
                
                <tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.name.label" /></td>
                    <td  align="left" class="value"><s:property value="store.name"/></td>
                </tr>

              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.region.label" /></td>
                    <td align="left" class="value"><s:property value="store.area.region.name"/></td>
                </tr>
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.area.label" /></td>
                    <td align="left" class="value"><s:property value="store.area.name"/></td>
				</tr>				
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.isdemo.label" /></td>
                    <td align="left" class="value">
						<s:if test="%{store.demo}">
							<img src="<s:url value="/images/check.png" includeParams="none"/>"/>
						</s:if>
						<s:else>
							<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
						</s:else>                    
                    </td>
				</tr>
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.intimeonly.label" /></td>
                    <td align="left" class="value">
						<s:if test="%{store.inTimeOnly}">
							<img src="<s:url value="/images/check.png" includeParams="none"/>"/>
						</s:if>
						<s:else>
							<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/>
						</s:else>                    
                    </td>
				</tr>				
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <s:form theme="simple"> 
	                    <s:hidden name="store.id"/>
	                    <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
		                    <tr>
		                    	<s:if test="removePage">
		                    		<td><s:submit action="store_list" key="cancel.button" theme="simple" cssClass="button"/></td>    
		                  			<td><s:submit action="store_delete" key="remove.button" theme="simple" cssClass="button"/></td>
		                    	</s:if>
		                    	<s:else>
		                    		<td><s:submit action="store_list" key="back.button" theme="simple" cssClass="button"/></td>
		                    	</s:else> 
		                    </tr>
	                    </table>
	                    </s:form>                       
                    </td>
                </tr>
              </table>

              </td>
              </tr>
        	  
        	  <s:if test="!removePage">
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
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeOperationTimes_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.hoursofoperation.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeDayPartDefinition_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.daypartdefinition.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storePositionNames_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.positionnames.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="positionGroup_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.positiongroupnames.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="projectionSettings_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storesettings.projections.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableDefinitions_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.storeoperations.variabledefinition.title" /></a></td></tr>
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
												<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeWeekdayGuestServ_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.weekdayguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeWeekendGuestServ_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.weekendguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableFlexible_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.variableflexible.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeVariableOpening_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.variableopening.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedFlexible_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedflexible.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedOpening_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedopening.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedPostRush_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedpostrush.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedClosing_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedclosing.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeFixedGuestServ_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.fixedguestservice.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeSecondaryVariables_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborallowance.secondaryvariables.title" /></a></td></tr>
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
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeUtilization_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.utilization.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeUtilizationLimits_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.utilizationlimits.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeMinimumStaffing_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.minimumstaffing.title" /></a></td></tr>
						    					<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeActivitySharing_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.activitysharing.title" /></a></td></tr>
				    							<tr><td><img src="<s:url value="/images/bullet.gif" includeParams="none"/>"/></td><td nowrap><a class="actionsLink" href="<s:url namespace="/store" action="storeOtherFactors_show" includeParams="none"><s:param name="storeId" value="store.id"/></s:url>"><s:text name="store.laborassumptions.otherfactors.title" /></a></td></tr>
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