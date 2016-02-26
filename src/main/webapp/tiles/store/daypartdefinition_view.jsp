<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<br />

<table border="0" cellspacing="0" align="center">
	
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storeoperations.daypartdefinition.title" /></td>
	</tr>

	<tr>
		<td align="center">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" align="center">
				<tr>
					<td>
				 		<s:set name="dayPartList" value="dayParts" scope="request"/>
						<display:table name="dayPartList" class="results" sort="external">		    
						    <display:column property="name" titleKey="store.storeoperations.daypartdefinition.daypart.label"/>
						    <display:column property="startHour" titleKey="store.storeoperations.daypartdefinition.starttime.label" format="{0,date,HH:mm}"/>
						    <display:setProperty name="paging.banner.all_items_found" value="" />
						    <display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="css.tr.even" value="editorTableEvenRow"/>
							<display:setProperty name="css.tr.odd" value="editorTableOddRow"/>
						</display:table>					
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
