insert into role values (1, 'USER'), (2, 'ADMIN');

--password
insert into users values (1, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testuser');
insert into users values (2, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testadmin');

insert into user_roles values (1, 1), (2, 1), (2, 2);