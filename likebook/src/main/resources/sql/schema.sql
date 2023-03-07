DROP TABLE users IF EXISTS;

CREATE TABLE users (
    user_name varchar(10),
    password varchar(100) not null,
    primary key (user_name)
);