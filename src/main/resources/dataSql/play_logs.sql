create table play_logs
(
	id int auto_increment
		primary key,
	game_name varchar(50) null,
	play_log_id varchar(20) null,
	winner varchar(50) null,
	usernames varchar(20000) charset utf8 null,
	timestamp varchar(50) null
);

