DROP SCHEMA IF EXISTS `JDBC`;    
CREATE SCHEMA `JDBC`;
USE `JDBC`;


CREATE TABLE person(
`id` INT NOT NULL AUTO_INCREMENT,
`first_name`  VARCHAR(255) DEFAULT NULL,
`last_name` VARCHAR(255) default null,
`username` varchar(255) default null,
`password` varchar(255) default null,
`email` varchar(255) default null,
`dob` Date default null,
`address` VARCHAR(255) DEFAULT NULL,
`phone` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY(id)
);

CREATE TABLE developer(
`id` INT PRIMARY KEY REFERENCES person(id) 
	ON DELETE CASCADE ON UPDATE CASCADE,
`developer_key` VARCHAR(255) DEFAULT NULL,
CONSTRAINT developer_person_generalization
	FOREIGN KEY(id) REFERENCES person(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `user`(
`id` INT PRIMARY KEY REFERENCES person(id) 
	ON DELETE CASCADE ON UPDATE CASCADE,
`approved_user` BOOLEAN DEFAULT NULL,
`user_agreement` BOOLEAN DEFAULT NULL,
CONSTRAINT user_person_generalization
	FOREIGN KEY(id) REFERENCES person(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE website(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT DEFAULT NULL,
`name` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) DEFAULT NULL,
`created` DATE DEFAULT NULL,
`updated` DATE DEFAULT NULL,
`visits` INT DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`developer_id`) 
	REFERENCES developer(id)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `page`(
`id` INT NOT NULL AUTO_INCREMENT,
`website_id` INT DEFAULT NULL,
`title` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) DEFAULT NULL,
`created` DATE DEFAULT NULL,
`updated` DATE DEFAULT NULL,
`views` INT DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`website_id`) 
	REFERENCES website(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `dtype`(
dtype VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(dtype)
);
INSERT INTO dtype(dtype) VALUES('HEADING');
INSERT INTO dtype(dtype) VALUES('IMAGE');
INSERT INTO dtype(dtype) VALUES('YOUTUBE');
INSERT INTO dtype(dtype) VALUES('HTML');


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
`youtube_shareable` BOOLEAN DEFAULT NULL,
`youtube_expandable` BOOLEAN DEFAULT NULL,
`image_src` VARCHAR(255) DEFAULT NULL,
`heading_size` INT DEFAULT 2,
`html` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY(dtype) 
	REFERENCES dtype(dtype)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(page_id) 
	REFERENCES `page`(id)
	ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `address`(
`id` INT NOT NULL AUTO_INCREMENT,
`person_id` INT NOT NULL,
`street1` VARCHAR(255) NOT NULL,
`street2` VARCHAR(255) DEFAULT NULL,
`city` VARCHAR(255) DEFAULT NULL,
`state` VARCHAR(255) DEFAULT NULL,
`zip` INT DEFAULT NULL,
`primary` BOOL DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`person_id`) 
	REFERENCES person(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `phone`(
`id` INT NOT NULL AUTO_INCREMENT,
`person_id` INT NOT NULL,
`phone` VARCHAR(255) NOT NULL,
`primary` BOOL DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`person_id`) 
	REFERENCES person(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


##  protable enumeration
CREATE TABLE `role`(
role VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(role)
);
INSERT INTO role(role) VALUES('owner');
INSERT INTO role(role) VALUES('admin');
INSERT INTO role(role) VALUES('writer');
INSERT INTO role(role) VALUES('editor');
INSERT INTO role(role) VALUES('reviewer');


CREATE TABLE `page_role`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`page_id` INT NOT NULL,
`role` VARCHAR(255) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`developer_id`) 
	REFERENCES developer(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`page_id`) 
	REFERENCES `page`(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`role`) 
	REFERENCES `role`(role)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `website_role`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`website_id` INT NOT NULL,
`role` VARCHAR(255) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`developer_id`) 
	REFERENCES developer(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`website_id`) 
	REFERENCES `website`(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`role`) 
	REFERENCES `role`(role)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `privilege`(
privilege VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(privilege)
);
INSERT INTO privilege(privilege) VALUES('create');
INSERT INTO privilege(privilege) VALUES('read');
INSERT INTO privilege(privilege) VALUES('update');
INSERT INTO privilege(privilege) VALUES('delete');


CREATE TABLE `page_privilege`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`page_id` INT NOT NULL,
`privilege` VARCHAR(255) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`developer_id`) 
	REFERENCES developer(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`page_id`) 
	REFERENCES `page`(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`privilege`) 
	REFERENCES `privilege`(privilege)
    ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `website_privilege`(
`id` INT NOT NULL AUTO_INCREMENT,
`developer_id` INT NOT NULL,
`website_id` INT NOT NULL,
`privilege` VARCHAR(255) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(`developer_id`) 
	REFERENCES developer(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`website_id`) 
	REFERENCES `website`(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`privilege`) 
	REFERENCES `privilege`(privilege)
    ON DELETE CASCADE ON UPDATE CASCADE
);


# trigger 
# create website privilege AFTER role created
delimiter $$
CREATE TRIGGER `after_website_role_insert`
	AFTER INSERT ON `website_role`
	FOR EACH ROW
BEGIN
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`),
        ('delete', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
		('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`);
	END IF;
END$$


# update website privilege after role updated
CREATE TRIGGER `after_website_role_update`
	AFTER UPDATE ON `website_role`
    FOR EACH ROW
BEGIN
	DELETE FROM `website_privilege`
		WHERE `developer_id`=NEW.`developer_id` AND `website_id`=NEW.`website_id`;
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`),
        ('delete', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
		('create', NEW.`website_id`, NEW.`developer_id`),
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`),
        ('update', NEW.`website_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `website_privilege`(`privilege`, `website_id`, `developer_id`)
        VALUES
        ('read', NEW.`website_id`, NEW.`developer_id`);
	END IF;
END$$


# delete website privilege after delete role
CREATE TRIGGER `after_website_role_delete`
	AFTER DELETE ON `website_role`
    FOR EACH ROW
BEGIN
	DELETE FROM `website_privilege`
		WHERE `developer_id`=OLD.`developer_id` AND `website_id`=OLD.`website_id`;
END$$


# create website privilege after roles created
CREATE TRIGGER `after_page_role_insert`
	AFTER INSERT ON `page_role`
	FOR EACH ROW
BEGIN
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`),
        ('delete', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
		('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`);
	END IF;
END $$


# update page privilege after role updated
CREATE TRIGGER `after_page_role_update`
	BEFORE UPDATE ON `page_role`
	FOR EACH ROW
BEGIN
	DELETE FROM `page_privilege`
		WHERE `developer_id`=NEW.`developer_id` AND `page_id`= NEW.`page_id`; 
	IF NEW.`role` = 'owner' OR NEW.`role` = 'admin' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`),
        ('delete', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'writer' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
		('create', NEW.`page_id`, NEW.`developer_id`),
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'editor' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`),
        ('update', NEW.`page_id`, NEW.`developer_id`);
	ELSEIF NEW.`role` = 'reviewer' THEN
		INSERT INTO `page_privilege`(`privilege`, `page_id`, `developer_id`)
        VALUES
        ('read', NEW.`page_id`, NEW.`developer_id`);
	END IF;
END $$


# delete page privilege after role deleted
CREATE TRIGGER `after_page_role_delete`
	AFTER DELETE ON `page_role`
    FOR EACH ROW
BEGIN
	DELETE FROM `page_privilege`
		WHERE `developer_id`=OLD.`developer_id` AND `page_id`=OLD.`page_id`;
END$$

delimiter ;


# Create Answer, Question, User, Module Table

CREATE TABLE IF NOT EXISTS `module`(
module VARCHAR(255) NOT NULL DEFAULT '',
PRIMARY KEY(module)
);
INSERT INTO module(module) VALUES('Project1');
INSERT INTO module(module) VALUES('Project2');
INSERT INTO module(module) VALUES('Assignment1');
INSERT INTO module(module) VALUES('Assignment2');
INSERT INTO module(module) VALUES('Quiz1');
INSERT INTO module(module) VALUES('Exam');
INSERT INTO module(module) VALUES('Logistics');

CREATE TABLE `question`(
`id` INT PRIMARY KEY REFERENCES `widget`(`id`),
`asked_by_user_id` INT DEFAULT NULL,
`text` VARCHAR(255) DEFAULT NULL,
`posted_on` DATE DEFAULT NULL,
`length` INT DEFAULT NULL,
`views` INT DEFAULT NULL,
`endorsed_by_instructor` BOOLEAN DEFAULT NULL,
`module` VARCHAR(20) DEFAULT '',
FOREIGN KEY(`asked_by_user_id`)
	REFERENCES `user`(`id`)
    ON DELETE CASCADE,
FOREIGN KEY(`module`)
	REFERENCES `module`(`module`)
    ON DELETE CASCADE
);

CREATE TABLE `answer`(
`id` INT PRIMARY KEY REFERENCES `widget`(`id`),
`question's_id` INT DEFAULT NULL,
`posted_by_user_id` INT DEFAULT NULL,
`text` VARCHAR(255) DEFAULT NULL,
`posted_on` DATE DEFAULT NULL,
`correct_answer` BOOLEAN DEFAULT NULL,
`up_votes` INT DEFAULT NULL,
`down_votes` INT DEFAULT NULL,
FOREIGN KEY(`question's_id`)
	REFERENCES `question`(`id`)
    ON DELETE CASCADE,
FOREIGN KEY(`posted_by_user_id`) 
	REFERENCES `user`(`id`)
    ON DELETE CASCADE
);