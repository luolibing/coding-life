drop table if exists `order`;
drop table if exists order_product;
drop table if exists service;
drop table if exists teacher;
drop table if exists teacher_has_role;
drop table if exists work_order;
create table `order` (id bigint not null auto_increment, code bigint, primary key (id));
create table order_product (id bigint not null auto_increment, name varchar(255), order_id bigint, primary key (id));
create table service (id bigint not null auto_increment, sku_name varchar(255), primary key (id));
create table teacher (id bigint not null auto_increment, name varchar(255), primary key (id));
create table teacher_has_role (id bigint not null auto_increment, role_id bigint, teacher_id bigint, primary key (id));
create table work_order (id bigint not null auto_increment, status varchar(255), service_id bigint, primary key (id));
alter table order_product add constraint FKm6igrp4lwucj1me05axmv885c foreign key (order_id) references `order` (id);
alter table work_order add constraint FKawpxld6xtsrlnv908if3na3bf foreign key (service_id) references service (id);