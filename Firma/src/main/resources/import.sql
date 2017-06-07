insert into national_bank (common_name, country, email, organization, organization_unit) values ('NBS','Srbija','nbs@nbs','org','orgU');

insert into bank(bank_code,pib,name,address,email,web,phone,fax) values ('UCR','asdfggfdsa','uni credit','glavna','uni@gmail.com','web','4892810','fax');
insert into bank(bank_code,pib,name,address,email,web,phone,fax) values ('ERS','pooppooppo','erste','bulevar','erste@gmail.com','web','4892810','fax');
insert into bank(bank_code,pib,name,address,email,web,phone,fax) values ('BIN','intesansns','intesa','glavna','intesa@gmail.com','web','4892810','fax');

insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm) values ('levi','trifkovicev trg','Novi Sad','Srbija','levi@levi.com','012656','web',1,'asdfggfdsaa');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm) values ('vega','trifkovicev trg','Novi Sad','Srbija','vega@vega.com','012656','web',1,'asdfggfdsas');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm) values ('dms','trifkovicev trg','Novi Sad','Srbija','dms@dms.com','012656','web',2,'asdfggfdsrr');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm) values ('ftn','bulevar','Novi Sad','Srbija','ftn@uns.com','012656','web',3,'asdfggfdsea');


insert into user (username, password, email, firma_id) values ('dachakg','$2a$10$bdLAFEAmvgWUApO.uKWqvOjVDlN7riKyB/d0/4w.9e.aeTF1Z3KQ.','a@s.com',1);
insert into user (username, password, email, firma_id) values ('dachakg1','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe','s@a.org',1);
insert into user (username, password, email, firma_id) values ('dachakg2','$2a$10$NGxm0C0hF28mYpmISxkrEe0JLMf8/2CsHBq4czTP9Rq9DX9AhEgYe','p@b.com',2);
insert into user (username, password, email, firma_id) values ('milance','$2a$10$LeQbMB55qkJYitFFGe2j3.uqe/1nrduf9ZRb8F6zSzg5n3GizOvw.','choda.94@gmail.com',3);
insert into user (username, password, email, firma_id) values ('duca','$2a$10$UteruuYLEll8yHbjCM7Q8OhIvB0EIzT9ErS7Wqqltj1gk37qEHkpW','ed@c.com',4);
insert into user (username, password, email, firma_id) values ('rada', '$2a$10$YQefMKcyoC1LNTPoqXRreOSvJvs.ytC857rlHHn5MjE0DqQluxnqi', 'rada@r.com',2);

insert into certificate (povucen, serial_number) values (0, '1496236139');
insert into certificate (povucen, serial_number) values (0, '1496256785');
insert into certificate (povucen, serial_number) values (0, '1496256921');
insert into certificate (povucen, serial_number) values (0, '1496257047');



insert into role (enum_role) values ('ROLE_ADMIN');
insert into role (enum_role) values ('ROLE_USER');
insert into role (enum_role) values ('ROLE_BANKER');
insert into role (enum_role) values ('ROLE_FIRM');


insert into users_roles(user_id, role_id) values (3,2);
insert into users_roles(user_id, role_id) values (1,3);
insert into users_roles(user_id, role_id) values (4,2);
insert into users_roles(user_id, role_id) values (5,1);
insert into users_roles(user_id, role_id) values (5,3);
insert into users_roles(user_id, role_id) values (6,4);

insert into privilege (privilege) values ('addCertificate');
insert into privilege (privilege) values ('revokeCertificate');
insert into privilege (privilege) values ('registerUser');
insert into privilege (privilege) values ('addCaSignedCertificate');
insert into privilege (privilege) values ('sendInvoice');

insert into roles_privileges (role_id, privilege_id) values (1,1);
insert into roles_privileges (role_id, privilege_id) values (1,2);
insert into roles_privileges (role_id, privilege_id) values (1,3);
insert into roles_privileges (role_id, privilege_id) values (3,3);
insert into roles_privileges (role_id, privilege_id) values (1,4);
insert into roles_privileges (role_id, privilege_id) values (4,5);
