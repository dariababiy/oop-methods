CREATE DATABASE library;
USE library;

DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	full_name VARCHAR(50),
	birthday DATETIME,
	lang VARCHAR(50),
	country VARCHAR(10)
);

INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (1, 'Stephen King', '1947-09-21', 'English', 'US');
INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (2, 'Joanne Rowling', '1965-07-31', 'English', 'GB');
INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (3, 'Erich Maria Remaeque', '1870-09-25', 'German', 'DE');
INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (4, 'Marcel Proust', '1871-07-10', 'French', 'FR');
INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (5, 'Sergey Yesenin', '1895-10-03', 'Russian', 'RU');
INSERT INTO authors(id, full_name, birthday, lang, country) VALUES (6, 'Taras Shevchenko', '1814-03-09', 'Ukrainian', 'UA');

DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_author INTEGER NOT NULL,
    title VARCHAR(100),
    genre VARCHAR(100),
    _year INTEGER,
    FOREIGN KEY (id_author) REFERENCES authors(id)
);

INSERT INTO books (id, id_author, title, genre, _year) VALUES (1, 1, 'It', 'horror', 1986);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (2, 1, 'Shining', 'horror', 1977);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (3, 1, 'Green Mile', 'fantasy', 1986);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (4, 2, 'Harry Potter and Philosophy Stone', 'fantasy', 1997);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (5, 2, 'Fantastic Beasts and Were to Find Them', 'fantasy', 2001);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (6, 2, 'Harry Potter and Deathly Hollows', 'fantasy', 2007);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (7, 3, 'Three Comrades', 'drama', 1936);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (8, 3, 'Shadows in the Paradise', 'novel', 1971);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (9, 3, 'Time to Live and Time to Die', 'novel', 1954);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (10, 4, 'Against Sainte-Beuve', 'modernist', 1954);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (11, 4, 'Pleasures and Days', 'modernist', 1896);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (12, 4, 'The Prisoner', 'novel', 1923);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (13, 5, 'Goodbye, My Friend, Goodbye', ' poem', 1925);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (14, 5, 'The Black Man', 'poem', 1925);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (15, 5, 'Hooligan', 'poem', 1919);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (16, 6, 'Testament', 'poem', 1845);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (17, 6, 'Catherine', 'poem', 1839);
INSERT INTO books (id, id_author, title, genre, _year) VALUES (18, 6, 'Haidamaky', 'poem', 1841);