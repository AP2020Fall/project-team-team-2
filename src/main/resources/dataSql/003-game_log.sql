create table game_log
(
	id int auto_increment
		primary key,
	game_name varchar(20) null,
	player_username varchar(30) null,
	enemies varchar(200) null,
	status enum('WON', 'LOST', 'DRAWN', 'FORFEITED') null
);

