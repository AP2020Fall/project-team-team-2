create table game_log_summary
(
	id int auto_increment
		primary key,
	frequency int null,
	wins int null,
	score int null,
	last_play varchar(50) null,
	game_name varchar(30) null,
	game_logs varchar(20000) charset utf8 null,
	game_log_summary_id varchar(20) null
);

