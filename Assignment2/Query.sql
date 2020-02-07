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

