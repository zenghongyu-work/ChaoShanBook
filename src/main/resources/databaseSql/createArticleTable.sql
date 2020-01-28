CREATE TABLE `article` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(128) not null,
  `content`   VARCHAR(5000) not null,
  `praise_count`    int not null,
  `create_by`   int not null,
  `create_at`   VARCHAR(64) not null
) COMMENT='文章' engine=InnoDB default charset=utf8mb4;