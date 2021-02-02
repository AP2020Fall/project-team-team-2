create table players
(
	id int auto_increment
		primary key,
	first_name varchar(30) null,
	last_name varchar(30) null,
	username varchar(30) null,
	hash_password varchar(50) null,
	email_address varchar(60) null,
	phone_number varchar(50) null,
	register_date int null,
	avatar_address varchar(120) null,
	player_id varchar(10) null,
	bio varchar(255) null,
	is_admin int default 0 null
);

