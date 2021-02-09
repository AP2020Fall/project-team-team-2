create table game
(
	id int auto_increment
		primary key,
	name varchar(30) null,
	game_id varchar(20) null,
	details varchar(150) null,
	avatar_address varchar(250) null,
	play_log_id varchar(3000) null,
	scoreboard varchar(4000) null
);

