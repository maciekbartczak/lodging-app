insert into role values (1, 'USER'), (2, 'ADMIN');

--password
insert into users values (1, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testuser');
insert into users values (2, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testadmin');
insert into users values (3, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'foo');

insert into session values (1, '1234', 1);
insert into session values (2, '4321', 2);

insert into user_roles values (1, 1), (2, 1), (2, 2);


insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (1, 5, 'hotel 1', 300, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (2, 2, 'hotel 2', 400, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (3, 3, 'hotel 3', 350, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (4, 1, 'foo', 600, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (5, 1, 'hotel 4', 600, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (6, 1, 'hotel 5', 600, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (7, 1, 'hotel 6', 600, '', 1);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (8, 1, 'hotel 7', 600, '', 2);
insert into hotel (id, max_guests, name, price_per_night, image_name, user_id) values (9, 1, 'hotel 8', 600, '', 1);

insert into address values (1, 'Warsaw', 'Poland', 'test street');
insert into address values (2, 'Warsaw', 'Poland', 'test street');
insert into address values (3, 'Warsaw', 'Poland', 'test street');
insert into address values (4, 'Lodz', 'Poland', 'test street');
insert into address values (5, 'Warsaw', 'Poland', 'test street');
insert into address values (6, 'Warsaw', 'Poland', 'test street');
insert into address values (7, 'Warsaw', 'Poland', 'test street');
insert into address values (8, 'Warsaw', 'Poland', 'test street');
insert into address values (9, 'Berlin', 'Germany', 'test street');


--test range 2022-03-26 2022-03-27 -> hotel.id [1, 2]
insert into booking (start_date, end_date, guest_count, hotel_id, user_id) values (date '2022-03-22', date '2022-03-25', 3, 1, 1),
                                                                         (date '2022-03-28', date '2022-03-30', 2, 1, 1),
                                                                         (date '2022-03-26', date '2022-03-30', 2, 3, 1);