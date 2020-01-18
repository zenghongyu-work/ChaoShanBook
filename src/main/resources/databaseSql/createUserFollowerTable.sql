CREATE TABLE `user_follower` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT not null,
  `follower_id`   INT not null,
  `follow_time`    VARCHAR(64) not null
) COMMENT='用户关注人信息' engine=InnoDB default charset=utf8;