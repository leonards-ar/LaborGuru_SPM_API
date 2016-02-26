<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 

<table width="100%" border="0" cellpadding="0" cellspacing="0" id="headerTable" colspan="0" cellspan="0">
	<tr>
		<td id="headerTop" valign="bottom">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" colspan="0" cellspan="0">
				<tr>
					<td width="20%">
						<img src="<s:url value="/images/logo.png" includeParams="none"/>"/>
					</td>
					<td width="60%" valign="bottom" align="center">
						<table border="0" cellpadding="0" cellspacing="0" id="menuTable"
							colspan="0" cellspan="0">
							<tr>
							<s:iterator value="#session.spmMenu.items" status="itCtx">
								<s:if test="#session.spmMenu.selectedItemIndex == #itCtx.index">
								<td class="selectedMenuItem"><s:text name="%{labelKey}"/></td>
								</s:if>
								<s:else>
								<td class="availableMenuItem"
									onmouseover="className='availableMenuItemOver'"
									onmouseout="className='availableMenuItem'">
									<a onclick="return showWaitSplash();" href="<s:url value="%{menuTarget}" includeParams="none"><s:param name="menuItemIndex" value="#itCtx.index" /></s:url>"
									class="availableMenuItemAnchor"><s:text name="%{labelKey}"/></a></td>						
								</s:else>
								<s:if test="!#itCtx.last">
								<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
								</s:if>						
							</s:iterator>
							</tr>
						</table>
					</td>
					<td width="20%" valign="top" align="right">
						<table width="100%" border="0" cellpadding="1" cellspacing="1">
							<tr>
								<td class="headerTopMessage" align="right">
									<s:text name="home.greeting"><s:param value="#session.spmUser.fullName"/></s:text>
								</td>
							</tr>
							<tr>
								<td align="right" class="headerTopMessage"><a class="logoutLink" href="<s:url namespace="/login" action="logout"  includeParams="none"/>"><s:text name="logout.label"/></a></td>
							</tr>
							<tr>
								<td align="right" class="headerTopMessage">
									<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><a href="<s:url><s:param name="request_locale" value="%{'en'}" /></s:url>"><img height="18px" src="<s:url value="/images/english.gif" includeParams="none"/>"/></a></td>
										<td><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/><img src="<s:url value="/images/transp2x1.gif" includeParams="none"/>"/></td>
										<td><a href="<s:url><s:param name="request_locale" value="%{'es'}" /></s:url>"><img height="18px" src="<s:url value="/images/spanish.gif" includeParams="none"/>"/></a></td>
									</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td id="headerBottom">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				colspan="0" cellspan="0">
				<tr>
					<td width="100%" align="center">
					<!-- Submenu -->
					<table border="0" cellpadding="0" cellspacing="0" id="subMenuTable"
						colspan="0" cellspan="0">
						<tr>
						<s:iterator value="#session.spmMenu.selectedItem.orderedChildMenuItems" status="itCtx">
							<s:if test="#session.spmMenu.selectedSubItemIndex == #itCtx.index">
							<td class="selectedSubMenuItem"><a onclick="return showWaitSplash();" href="<s:url value="%{menuTarget}" includeParams="none"><s:param name="subMenuItemIndex" value="#itCtx.index" /></s:url>" class="subMenuSelectedItemAnchor"><s:text name="%{labelKey}"/></a></td>
							</s:if>
							<s:else>
							<td class="subMenuItem"><a onclick="return showWaitSplash();" href="<s:url value="%{menuTarget}" includeParams="none"><s:param name="subMenuItemIndex" value="#itCtx.index" /></s:url>" class="subMenuItemAnchor"><s:text name="%{labelKey}"/></a></td>
							</s:else>
							<s:if test="!#itCtx.last">
							<td>|</td>
							</s:if>
						</s:iterator>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
