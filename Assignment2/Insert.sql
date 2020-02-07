/*---------------Insert-----------------*/
INSERT INTO 
`person`(`id`, `username`, `password`, `first_name`, `last_name`, `email`)
VALUES
	(12, 'alice', 'alice', 'Alice', 'Wonder', 'alice@wonder.com'),
	(23, 'bob', 'bob', 'Bob', 'Marley', 'bob@marley.com'),
	(34, 'charlie', 'charlie', 'Charles', 'Garcia', 'chuch@garcia.com'),
	(45, 'dan', 'dan', 'Dan', 'Martin', 'dan@martin.com'),
	(56, 'ed', 'ed', 'Ed', 'Karaz', 'ed@kar.com');

INSERT INTO 
	`developer`(`id`, `developer_key`)
VALUES
	(12, '4321rewq'),
	(23, '5432trew'),
	(34, '6543ytre');

INSERT INTO `user`(`id`) VALUES (45), (56);

INSERT INTO 
	`website`(`id`, `name`, `description`, `created`, `updated`, `visits`)
VALUES
	(123, 'Facebook', 'an online social media and social networking service', CURDATE(), CURDATE(), 1234234),
	(234, 'Twitter', 'an online news and social networking service', CURDATE(), CURDATE(), 4321543),
	(345, 'Wikipedia', 'a free online encyclopedia', CURDATE(), CURDATE(), 3456654),
	(456, 'CNN', 'an American basic cable and satellite television news channel', CURDATE(), CURDATE(), 6543345),
	(567, 'CNET', 'an American media website that publishes reviews, news, articles, blogs, podcasts and videos on technology and consumer electronics', CURDATE(), CURDATE(), 5433455),
	(678, 'Gizmodo', 'a design, technology, science and science fiction website that also writes articles on politics', CURDATE(), CURDATE(), 4322345);


