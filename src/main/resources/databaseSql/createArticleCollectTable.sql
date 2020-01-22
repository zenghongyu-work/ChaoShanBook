CREATE TABLE `article_collect` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `article_id`   INT not null,
  `user_id` INT not null,
  `collect_time`    VARCHAR(64) not null
) COMMENT='文章收藏信息' engine=InnoDB default charset=utf8;