<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" cellspacing="0" align="center">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<!-- Search Form --> 
		<s:form action="%{getText(actionName)}_search" theme="simple">
        <s:hidden name="paramId"/>
		<td align="center">
			<table border="0" cellspacing="6" align="center" id="searchFormTable">
				<tr>
					<td class="form_label" nowrap><s:text name="employee.fullname.label" /></td>
					<td><s:textfield name="searchManager.fullName" size="30" theme="simple" /></td>
					<td><s:submit key="search.button" theme="simple" cssClass="button" /></td>
				</tr>
			</table>
		</td>
		</s:form> <!-- End Search Form -->
	</tr>

	<!-- Search and results separator -->
	<tr>
		<td><br/></td>
	</tr>

	<tr>
		<td align="center">
		<!-- Search Results -->
	
 		<s:set name="userList" value="users" scope="request"/>
        
		<display:table name="userList" class="results" pagesize="5" requestURI="areaUser_list.action" sort="list" defaultsort="1">		    
		    <display:column property="fullName" titleKey="user.fullname.label" sortable="true" />
		    <display:column property="email" titleKey="user.email.label" autolink="true"/>
		    <display:column href="areaUser_show.action" paramId="userId" paramProperty="id" class="resultsColumnCentered">
		    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
		    </display:column>
		    <display:column href="areaUser_edit.action" paramId="userId" paramProperty="id" class="resultsColumnCentered">
		    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
		    </display:column>		    
		    <display:column href="areaUser_remove.action" paramId="userId" paramProperty="id" class="resultsColumnCentered"> 
		    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
		    </display:column>
		</display:table>			
		<!-- Search Results -->
		</td>
	</tr>
    <tr>
     <td align="right">
     <s:form theme="simple">
	   <s:hidden name="paramId" theme="simple"/>
       <s:hidden name="regionId" value="%{getText(area.region.id)}" theme="simple"/>
       <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0"><tr>
       <td>
         <s:submit action="%{getText(actionName)}_add" key="employee.create.button" theme="simple" cssClass="button"/>
       </td>
       <td>
         <s:submit action="%{getText(previousActionName)}_edit" key="back.button" theme="simple" cssClass="button"/>
       </td>
       </tr>
       </table>
     </s:form>
     </td>
    </tr>
</table>
