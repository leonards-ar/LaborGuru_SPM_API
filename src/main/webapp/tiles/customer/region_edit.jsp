<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

			<br/>
	      <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="customer.region.edit.title" />
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
              <s:form name="customerRegion_form" id="customerRegion_form" action="customerRegion_save" theme="simple">
              <s:hidden name="regionId" theme="simple"/>
              
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="customer.region.customer.name.label" /></td>
                    <td align="left" class="value"><s:property value="region.customer.name"/></td>
              	</tr>
              	
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="customer.region.name.label" /></td>
                    <td align="left" class="value"><s:textfield name="regionName" maxlength="100" size="30" theme="simple"/></td>
				</tr>
                                  	
              	<tr class="editFormEvenRow">
              		<td colspan="2">
              			<br/>
						<table border="0" cellpadding="0" cellspacing="6" colspan="0" cellspan="0">
							<tr>
								<td align="center">
								<table border="0" cellpadding="3" cellspacing="1" colspan="0" cellspan="0" align="center">
                                   <thead>
									<tr class="editorTableHeader">
										<th><s:text name="customer.region.area.label" /></th>
                                        <th></th><th></th><th></th>
									</tr>
									</thead>
                                    <tbody>
									<s:iterator id="area" value="areas" status="itArea">
										<tr class="editorTable<s:if test="#itArea.even">Even</s:if><s:else>Odd</s:else>Row">
											<td class="value">
												<s:hidden name="areas[%{#itArea.index}].id"/>
												<s:textfield name="areas[%{#itArea.index}].name" value="%{name}" size="30" maxlength="150" theme="simple" />
											</td>
											<td>
												<a href="<s:url value="#" includeParams="none"/>" onclick="customerRegion_form.action='customerRegion_removeArea.action?index=<s:property value='#itArea.index'/>'; customerRegion_form.submit();">
													<img src="<s:url value="/images/delete.png" includeParams="none"/>" />
												</a>
														</td>
											<td>
											<s:if test="id != null">
												<a href="<s:url value="#" includeParams="none"/>" onclick="customerRegion_form.action='areaUser_add.action?paramId=<s:property value='id'/>'; customerRegion_form.submit();">
													<img src="<s:url value="/images/user_add.png" includeParams="none"/>" />
												</a>
											</s:if>
											<s:else>&nbsp;</s:else>
											</td>
											<td>
											<s:if test="id != null">
												<a href="<s:url value="#" includeParams="none"/>" onclick="customerRegion_form.action='areaUser_list.action?paramId=<s:property value='id'/>'; customerRegion_form.submit();">
													<img src="<s:url value="/images/usergroup.png" includeParams="none"/>" />
												</a>
											</s:if>
											<s:else>&nbsp;</s:else>
											</td>
										</tr>
									</s:iterator>
										<tr class="editorTableEvenRow">
											<td class="value">
												<s:textfield name="newAreaName" size="30" maxlength="150" theme="simple" />
											</td>
											<td>
  											  <a href="<s:url value="#" includeParams="none"/>" onclick="customerRegion_form.action='customerRegion_addArea.action'; customerRegion_form.submit();"><img
												src="<s:url value="/images/add.png" includeParams="none"/>" /></a>
											</td>
                                            <td>
											<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>" />
											</td><td>
											<img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>" />
											</td>
										</tr>
                                       </tbody>
									</table>
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
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="customer_edit" theme="simple" cssClass="button"/></td>		                    
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
          </table>