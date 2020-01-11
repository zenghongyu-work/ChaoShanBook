CREATE TABLE `user` (
  `id`   INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(64) not null,
  `pd`   VARCHAR(64) not null,
  `nickname`   VARCHAR(64) not null,
  `phone`   VARCHAR(64) not null,
  `gender`   VARCHAR(64) not null,
  `icon`   VARCHAR(64) not null,
  `province`   VARCHAR(64) not null,
  `downtown`   VARCHAR(64) not null,
  `follow_count`    int not null,
  `fan_count`    int not null,
  `praise_count`    int not null,
  `token`    VARCHAR(256) not null
) COMMENT='用户信息' engine=InnoDB default charset=utf8;