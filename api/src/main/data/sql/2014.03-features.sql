-- Add new Print Daily Schedule that only Store Managers have access to
insert into tbl_permissions values (46, 'PRINT_DAILY_SHIFT', 'Print Daily Schedule Shifts');
insert into tbl_menu_items values (36, 'schedule.submenu.printdailyshift', 'SCHEDULE_PRINTDAILY_HELP', '/schedule/printdailyshiftbyposition_view.action?selectedDate=${selectedDate}&selectedWeekDay=${selectedWeekDay}', 3, 15, 46);
insert into tbl_profiles_permissions (profile_id, permission_id) values (2, 46);
update tbl_menu_items set position = 4 where menu_item_id = 26;
update tbl_menu_items set position = 5 where menu_item_id = 27;


# SPM-240
alter table tbl_stores modify average_variable double DEFAULT 1.0;
--update tbl_stores set average_variable = 1.0 where average_variable = 0.0;