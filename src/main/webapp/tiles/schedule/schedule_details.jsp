<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:form id="scheduledetails_form" name="scheduledetails_form" action="showscheduledetails" theme="simple">
<s:hidden name="position.id"/>
<s:hidden name="opening"/>
<s:hidden name="guestService"/>
<s:hidden name="closing"/>
<s:hidden name="total"/>
<s:hidden name="postRush"/>
<s:hidden name="flexible"/>
<s:iterator id="detail" value="hourDetails" status="status">
<s:hidden name="hourDetails[%{#status.index}].hour"/><s:hidden name="hourDetails[%{#status.index}].value"/>
</s:iterator>

<table border="0" cellspacing="0" align="center">
	<tr>
		<td valign="top">
			<!-- First detail level -->
			<table id="windowTable">
				<tr>
					<td colspan="6" class="windowTableHeader">
						<s:if test="position == null">
							<s:text name="schedule.details1.all_positions.title"/>
						</s:if>
						<s:else>
							<s:text name="schedule.details1.position.title">
								<s:param value="position.name"/>
							</s:text>
						</s:else>
					</td>
				</tr>
				<tr>
					<td class="windowTableLabel">&nbsp;</td>
					<td class="windowTableLabel"><s:text name="schedule.details1.target.label"/></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details1.opening.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="opening"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details1.guest_service.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="guestService"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details1.closing.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="closing"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details1.total.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="total"/></s:text></td>
				</tr>
			</table>
			<!-- First detail level -->
		</td>
		
		<s:if test="completeDetails">
		<td>
			<!-- Second detail level -->
			<table id="windowTable">
				<tr>
					<td colspan="6" class="windowTableHeader">
						<s:if test="position == null">
							<s:text name="schedule.details2.all_positions.title"/>
						</s:if>
						<s:else>
							<s:text name="schedule.details2.position.title">
								<s:param value="position.name"/>
							</s:text>
						</s:else>
					</td>
				</tr>
				<tr>
					<td class="windowTableLabel">&nbsp;</td>
					<td class="windowTableLabel"><s:text name="schedule.details2.target.label"/></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details2.opening.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="opening"/></s:text></td>
				</tr>
				
				<s:iterator id="detail" value="hourDetails" status="status">
					<tr>
						<td class="windowTableLabel"><s:text name="schedule.details2.hour.dateformat"><s:param value="hour"/></s:text></td>
						<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="value"/></s:text></td>
					</tr>
				</s:iterator>
				
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details2.post_rush.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="postRush"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details2.flexible.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="flexible"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details2.closing.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="closing"/></s:text></td>
				</tr>
				<tr>
					<td class="windowTableLabel"><s:text name="schedule.details1.total.label"/></td>
					<td class="windowTableValue"><s:text name="schedule.details.value.numberformat"><s:param value="total"/></s:text></td>
				</tr>
			</table>
			<!-- Second detail level -->
		</td>		
		</s:if>
	</tr>
</table>
</s:form>