insert into role (authority) values ('USER');
insert into users values (1, 'test', 'test', '$2a$12$aM6aqvT1BWv0gkojKuxVMOhdqyQsn69GImWX2kYYQqw7AxdJj.0IO', 'testuser');
insert into user_roles (user_id, role_id) values (1, 1);

insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (2, 'hotel 2', 400, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (3, 'hotel 3', 350, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (1, 'foo', 600, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (5, 'hotel 1', 300, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (5, 'Apartamenty Full Wypas', 300, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (2, 'Apartamenty Pawełek', 400, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (3, 'hotel 3', 350, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (1, 'foo', 600, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (5, 'hotel 1', 300, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (2, 'hotel 2', 400, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (3, 'hotel 3', 350, 'test.png', 1);
insert into hotel (max_guests, name, price_per_night, image_name, user_id) values (1, 'foo', 600, 'test.png', 1);

insert into address values (1, 'Warsaw', 'Poland', 'test street');
insert into address values (2, 'Cracow', 'Poland', 'test street');
insert into address values (3, 'Lodz', 'Poland', 'test street');
insert into address values (4, 'Wroclaw', 'Poland', 'test street');
insert into address values (5, 'Radom', 'Poland', 'test street');
insert into address values (6, 'Wołomin', 'Poland', 'test street');
insert into address values (7, 'Plock', 'Poland', 'test street');
insert into address values (8, 'Berlin', 'Germany', 'test street');
insert into address values (9, 'Warsaw', 'Poland', 'test street');
insert into address values (10, 'Cracow', 'Poland', 'test street');
insert into address values (11, 'Lodz', 'Poland', 'test street');
insert into address values (12, 'Wroclaw', 'Poland', 'test street');

