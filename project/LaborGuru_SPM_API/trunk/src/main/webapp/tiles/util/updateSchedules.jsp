<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<table cellspacing="0" width="0">
<s:iterator id="shift" value="shifts">
	<tr>
		<td>
			<s:property value="id" />	
		</td>
		<td>
			<s:property value="fromHour" />	
		</td>
		<td>
			<s:property value="toHour" />	
		</td>
		<td>
			<s:property value="serviceHours" />	
		</td>
		<td>
			<s:property value="openingHours" />	
		</td>
		<td>
			<s:property value="closingHours" />	
		</td>
	</tr>
</s:iterator>
</table>
</html>