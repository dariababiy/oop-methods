package com.example.oop.methods.lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    public static final String URL = "jdbc:mysql://localhost:3306/library";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    public static final String INSERT_AUTHOR_QUERY =
            "INSERT INTO authors(full_name, birthday, lang, country) VALUES (?, ?, ?, ?)";
    public static final String INSERT_BOOK_QUERY =
            "INSERT INTO books(id_author, title, genre, _year) VALUES (?, ?, ?, ?)";
    public static final String GET_AUTHOR_BY_ID_QUERY =
            "SELECT * FROM authors WHERE id = ?";
    public static final String GET_BOOK_BY_ID_QUERY =
            "SELECT * FROM books WHERE id = ?";
    public static final String UPDATE_AUTHOR_QUERY =
            "UPDATE authors SET full_name = ?, birthday = ?, lang = ?, country = ? WHERE id = ?";
    public static final String UPDATE_BOOK_QUERY =
            "UPDATE books SET id_author = ?, title = ?, genre = ?, _year = ? WHERE id = ?";
    public static final String DELETE_AUTHOR_QUERY =
            "DELETE FROM authors WHERE id = ?";
    public static final String DELETE_BOOK_QUERY =
            "DELETE FROM books WHERE id = ?";
    public static final String GET_ALL_AUTHORS_QUERY =
            "SELECT * FROM authors";
    public static final String GET_ALL_BOOKS_QUERY =
            "SELECT * FROM books";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }

}
