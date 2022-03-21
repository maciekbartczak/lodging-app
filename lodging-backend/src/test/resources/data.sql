insert into role values (1, 'USER'), (2, 'ADMIN');

--password
insert into users values (1, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testuser');
insert into users values (2, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testadmin');
insert into users values (3, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'foo');

insert into session values (1, '1234', 1);
insert into session values (2, '4321', 2);

insert into user_roles values (1, 1), (2, 1), (2, 2);

insert into hotel values (1, 5, 300);
insert into hotel values (2, 2, 400);
insert into hotel values (3, 3, 350);
insert into hotel values (4, 1, 350);
insert into hotel values (5, 2, 200);
insert into hotel values (6, 7, 600.5);
insert into hotel values (7, 4, 100);
insert into hotel values (8, 3, 230);
