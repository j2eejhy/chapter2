CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `contact` varchar(255) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO customer
(name, contact, telephone, email, remark)
VALUES('customer1', '张三', '13333333333', '333@qq.com', null);


INSERT INTO customer
(name, contact, telephone, email, remark)
VALUES( 'customer2', '李四', '13333333331', '331@qq.com', null);