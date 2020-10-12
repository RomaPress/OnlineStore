CREATE DATABASE `onlinestore` DEFAULT CHARACTER SET utf8;

use onlinestore;

drop table if exists `order`;

drop table if exists order_product;

drop table if exists product;

drop table if exists role;

drop table if exists status;

drop table if exists type;

drop table if exists user;

create table if not exists role
(
    id   int auto_increment
        primary key,
    name enum ('USER', 'ADMIN', 'UNKNOWN', 'BLOCK') not null
);

create table if not exists status
(
    id   int auto_increment
        primary key,
    name enum ('PAID', 'REGISTERED', 'CANCELED') not null,
    constraint name_UNIQUE
        unique (name)
);

create table if not exists type
(
    id   int auto_increment
        primary key,
    name varchar(25) not null,
    constraint type_name_uindex
        unique (name)
);

create table if not exists product
(
    id          int auto_increment
        primary key,
    name        varchar(50) not null,
    price       double      not null,
    amount      int         not null,
    description text        null,
    type_id     int         not null,
    constraint name_UNIQUE
        unique (name),
    constraint fk_product_type
        foreign key (type_id) references type (id)
            on update cascade
);

create index fk_product_type_idx
    on product (type_id);

create table if not exists user
(
    id           int auto_increment
        primary key,
    role_id      int         not null,
    first_name   varchar(20) not null,
    last_name    varchar(20) not null,
    login        varchar(20) null,
    password     varchar(20) null,
    phone_number varchar(20) not null,
    city         varchar(20) not null,
    post_office  int         not null,
    constraint login_UNIQUE
        unique (login),
    constraint fk_user_position
        foreign key (role_id) references role (id)
            on update cascade
);

create table if not exists `order`
(
    id             int auto_increment
        primary key,
    user_id        int                                 not null,
    status_id      int                                 not null,
    date_time      timestamp default CURRENT_TIMESTAMP not null,
    invoice_number varchar(20)                         null,
    total          double                              null,
    city           varchar(20)                         not null,
    post_office    int                                 not null,
    constraint invoice_number_UNIQUE
        unique (invoice_number),
    constraint fk_order_status1
        foreign key (status_id) references status (id),
    constraint fk_order_user1
        foreign key (user_id) references user (id)
);

create index fk_order_status1_idx
    on `order` (status_id);

create index fk_order_user1_idx
    on `order` (user_id);

create definer = root@localhost trigger order_BEFORE_UPDATE
    before update
    on `order`
    for each row
BEGIN
    DECLARE newstatus varchar(20) DEFAULT (SELECT s.name FROM status s WHERE s.id = NEW.status_id);
    DECLARE count INT DEFAULT (SELECT count(*) FROM order_product WHERE NEW.id = order_id);
    DECLARE current_product_id INT;
    DECLARE current_amount INT;
    DECLARE i INT DEFAULT 1;
    DECLARE i_dec1 INT DEFAULT 0;

    IF newstatus = 'CANCELED' THEN
        BEGIN
            WHILE i_dec1 != count
                DO
                    SET current_product_id =
                            (SELECT product_id FROM order_product WHERE order_id = NEW.id LIMIT i_dec1, i);
                    SET current_amount = (SELECT amount FROM order_product WHERE order_id = NEW.id LIMIT i_dec1, i);


                    UPDATE product
                    SET product.amount = product.amount + current_amount
                    WHERE product.id = current_product_id;


                    SET i_dec1 = i_dec1 + 1;
                    SET i = i + 1;
                END WHILE;
        END;
    END IF;
END;

create table if not exists order_product
(
    order_id   int    not null,
    product_id int    not null,
    amount     int    not null,
    price      double null,
    primary key (order_id, product_id),
    constraint fk_order_has_product_order1
        foreign key (order_id) references `order` (id)
            on update cascade on delete cascade,
    constraint fk_order_has_product_product1
        foreign key (product_id) references product (id)
);

create index fk_order_has_product_order1_idx
    on order_product (order_id);

create index fk_order_has_product_product1_idx
    on order_product (product_id);

create definer = root@localhost trigger order_product_AFTER_UPDATE
    after update
    on order_product
    for each row
BEGIN
    DECLARE newamount double;

    SET newamount = NEW.amount;

    UPDATE product
    SET product.amount = product.amount - newamount + OLD.amount
    WHERE product.id = NEW.product_id;

    UPDATE `order`
    SET total = total - OLD.price * OLD.amount
        + NEW.price * NEW.amount
    WHERE `order`.id = NEW.order_id;
END;

create definer = root@localhost trigger order_product_BEFORE_DELETE
    before delete
    on order_product
    for each row
BEGIN
    UPDATE product
    SET product.amount = product.amount + OLD.amount
    WHERE product.id = OLD.product_id;


    UPDATE `order`
    SET total = total - OLD.price * OLD.amount
    WHERE `order`.id = OLD.order_id;
END;

create definer = root@localhost trigger order_product_BEFORE_INSERT
    after insert
    on order_product
    for each row
BEGIN
    DECLARE newamount double;

    UPDATE product
    SET product.amount = product.amount - NEW.amount
    WHERE product.id = NEW.product_id;
END;

create definer = root@localhost trigger order_product_BEFORE_INSERT_1
    before insert
    on order_product
    for each row
BEGIN
    SET NEW.price = (SELECT price FROM product WHERE NEW.product_id = product.id);

    UPDATE `order`
    SET total = ifnull(total, 0) + NEW.price * NEW.amount
    WHERE `order`.id = NEW.order_id;
END;

create index fk_user_position_idx
    on user (role_id);

