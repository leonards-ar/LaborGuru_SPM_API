<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="s"
uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
	<head>
		<title><s:text name="application.title" /></title>
		<link rel="icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon">
		<link rel="shortcut icon" href="<s:url value="/images/favicon.ico" includeParams="none" />" type="image/x-icon">
		<script language="javascript" type="text/javascript" src="<s:url value="/js/spmcommon.js" includeParams="none" />"></script>
		
		<script language="JavaScript" type="text/javascript">
		<!--
		//breakout_of_frame();
		-->
		</script>
	</head>

	<frameset rows="100%" cols="100%">
		<frame src="<s:url value="/home/home.action"  includeParams="none"/>"/>
	</frameset>
</html>