-- roles 테이블에 권한 넣기
insert into roles (id, name) values (default, 'ROLE_USER');
insert into roles (id, name) values (default, 'ROLE_ADMIN');
select * from roles;