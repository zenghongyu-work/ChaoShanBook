CREATE TABLE `video_collect` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `video_id`   INT not null,
  `user_id` INT not null,
  `collect_time`    VARCHAR(64) not null
) COMMENT='视频收藏信息' engine=InnoDB default charset=utf8;