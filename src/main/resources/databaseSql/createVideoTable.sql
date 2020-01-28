CREATE TABLE `video` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(128) not null,
  `video`   VARCHAR(64) not null,
  `praise_count`    int not null,
  `create_by`   int not null,
  `create_at`   VARCHAR(64) not null
) COMMENT='短视频' engine=InnoDB default charset=utf8mb4;