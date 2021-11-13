# library

Requirements:
Tomcat 9.0
MySQL 8.0 

Docker for testing DAO via TestContainers

URl = "jdbc:mysql://localhost:3306/"
DRIVER = "com.mysql.cj.jdbc.Driver"
USER and PASSWORD are set in /connection/C3P0DataSource - by default are "root"/"root"


App will create the database HARDZEYEU (if not exists) with 2 tables: BOOKS & BORROWS
Tables are empty, so feel free to add/update/remove some books/borrows. 

Sorry for app appearance) 

