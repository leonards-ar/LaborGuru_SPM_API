<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />

<table border="0" cellspacing="0" align="center">
	
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.positiongroupnames.title" /></td>
	</tr>

	<tr>
		<td align="center">
				<tr class="editFormEvenRow" style=" width : 297px;">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td><s:text name="store.storeoperations.positiongroupnames.label" /></td>
							</tr>
							<s:iterator id="positionGroup" value="positionGroups" status="itPositionGroups">
								<tr class="editorTable<s:if test="#itPositionGroups.even">Even</s:if><s:else>Odd</s:else>Row">
									<td class="value"><s:property value="%{name}"/></td>
								</tr>
							</s:iterator>
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
		</td>
	</tr>
</table>
