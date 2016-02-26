<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
	<head>
		<title><s:text name="application.title"/></title>
		<script language="javascript" type="text/javascript" src="<s:url value="/js/spmcommon.js" includeParams="none" />"></script>
		<tiles:insertAttribute name="htmlExtHeader" />
		<link rel="stylesheet" href="<s:url value="/css/style.css" includeParams="none"/>" type="text/css" charset="utf-8" />
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="bodyTable" colspan="0" cellspan="0">
			<!-- Header -->
			<tr>
				<td id="popUpHeaderRow"><tiles:insertAttribute name="popUpHeader" /></td>
			</tr>			
			<!-- Body -->
			<tr>
				<td id="popUpbodyRow"><tiles:insertAttribute name="body" /></td>
			</tr>
			<!-- Footer -->
			<tr>
				<td id="popUpFooterRow"><tiles:insertAttribute name="popUpFooter" /></td>
			</tr>			
		</table>
	</body>
</html>