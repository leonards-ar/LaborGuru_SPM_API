-- Delete
delete from tbl_profiles_permissions;
delete from tbl_users_profiles;
delete from tbl_profiles;
delete from tbl_menu_items;
delete from tbl_permissions;
delete from tbl_users;
delete from tbl_positions;
delete from tbl_stores;
delete from tbl_offices;

-- Profiles
insert into tbl_profiles (profile_id, name, description) values (1, 'ROLE_USER', 'Profile Desc');

commit;

-- Office
insert into tbl_offices (office_id, name, type, parent_office_id) values (0,'Mac Donalds', 'A', null);

commit;

-- Users
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'mcapurro@gmail.com', 1, null, 0, 'Mariano', '1234', 0, 'Capurro', 'mcapurro');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'cnunezre@gmail.com', 2, null, 0, 'Cristian', '1234', 0, 'Nuñez Rebolledo', 'cnunezre');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'fbarrera@gmail.com', 3, null, 0, 'Federico', '1234', 0, 'Barrera Oro', 'fbarrera');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'ignacio@laborguru.com', 4, null, 0, 'Ignacio', '1234', 0, 'Goris', 'igoris');

insert into tbl_users_profiles (profile_id, user_id) values (1, 1);
insert into tbl_users_profiles (profile_id, user_id) values (1, 2);
insert into tbl_users_profiles (profile_id, user_id) values (1, 3);
insert into tbl_users_profiles (profile_id, user_id) values (1, 4);


-- Permissions
insert into tbl_permissions (permission_id, key, description) values (0, 'FREE_ACCESS', 'Dummy permission that all profiles should have');
insert into tbl_permissions (permission_id, key, description) values (1, 'VIEW_EMPLOYEE', 'View and query employees of the own store');
insert into tbl_permissions (permission_id, key, description) values (2, 'EDIT_EMPLOYEE', 'Edit existing employees of the own store');
insert into tbl_permissions (permission_id, key, description) values (3, 'CREATE_EMPLOYEE', 'Create new employees in the own store');

insert into tbl_profiles_permissions (permission_id, profile_id) values (0, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (1, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (2, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (3, 1);

-- Menu Items
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (1, 'home.menu.label', 'HOME_MENU_HELP', null, 0, 0, '/home/home.action');

insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (2, 'employee.menu.label', 'EMPLOYEE_MENU_HELP', null, 1, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (3, 'employee.submenu.list', 'EMPLOYEE_LIST_HELP', 2, 2, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (4, 'employee.submenu.create', 'EMPLOYEE_ADD_HELP', 2, 3, 1, '/employee/employee_add.action');

commit;


-- Other data Test
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (5, 'Otra opcion', 'EMPLOYEE_MENU_HELP', null, 1, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (6, 'Otra subopcion 1', 'EMPLOYEE_ADD_HELP', 4, 3, 1, '/employee/employee_add.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (7, 'Otra subopcion 2', 'EMPLOYEE_LIST_HELP', 4, 2, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permissionid, position, target) values (8, 'Otra subopcion sin permisos 3', 'EMPLOYEE_LIST_HELP', 4, 4, 0, '/employee/employee_list.action');

commit;

-- Store
insert into tbl_stores (store_id, name, office_id) values (0,'microcentro', 0);

commit;

-- Positions
insert into tbl_positions (position_id, name, store_id) values (0, 'chef',0);
insert into tbl_positions (position_id, name, store_id) values (1, 'waiter',0);
insert into tbl_positions (position_id, name, store_id) values (2, 'cook',0);
insert into tbl_positions (position_id, name, store_id) values (4, 'porter',0);

commit;

