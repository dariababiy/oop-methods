package com.example.oop.methods.lab1;

import com.example.oop.methods.commons.util.IOService;
import com.example.oop.methods.commons.model.Library;

public class Main {

    public static void main(String[] args) {
        testWrite();
    }

    private static void testAuthorsCrud() {
        Library library = IOService.loadFromXml();
        LibraryService ls = new LibraryService(library);

        if (library == null) {
            System.out.println("Library obj is null");
            return;
        }

        // CREATE
        System.out.println("***");
        System.out.println("Create an author ...");
        System.out.println("***");
        ls.addAuthor("test", "2021-01-01", "test", "test");
        IOService.writeToConsole(library);

        // READ
        System.out.println("***");
        System.out.println("Get an author by full name ...");
        System.out.println("***");
        System.out.println(ls.getAuthorByFullName("test"));

        // UPDATE
        System.out.println("***");
        System.out.println("Update info about an author ...");
        System.out.println("***");
        Long authorIdToUpdate = ls.getAuthorByFullName("test").getId();
        ls.updateAuthor(authorIdToUpdate,
                "Dariia Babii",
                "2000-06-12",
                "Ukrainian",
                "UA");
        IOService.writeToConsole(library);

        // DELETE
        System.out.println("***");
        System.out.println("Delete an author ...");
        System.out.println("***");
        Long authorIdToDelete = ls.getAuthorByFullName("Dariia Babii").getId();
        ls.deleteAuthor(authorIdToDelete);
        IOService.writeToConsole(library);

        System.out.println("***");
        System.out.println("Writing to a file ...");
        System.out.println("***");
        boolean success = IOService.writeToXml(library, "test_authors_crud");
        System.out.println("Write: " + success);
    }

    private static void testBooksCrud() {
        Library library = IOService.loadFromXml();
        LibraryService ls = new LibraryService(library);

        if (library == null) {
            System.out.println("Library obj is null");
            return;
        }

        // CREATE
        System.out.println("***");
        System.out.println("Create new book ...");
        System.out.println("***");
        String authorName = "Lina Kostenko";
        ls.addAuthor(authorName, "1930-03-19", "Ukrainian", "UA");
        ls.addBook("Sails", "poem", 1958, authorName);
        ls.addBook("Wandering of the Heart", "poem", 1961, authorName);
        ls.addBook("test", "test", -1, authorName);
        IOService.writeToConsole(library);

        // READ
        System.out.println("***");
        System.out.println("Get a book by title ...");
        System.out.println("***");
        System.out.println(ls.getBookByTitle("Sails"));

        // UPDATE
        System.out.println("***");
        System.out.println("Update info about a book ...");
        System.out.println("***");
        Long bookIdToUpdate = ls.getBookByTitle("test").getId();
        ls.updateBook(bookIdToUpdate,
                "Rays of the Earth",
                "poem",
                1957,
                authorName
                );
        IOService.writeToConsole(library);

        // DELETE
        System.out.println("***");
        System.out.println("Delete a book ...");
        System.out.println("***");
        Long bookIdToDelete = ls.getBookByTitle("Rays of the Earth").getId();
        ls.deleteBook(bookIdToDelete);
        IOService.writeToConsole(library);

        System.out.println("***");
        System.out.println("Writing to a file ...");
        System.out.println("***");
        boolean success = IOService.writeToXml(library, "test_books_crud");
        System.out.println("Write: " + success);
    }

    private static void testWrite() {
        Library library = IOService.loadFromXml();
        LibraryService ls = new LibraryService(library);

        if (library == null) {
            System.out.println("Library obj is null");
            return;
        }

        System.out.println("***");
        System.out.println("Create new book ...");
        System.out.println("***");
        String authorName = "Lina Kostenko";
        ls.addAuthor(authorName, "1930-03-19", "Ukrainian", "UA");
        ls.addBook("Sails", "poem", 1958, authorName);
        ls.addBook("Wandering of the Heart", "poem", 1961, authorName);
        ls.addBook("test", "test", -1, authorName);
        IOService.writeToConsole(library);

        System.out.println("***");
        System.out.println("Writing to a file ...");
        System.out.println("***");
        boolean success = IOService.writeToXml(library, "test_books_crud");
        System.out.println("Write: " + success);
    }

}
