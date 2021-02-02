create table events
(
	id int auto_increment
		primary key,
	game_event int null,
	start_date int null,
	end_date int null,
	score int null,
	avatar_address varchar(20) null,
	event_id varchar(20) null,
	comment varchar(255) null
);

