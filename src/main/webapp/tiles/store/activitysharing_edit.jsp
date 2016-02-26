<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
              <s:form action="storeActivitySharing_save" theme="simple">
              <s:hidden name="storeId" theme="simple"/>
<table border="0" cellspacing="0" align="center">


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
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.activitysharing.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow">
					<td>
						<table border="0" cellpadding="3" cellspacing="1" align="center">
							<tr class="editorTableHeader">
								<td><s:text name="store.laborassumptions.activitysharing.position.label" /></td>
								<td><s:text name="store.laborassumptions.activitysharing.sharingwith.label" /></td>
							</tr>
							<s:iterator id="position" value="storePositions" status="itPosition">
								<tr>
								<td class="editorTableFirstColumn">
								<s:hidden name="storePositions[%{#itPosition.index}].id" theme="simple"/>
								<s:hidden name="storePositions[%{#itPosition.index}].name" theme="simple"/>
								<s:property value="%{name}"/>
								</td>
								<td class="editorTable<s:if test="#itPosition.even">Even</s:if><s:else>Odd</s:else>Row">
								<s:select name="positionPositionGroups[%{#itPosition.index}]" list="positionGroups" listKey="name" listValue="name" headerKey="" headerValue="%{getText('store.activitySharing.search.position.header.label')}" theme="simple" />
								</td>
							</s:iterator>							
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
		</td>
	</tr>
</table>
</s:form>