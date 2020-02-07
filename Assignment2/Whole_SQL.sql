DROP SCHEMA IF EXISTS `cs5200_spring2020_bao`;
CREATE SCHEMA `cs5200_spring2020_bao`;
USE `cs5200_spring2020_bao`;

/*---------------Create-----------------*/
CREATE TABLE `person`(
`id` INT NOT NULL AUTO_INCREMENT,
`first_name`  VARCHAR(255) DEFAULT NULL,
`last_name` VARCHAR(255) DEFAULT NULL,
`username` VARCHAR(255) DEFAULT NULL,
`password` VARCHAR(255) DEFAULT NULL,
`email` VARCHAR(255) DEFAULT NULL,
`dob` DATE DEFAULT NULL,
PRIMARY KEY(`id`)
);

CREATE TABLE `developer`(
`id` INT PRIMARY KEY REFERENCES `person`(`id`) ON DELETE CASCADE,
`developer_key` VARCHAR(255) DEFAULT NULL,
CONSTRAINT `developer_person_generalization` FOREIGN KEY(`id`) REFERENCES `person`(`id`) ON DELETE CASCADE
);

CREATE TABLE `user`(
`id` INT PRIMARY KEY REFERENCES `person`(`id`) ON DELETE CASCADE,
`user_agreement` TINYINT(1) DEFAULT NULL,
CONSTRAINT `user_person_generalization` FOREIGN KEY(`id`) REFERENCES `person`(`id`) ON DELETE CASCADE
);

CREATE TABLE `website`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT DEFAULT NULL,
`name` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) DEFAULT NULL,
`created` DATE DEFAULT NULL,
`updated` DATE DEFAULT NULL,
`visits` INT DEFAULT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`developer_id`) REFERENCES `developer`(`id`) ON DELETE CASCADE
);

CREATE TABLE `page`(
`id` INT NOT NULL AUTO_INCREMENT,
`website_id` INT DEFAULT NULL,
`title` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) DEFAULT NULL,
`created` DATE DEFAULT NULL,
`updated` DATE DEFAULT NULL,
`views` INT DEFAULT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`website_id`) REFERENCES `website`(`id`) ON DELETE CASCADE
);

CREATE TABLE `dtype`(
`dtype` VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(`dtype`)
);
INSERT INTO `dtype`(`dtype`) VALUES('HEADING');
INSERT INTO `dtype`(`dtype`) VALUES('IMAGE');
INSERT INTO `dtype`(`dtype`) VALUES('YOUTUBE');
INSERT INTO `dtype`(`dtype`) VALUES('HTML');

CREATE TABLE `widget`(
`dtype` VARCHAR(255) DEFAULT '',
`id` INT NOT NULL AUTO_INCREMENT,
`page_id` INT DEFAULT NULL,
`name` VARCHAR(255) DEFAULT NULL,
`width` INT DEFAULT NULL,
`height` INT DEFAULT NULL,
`css_class` VARCHAR(255) DEFAULT NULL,
`css_style` VARCHAR(255) DEFAULT NULL,
`text` VARCHAR(255) DEFAULT NULL,
`order` INT DEFAULT NULL,
`youtube_url` VARCHAR(255) DEFAULT NULL,
`youtube_shareble` TINYINT(1) DEFAULT NULL,
`youtube_expandable` TINYINT(1) DEFAULT NULL,
`image_src` VARCHAR(255) DEFAULT NULL,
`heading_size` INT DEFAULT 2,
`html` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`dtype`) REFERENCES `dtype`(`dtype`) ON DELETE CASCADE,
FOREIGN KEY(`page_id`) REFERENCES `page`(`id`) ON DELETE CASCADE
);

CREATE TABLE `address`(
`id` INT NOT NULL AUTO_INCREMENT,
`person_id` INT NOT NULL,
`street1` VARCHAR(255) NOT NULL,
`street2` VARCHAR(255) DEFAULT NULL,
`city` VARCHAR(255) DEFAULT NULL,
`state` VARCHAR(255) DEFAULT NULL,
`zip` INT DEFAULT NULL,
`primary` TINYINT(1) DEFAULT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`person_id`) REFERENCES `person`(`id`) ON DELETE CASCADE
);

