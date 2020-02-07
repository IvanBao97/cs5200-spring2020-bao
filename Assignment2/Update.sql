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
