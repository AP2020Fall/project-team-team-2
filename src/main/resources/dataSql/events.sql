create table events
(
	id int auto_increment
		primary key,
	game_name varchar(20) null,
	start_date varchar(20) null,
	end_date varchar(20) null,
	score int null,
	avatar_address varchar(100) null,
	event_id varchar(20) null,
	event_comment varchar(255) null
);

