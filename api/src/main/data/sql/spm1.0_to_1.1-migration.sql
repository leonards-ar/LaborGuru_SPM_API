    create table tbl_store_variable_definitions (
        variable_definition_id integer not null auto_increment,
        name varchar(100),
        store_id integer,
        variable_index integer not null,
        primary key (variable_definition_id)
    );
    alter table tbl_store_variable_definitions AUTO_INCREMENT=1000;

    alter table tbl_store_variable_definitions
        add index fk_stores_variable_definitions (store_id),
        add constraint fk_stores_variable_definitions
        foreign key (store_id)
        references tbl_stores (store_id);

alter table tbl_stores add distribution_type varchar(255);
alter table tbl_projections add daily_projection_variable2 numeric(19,2);
alter table tbl_projections add daily_projection_variable3 numeric(19,2);
alter table tbl_projections add daily_projection_variable4 numeric(19,2);

alter table tbl_positions add variable2_flexible double;
alter table tbl_positions add variable2_opening double;
alter table tbl_positions add variable3_flexible double;
alter table tbl_positions add variable3_opening double;
alter table tbl_positions add variable4_flexible double;
alter table tbl_positions add variable4_opening double;

insert into tbl_menu_items (menu_item_id, label_key, help_key, parent_id, permission_id, position, target) values (null, 'projection.submenu.actualvalues', 'PROJECTION_ACTUAL_VALUES_HELP', 11, 11, 3, '/projection/actualValues_edit.action');

-- 07/10/2009
alter table tbl_upload_files add upload_type varchar(255);
update tbl_upload_files set upload_type = 'FILE';