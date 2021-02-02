create table game_log_summary
(
	id int auto_increment
		primary key,
	frequency int null,
	wins int null,
	scores int null,
	last_play varchar(30) null,
	game_name varchar(30) null,
	game_logs varchar(20000) charset utf8 null
);

