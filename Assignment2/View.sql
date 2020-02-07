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
