<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
	<head>
		<title><s:text name="application.title"/></title>
		<tiles:insertAttribute name="htmlExtHeader" />
		<link rel="stylesheet" href="<s:url value="/css/style.css" includeParams="none"/>" type="text/css" charset="utf-8" />
		<link rel="stylesheet" href="<s:url value="/css/print_style.css" includeParams="none"/>" type="text/css" charset="utf-8" media="print"/>
		<link rel="icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon">
		<link rel="shortcut icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon"> 
		<script language="javascript" type="text/javascript" src="<s:url value="/js/spmcommon.js" includeParams="none" />"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</body>
</html>
