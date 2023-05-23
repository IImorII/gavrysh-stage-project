drop database insurance;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

create database if not exists insurance DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use insurance;

create table if not exists client
(
    id    int unique auto_increment primary key,
    name  varchar(50) not null,
    birth int
    );


create table if not exists insurance_policy
(
    id        int unique auto_increment primary key,
    name      varchar(50) not null,
    client_fk int,
    constraint policy_client_fk foreign key (client_fk) references client (id)
    );

create table if not exists object_type
(
    id   int unique auto_increment primary key,
    name varchar(50) not null
    );

create table if not exists insurance_object
(
    id                  int unique auto_increment primary key,
    name                varchar(50) not null,
    price               decimal,
    object_type_fk      int,
    insurance_policy_fk int,
    constraint object_type_fk foreign key (object_type_fk) references object_type (id),
    constraint object_policy_fk foreign key (insurance_policy_fk) references insurance_policy (id)
    );

create table if not exists insurance_option
(
    id   int unique auto_increment primary key,
    name varchar(50) not null
    );

create table if not exists insurance_object_option
(
    id        int not null,
    option_id int not null,
    primary key (id, option_id),
    foreign key (id) references insurance_object (id) on delete cascade,
    foreign key (option_id) references insurance_option (id) on delete cascade
    );

COMMIT;