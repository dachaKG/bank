insert into national_bank (common_name, country, email, organization, organization_unit) values ('NBS','Srbija','nbs@nbs','org','orgU');


insert into user (username, password, email) values ('dachakg','$2a$10$bdLAFEAmvgWUApO.uKWqvOjVDlN7riKyB/d0/4w.9e.aeTF1Z3KQ.','a@s.com');
insert into user (username, password, email) values ('dachakg1','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe','s@a.org');
insert into user (username, password, email) values ('dachakg2','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe','p@b.com');
insert into user (username, password, email) values ('milance','$2a$10$LeQbMB55qkJYitFFGe2j3.uqe/1nrduf9ZRb8F6zSzg5n3GizOvw.','choda.94@gmail.com');
insert into user (username, password, email) values ('duca','$2a$10$UteruuYLEll8yHbjCM7Q8OhIvB0EIzT9ErS7Wqqltj1gk37qEHkpW','ed@c.com');


insert into certificate (povucen, serial_number) values (0, '1496236139');
insert into certificate (povucen, serial_number) values (0, '1496256785');
insert into certificate (povucen, serial_number) values (0, '1496256921');
insert into certificate (povucen, serial_number) values (0, '1496257047');



insert into role (enum_role) values ('ROLE_ADMIN');
insert into role (enum_role) values ('ROLE_USER');
insert into role (enum_role) values ('ROLE_BANKER');



insert into users_roles(user_id, role_id) values (3,2);
insert into users_roles(user_id, role_id) values (1,3);
insert into users_roles(user_id, role_id) values (4,2);
insert into users_roles(user_id, role_id) values (5,1);
insert into users_roles(user_id, role_id) values (5,3);

insert into privilege (privilege) values ('addCertificate');
insert into privilege (privilege) values ('revokeCertificate');
insert into privilege (privilege) values ('registerUser');
insert into privilege (privilege) values ('addCaSignedCertificate');


insert into roles_privileges (role_id, privilege_id) values (1,1);
insert into roles_privileges (role_id, privilege_id) values (1,2);
insert into roles_privileges (role_id, privilege_id) values (1,3);
insert into roles_privileges (role_id, privilege_id) values (3,3);
insert into roles_privileges (role_id, privilege_id) values (1,4);