CREATE TABLE `phone`(
`id` INT NOT NULL AUTO_INCREMENT,
`person_id` INT NOT NULL,
`phone` VARCHAR(255) NOT NULL,
`primary` TINYINT(1) DEFAULT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`person_id`) REFERENCES `person`(`id`) ON DELETE CASCADE
);

CREATE TABLE `role`(
`role` VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(`role`)
);
INSERT INTO `role`(`role`) VALUES('owner');
INSERT INTO `role`(`role`) VALUES('admin');
INSERT INTO `role`(`role`) VALUES('writer');
INSERT INTO `role`(`role`) VALUES('editor');
INSERT INTO `role`(`role`) VALUES('reviewer');

CREATE TABLE `page_role`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`page_id` INT NOT NULL,
`role` VARCHAR(255) NOT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`developer_id`) REFERENCES `developer`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`page_id`) REFERENCES `page`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`role`) REFERENCES `role`(`role`) ON DELETE CASCADE
);

CREATE TABLE `website_role`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`website_id` INT NOT NULL,
`role` VARCHAR(255) NOT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`developer_id`) REFERENCES `developer`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`website_id`) REFERENCES `website`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`role`) REFERENCES `role`(`role`) ON DELETE CASCADE
);

CREATE TABLE `priviledge`(
`priviledge` VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(`priviledge`)
);
INSERT INTO `priviledge`(`priviledge`) VALUES('create');
INSERT INTO `priviledge`(`priviledge`) VALUES('read');
INSERT INTO `priviledge`(`priviledge`) VALUES('update');
INSERT INTO `priviledge`(`priviledge`) VALUES('delete');

CREATE TABLE `page_priviledge`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`page_id` INT NOT NULL,
`priviledge` VARCHAR(255) NOT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`developer_id`) REFERENCES `developer`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`page_id`) REFERENCES `page`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`priviledge`) REFERENCES `priviledge`(`priviledge`) ON DELETE CASCADE
);

CREATE TABLE `website_priviledge`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`website_id` INT NOT NULL,
`priviledge` VARCHAR(255) NOT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY(`developer_id`) REFERENCES `developer`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`website_id`) REFERENCES `website`(`id`) ON DELETE CASCADE,
FOREIGN KEY(`priviledge`) REFERENCES `priviledge`(`priviledge`) ON DELETE CASCADE
);



/*---------------Trigger-----------------*/
Delimiter $
CREATE TRIGGER `after_website_role_insert` AFTER INSERT ON `website_role` FOR EACH ROW
BEGIN
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`),
        ('delete', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
		('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`);
	END IF;
END $

CREATE TRIGGER `after_website_role_update` AFTER UPDATE ON `website_role` FOR EACH ROW
BEGIN
	DELETE FROM `website_priviledge`
		WHERE `developer_id`=NEW.`developer_id` AND `website_id`=NEW.`website_id`;
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`),
        ('delete', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
		('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `website_priviledge`(`priviledge`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`);
	END IF;
END $

CREATE TRIGGER `after_website_role_delete` BEFORE DELETE ON `website_role` FOR EACH ROW
BEGIN
	DELETE FROM `website_priviledge`
		WHERE `developer_id`=OLD.`developer_id` AND `website_id`=OLD.`website_id`;
END $

CREATE TRIGGER `after_page_role_insert` AFTER INSERT ON `page_role` FOR EACH ROW
BEGIN
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`),
        ('delete', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
		('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`);
	END IF;
END $

CREATE TRIGGER `after_page_role_update` BEFORE UPDATE ON `page_role` FOR EACH ROW
BEGIN
	DELETE FROM `page_priviledge`
		WHERE `developer_id`=NEW.`developer_id` AND `page_id`= NEW.`page_id`; 
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`),
        ('delete', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
		('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `page_priviledge`(`priviledge`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`);
	END IF;
END $

CREATE TRIGGER `after_page_role_delete` BEFORE DELETE ON `page_role` FOR EACH ROW
BEGIN
	DELETE FROM `page_priviledge`
		WHERE `developer_id`=OLD.`developer_id` AND `page_id`=OLD.`page_id`;
END $

Delimiter ;



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



