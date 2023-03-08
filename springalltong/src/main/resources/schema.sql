create table t_goods
(
    id          bigint not null auto_increment,
    name        varchar(255),
    price       bigint not null,
    addTime timestamp,
    updateTime timestamp,
    primary key (id)
);