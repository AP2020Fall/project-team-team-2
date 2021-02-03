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
	register_date varchar(25) null,
	avatar_address varchar(120) null,
	bio varchar(255) null,
	is_admin int default 0 null,
	player_id varchar(10) null,
	admin_messages varchar(1000) null,
	money varchar(10) null,
	game_log_summaries varchar(2000) null,
	friends varchar(2000) null,
	received_friend_request varchar(2000) null,
	sent_friend_request varchar(2000) null,
	favourite_game varchar(2000) null,
	player_messages varchar(2000) null,
	suggestions varchar(2000) null,
	status varchar(30) null
);

