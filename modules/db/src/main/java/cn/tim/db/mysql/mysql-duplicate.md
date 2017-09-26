set @flag=-1;
insert into tbl_change(order_id,VERSION,`modified`) values(600, '2017-01-01 10:00:00',now())
on DUPLICATE KEY UPDATE 
version=if(@flag:=version<='2017-01-01 08:00:00' , '2017-01-01 12:00:00', version),
modified=if(version<='2017-01-01 10:00:00', now(), modified)
select @flag