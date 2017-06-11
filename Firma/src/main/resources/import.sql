insert into national_bank (common_name, country, email, organization, organization_unit) values ('NBS','Srbija','nbs@nbs','org','orgU');

insert into bank(bank_code,pib,name,address,email,web,phone,fax,obracunski_racun_banke,swift_kod_banke,stanje_racuna_banke) values ('BAC','asdfggfdsa','uni credit','glavna','uni@gmail.com','web','4892810','fax','200123456123141124','BACXRSBG',0);
insert into bank(bank_code,pib,name,address,email,web,phone,fax,obracunski_racun_banke,swift_kod_banke,stanje_racuna_banke) values ('GIB','pooppooppo','erste','bulevar','erste@gmail.com','web','4892810','fax','300124510974841158','GIBARS22',0);
insert into bank(bank_code,pib,name,address,email,web,phone,fax,obracunski_racun_banke,swift_kod_banke,stanje_racuna_banke) values ('DBD','intesansns','intesa','glavna','intesa@gmail.com','web','4892810','fax','400123456321454147','DBDBRSBG',0);

insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm,stanje_racuna,broj_racuna) values ('levi','trifkovicev trg','Novi Sad','Srbija','levi@levi.com','012656','web',1,'asdfggfdsaa',0,'200636547896321456');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm,stanje_racuna,broj_racuna) values ('vega','trifkovicev trg','Novi Sad','Srbija','vega@vega.com','012656','web',1,'asdfggfdsas',0,'200854578787874202');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm,stanje_racuna,broj_racuna) values ('dms','trifkovicev trg','Novi Sad','Srbija','dms@dms.com','012656','web',2,'asdfggfdsrr',0,'300874521693695451');
insert into firma(name,address,city,country,email,phone,web,bank_id,pib_firm,stanje_racuna,broj_racuna) values ('ftn','bulevar','Novi Sad','Srbija','ftn@uns.com','012656','web',3,'asdfggfdsea',0,'400254547845612156');


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

insert into Faktura(adresa_dobavljaca, adresa_kupca, broj_racuna, datum_racuna, datum_valute, iznos_rabata, iznos_za_uplatu, jedinica_mere, jedinicna_cena, kolicina, naziv_dobavljaca, naziv_kupca, naziv_robe_ili_usluge, obradjena, oznaka_valute, pib_dobavljaca, pib_kupca, procena_rabata, redni_broj, ukupan_porez, ukupan_porez_stavka, ukupan_rabat, ukupno_robaiusluge, umanjeno_za_rabat, uplata_na_racun, vrednost, vrednost_robe, vrednost_usluga, firma_id) values('bulevar', 'trifkovicev trg', '111111111111111111', '2017-06-08 02:00:00', '2017-06-08 02:00:00', '32', '1250', 'kg', '1433', '343', 'ftn', 'levi', 'ds', 0, 'din', '23213123431', NULL, '123', '1', '34', '232', '34', '434', '222', '12', '43434', '3434', '434', '1');
