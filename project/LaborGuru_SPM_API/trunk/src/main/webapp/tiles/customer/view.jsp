<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>
<table border="0" cellspacing="0" align="center">
	<tr>
		<td>
	    <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
                    	<s:if test="removePage">
                    		<s:text name="customer.remove.title" />
                    	</s:if>
                    	<s:else>
                    		<s:text name="customer.view.title" />
                    	</s:else> 
			      </td>
        	  </tr>
             
              <tr>                            
              <td align="center">              
              <table id="editFormTable" border="0" cellpadding="6" cellspacing="0" align="center">
              	<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="customer.name.label" /></td>
                    <td align="left" class="value"><s:property value="customer.name"/></td>
				</tr>
              	<tr class="editFormEvenRow">
                    <td align="right" class="form_label" nowrap><s:text name="customer.code.label" /></td>
                    <td align="left" class="value"><s:property value="customer.code"/></td>
				</tr>
              </table>
              </tr>                            
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<br/>
			<table border="0" cellpadding="0" cellspacing="6" colspan="0" cellspan="0">
				<tr>
					<td align="center">
					<table border="0" cellpadding="3" cellspacing="1" colspan="0" cellspan="0" align="center">
						<tr class="editorTableHeader">
							<td><s:text name="customer.region.label" /></td>
							<td><s:text name="customer.region.area.label" /></td>
						</tr>
		
						<s:iterator id="region" value="customer.regions" status="itRegion">
							<tr class="editorTable<s:if test="#itRegion.even">Even</s:if><s:else>Odd</s:else>Row">
								<td class="value" valign="top">
									<s:property value="name"/>
								</td>
								<td>
									<table border="0" cellpadding="2" cellspacing="2" colspan="0" cellspan="0">
										<s:iterator id="area" value="areas" status="itArea">
										<tr>
											<td class="value">
												<s:property value="name"/>
											</td>
										</tr>
										</s:iterator>
									</table>
								</td>
							</tr>
						</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>

   	<tr class="editFormOddRow">
         <td width="100%" align="right" colspan="2">
          <s:form theme="simple"> 
          <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
           <tr>
           	<s:if test="removePage">
          		<s:hidden name="customer.id"/>
           		<td><s:submit action="customer_list" key="cancel.button" theme="simple" cssClass="button"/></td>    
         			<td><s:submit action="customer_delete" key="remove.button" theme="simple" cssClass="button"/></td>
           	</s:if>
           	<s:else>
           		<td><s:submit action="customer_list" key="back.button" theme="simple" cssClass="button"/></td>
           	</s:else> 
           </tr>
          </table>
          </s:form>                       
         </td>
     </tr>

</table>
