<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
	<head>
		<title><s:text name="application.title"/></title>
		<link rel="stylesheet" href="<s:url value="/css/style.css" includeParams="none" />" type="text/css" charset="utf-8" />
		<link rel="icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon">
		<link rel="shortcut icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon">
		<script language="javascript" type="text/javascript" src="<s:url value="/js/spmcommon.js" includeParams="none" />"></script>
		<tiles:insertAttribute name="htmlExtHeader" />
	</head>
	<body>
		<tiles:insertAttribute name="splash" />
		<div id="content" style="width: 100%; height: 100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="bodyTable" colspan="0" cellspan="0">
			<!-- Header and Menu -->
			<tr>
				<td id="headerRow"><tiles:insertAttribute name="header" /></td>
			</tr>
			<!-- Body -->
			<tr>
				<td id="bodyRow"><tiles:insertAttribute name="body" /></td>
			</tr>
			<!-- Footer -->
			<tr>
				<td id="footerRow"><tiles:insertAttribute name="footer" /></td>
			</tr>
		</table>
		</div>
	</body>
</html>
