<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<br/>
<s:form action="projectionSettings_save" theme="simple">
<s:hidden name="storeId" theme="simple"/>
<table border="0" cellspacing="0" align="center">
	<tr>
		<td id="subtitleBar" nowrap><s:text name="store.storeoperations.title" /> - <s:text name="store.storesettings.projections.title" /></td>
	</tr>

	<tr>
		<td align="left">
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
				<tr class="editFormOddRow">
                    <td align="right" class="form_label" nowrap><s:text name="store.storesettings.distributiontype.label" /></td>
                    <td align="left" class="value">
                    	<s:select name="distributionType" list="{'HISTORIC_AVG','STATIC'}" listValue="%{getText('distributionType.'+toString())}"/>
                    </td>
				</tr>				
			</table>
		</td>
	</tr>

   	<tr class="editFormOddRow">
         <td width="100%" align="right" colspan="2">
          <table border="0" cellpadding="1" cellspacing="5" colspan="0" cellspan="0">
           <tr>
           	<td><s:submit id="saveButton" key="save.button" theme="simple" cssClass="button"/></td>
           	<td><s:submit id="cancelButton" key="cancel.button" action="store_edit" theme="simple" cssClass="button"/></td>		                    
           	<td><s:reset id="resetButton" key="reset.button" theme="simple" cssClass="button"/></td>
           </tr>
          </table>                    
         </td>
     </tr>
</table>
</s:form>