DROP TABLE IF EXISTS club_child;
DROP TABLE IF EXISTS child;
DROP TABLE IF EXISTS club;

CREATE TABLE IF NOT EXISTS club
(
    id          bigserial primary key,
    title       varchar not null,
    description varchar not null,
    image_url   varchar,
    category_id int8 references categories (id)
);

CREATE TABLE IF NOT EXISTS child
(
    id         bigserial primary key,
    first_name varchar not null,
    last_name  varchar not null,
    birth_date date
);

CREATE TABLE IF NOT EXISTS club_child
(
    club_id  int8 references club (id),
    child_id int8 references child (id)
);
