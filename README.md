library

Requirements:
Tomcat 9.0
MySQL 8.0 

Docker for testing DAO via TestContainers

URl = "jdbc:mysql://localhost:3306/"
DRIVER = "com.mysql.cj.jdbc.Driver"
USER and PASSWORD are set in /connection/C3P0DataSource - by default are "root"/"root"

App will create the database HARDZEYEU (if not exists) with 2 tables: BOOKS & BORROWS
Tables are empty, so feel free to add/update/remove some books/borrows. 

Covers are saved in the local path "e:\books_covers_server\", you can set it up in /utils/Utils -> saveCoverToDisc()
Because Chrome doesn`t allow viewing images from local disks, I used WebServer for Chrome - app, that emulated 
my local storage as web server. So, in bookPage.jsp path to cover file is set "http://127.0.0.1:8887/ which is an
emulated path.

