<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<div id="splash" style="position:absolute; top:200px; z-index:10; visibility:hidden;" align="center">
	<table width="200px" class="waitTable" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td align="center" valign="middle">
				<table width="100%" align="center">
					<tr>
						<td align="center"><img src="<s:url value="/images/wait.gif" includeParams="none"/>" alt="<s:text name="wait.message"/>" title="<s:text name="wait.message"/>" border="0"/></td>
					</tr>
					<tr>
						<td align="center" valign="middle" class="waitMessage">
							<s:text name="wait.message"/>
						</td>
					</tr>
					<tr>
						<td id="extendedWaitMessage" class="extendedWaitMessage">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
