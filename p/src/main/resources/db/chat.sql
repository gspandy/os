-- 新增客服自动回复字段 add by xu 2017-01-20
ALTER TABLE `tb_user` ADD COLUMN `auto_reply` varchar(1000)  DEFAULT null;