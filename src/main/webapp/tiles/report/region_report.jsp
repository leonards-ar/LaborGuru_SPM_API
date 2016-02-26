<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<table border="0" cellspacing="0" align="center">
  <tr>
    <td id="titleBar"><s:text
      name="report.regional.title" /></td>
  </tr>
  <tr>
    <td class="errorMessage" align="center">
    <table border="0" align="center" cellpadding="0" cellspacing="0"
      colspan="0" cellspan="0">
      <tr>
        <td><s:fielderror theme="simple" /> <s:actionerror
          theme="simple" /></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td align="center">
    <table border="0" cellspacing="0" align="center">
      <tr>
        <td><s:form id="regionalReport_form"
          name="regionalReport_form" theme="simple" method="post"
          action="regionalReport_showReport">
          <table border="0" cellspacing="0" align="center">
            <tr>
              <td align="right" class="form_label"><s:text
                name="report.reportType.label" /></td>
              <td align="left"><s:select name="selectView" list="reportTypes"
                listKey="name" listValue="%{getText(title)}" theme="simple" /></td>
              <td align="right" class="form_label"><s:text
                name="report.historicalComparison.startDate.label" /></td>
              <td><s:datetimepicker id="startDate" name="startDate"
                adjustWeeks="true" displayFormat="%{getText('datepicker.format.date')}" theme="simple" /></td>
              <td align="right" class="form_label"><s:text
                name="report.historicalComparison.endDate.label" /></td>
              <td><s:datetimepicker id="endDate" name="endDate"
                adjustWeeks="true" displayFormat="%{getText('datepicker.format.date')}" theme="simple" /></td>
            </tr>
          </table></td>
      </tr>
      <tr>
        <td align="right"><s:submit id="submit"
          key="report.historicalComparison.submit.label"
          cssClass="button" loadingText='%{getText("wait.message")}'
          title="wait.message" type="button" theme="ajax"
          targets="tableFrame" indicator="regionalReportIndicator"
          formId="regionalReport_form" /></td>
      </tr>
      </s:form>
      </td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
    <tr>
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><s:div id="tableFrame" theme="ajax"
      indicator="regionalReportIndicator" /></td>
  </tr>

</table>

<center><img id="regionalReportIndicator"
  style="display: none;"
  src="<s:url value="/images/wait.gif" includeParams="none"/>"
  alt="<s:text name="wait.message"/>"
  title="<s:text name="wait.message"/>" border="0" /></center>

<script language="javascript" type="text/javascript">
  djConfig.searchIds.push("startDate");
  djConfig.searchIds.push("endDate");
  djConfig.searchIds.push("tableFrame");
  djConfig.searchIds.push("submit");
  //djConfig.searchIds.push("historicalComparison_form");
</script>
