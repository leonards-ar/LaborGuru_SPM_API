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
						<th><s:text name="customer.region.label" /></th>
                        <th><s:text name="customer.region.area.label" /> </th>
					</tr>
				</thead>
				<tbody>
					<tr class="resultsTableOddRow">
						<td><s:property value="area.region.customer.name" /></td>
						<td><s:property value="area.region.name" /></td>
						<td><s:property value="area.name" /></td>
					</tr>
				</tbody>
			</table>		

		</td>
	</tr>
</table>