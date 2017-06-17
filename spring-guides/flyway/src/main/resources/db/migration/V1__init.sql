CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) not null,
  `last_name` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into PERSON (first_name, last_name) values ('Dave', 'Syer');