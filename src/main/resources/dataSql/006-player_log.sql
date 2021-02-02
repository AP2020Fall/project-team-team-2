create table player_log
(
	id int auto_increment
		primary key,
	player_log_id varchar(30) null,
	usernames varchar(20000) charset utf8 null,
	winner varchar(50) null,
	time varchar(50) null,
	game_name varchar(50) null
);

