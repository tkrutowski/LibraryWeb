
CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use library;


CREATE TABLE `users` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `is_admin` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_user`));
  
  CREATE TABLE `authors` (
  `id_author` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_author`));
  
  CREATE TABLE `bookstores`  (
  `id_bookstore` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `www` VARCHAR(100) NULL,
  PRIMARY KEY (`id_bookstore`));
  
    CREATE TABLE `series` (
  `id_series` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id_series`));
  
    CREATE TABLE `categories` (
  `id_category` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id_categories`));
  
   CREATE TABLE `books` (
  `id_book` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_bookstore` INT NOT NULL,
  `id_series` INT NULL,
  `title` VARCHAR(45) NULL,
  `subtitle` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `cover` VARCHAR(45) NULL,
  `edition_type` TINYINT NULL,
  `reading_status` TINYINT NULL,
  `ownership_status` TINYINT NULL,
  `read_from` DATE NULL,
  `read_to` DATE NULL,
  `info` VARCHAR(200) NULL,
  `is_read` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id_book`),
  FOREIGN KEY (id_user) REFERENCES users(id_user),
  FOREIGN KEY (id_bookstore) REFERENCES bookstores(id_bookstore),
   FOREIGN KEY (id_series) REFERENCES series(id_series),
  );
  
	CREATE TABLE `books_categories` (
  `id_category` INT NOT NULL,
  `id_book` INT NOT NULL,
   PRIMARY KEY ('id_books'`id_category`)
   FOREIGN KEY (id_books) REFERENCES books(id_books),
   FOREIGN KEY (id_category) REFERENCES categories(id_category)
   );
   
   	CREATE TABLE `books_authors` (
  `id_author` INT NOT NULL,
  `id_book` INT NOT NULL,
   PRIMARY KEY ('id_books'`id_author`)
   FOREIGN KEY (id_books) REFERENCES books(id_books),
   FOREIGN KEY (id_author) REFERENCES authors(id_author)
   );
    
    
    select * from author;