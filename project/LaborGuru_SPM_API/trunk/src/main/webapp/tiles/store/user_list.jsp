<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<br />
<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<!-- Search Form --> 
		<s:form action="employeeStore_search" theme="simple">
        
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
		<display:table name="employeesList" class="results" pagesize="15" requestURI="employeeStore_list.action" sort="list" defaultsort="1">		    
		    <display:column property="fullName" titleKey="employee.fullname.label" sortable="true" />
		    <display:column property="email" titleKey="employee.email.label" autolink="true"/>
		    <display:column property="phone" titleKey="employee.phone.label" />
		    <display:column href="employeeStore_show.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered">
		    	<img src="<s:url value="/images/view.png" includeParams="none"/>"/>
		    </display:column>
		    <display:column href="employeeStore_edit.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered">
		    	<img src="<s:url value="/images/edit.png" includeParams="none"/>"/>
		    </display:column>		    
		    <display:column href="employeeStore_remove.action" paramId="employeeId" paramProperty="id" class="resultsColumnCentered"> 
		    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
		    </display:column>
		</display:table>			
		<!-- Search Results -->
		</td>
	</tr>
    <tr>
     <td align="right">
     <s:form theme="simple">
       <s:hidden name="storeId" theme="simple"/>
       <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0"><tr>
       <td>
         <s:submit action="employeeStore_add" key="employee.create.button" theme="simple" cssClass="button"/>
       </td>
       <td>
         <s:submit action="store_list" key="back.button" theme="simple" cssClass="button"/>
       </td>
       </tr>
       </table>
     </s:form>
     </td>
    </tr>
</table>
