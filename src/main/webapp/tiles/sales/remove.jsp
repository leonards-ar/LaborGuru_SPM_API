<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<br/>
<table border="0" cellspacing="0" align="center">
    <s:form name="upload_form" id="upload_form" action="sales_delete" theme="simple" method="post">
	<s:hidden name="storeId"/>
	<s:hidden name="uploadFileId"/>
	<tr>
		<td>
	    <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="upload.remove.title" />
			      </td>
        	  </tr>      
              <tr>
              	<td class="errorMessage" align="center">
	              	<table border="0" align="center" cellpadding="0" cellspacing="0">
              			<tr>
              				<td>
				              	<s:fielderror theme="simple"/>
				              	<s:actionerror theme="simple"/>
			              	</td>
            		  	</tr>
              		</table>
              	</td>
              </tr>                                     
		</table>
		</td>
	</tr>
	<tr>
		<td>
			<table id="editFormTable" border="0" cellpadding="6" cellspacing="0" colspan="0" cellspan="0">
			  	<tr class="editFormEvenRow">
			        <td class="form_label" nowrap><s:text name="uploadFile.uploadDate.label" /></td>
			        <td class="value"><s:property value="uploadFileSelected.uploadDate"/></td>
			    </tr>
			  	<tr class="editFormOddRow">
			        <td class="form_label" nowrap><s:text name="uploadFile.filename.label" /></td>
			        <td class="value"><s:property value="uploadFileSelected.filename"/></td>
			    </tr>
			  	<tr class="editFormEvenRow">
			        <td class="form_label" nowrap><s:text name="uploadFile.salesRecordsSize.label" /></td>
			        <td class="value"><s:property value="uploadFileSelected.historicSalesSize"/></td>
			    </tr>			    
			</table>
		</td>
	</tr>
	<tr>
		<td>
			</br>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5">
		                    <tr>
		                		<td><s:submit id="removeButton" key="remove.button" action="sales_delete" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="sales_edit" theme="simple" cssClass="button"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr> 
           </table>
		</td>
	</tr>
	</s:form>
</table>
