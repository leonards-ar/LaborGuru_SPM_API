-- SPM#202
insert into tbl_permissions (permission_id, name, description) values (48, 'POST_SHIFT', 'Post Schedule Shifts');
insert into tbl_profiles_permissions (profile_id, permission_id) values (2, 48);

-- SPM#203
insert into tbl_menu_items (menu_item_id, label_key, help_key, target, position, parent_id, permission_id) values (34, 'employee.submenu.uploademployeesdefinition', 'EMPLOYEES_UPLOAD_DEFINITION_HELP', '/employee/uploadDefinition_edit.action', 5, 5, 6);


-- SPM#198
update tbl_profiles set home_result="customerReport" where profile_id="4";
update tbl_profiles set home_result="regionReport" where profile_id="5";
update tbl_profiles set home_result="areaReport" where profile_id="6";


--update tbl_permissions set name="CUSTOMER_MANAGER_REPORTS", description="Show Report Menu for Customer, Regional and Area managers" where permission_id="47";
insert into tbl_permissions (permission_id, name, description) values ("47","CUSTOMER_MANAGER_REPORTS","Show Report Menu for Customer, Regional and Area managers");
update tbl_menu_items set permission_id="19" where menu_item_id="21";

delete from tbl_profiles_permissions where profile_id="2" and permission_id="47";

insert into tbl_permissions (permission_id, name, description) values ("43","SHOW_CUSTOMER_REPORT","Show Report for Customer Manager");
insert into tbl_permissions (permission_id, name, description) values ("44","SHOW_REGIONAL_REPORT","Show Report for Regional Manager");
insert into tbl_permissions (permission_id, name, description) values ("45","SHOW_AREA_REPORT", "Show Report for Area Manager");

delete from tbl_menu_items where menu_item_id in (35,31,32,33);
insert into tbl_menu_items (menu_item_id, label_key, help_key, target, position, parent_id, permission_id) values ("35","report.manager.menu.label","CUSTOMER_ADMIN_REPORT_MENU_HELP",null, "0",null,47);
insert into tbl_menu_items (menu_item_id, label_key, help_key, target, position, parent_id, permission_id) values ("31","report.customerManager.title","CUSTOMER_MANAGER_REPORT","/report/customerReport_showFirstReport.action?selectedDate=${selectedDate}&amp;selectedWeekDay=${selectedWeekDay}", "0","35","43");
insert into tbl_menu_items (menu_item_id, label_key, help_key, target, position, parent_id, permission_id) values ("32","report.regionalManager.title","CUSTOMER_REGIONAL_REPORT","/report/regionalReport_showFirstReport.action?selectedDate=${selectedDate}&amp;selectedWeekDay=${selectedWeekDay}","1","35","44");
insert into tbl_menu_items (menu_item_id, label_key, help_key, target, position, parent_id, permission_id) values ("33","report.areaManager.title","CUSTOMER_AREA_REPORT","/report/areaReport_showFirstReport.action?selectedDate=${selectedDate}&amp;selectedWeekDay=${selectedWeekDay}","2","35","45");


delete from tbl_profiles_permissions where profile_id=4;
delete from tbl_profiles_permissions where profile_id=5;
delete from tbl_profiles_permissions where profile_id=6;

insert into tbl_profiles_permissions (profile_id, permission_id) values(4,47);
insert into tbl_profiles_permissions (profile_id, permission_id) values(5,47);
insert into tbl_profiles_permissions (profile_id, permission_id) values(6,47);

insert into tbl_profiles_permissions (profile_id, permission_id) values(4,43);
insert into tbl_profiles_permissions (profile_id, permission_id) values(5,44);
insert into tbl_profiles_permissions (profile_id, permission_id) values(6,45);
