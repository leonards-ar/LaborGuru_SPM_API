insert into spm.tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'mail', 1, null, 0, 'Name', '1234', 0, 'Surname', 'user');
insert into spm.tbl_employees(user_id, store_id, default_position, manager, mobile_phone, home_phone, hire_date, max_hours_week, max_days_week, max_hour_day, address, address2, city, state, zip) values(2, 1, 1, false, '719-444-5555', '719-456-7890', now(), 40, 6, 6, 'street1', 'street2', 'city', 'state', 'zip');

insert into spm.tbl_profiles (profile_id, name, description) values (1, 'ROLE_USER', 'Profile Desc');

update tbl_profiles set name = 'ROLE_USER';
insert into tbl_users_profiles (profile_id, user_id) values (1, 1);


insert into tbl_permissions (permission_id, key, description) values (1, 'VIEW_EMPLOYEE', 'View and query employees of the own store');
insert into tbl_permissions (permission_id, key, description) values (2, 'EDIT_EMPLOYEE', 'Edit existing employees of the own store');
insert into tbl_permissions (permission_id, key, description) values (3, 'CREATE_EMPLOYEE', 'Create new employees in the own store');

insert into tbl_profiles_permissions (permission_id, profile_id) values (1, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (2, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (3, 1);


delete from tbl_menu_items;
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (1, 'employee.menu.label', 'EMPLOYEE_MENU_HELP', null, 1, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (2, 'employee.submenu.create', 'EMPLOYEE_ADD_HELP', 1, 3, 1, '/employee/employee_add.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (3, 'employee.submenu.list', 'EMPLOYEE_LIST_HELP', 1, 2, 0, '/employee/employee_list.action');

insert into tbl_offices (office_id, name, type, parent_office_id) values(1, 'Office', 'A', null);

insert into tbl_stores(store_id, name, office_id) values(1, 'Store Potatoe', 1);

insert into tbl_positions(position_id, name, store_id) values(1, 'Position 1', 1);
insert into tbl_positions(position_id, name, store_id) values(2, 'Position 2', 1);
insert into tbl_positions(position_id, name, store_id) values(3, 'Position 3', 1);
commit;
