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