/*---------------View-----------------*/
CREATE VIEW `developer_roles_and_priviledge` AS 
	SELECT
		`person`.`first_name` AS `first_name`,
        `person`.`last_name` AS `last_name`,
        `person`.`username` AS `username`,
        `person`.`email` AS `email`,
        `website`.`name` AS `web_name`,
        `website`.`visits` AS `visits`,
        `website`.`updated` AS `web_updated`,
        `website_role`.`role` AS `web_role`,
        `website_priviledge`.`priviledge` AS `web_priviledge`,
        `page`.`title` AS `title`,
        `page`.`views` AS `views`,
        `page`.`updated` AS `p_update`,
		`page_role`.`role` AS `p_role`,
        `page_priviledge`.`priviledge` AS `p_priviledge`
	FROM
		`person` JOIN `developer` JOIN `website` JOIN `website_role` JOIN `website_priviledge` JOIN `page` JOIN `page_role` JOIN `page_priviledge`
	WHERE
		`person`.`id` = `developer`.`id`
		AND `website`.`id` = `website_role`.`website_id` 
        AND `website`.`id` = `website_priviledge`.`website_id` 
        AND `website_role`.`developer_id` = `developer`.`id` 
        AND `website_priviledge`.`developer_id` = `developer`.`id`
        AND `website`.`id` = `page`.`website_id` 
        AND `page_role`.`developer_id` = `developer`.`id`
        AND `page_role`.`page_id` = `page`.`id` 
        AND `page_priviledge`.`developer_id` = `developer`.`id` 
        AND `page_priviledge`.`page_id` = `page`.`id`;
    
SELECT * FROM `developer_roles_and_priviledge`;



/*---------------Query-----------------*/
## 1.Retrieve developers
# a) Retrieve all developers
SELECT * FROM  `person` JOIN `developer` ON `person`.`id` = `developer`.`id`;

# b) Retrieve a developer with id equal to 34
SELECT * FROM  `person` JOIN `developer` ON `person`.`id` = `developer`.`id` WHERE `person`.`id` = 34;

# c) Retrieve all developers who have a role in Twitter other than owner
SELECT * FROM 
	`person` JOIN `developer` JOIN `website` JOIN `website_role`
ON 
	`person`.`id` = `developer`.`id` 
    AND `website_role`.`developer_id` = `developer`.`id`
    AND `website_role`.`website_id` = `website`.`id`
WHERE
	`website`.`name` = 'Twitter'
    AND `website_role`.`role` != 'owner';

# d) Retrieve all developers who are page reviewers of pages with less than 300000 visits
SELECT * FROM 
	`person` JOIN `developer` JOIN `page` JOIN `page_role`
ON 
	`person`.`id` = `developer`.`id` 
    AND `page_role`.`developer_id` = `developer`.`id`
    AND `page_role`.`page_id` = `page`.`id`
WHERE
	`page`.`views` < 300000
    AND `page_role`.`role` = 'reviewer';

# e) Retrieve the writer developer who added a heading widget to CNET's home page
SELECT * FROM
	`person` JOIN `developer` JOIN `page` JOIN `website` JOIN `page_role` JOIN `widget`
ON
	`person`.`id` = `developer`.`id`
    AND `page_role`.`developer_id` = `developer`.`id`
    AND `page_role`.`page_id` = `page`.`id`
    AND `page`.`website_id` = `website`.`id`
    AND `widget`.`page_id` = `page`.`id`
WHERE
	`page_role`.`role` = 'writer'
    AND `website`.`name` = 'CNET'
	AND `widget`.`dtype` = 'HEADING'
    AND `page`.`title` = 'Home';


## 2.Retrieve websites
# a) Retrieve the website with the least number of visits 
SELECT * FROM `website` WHERE `website`.`visits`= (SELECT MIN(`website`.`visits`) FROM `website`);

# b) Retrieve the name of a website whose id is 678 
SELECT `name` FROM `website` WHERE `website`.`id` = 678;

# c) Retrieve all websites with videos reviewed by bob 
SELECT * FROM 
	`person` JOIN `developer` JOIN `website` JOIN `page_role` JOIN `widget` JOIN `page`
ON 
	`person`.`id` = `developer`.`id`
    AND `page_role`.`developer_id` = `developer`.`id` 
	AND `page`.`id` = `page_role`.`page_id`
    AND `page`.`website_id` = `website`.`id`
    AND `page`.`id` = `widget`.`page_id`
