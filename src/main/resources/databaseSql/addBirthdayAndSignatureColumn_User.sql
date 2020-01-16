ALTER TABLE `user` ADD `birthday` varchar(64)  NOT NULL after `gender`;
ALTER TABLE `user` ADD `signature` varchar(512)  NOT NULL after `birthday`;
ALTER TABLE `user` MODIFY `icon` varchar(512) NOT NULL;