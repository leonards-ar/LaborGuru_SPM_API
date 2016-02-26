insert into tbl_menu_items values (27, 'schedule.submenu.printtotalhours', 'SCHEDULE_PRINTTOTALHOURS_HELP', '/schedule/printweeklytotalhoursbyposition_view.action', 4, 15, 41);
alter tbl_shifts add reference_shift_id integer;
    alter table tbl_shifts 
        add index fk_shifts_startingshifts (reference_shift_id), 
        add constraint fk_shifts_startingshifts 
        foreign key (reference_shift_id) 
        references tbl_shifts (shift_id);