WHERE 
	`person`.`username` = 'bob'
    AND `widget`.`dtype` = 'YOUTUBE';
    
# d) Retrieve all websites where alice is an owner 
SELECT * FROM 
	`person` JOIN `developer` JOIN `website` JOIN `website_role`
ON 
	`person`.`id` = `developer`.`id`
    AND `website_role`.`developer_id` = `developer`.`id` 
	AND `website_role`.`website_id` = `website`.`id`
WHERE 
	`person`.`username` = 'alice'
    AND `website_role`.`role` = 'owner';

# e) Retrieve all websites where charlie is an admin and get more than 6000000 visits
SELECT * FROM 
	`person` JOIN `developer` JOIN `website` JOIN `website_role`
ON 
	`person`.`id` = `developer`.`id`
    AND `website_role`.`developer_id` = `developer`.`id` 
	AND `website_role`.`website_id` = `website`.`id`
WHERE 
	`person`.`username` = 'charlie'
    AND `website_role`.`role` = 'admin'
    AND `website`.`visits` > 6000000;


## 3.Retrieve pages
# a) Retrieve the page with the most number of views 
SELECT * FROM `page` WHERE `page`.`views` = (SELECT MAX(`page`.`views`) FROM `page`);

# b) Retrieve the title of a page whose id is 234 
SELECT `title` FROM `page` WHERE `page`.`id` = 234;

# c) Retrieve all pages where alice is an editor 
SELECT * FROM
	`person` JOIN `developer` JOIN `page` JOIN `page_role`
ON
	`person`.`id` = `developer`.`id`
    AND `page_role`.`page_id` = `page`.`id`
    AND `page_role`.`developer_id` = `developer`.`id`
WHERE
	`person`.`username` = 'alice'
    AND `page_role`.`role` = 'editor';

# d) Retrieve the total number of page views in CNET 
SELECT SUM(`page`.`views`) AS `Sum # of page views in CNET` FROM
	`page` JOIN `website`
ON
    `page`.`website_id` = `website`.`id`
WHERE
	`website`.`name` = 'CNET';
    
# e) Retrieve the average number of page views in the Website Wikipedia
SELECT AVG(`page`.`views`) AS `Ave # of page views in Wikipedia` FROM
	`page` JOIN `website`
ON
    `page`.`website_id` = `website`.`id`
WHERE
	`website`.`name` = 'Wikipedia';


## 4.Retrieve widgets
# a) Retrieve all widgets in CNET's Home page 
SELECT * FROM
	`widget` JOIN `page` JOIN `website`
ON
	`page`.`website_id` = `website`.`id`
    AND `widget`.`page_id` = `page`.`id`
WHERE
	`website`.`name` = 'CNET'
    AND `page`.`title` = 'Home';

# b) Retrieve all youtube widgets in CNN 
SELECT * FROM 
	`widget` JOIN `website` JOIN `page`
ON
	`widget`.`page_id` = `page`.`id`
	AND `page`.`website_id` = `website`.`id`
WHERE 
	`website`.`name` = 'CNN'
	AND `widget`.`dtype` = 'YOUTUBE';

# c) Retrieve all image widgets on pages reviewed by Alice 
SELECT * FROM 
	`widget` JOIN `developer` JOIN `page` JOIN `person` JOIN `page_role`
ON
	`person`.`id` = `developer`.`id`
    AND `widget`.`page_id` = `page`.`id`
	AND `page`.`id` = `page_role`.`page_id`
	AND `developer`.`id` = `page_role`.`developer_id`
WHERE 
	`person`.`first_name` = 'Alice'
	AND `widget`.`dtype` = 'IMAGE';
    
# d) Retrieve how many widgets are in Wikipedia
SELECT COUNT(*) AS `Widgets' # in Wikipedia` FROM 
	`website` JOIN `page` JOIN `widget`
ON
	`website`.`id` = `page`.`website_id`
	AND `widget`.`page_id` = `page`.`id`
WHERE 
	`website`.`name` = 'Wikipedia';
		

## 5.To verify the page and website triggers written earlier function properly:
# a) Retrieve the names of all the websites where Bob has DELETE privileges. 
SELECT `website`.`name` FROM
	`person` JOIN `developer` JOIN `website` JOIN `website_priviledge`
