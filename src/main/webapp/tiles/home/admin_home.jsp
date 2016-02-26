<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<br/>
<table border="0" cellspacing="0" align="center">
	<tr>
		<td>
			<table border="0" cellpadding="2" cellspacing="2" width="100%">
				<tr>
					<td valign="top">
						<!-- Left column -->
						<table style="border: 1px solid #D6DE26;" cellspacing="0" cellspacing="0">
							<tr>
								<td align="center" class="windowTableHeader"><s:text name="home.admin.store.title"/></td>
							</tr>
							<tr>
								<td align="center">
									<table border="0" cellspacing="3" cellpadding="3">
										<tr>
											<td class="windowTableTitle"><s:text name="home.admin.store.latest.title"/></td>
										</tr>
										<tr>
											<td align="center">
					 							<s:set name="latestStores" value="latestStores" scope="request"/>
												<display:table name="latestStores" sort="list" defaultsort="3" defaultorder="descending" cellpadding="3" cellspacing="3">
												    <display:column headerClass="homeAdminGreyTableLabel" property="area.region.customer.name" titleKey="home.admin.store.customer.label"/>
												    <display:column headerClass="homeAdminGreyTableLabel" property="name" titleKey="home.admin.store.store.label"/>
												    <display:column headerClass="homeAdminGreyTableLabel" property="lastUpdateDate" titleKey="home.admin.store.lastupdate.label" format="{0,date,MM/dd/yyyy h:mm:ss a}"/>
													<display:setProperty name="basic.empty.showtable" value="true"/>
													<display:setProperty name="paging.banner.one_item_found" value=""/>
													<display:setProperty name="paging.banner.all_items_found" value=""/>
													<display:setProperty name="css.tr.even" value="homeAdminTableEvenRow"/>
													<display:setProperty name="css.tr.odd" value="homeAdminTableOddRow"/>
												</display:table>
											</td>
										</tr>
										<tr>
											<td class="windowTableTitle"><s:text name="home.admin.store.oldest.title"/></td>
										</tr>
										<tr>
											<td align="center">
					 							<s:set name="oldestStores" value="oldestStores" scope="request"/>
												<display:table name="oldestStores" sort="list" defaultsort="3" defaultorder="ascending" cellpadding="3" cellspacing="3">		    
												    <display:column headerClass="homeAdminGreyTableLabel" property="area.region.customer.name" titleKey="home.admin.store.customer.label"/>
												    <display:column headerClass="homeAdminGreyTableLabel" property="name" titleKey="home.admin.store.store.label"/>
												    <display:column headerClass="homeAdminGreyTableLabel" property="lastUpdateDate" titleKey="home.admin.store.lastupdate.label" format="{0,date,MM/dd/yyyy h:mm:ss a}"/>
													<display:setProperty name="basic.empty.showtable" value="true"/>
													<display:setProperty name="paging.banner.one_item_found" value=""/>
													<display:setProperty name="paging.banner.all_items_found" value=""/>
													<display:setProperty name="css.tr.even" value="homeAdminTableEvenRow"/>
													<display:setProperty name="css.tr.odd" value="homeAdminTableOddRow"/>
												</display:table>
											</td>
										</tr>										
									</table>
								</td>
							</tr>
						</table>
					</td>
					<!-- end Left column -->
					<!-- Right column -->
					<td valign="top" align="right">
						<table style="border: 1px solid #D6DE26;" cellspacing="0" cellspacing="0">
							<tr>
								<td align="center" class="windowTableHeader"><s:text name="home.admin.system.title"/></td>
							</tr>
							<tr>
								<td align="center">
									<table border="0" cellspacing="5" cellpadding="5">
										<tr>
											<td class="windowTableLeftLabel"><s:text name="home.admin.system.client.label"/></td>
											<td class="windowTableValue" ><s:property value="numberOfCustomers"/></td>
										</tr>
										<tr>
											<td class="windowTableLeftLabel"><s:text name="home.admin.system.store.label"/></td>
											<td class="windowTableValue"><s:property value="numberOfStores"/></td>
										</tr>
										<tr>
											<td class="windowTableLeftLabel"><s:text name="home.admin.system.user.total.label"/></td>
											<td class="windowTableValue"><s:property value="totalUsers"/></td>
										</tr>
										<tr>
											<td class="windowTableLeftLabel"><s:text name="home.admin.system.user.enabled.label"/></td>
											<td class="windowTableValue"><s:property value="enabledUsers"/></td>
										</tr>
									</table>						
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>		