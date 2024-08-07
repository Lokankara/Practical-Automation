DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS club_child;
DROP TABLE IF EXISTS child;
DROP TABLE IF EXISTS club;

CREATE TABLE IF NOT EXISTS categories (
    id bigserial primary key,
    avatar varchar not null,
    title varchar not null unique
);

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

insert into categories (title, avatar) values ('Old Category', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII');
insert into categories (title, avatar) values ('For Delete Category', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII');
insert into categories (title, avatar) values ('Unique Category', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII');
insert into categories (title, avatar) values ('Second unique Category', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII');
