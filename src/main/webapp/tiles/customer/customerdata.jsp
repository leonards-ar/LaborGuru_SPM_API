<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<table border="0" cellspacing="0" align="center">
	<tr>
		<td align="center">
		
			<table class="results">
				<thead>
					<tr>
						<th><s:text name="customer.region.customer.name.label" /></th>
					</tr>
				</thead>
				<tbody>
					<tr class="resultsTableOddRow">
						<td><s:property value="customer.name" /></td>
					</tr>
					<tr class="resultsTableEvenRow">
						<td><s:property value="customer.code" /></td>
					</tr>
				</tbody>
			</table>		

		</td>
	</tr>
</table>