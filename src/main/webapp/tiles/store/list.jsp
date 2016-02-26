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
		<s:form action="store_search" theme="simple">
		<td align="center">
			<table border="0" cellspacing="6" align="center" id="searchFormTable">
				<tr>
					<td class="form_label" nowrap><s:text name="store.code.label" /></td>
					<td><s:textfield name="searchStore.code" size="10" theme="simple" /></td>
					<td class="form_label" nowrap><s:text name="store.name.label" /></td>
					<td><s:textfield name="searchStore.name" size="20" theme="simple" /></td>
					<td class="form_label" nowrap><s:text name="store.customer.label" /></td>
					<td>
						<s:select name="searchStore.customerId" list="customers" headerKey="" headerValue="%{getText('store.search.customer.header.label')}" listKey="id" listValue="name" theme="simple"/>
					</td>
					<td><s:submit key="search.button" theme="simple" cssClass="button" /></td>
				</tr>
			</table>
		</td>
		</s:form>
		<!-- End Search Form -->
	</tr>

	<!-- Search and results separator -->
	<tr>
		<td><br/></td>
	</tr>

	<tr>
		<td align="center">
		<!-- Search Results -->
		<s:set name="storesList" value="stores" scope="request"/>
		<display:table name="storesList" class="results" pagesize="20" requestURI="store_list.action" sort="list" defaultsort="1">
			<display:column property="area.region.customer.name" titleKey="store.customer.label" sortable="true"/>
			<display:column property="code" titleKey="store.code.label" sortable="true" />
		    <display:column property="name" titleKey="store.name.label" sortable="true"/>
		    <authz:authorize ifAllGranted="VIEW_STORE">
			    <display:column href="store_show.action" paramId="storeId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="EDIT_STORE">
			    <display:column href="store_edit.action" paramId="storeId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="REMOVE_STORE">
			    <display:column href="store_remove.action" paramId="storeId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="ADD_EMPLOYEE_STORE">
			    <display:column href="employeeStore_add.action" paramId="storeId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/user_add.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAnyGranted="LIST_EMPLOYEE_STORE,ADD_EMPLOYEE_STORE,VIEW_EMPLOYEE_STORE,EDIT_EMPLOYEE_STORE,REMOVE_EMPLOYEE_STORE">
			    <display:column href="employeeStore_list.action" paramId="storeId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/usergroup.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
		</display:table>
		<!-- Search Results -->
		</td>
	</tr>
</table>
