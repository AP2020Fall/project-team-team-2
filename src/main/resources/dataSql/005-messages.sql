create table messages
(
	id int auto_increment
		primary key,
	account_id varchar(20) null,
	admin_messages varchar(50) null,
	message_id varchar(30) null,
	timestamp varchar(50) null
);

