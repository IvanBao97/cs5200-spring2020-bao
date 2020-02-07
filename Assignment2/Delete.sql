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