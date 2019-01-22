CREATE TABLE IF NOT EXISTS `user` (
  `user_id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `age` INT NOT NULL ,
  `country` VARCHAR(45) NOT NULL , 
  UNIQUE(username),
  PRIMARY KEY (`user_id`) )
  ENGINE=InnoDB;
  
  CREATE TABLE IF NOT EXISTS `friend_list` (
  `host` INT NOT NULL ,
  `friend` INT NOT NULL ,
  PRIMARY KEY (`host`, `friend`) ,
  CONSTRAINT `fk_person_has_person_person`
  FOREIGN KEY (`host` )
  REFERENCES `user` (`user_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_has_person_person1`
  FOREIGN KEY (`friend` )
  REFERENCES `user` (`user_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE=InnoDB;
  
insert into user (user_id,username,password,name,age,country) values (101,'omkar','12345','Omkar',28,'India');
insert into user (user_id,username,password,name,age,country) values (102,'priya','12345','Priya',24,'India');
insert into friend_list (host,friend) values (101,102);