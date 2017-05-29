insert into national_bank (common_name, country, email, organization, organization_unit) values ('NBS','Srbija','nbs@nbs','org','orgU');

insert into user (username, password) values ('dachakg','$2a$10$bdLAFEAmvgWUApO.uKWqvOjVDlN7riKyB/d0/4w.9e.aeTF1Z3KQ.');
insert into user (username, password) values ('dachakg1','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe');
insert into user (username, password) values ('dachakg2','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe');
insert into user (username, password) values ('milance','$2a$10$LeQbMB55qkJYitFFGe2j3.uqe/1nrduf9ZRb8F6zSzg5n3GizOvw.');
insert into user (username, password) values ('duca','$2a$10$UteruuYLEll8yHbjCM7Q8OhIvB0EIzT9ErS7Wqqltj1gk37qEHkpW');

insert into role (enum_role) values ('ROLE_ADMIN');
insert into role (enum_role) values ('ROLE_ADMIN');
insert into role (enum_role) values ('ROLE_USER');
insert into role (enum_role) values ('ROLE_USER');
insert into role (enum_role) values ('ROLE_BANKER');


insert into users_roles(user_id, role_id) values (1,2);
insert into users_roles(user_id, role_id) values (2,2);
insert into users_roles(user_id, role_id) values (2,3);
insert into users_roles(user_id, role_id) values (3,4);
insert into users_roles(user_id, role_id) values (1,5);
insert into users_roles(user_id, role_id) values (4,5);
insert into users_roles(user_id, role_id) values (4,4);
insert into users_roles(user_id, role_id) values (5,2);
insert into users_roles(user_id, role_id) values (5,4);