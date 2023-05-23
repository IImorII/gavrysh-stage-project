drop database insurance;
create database insurance;
use insurance;

create table if not exists client
(
    id    int unique auto_increment primary key,
    name  varchar(50) not null,
    birth int
);

create table if not exists object_type
(
    id   int unique auto_increment primary key,
    name varchar(50) not null
);

create table if not exists insurance_object
(
    id      int unique auto_increment primary key,
    name    varchar(50) not null,
    price   decimal,
    type_fk int,
    constraint object_type_fk foreign key (type_fk) references object_type (id)
);


create table if not exists insurance_policy
(
    id        int unique auto_increment primary key,
    name      varchar(50) not null,
    client_fk int,
    constraint policy_client_fk foreign key (client_fk) references client (id)
);

create table if not exists insurance_case
(
    id        int unique auto_increment,
    name      varchar(50) not null,
    policy_id int         not null,
    object_id int         not null,
    primary key (policy_id, object_id),
    foreign key (policy_id) references insurance_policy (id) on delete cascade,
    foreign key (object_id) references insurance_object (id) on delete cascade
);

create table if not exists case_option
(
    id   int unique auto_increment primary key,
    name varchar(50) not null
);

create table if not exists insurance_case_option
(
    id        int not null,
    option_id int not null,
    primary key (id, option_id),
    foreign key (id) references insurance_case (id) on delete cascade,
    foreign key (option_id) references case_option (id) on delete cascade
);
