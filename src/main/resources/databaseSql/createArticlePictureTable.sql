CREATE TABLE `article_picture` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `article_id` INT not null,
  `path`   VARCHAR(64) not null
) COMMENT='文章' engine=InnoDB default charset=utf8;