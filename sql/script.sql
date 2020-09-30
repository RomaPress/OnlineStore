create table role
(
    id   int auto_increment
        primary key,
    name enum ('USER', 'ADMIN', 'UNKNOWN') default 'UNKNOWN' not null
);

create table status
(
    id   int auto_increment
        primary key,
    name enum ('PAID', 'REGISTERED', 'CANCELED') not null,
    constraint name_UNIQUE
        unique (name)
);

create table type
(
    id   int auto_increment
        primary key,
    name enum ('Classical guitar', 'Bas-guitar', 'Electric guitar', 'Acoustic guitar', 'Electro-acoustic guitar') not null
);

create table product
(
    id          int auto_increment
        primary key,
    name        varchar(30)  not null,
    price       double       not null,
    amount      int          not null,
    description varchar(256) null,
    type_id     int          not null,
    constraint name_UNIQUE
        unique (name),
    constraint fk_product_type
        foreign key (type_id) references type (id)
            on update cascade
);

create index fk_product_type_idx
    on product (type_id);

create table user
(
    id           int auto_increment
        primary key,
    role_id      int         not null,
    first_name   varchar(20) not null,
    last_name    varchar(20) not null,
    login        varchar(20) null,
    password     varchar(20) null,
    phone_number varchar(20) not null,
    constraint login_UNIQUE
        unique (login),
    constraint fk_user_position
        foreign key (role_id) references role (id)
            on update cascade
);

create table `order`
(
    id             int auto_increment
        primary key,
    user_id        int                                 not null,
    status_id      int                                 not null,
    date_time      timestamp default CURRENT_TIMESTAMP not null,
    invoice_number int                                 null,
    total          double                              null,
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

create table order_product
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

create definer = root@localhost trigger order_product_AFTER_DELETE_newAmount
    after delete
    on order_product
    for each row
begin
    -- missing source code
end;

create definer = root@localhost trigger order_product_AFTER_UPDATE
    after update
    on order_product
    for each row
begin
    -- missing source code
end;

create definer = root@localhost trigger order_product_BEFORE_INSERT
    after insert
    on order_product
    for each row
begin
    -- missing source code
end;

create definer = root@localhost trigger order_product_BEFORE_INSERT_1
    before insert
    on order_product
    for each row
begin
    -- missing source code
end;

create index fk_user_position_idx
    on user (role_id);
