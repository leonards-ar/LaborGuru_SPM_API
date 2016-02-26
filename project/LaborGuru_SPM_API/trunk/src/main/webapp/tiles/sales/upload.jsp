<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<br/>
<table border="0" cellspacing="0" align="center">
    <s:form name="salesUpload_form" id="salesUpload_form" action="sales_upload" theme="simple" method="post" enctype="multipart/form-data">
	<s:hidden name="storeId"/>
	<tr>
		<td>
	    <table border="0" cellspacing="0" align="center">
		      <tr>
			      <td id="titleBar">
			      	<s:text name="sales.upload.title" />
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
			<table border="0" cellpadding="3" cellspacing="1" align="center">
				<tr class="editorTableEvenRow">
                    <td align="right" class="form_label" nowrap>* <s:text name="sales.filename.label" /></td>
					<td class="value"><s:file name="salesFile" size="30"  theme="simple" /></td>
				</tr>
				<s:if test="numberOfRecordsAdded > 0">
					<tr class="editorTableOddRow">
						<td>&nbsp;</td>
	                    <td align="left" class="form_label" nowrap>
						<s:if test ="numberOfRecordsWithError > 0">
	                    	<s:text name="sales.upload.partialsuccess.message">
	                    		<s:param value ="salesFileFileName"/>
	                    		<s:param value ="numberOfRecordsAdded"/>
	                    		<s:param value ="numberOfRecordsWithError"/>
	                    	</s:text>
						</s:if>
						<s:else>							
	                    	<s:text name="sales.upload.success.message">
	                    		<s:param value ="salesFileFileName"/>
	                    		<s:param value ="numberOfRecordsAdded"/>
	                    	</s:text>
						</s:else>
						</td>
					</tr>
				</s:if>
			</table>
		</td>
	</tr>
	<br/>	
	</tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
              	<tr class="editFormOddRow">
                    <td width="100%" align="right" colspan="2">
	                    <table border="0" cellpadding="1" cellspacing="5">
		                    <tr>
		                		<td><s:submit id="uploadButton" key="upload.button" action="sales_upload" theme="simple" cssClass="button"/></td>
		                    	<td><s:submit id="cancelButton" key="cancel.button" action="sales_cancel" theme="simple" cssClass="button"/></td>		                    
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
	<tr>
		<td>&nbsp;</td>
	</tr>
	<!-- Search and results separator -->
	<tr>
		<td><br/></td>
	</tr>
	<s:if test="uploadFileRemoved">
		<tr class="editorTableOddRow">
            <td class="form_label" nowrap>
                <s:text name="sales.removed.success.message">
                	<s:param value ="salesFileRemovedName"/>
                </s:text>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</s:if>
	<tr>
		<td align="center">
		<!-- Search Results -->	
 		<s:set name="uploadFileListDisplayTagsAux" value="uploadFileList" scope="request"/>
		<display:table name="uploadFileListDisplayTagsAux" class="results" pagesize="20" requestURI="sales_edit.action" sort="list" defaultsort="1" defaultorder="descending">		    
		    <display:column property="uploadDate" titleKey="uploadFile.uploadDate.label" sortable="true" format="{0,date,MM/dd/yyyy h:mm:ss a}"/>
		    <display:column property="filename" titleKey="uploadFile.filename.label"/>
			<display:column property="historicSalesSize" titleKey="uploadFile.salesRecordsSize.label" class="resultsColumnCentered"/>
		    <display:column href="sales_remove.action" paramId="uploadFileId" paramProperty="id" class="resultsColumnCentered"> 
		    	<img src="<s:url value="/images/delete.png" includeParams="none"/>"/>
		    </display:column>
			<display:setProperty name="basic.empty.showtable" value="true"/>
		</display:table>			
		<!-- Search Results -->
		</td>
	</tr>
</table>
