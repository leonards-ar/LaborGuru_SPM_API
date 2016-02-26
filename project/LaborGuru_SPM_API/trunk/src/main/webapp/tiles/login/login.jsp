<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@page import="com.mindpool.security.exceptions.UnknownUserException"%>
<%@page import="com.mindpool.security.exceptions.BadPasswordException"%>
<%@page import="com.mindpool.security.exceptions.UserDisabledException"%>
<%@page import="com.mindpool.security.exceptions.PasswordExpiredException"%>

<script language="javascript" type="text/javascript">
	function forgotPassword() {
		if(confirm('<s:text name="login.forgot_password.confirm.message" />')) {
			document.login.action = '<s:url namespace="/login" action="forgotpassword" includeParams="none"/>';
			document.login.username.value = document.login.j_username.value;
			document.login.submit();
		}
	}
	
	function createDemoAccount() {
		document.login.action = '<s:url namespace="/demo" action="stepOne_execute" includeParams="none"/>';
		document.login.submit();
	}
</script>

<br/>
<br/>
<br/>
<br/>
<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="titleBar"><s:text name="login.head" /></td>
	</tr>
	<tr>
		<td align="center">
		<form action="<s:url value="/j_acegi_security_check"/>" method="post" name="login">
		<input id="username" name="username" type="hidden"/>
		<table border="0" cellspacing="6" align="center" id="loginFormTable">
			<tr>
				<td class="form_label"><s:text name="user.username" /></td>
				<td><input type="text" name="j_username" maxlength="50" style="{width: 150px}"/></td>
			</tr>
			<tr>
			<tr>
				<td class="form_label"><s:text name="user.password" /></td>
				<td><input type="password" name="j_password" maxlength="50" style="{width: 150px}"/></td>
			</tr>
			<%
				if (request.getParameter("login_error") != null) {
					Throwable e = (Throwable) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY);
					String exceptionMessage = "error.unknown";
				    
				    if(e.getCause() != null) {
				    	if(e.getCause() instanceof UnknownUserException) {
				    		exceptionMessage = "error.username.invalid";
				    	} else if(e.getCause() instanceof BadPasswordException) {
				    		exceptionMessage = "error.password.invalid";
				    	} else if(e.getCause() instanceof UserDisabledException) {
				    		exceptionMessage = "error.user.disabled";
				    	} else if(e.getCause() instanceof PasswordExpiredException) {
				    		exceptionMessage = "error.password.expired";
				    	}
				    }
				    
				    request.setAttribute("exceptionMessage", exceptionMessage);
			%>
			<tr>
				<td colspan="2" align="center">
					<span class="errorMessage"><s:text name="%{#request.exceptionMessage}"/></span>
				</td>
			</tr>
			<%
				}
			%>
			<tr>
				<td align="right" colspan="2"><input type="submit" value="<s:text name="login.submit"/>" class="button" /></td>
			</tr>
			<tr>
				<td align="left" colspan="2"><a href="#" onClick="forgotPassword();" class="link"><s:text name="login.forgot_password.label"/></a></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<table border="0" align="center" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
						<tr>
							<td class="errorMessage">
								<s:actionerror theme="simple" />
							</td>
						</tr>
						<tr>
							<td class="actionMessage">
								<s:actionmessage theme="simple"/>
							</td>
						</tr>
					</table>				
				</td>
			</tr>

		</table>
		</form>
		</td>
	</tr>
</table>
<script language="javascript" type="text/javascript">
<!--
document.login.j_username.focus();
// -->
</script>