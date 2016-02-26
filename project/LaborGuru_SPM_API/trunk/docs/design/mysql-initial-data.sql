-- Creation
--create database spm;
--GRANT ALL ON spm.* TO 'spm'@'localhost' IDENTIFIED BY 'java1234';
--GRANT ALL ON spm.* TO 'spm'@'%' IDENTIFIED BY 'java1234';
--FLUSH PRIVILEGES;

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
insert into tbl_profiles (profile_id, name, description) values (2, 'ROLE_FAKE', 'Profile Desc');

-- Office
insert into tbl_offices (office_id, name, type, parent_office_id) values (1,'Mac Donalds', 'A', null);

-- Users
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'mcapurro@gmail.com', 1, null, 0, 'Mariano', '1234', 0, 'Capurro', 'mcapurro');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'cnunezre@gmail.com', 2, null, 0, 'Cristian', '1234', 0, 'Nuñez Rebolledo', 'cnunezre');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'fbarrera@gmail.com', 3, null, 0, 'Federico', '1234', 0, 'Barrera Oro', 'fbarrera');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'ignacio@laborguru.com', 4, null, 0, 'Ignacio', '1234', 0, 'Goris', 'igoris');
insert into tbl_users (creation_date, email, user_id, last_update_date, login_count, name, password, status, surname, username) values (now(), 'fake@fake.com', 5, null, 0, 'Fake', '1234', 0, 'User', 'fake');

insert into tbl_users_profiles (profile_id, user_id) values (1, 1);
insert into tbl_users_profiles (profile_id, user_id) values (1, 2);
insert into tbl_users_profiles (profile_id, user_id) values (1, 3);
insert into tbl_users_profiles (profile_id, user_id) values (1, 4);

insert into tbl_users_profiles (profile_id, user_id) values (2, 5);

-- Permissions
insert into tbl_permissions (permission_id, name, description) values (1, 'FREE_ACCESS', 'Dummy permission that all profiles should have');
insert into tbl_permissions (permission_id, name, description) values (2, 'VIEW_EMPLOYEE', 'View and query employees of the own store');
insert into tbl_permissions (permission_id, name, description) values (3, 'EDIT_EMPLOYEE', 'Edit existing employees of the own store');
insert into tbl_permissions (permission_id, name, description) values (4, 'CREATE_EMPLOYEE', 'Create new employees in the own store');

insert into tbl_permissions (permission_id, name, description) values (5, 'VIEW_STORE', 'View and query stores');
insert into tbl_permissions (permission_id, name, description) values (6, 'EDIT_STORE', 'Edit existing stores');
insert into tbl_permissions (permission_id, name, description) values (7, 'CREATE_STORE', 'Create new stores');

insert into tbl_profiles_permissions (permission_id, profile_id) values (1, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (2, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (3, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (4, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (5, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (6, 1);
insert into tbl_profiles_permissions (permission_id, profile_id) values (7, 1);

insert into tbl_profiles_permissions (permission_id, profile_id) values (1, 2);

-- Menu Items
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (1, 'home.menu.label', 'HOME_MENU_HELP', null, 1, 0, '/home/home.action');

insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (2, 'employee.menu.label', 'EMPLOYEE_MENU_HELP', null, 2, 0, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (3, 'employee.submenu.list', 'EMPLOYEE_LIST_HELP', 2, 3, 1, '/employee/employee_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (4, 'employee.submenu.create', 'EMPLOYEE_ADD_HELP', 2, 4, 0, '/employee/employee_add.action');

insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (5, 'store.menu.label', 'STORE_MENU_HELP', null, 5, 0, '/store/store_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (6, 'store.submenu.list', 'STORE_LIST_HELP', 5, 6, 1, '/store/store_list.action');
insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (7, 'store.submenu.create', 'STORE_ADD_HELP', 5, 7, 0, '/store/store_add.action');

-- Store
insert into tbl_stores (store_id, name, office_id) values (1,'microcentro', 1);

-- Positions
insert into tbl_positions (position_id, name, store_id) values (1, 'chef',1);
insert into tbl_positions (position_id, name, store_id) values (2, 'waiter',1);
insert into tbl_positions (position_id, name, store_id) values (3, 'cook',1);
insert into tbl_positions (position_id, name, store_id) values (4, 'porter',1);

commit;

