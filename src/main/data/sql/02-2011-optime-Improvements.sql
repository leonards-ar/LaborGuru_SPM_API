# FS-285

alter table tbl_operation_times add opening_extra_hours integer DEFAULT 2;
alter table tbl_operation_times add closing_extra_hours integer DEFAULT 2;

update tbl_operation_times o set opening_extra_hours = (select extra_schedule_hours from tbl_stores s where store_id=o.store_id);

update tbl_operation_times set closing_extra_hours = opening_extra_hours;

alter table tbl_stores drop extra_schedule_hours;
