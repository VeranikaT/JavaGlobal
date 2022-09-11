create table supply
(
    id           serial       not null primary key,
    title        varchar(255) not null,
    date         date         not null,
    supplier     decimal      not null
);