begin transaction;

drop sequence if exists hibernate_sequence;
drop table if exists authorities cascade;
drop table if exists categories cascade;
drop table if exists events cascade;
drop table if exists user_authority cascade;
drop table if exists user_profile cascade;
drop table if exists users cascade;

create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to pilsik;

create table authorities
(
	id bigint not null
		constraint authorities_pkey
			primary key,
	type varchar(255) not null
);

alter table authorities owner to pilsik;

create table user_profile
(
	id bigint not null
		constraint user_profile_pkey
			primary key,
	address varchar(255),
	address2 varchar(255),
	city varchar(255),
	phone varchar(255),
	state varchar(255),
	zip varchar(10)
);

alter table user_profile owner to pilsik;

create table users
(
	id bigint not null
		constraint users_pkey
			primary key,
	account_expired boolean,
	account_locked boolean,
	activationcode varchar(255),
	credentials_expired boolean,
	email varchar(255)
		constraint users_email_unique_key
			unique,
	enabled boolean,
	password varchar(255),
	username varchar(255)
		constraint users_username_unique_key
			unique
);

alter table users owner to pilsik;

create table categories
(
	id bigint not null
		constraint categories_pkey
			primary key,
	capacity numeric(19,2) not null,
	createat timestamp not null,
	description varchar(255),
	name varchar(255),
	user_id bigint
		constraint categories_users_fk
			references users
);

alter table categories owner to pilsik;

create table events
(
	id bigint not null
		constraint events_pkey
			primary key,
	amount numeric(19,2) not null,
	createat timestamp not null,
	description varchar(255),
	name varchar(255),
	type varchar(255) not null,
	category_id bigint not null
		constraint events_categories_fk
			references categories
);

alter table events owner to pilsik;

create table user_authority
(
	user_id bigint not null
		constraint user_authority_users_fk
			references users,
	authority_id bigint not null
		constraint user_authority_authority_fk
			references authorities,
	constraint user_authority_pkey
		primary key (user_id, authority_id)
);

alter table user_authority owner to pilsik;

end transaction;