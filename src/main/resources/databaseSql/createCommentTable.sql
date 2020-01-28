CREATE TABLE `comment` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `content` VARCHAR(5000) not null,
  `user_id`    int not null,
  `user_nickname` VARCHAR(64) not null,
  `user_icon` VARCHAR(64) not null,
  `relation_id`    int not null,
  `type` VARCHAR(8) not null,
  `praise_count`    int not null,
  `comment_id`    int not null,
  `reply_id`    int not null,
  `to_user_id`    int not null,
  `to_user_nickname` VARCHAR(64) not null,
  `to_user_icon` VARCHAR(64) not null,
  `create_at`   VARCHAR(64) not null,
  `status` VARCHAR(8) not null
) COMMENT='评论' engine=InnoDB default charset=utf8mb4;