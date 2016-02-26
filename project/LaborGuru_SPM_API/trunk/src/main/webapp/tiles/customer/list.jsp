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
		<s:form action="customer_search" theme="simple">
		<td align="center">
			<table border="0" cellspacing="6" align="center" id="searchFormTable">
				<tr>
					<td class="form_label" nowrap="true"><s:text name="customer.name.label" /></td>
					<td><s:textfield name="customerSearch.name" size="30" theme="simple" /></td>
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
		<s:set name="customersList" value="customers" scope="request"/>
		<display:table name="customersList" class="results" pagesize="20" requestURI="customer_list.action" sort="list" defaultsort="1">
			<display:column property="id" titleKey="customer.id.label" sortable="true"/>
		    <display:column property="code" titleKey="customer.code.label" sortable="true"/>
		    <display:column property="name" titleKey="customer.name.label" sortable="true"/>
		    <authz:authorize ifAllGranted="VIEW_CUSTOMER">
			    <display:column href="customer_show.action" paramId="customerId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="EDIT_CUSTOMER">
			    <display:column href="customer_edit.action" paramId="customerId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="REMOVE_CUSTOMER">
			    <display:column href="customer_remove.action" paramId="customerId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="ADD_MANAGER_CUSTOMER">
			    <display:column href="customerUser_add.action" paramId="paramId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/user_add.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAnyGranted="ADD_MANAGER_CUSTOMER,VIEW_MANAGER_CUSTOMER,EDIT_MANAGER_CUSTOMER,REMOVE_MANAGER_CUSTOMER">
			    <display:column href="customerUser_list.action" paramId="paramId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/usergroup.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>

		</display:table>
		<!-- Search Results -->
		</td>
	</tr>
</table>
