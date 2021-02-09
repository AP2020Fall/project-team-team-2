create table friend_requests
(
	id int auto_increment
		primary key,
	friend_id varchar(30) null,
	player_id varchar(30) null,
	friend_request_id varchar(30) null,
	status enum('NOTANSWERED', 'ACCEPTED', 'DECLINED') null
);

