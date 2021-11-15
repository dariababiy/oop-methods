package com.example.oop.methods.commons.model;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Author> authors;
    private List<Book> books;

    public Library() {
        this.authors = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
