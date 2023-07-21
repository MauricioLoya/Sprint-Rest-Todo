-- create table users
CREATE TABLE user (
  id bigint not null auto_increment,
  email varchar(100) not null,
  name varchar(100) not null,
  last_name varchar(100) not null,
  password varchar(300) not null,
  is_active boolean not null default true,
  primary key (id)
);