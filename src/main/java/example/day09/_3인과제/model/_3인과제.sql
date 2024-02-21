drop database if exists springtest;
create database springtest;
use springtest;

drop table if exists staff;
create table staff(
	sno int auto_increment,
    sname varchar(30),
    sphone varchar(13),
    primary key(sno)
);
select * from staff;

drop table if exists score;
create table score(
	sno int,
    scno int auto_increment,
    sccontent text,
    scscore int,
    scdate datetime default now(),
    primary key(scno),
    foreign key(sno) references staff(sno)
);
insert into staff(sname,sphone) values("홍길동","010-1111-1111");
insert into staff(sname,sphone) values("김자바","010-2222-2222");

select * from score;
select * from score where sno = 1;
insert into score(sno, sccontent, scscore) values (1,"우수",10);
insert into score(sno, sccontent, scscore) values (2,"최우수",30);