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
		<s:form action="employee_search" theme="simple">
		<td align="center">
			<table border="0" cellspacing="6" align="center" id="searchFormTable">
				<tr>
					<td class="form_label" nowrap><s:text name="employee.fullname.label" /></td>
					<td><s:textfield name="searchEmployee.fullName" size="30" theme="simple" /></td>
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
 		<s:set name="employeesList" value="storeEmployees" scope="request"/>
		<display:table name="employeesList" class="results" pagesize="15" requestURI="employee_list.action" sort="list" defaultsort="1">		    
		    <display:column property="fullName" titleKey="employee.fullname.label" sortable="true" />
		    <display:column property="email" titleKey="employee.email.label" autolink="true"/>
		    <display:column property="phone" titleKey="employee.phone.label" />
            <authz:authorize ifAllGranted="VIEW_EMPLOYEE">
			    <display:column href="employee_show.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
			<authz:authorize ifAllGranted="EDIT_EMPLOYEE">
			    <display:column href="employee_edit.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered">
			    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>		    
			<authz:authorize ifAllGranted="REMOVE_EMPLOYEE">
			    <display:column href="employee_remove.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered"> 
			    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
			    </display:column>
			</authz:authorize>
		</display:table>			
		<!-- Search Results -->
		</td>
	</tr>
</table>
