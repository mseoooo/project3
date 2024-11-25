drop table TelTable225;
create table TelTable225(
	id varchar2(20),
	pw varchar2(20)
);

insert into TELTABLE225 values('babo','ondal');

commit;

select * from TELTABLE225;















------------------------------------------
create table ProductTBL(
	cId int,
	cName varchar2(10),
	pName varchar2(15)
);

insert into ProductTBL values (807, '김손님','구우두');
insert into ProductTBL values (808, '나손님','가아아아방');

select * from ProductTBL;
-------------------------------
drop table simple_bbs;
create table simple_bbs(
  id number(4) primary key,
  writer varchar2(100),
  title varchar2(100),
  content varchar2(100)
);
drop sequence simple_bbs_seq;
create sequence simple_bbs_seq;





