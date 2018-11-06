# insert a twit with user mentions
insert into twit(userId, twit) values
((select userId from user where username = '<some username here>'), '<some twit here>');
insert into userMentions(originUserId, mentionedUserId, twitId) values
((select userId from user where username = '<origin username here>'), (select userId from user where username = '<mentioned username here>'), LAST_INSERT_ID();


# load sample twits with mentions
insert into twit(userId, twit) values 
(1, 'Hey @soeren1991, what\'s up?');
insert into usermention(originuserid, mentioneduserid, twitId) values
  ((select userid from user where username='jodo1991'),(select userid from user where username='soeren1991'), LAST_INSERT_ID());

insert into twit(userId, twit) values
(1, 'Just talking to myself...');

insert into twit(userId, twit) values
(2, '@jodo1991, not much, you?');
insert into usermention(originuserid, mentioneduserid, twitId) values
  ((select userid from user where username='soeren1991'),(select userid from user where username='jodo1991'), LAST_INSERT_ID());

insert into twit(userId, twit) values
(2, 'Now I\'m talking to myself');


# see all twits from or about a username
use twitterdb;
select distinct originUsername, originFullname, named_twits.twitid, twit, postedDateTime from
(select twitid, twit, twit.userid, username as originUsername, fullname as originFullname, postedDateTime from twit
	join user on twit.userid = user.userid) as named_twits
left join 
	(select username as mentionedUsername, twitid from usermention 
	join user on usermention.mentionedUserId = user.userid) as named_mentions
on named_twits.twitid = named_mentions.twitid
where originUsername = '<username>' or mentionedUsername = '<username>'
order by postedDateTime;


# manually inserting two test users
insert into user(fullname, username, email, birthdate,password,questionNo,answer) VALUES
('Joe d','jodo1991','jodo1991@gmail.com','1991-12-30','Password1',1,'Rascal'),
('Soeren O','soeren1991','soeren@gmail.com','1991-01-01','Password1',1,'Rufus')