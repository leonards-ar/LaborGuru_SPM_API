<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<s:select id="selectItem" name="itemId" list="itemsMap" listKey="key" listValue="%{getText(value)}" headerKey="" headerValue="%{getText('report.allItems.label')}" onchange="controller.refresh()" theme="simple" disabled="%{disabled}"/>
