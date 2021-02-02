create table game
(
	id int auto_increment
		primary key,
	name varchar(30) null,
	game_id varchar(20) null,
	details varchar(150) null,
	avatar_address varchar(250) null,
	player_log_id varchar(30) null
);

