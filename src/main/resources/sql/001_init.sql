begin transaction;
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
	credentials_expired boolean,
	email varchar(255)
		constraint uk_6dotkott2kjsp8vw4d0m25fb7
			unique,
	enabled boolean,
	password varchar(255),
	username varchar(255)
		constraint uk_r43af9ap4edm43mmtq01oddj6
			unique
);

alter table users owner to pilsik;

create table user_authority
(
	user_id bigint not null
		constraint fkhi46vu7680y1hwvmnnuh4cybx
			references users,
	authority_id bigint not null
		constraint fkil6f39w6fgqh4gk855pstsnmy
			references authorities,
	constraint user_authority_pkey
		primary key (user_id, authority_id)
);

alter table user_authority owner to pilsik;

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
		constraint fkghuylkwuedgl2qahxjt8g41kb
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
	category_id bigint
		constraint fko6mla8j1p5bokt4dxrlmgwc28
			references categories
);

alter table events owner to pilsik;
end transaction;