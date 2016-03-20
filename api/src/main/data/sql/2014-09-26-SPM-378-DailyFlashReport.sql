create table tbl_daily_flash (
	daily_flash_id bigint(20) not null auto_increment,
	date date,
	open_hours integer,
	close_hours integer,
	delivered_sales double precision,
	planned_sales double precision,
	store_id integer not null,
	primary key (daily_flash_id)
);

create table tbl_daily_flash_detail (
	daily_flash_detail_id bigint(20) not null auto_increment,
	hour datetime,
	actual_sale double precision,
	actual_hour double precision,
	ideal_hour double precision,
	daily_flash_id integer not null,
	primary key (daily_flash_detail_id)
);

alter table tbl_daily_flash 
	add index fk_daily_flash_store (store_id), 
	add constraint fk_daily_flash_store 
	foreign key (store_id)
	references tbl_stores (store_id);
	
alter table tbl_daily_flash_detail 
	add index fk_daily_flash_details (daily_flash_id), 
	add constraint fk_daily_flash_details 
	foreign key (daily_flash_id)
	references tbl_daily_flash (daily_flash_id);

ALTER TABLE tbl_daily_flash AUTO_INCREMENT=1000;

ALTER TABLE tbl_daily_flash_detail AUTO_INCREMENT=1000;		

insert into tbl_menu_items(menu_item_id,label_key,help_key,target,position,parent_id,permission_id) values(37,'report.dailyFlashReport.title','DAYLY_FLASH_REPORT_HELP','/report/dailyFlashReport_showFirstReport.action',4,21,19);