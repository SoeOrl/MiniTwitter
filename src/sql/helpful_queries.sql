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


# see all twits for a user
SET @username = '<some-username>';
SELECT DISTINCT
    originUsername,
    originFullname,
    named_twits.twitid,
    twit,
    postedDateTime
FROM
    (SELECT 
        twitid,
            twit,
            twit.userid,
            username AS originUsername,
            fullname AS originFullname,
            postedDateTime
    FROM
        twit
    JOIN user ON twit.userid = user.userid) AS named_twits
        LEFT JOIN
    (SELECT 
        username AS mentionedUsername, twitid
    FROM
        usermention
    JOIN user ON usermention.mentionedUserId = user.userid) AS named_mentions ON named_twits.twitid = named_mentions.twitid
WHERE
    originUsername = @username
        OR mentionedUsername = @username
        OR named_twits.userid IN (SELECT 
            followedId
        FROM
            follows
        WHERE
            userId = (SELECT 
                    userid
                FROM
                    user
                WHERE
                    username = @username))
ORDER BY postedDateTime DESC;


# manually inserting two test users
insert into user(fullname, username, email, birthdate,password,questionNo,answer) VALUES
('Joe d','jodo1991','jodo1991@gmail.com','1991-12-30','Password1',1,'Rascal'),
('Soeren O','soeren1991','soeren@gmail.com','1991-01-01','Password1',1,'Rufus')