INSERT INTO `website_role`(`role`, `website_id`, `developer_id`)
VALUES
	# Facebook
	('owner', (SELECT `id` FROM `website` WHERE `name`='Facebook'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='Facebook'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='Facebook'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	# Twitter
	('owner', (SELECT `id` FROM `website` WHERE `name`='Twitter'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='Twitter'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='Twitter'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	# Wikipedia
	('owner', (SELECT `id` FROM `website` WHERE `name`='Wikipedia'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='Wikipedia'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='Wikipedia'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	# CNN
	('owner', (SELECT `id` FROM `website` WHERE `name`='CNN'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='CNN'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='CNN'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	# CNET
	('owner', (SELECT `id` FROM `website` WHERE `name`='CNET'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='CNET'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='CNET'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	# Gizmodo
	('owner', (SELECT `id` FROM `website` WHERE `name`='Gizmodo'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('editor', (SELECT `id` FROM `website` WHERE `name`='Gizmodo'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('admin', (SELECT `id` FROM `website` WHERE `name`='Gizmodo'), (SELECT `id` FROM `person` WHERE `username`='bob'));


INSERT INTO
	`page`(`id`, `title`, `description`, `website_id`, `created`, `updated`, `views`)
VALUES
	(123, 'Home', 'Landing page', (SELECT `id` FROM `website` WHERE `name`='CNET'), '2020-01-06', '2020-02-07', 123434),
	(234, 'About', 'Website description', (SELECT `id` FROM `website` WHERE `name`='Gizmodo'), '2020-01-06', '2020-02-07', 234545),
	(345, 'Contact', 'Addresses, phones, and contact info', (SELECT `id` FROM `website` WHERE `name`='Wikipedia'), '2020-01-06', '2020-02-07', 345656),
	(456, 'Preferences', 'Where users can configure their preferences', (SELECT `id` FROM `website` WHERE `name`='CNN'), '2020-01-06', '2020-02-07', 456776),
	(567, 'Profile', 'Users can configure their personal information', (SELECT `id` FROM `website` WHERE `name`='CNET'), '2020-01-06', '2020-02-07', 567878);


INSERT INTO `page_role`(`role`, `page_id`, `developer_id`)
VALUES
	# Home
	('editor', (SELECT `id` FROM `page` WHERE `title`='Home'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('reviewer', (SELECT `id` FROM `page` WHERE `title`='Home'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('writer', (SELECT `id` FROM `page` WHERE `title`='Home'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	# About
	('editor', (SELECT `id` FROM `page` WHERE `title`='About'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('reviewer', (SELECT `id` FROM `page` WHERE `title`='About'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('writer', (SELECT `id` FROM `page` WHERE `title`='About'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	# Contact
	('editor', (SELECT `id` FROM `page` WHERE `title`='Contact'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('reviewer', (SELECT `id` FROM `page` WHERE `title`='Contact'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('writer', (SELECT `id` FROM `page` WHERE `title`='Contact'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	# Preferences
	('editor', (SELECT `id` FROM `page` WHERE `title`='Preferences'), (SELECT `id` FROM `person` WHERE `username`='alice')),
	('reviewer', (SELECT `id` FROM `page` WHERE `title`='Preferences'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('writer', (SELECT `id` FROM `page` WHERE `title`='Preferences'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	# Profile
	('editor', (SELECT `id` FROM `page` WHERE `title`='Profile'), (SELECT `id` FROM `person` WHERE `username`='bob')),
	('reviewer', (SELECT `id` FROM `page` WHERE `title`='Profile'), (SELECT `id` FROM `person` WHERE `username`='charlie')),
	('writer', (SELECT `id` FROM `page` WHERE `title`='Profile'), (SELECT `id` FROM `person` WHERE `username`='alice'));

INSERT INTO `widget`(`dtype`, `id`, `name`, `text`, `order`, `page_id`)
VALUES
	('HEADING', 123, 'head123', 'Welcome', 0, (SELECT `id` FROM `page` WHERE `title`='Home')),
	('HTML', 234, 'post234', '<p>Lorem</p>', 0, (SELECT `id` FROM `page` WHERE `title`='About')),
	('HEADING', 345, 'head345', 'Hi', 1, (SELECT `id` FROM `page` WHERE `title`='Contact')),
	('HTML', 456, 'intro456', '<h1>Hi</h1>', 2, (SELECT `id` FROM `page` WHERE `title`='COntact'));
INSERT INTO 
	`widget`(`dtype`, `id`, `name`, `order`, `width`, `height`, `image_src`, `page_id`)
VALUES
	('IMAGE', 567, 'image345', 3, 50, 100, '/img/567.png',(SELECT `id` FROM `page` WHERE `title`='Contact'));
INSERT INTO 
	`widget`(`dtype`, `id`, `name`, `order`, `width`, `height`, `youtube_url`, `page_id`)
VALUES
	('YOUTUBE', 678, 'video456', 0, 400, 300, 'https://youtu.be/h67VX51QXiQ', (SELECT `id` FROM `page` WHERE `title`='Preferences'));


INSERT INTO 
	`phone`(`person_id`, `phone`, `primary`)
VALUES
	# Alice
	((SELECT `id` FROM `person` WHERE `username`='alice'), '123-234-3456', TRUE),
	((SELECT `id` FROM `person` WHERE `username`='alice'), '234-345-4566', FALSE),
	# Bob
	((SELECT `id` FROM `person` WHERE `username`='bob'), '345-456-5677', TRUE),
	# Charlie
	((SELECT `id` FROM `person` WHERE `username`='charlie'), '321-432-5435', TRUE),
	((SELECT `id` FROM `person` WHERE `username`='charlie'), '432-432-5433', FALSE),
	((SELECT `id` FROM `person` WHERE `username`='charlie'), '543-543-6544', FALSE);


INSERT INTO 
	`address`(`person_id`,`street1`,`city`, `zip`,`primary`)
VALUES
	# Alice
	((SELECT `id` FROM `person` WHERE `username`='alice'), '123 Adam St.', 'Alton', 01234, TRUE),
	((SELECT `id` FROM `person` WHERE `username`='alice'), '234 Birch St.', 'Boston', 02345, FALSE),
	# Bob
	((SELECT `id` FROM `person` WHERE `username`='bob'), '345 Charles St.', 'Chelms', 03455, TRUE),
	((SELECT `id` FROM `person` WHERE `username`='bob'), '456 Down St.', 'Dalton', 04566, FALSE),
	((SELECT `id` FROM `person` WHERE `username`='bob'), '543 East St.', 'Everett', 01112, FALSE),
	# Charlie
	((SELECT `id` FROM `person` WHERE `username`='charlie'), '654 Frank St.', 'Foulton', 04322, TRUE);

