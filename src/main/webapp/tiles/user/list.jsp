<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>

<br />
<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<!-- Search Form --> 
		<s:form action="user_search" theme="simple">
		<td align="center">
			<table border="0" cellspacing="6" align="center" id="searchFormTable">
				<tr>
					
					<td class="form_label" nowrap><s:text name="user.firstname.label" /></td>
					<td><s:textfield name="searchUser.fullName" size="30" theme="simple" /></td>
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
 		<s:set name="usersList" value="users" scope="request"/>
		<display:table name="usersList" class="results" pagesize="5" requestURI="user_list.action" sort="list" defaultsort="1">		    
		    <display:column property="fullName" titleKey="user.fullname.label" sortable="true" />
		    <display:column property="email" titleKey="user.email.label" autolink="true"/>
			<authz:authorize ifAllGranted="VIEW_USER">
			    <display:column href="user_show.action" paramId="userId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="CREATE_USER">
			    <display:column href="user_edit.action" paramId="userId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="REMOVE_USER">		    
			    <display:column href="user_remove.action" paramId="userId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
		</display:table>			
		<!-- Search Results -->
		</td>
	</tr>
</table>
