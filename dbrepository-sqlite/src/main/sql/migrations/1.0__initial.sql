-- apply changes
create table accounts (
  id                            varchar(255) not null,
  name                          varchar(255),
  category_id                   varchar(255),
  constraint pk_accounts primary key (id),
  foreign key (category_id) references categories (id) on delete restrict on update restrict
);

create table categories (
  id                            varchar(255) not null,
  name                          varchar(255),
  constraint pk_categories primary key (id)
);

