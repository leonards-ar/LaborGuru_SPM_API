<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<br/>
<table border="0" cellspacing="0" align="center">
    <s:form name="uploadDefinition_form" id="uploadDefinition_form" action="uploadDefinition_upload" theme="simple" method="post" enctype="multipart/form-data">
	<tr>
		<td>
	    <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="employee.uploademployeesdefinition.title" />
			      </td>
        	  </tr>      
              <tr>
              	<td class="errorMessage" align="center">
	              	<table border="0" align="center" cellpadding="0" cellspacing="0">
              			<tr>
              				<td>
				              	<s:fielderror theme="simple"/>
				              	<s:actionerror theme="simple"/>
								<s:actionmessage theme="simple"/>
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
			<table border="0" cellpadding="3" cellspacing="1" align="left">
				<tr class="editorTableEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="employee.uploademployeesdefinition.filename.label" /></td>
					<td class="value"><s:file name="employeeDefinition" size="30"  theme="simple" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0" align="left">
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5">
		                    <tr>
		                		<td><s:submit id="uploadButton" key="upload.button" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="uploadDefinition_cancel" theme="simple" cssClass="button"/></td>		                    
		                    </tr>
	                    </table>                    
                    </td>
                </tr>

		      <tr>
			      <td colspan="2" class="infoMessage"><s:text name="info.mandatory.fields" /></td>
        	  </tr>	  

           </table>
		</td>
	</tr>
	</s:form>
</table>
