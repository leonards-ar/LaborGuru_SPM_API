<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script language="javascript" type="text/javascript">
</script>

<br/>
<br/>
<br/>
<br/>
<table border="0" cellspacing="2" cellpadding="2" align="center">
	<tr>
		<td id="titleBar"><s:text name="error.head" /></td>
	</tr>
	<tr>
		<td align="center" class="errorMessage">
			<s:text name="error.message" />
		</td>
	</tr>
	<tr>
		<td align="center">
			<br/>
		</td>
	</tr>	
	<tr>
		<td align="center" class="errorMessage">
			<s:text name="error.contact.message" />
		</td>
	</tr>
	<tr>
		<td align="center">
			<br/><br/>
		</td>
	</tr>	
	<tr>
		<td align="center">
			<table border="0" cellspacing="2" cellpadding="2" align="center" width="100%">
				<tr>
					<td align="center"><a href="javascript:history.back(1)" class="link"><s:text name="error.back.label" /></a></td>
					<td align="center"><a href="<s:url namespace="/home" action="home" includeParams="none"/>" class="link"><s:text name="error.home.label" /></a></td>
					<td align="center"><a href="<s:url namespace="/login" action="logout" includeParams="none"/>" class="link"><s:text name="error.logout.label" /></a></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!--
********* ERROR ***********
<s:property value="exceptionStack"/>

-->
