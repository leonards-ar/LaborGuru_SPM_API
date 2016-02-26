<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<br />
<table align="center" border="0" cellspacing="2" cellpadding="2">
	<tr>
		<td class="errorSecurityTitle" align="center">
			<s:text	name="error.security.accessdenied.title" />
		</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td class="errorSecurityMessage" align="center">
			<s:text name="error.security.accessdenied.message" />
		</td>
	</tr>

	<tr>
		<td>
			<br />
			<br />
		</td>
	</tr>

	<tr>
		<td align="center">
			<a class="link" href="javascript:history.back(1);"><s:text name="error.security.back.label" /></a>
		</td>
	</tr>
</table>