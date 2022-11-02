-- liquibase formatted sql
-- changeset liquibase:1

create table access_level
(
    name varchar(255) not null,
    primary key (name)
);

create table account
(
    id                uuid         not null,
    created_at        timestamp    not null,
    etag_version      int8         not null,
    version           int8,
    password          varchar(255) not null,
    username          varchar(255) not null,
    access_level_name varchar(255),
    primary key (id)
);

create table account_auctions
(
    account_id  uuid not null,
    auctions_id uuid not null
);

create table auction
(
    id           uuid         not null,
    created_at   timestamp    not null,
    etag_version int8         not null,
    version      int8,
    end_date     timestamp    not null,
    price        float8       not null,
    title        varchar(256) not null,
    winner_id    uuid,
    primary key (id)
);

alter table account_auctions
    add constraint UK_d5i4f2g1v83pvnnh4rdpxepi3 unique (auctions_id);

alter table account
    add constraint FKskom6yor2k73p3wjpfyqchdoq
        foreign key (access_level_name)
            references access_level;

alter table account_auctions
    add constraint FKjyht69w4rt2bmpkyscoiv5u0k
        foreign key (auctions_id)
            references auction;

alter table account_auctions
    add constraint FKd1ff7gxga2s4eshh1x8ixaobf
        foreign key (account_id)
            references account;

alter table auction
    add constraint FKl6iita278nxg4neo9e6gvlew5
        foreign key (winner_id)
            references account;