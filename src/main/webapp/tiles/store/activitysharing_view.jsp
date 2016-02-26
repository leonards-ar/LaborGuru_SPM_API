<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.laborassumptions.title" /> - <s:text name="store.laborassumptions.activitysharing.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0" align="center">
				<tr class="editFormEvenRow" style=" width : 297px;">
					<td>
				 		<s:set name="positionList" value="storePositions" scope="request"/>
						<display:table name="positionList" class="results" sort="external">		    
						    <display:column property="name" titleKey="store.laborassumptions.activitysharing.position.label"/>
						    <display:column property="positionGroup.name" titleKey="store.laborassumptions.activitysharing.sharingwith.label"/>
						    <display:setProperty name="paging.banner.all_items_found" value="" />
						    <display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="css.tr.even" value="editorTableEvenRow"/>
							<display:setProperty name="css.tr.odd" value="editorTableOddRow"/>
						</display:table>					
					</td>
				</tr>

              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
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