ON 
	`person`.`id` = `developer`.`id`
    AND `website_priviledge`.`developer_id` = `developer`.`id`
    AND `website_priviledge`.`website_id` = `website`.`id`
WHERE
	`person`.`username` = 'bob'
    AND `website_priviledge`.`priviledge` = 'delete';

# b) Retrieve the names of all the pages where Charlie has CREATE privileges.
SELECT `page`.`title` FROM 
	`person` JOIN `developer` JOIN `page` JOIN `page_priviledge`
ON
	`person`.`id` = `developer`.`id`
    AND `page_priviledge`.`developer_id` = `developer`.`id`
	AND `page_priviledge`.`page_id` = `page`.`id`
WHERE 	
	`person`.`first_name` = 'Charles'	
	AND `page_priviledge`.`priviledge` = 'create';



/*---------------Update-----------------*/
SET SQL_SAFE_UPDATES = 0;

# 1.Update developer - Update Charlie's primary phone number to 333-444-5555 
UPDATE `phone` JOIN `person` 
	ON `phone`.`person_id` = `person`.`id` AND `person`.`username` = 'charlie' 
SET `phone` = '333-444-5555'
	WHERE `phone`.`primary` = TRUE;

# 2.Update widget - Update the relative order of widget head345 on the page so that it's new order is 3. Note that the other widget's order needs to update as well 
UPDATE `widget` w, (SELECT `page_id` FROM `widget` WHERE `name` = 'head345') p_id
    SET w.`order` = CASE
    WHEN w.`name` = 'head345' THEN 3
    ELSE w.`order`-1
    END
WHERE w.`page_id` = p_id.`page_id`;

# 3.Update page - Append 'CNET - ' to the beginning of all CNET's page titles 
UPDATE `page` p, (SELECT `id` FROM `website` WHERE `name` = 'CNET') w_id
	SET p.`title` = CONCAT('CNET', ' ', '-', ' ', p.`title`)
    WHERE p.`website_id` = w_id.`id`;

# 4.Update roles - Swap Charlie's and Bob's role in CNET's Home page
UPDATE `page_role` pr,
	(SELECT `id` FROM `person` WHERE `username` = 'bob') b_id,
    (SELECT `id` FROM `person` WHERE `username` = 'charlie') c_id,
    (SELECT `id` FROM `page` WHERE `title` = 'CNET - Home') p_id
	SET pr.`developer_id` = (
		CASE
			WHEN pr.`developer_id` = b_id.`id` THEN c_id.`id`
			WHEN pr.`developer_id` = c_id.`id` THEN b_id.`id`
            ELSE pr.`developer_id`
		END)
	WHERE pr.`page_id` = p_id.`id`;

SET SQL_SAFE_UPDATES = 1;



/*---------------Delete-----------------*/
SET SQL_SAFE_UPDATES = 0;

# 1.Delete developer - Delete Alice's primary address 
DELETE `address`.* FROM
	`person`, `address`
WHERE
	`person`.`id` = `address`.`person_id`
	AND `person`.`username` = 'alice'
    AND `address`.`primary` = TRUE;

# 2.Delete widget - Remove the last widget in the Contact page. The last widget is the one with the highest value in the order field 
DELETE `widget`.* FROM
	`widget`, `page`, (SELECT MAX(`widget`.`order`) AS `max_order` FROM `widget`) AS `x`
WHERE
	`widget`.`page_id` = `page`.`id`
    AND `page`.`title` = 'Contact'
    AND `widget`.`order` = `x`.`max_order`;
        
# 3.Delete page - Remove the last updated page in Wikipedia 
DELETE `page`.* FROM 
	`page`, `website`, (SELECT MAX(`page`.`updated`) AS `last_updated` FROM `page`) AS `x`
WHERE
	`page`.`website_id` = `website`.`id`
    AND `website`.`name` = 'Wikipedia'
    AND `page`.`updated` = `x`.`last_updated`;

# 4.Delete website - Remove the CNET website, as well as all related roles and privileges relating developers to the Website and Pages
DELETE FROM `website` WHERE `name` = 'CNET'; 

SET SQL_SAFE_UPDATES = 1;
