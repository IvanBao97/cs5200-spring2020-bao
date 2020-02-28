# Stored procedure
USE `JDBC`;

DROP PROCEDURE IF EXISTS getUnansweredQuestions;
DELIMITER //
CREATE PROCEDURE getUnansweredQuestions()
BEGIN
	SELECT q.`id`, q.`text`, MAX(`ca`.`count`) 
    FROM `question` q, `answer` a, (SELECT  COUNT(a.`id`) AS `count` FROM `question` q, `answer` a 
									WHERE a.`question's_id`=q.`id` AND a.`correct_answer` = FALSE) AS `ca`
    GROUP BY q.`module`;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS endorsedUsersForWeek;
DELIMITER //
CREATE PROCEDURE endorsedUsersForWeek(IN week_begin DATE, IN week_end DATE)
BEGIN
	SELECT p.`first_name` as `First Name`, p.`last_name` as `Last Name`, COUNT(a.`correct_answer`) AS `number of correct answer`
    FROM `person` p, `answer` a, `user` u
    WHERE a.`posted_on` > week_begin AND a.`posted_on` < week_end
		AND p.`id` = u.`id` AND p.`id` = a.`posted_by_user_id`
        AND a.`correct_answer` = TRUE
	GROUP BY(p.`first_name`)
    LIMIT 5;
END //
DELIMITER ;