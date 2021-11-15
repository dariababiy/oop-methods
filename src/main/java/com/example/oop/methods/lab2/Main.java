package com.example.oop.methods.lab2;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        testCrud();

    }

    private static void testCrud() {
        AuthorRepository authorRepository = new AuthorRepository();
        BookRepository bookRepository = new BookRepository();

        // create author
        System.out.println("*** CREATE AUTHOR ***");
        Long savedAuthorId = authorRepository.createAuthor(
                "Lina Kostenko",
                "1930-03-19",
                "mistake", // human mistake while creating new author
                "UA"
        );
        print(authorRepository.getAllAuthors());

        // get single author by id
        System.out.println("*** READ AUTHOR ***");
        Author author = authorRepository.getAuthorById(savedAuthorId);
        print(author);

        // update author info
        System.out.println("*** UPDATE AUTHOR ***");
        author.setLang("Ukrainian");
        authorRepository.updateAuthor(
                savedAuthorId,
                author.getFullName(),
                author.getBirthDay(),
                author.getLang(),
                author.getCountry()
        );
        print(authorRepository.getAllAuthors());

        // create book
        System.out.println("*** CREATE BOOK ***");
        Long savedBookId = bookRepository.createBook(
                author.getFullName(),
                "Sails",
                "xxxx", // human mistake while creating book
                1958);
        print(bookRepository.getAllBooks());

        // get book by id
        System.out.println("*** READ BOOK ***");
        Book book = bookRepository.getBookById(savedBookId);
        print(book);

        // update book info
        System.out.println("*** UPDATE BOOK ***");
        book.setGenre("poem");
        bookRepository.updateBook(
                savedBookId,
                author.getFullName(),
                book.getTitle(),
                book.getGenre(),
                book.getYear()
        );
        print(bookRepository.getAllBooks());

        // delete book
        System.out.println("*** DELETE BOOK ***");
        boolean bookDeleteSuccess = bookRepository.deleteBook(savedBookId);
        System.out.println(">>> Success: " + bookDeleteSuccess);
        print(bookRepository.getAllBooks());

        // delete author
        System.out.println("*** DELETE AUTHOR ***");
        boolean authorDeleteSuccess = authorRepository.deleteAuthor(savedAuthorId);
        System.out.println(">>> Success: " + authorDeleteSuccess);
        print(authorRepository.getAllAuthors());
    }

    private static <T> void print(List<T> objects) {
        for (T obj: objects) {
            System.out.println(">>> " + obj.toString());
        }
    }

    private static <T> void print(T obj) {
        System.out.println(">>> " + obj.toString());
    }

}
