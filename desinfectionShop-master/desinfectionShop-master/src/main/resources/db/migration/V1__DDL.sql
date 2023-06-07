create table account
(
    id bigserial,
    email varchar(255),
    password varchar,
    phone_number varchar(12),
    first_name varchar(30),
    last_name varchar(40),
    address varchar(510),
    creation_date timestamp,
    is_active boolean,
    current_order_id bigint,

    primary key (id)
);

CREATE SEQUENCE IF not EXISTS ACCOUNT_SEQ START WITH 1;


create table item
(
    id bigserial,
    name varchar(255),
    cost decimal,
    amount int,
    amount_sold int,
    country varchar(255),
    chemical_composition varchar,
    picture_path varchar,
    description varchar,
    instruction varchar,
    capacity varchar(255),

    primary key (id)
);

CREATE SEQUENCE IF not EXISTS ITEM_SEQ START WITH 1;


create table orders
(
    id bigserial,
    account_id bigint,
    final_cost decimal,
    status varchar(255),
    shipping_address varchar,
    comment varchar,
    creation_date timestamp,

    primary key (id),
    foreign key (account_id) references account(id)
);

CREATE SEQUENCE IF not EXISTS ORDER_SEQ START WITH 1;


create table pest
(
    id bigserial,
    name varchar(255),
    picture_path varchar,

    primary key (id)
);

CREATE SEQUENCE IF not EXISTS PEST_SEQ START WITH 1;

create table role
(
    id bigserial,
    name varchar(255),
    primary key (id)
);

CREATE SEQUENCE IF not EXISTS ROLE_SEQ START WITH 1;

create table account_role
(
    account_id bigint,
    role_id bigint,
    primary key (account_id, role_id),
    foreign key (account_id) references account,
    foreign key (role_id) references role
);

create table item_pest
(
    item_id bigint,
    pest_id bigint,
    primary key (item_id, pest_id),
    foreign key (item_id) references item,
    foreign key (pest_id) references pest
);

create table order_item
(
    order_id bigint,
    item_id bigint,
    item_cost decimal,
    amount int,
    primary key (order_id, item_id),
    foreign key (order_id) references orders,
    foreign key (item_id) references item
);