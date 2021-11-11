DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS borrows;

CREATE TABLE books
(
    book_id    INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    publ_date DATE NOT NULL,
    page_count INTEGER NOT NULL,
    isbn VARCHAR(100) NOT NULL,
    des VARCHAR(300) NULL,
    status VARCHAR(100) NULL,
    authors VARCHAR(100) NOT NULL,
    genres VARCHAR(100) NOT NULL,
    amount INTEGER      NOT NULL,
    cover_ext VARCHAR(45) NULL
) ENGINE = InnoDB;

CREATE TABLE borrows
(
    book_id     INTEGER NOT NULL ,
    user_name VARCHAR(45) NOT NULL,
    user_email VARCHAR(45) NOT NULL,
    borrow_date DATE NOT NULL,
    time_period INTEGER NOT NULL,
    status VARCHAR(45) NULL,
    comment VARCHAR(200) NULL,
    borrow_id INTEGER AUTO_INCREMENT PRIMARY KEY ,
    return_date DATE NULL,
    due_date DATE NULL,
    CONSTRAINT fk_books FOREIGN KEY (book_id) REFERENCES books (book_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;
