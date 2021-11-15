package com.example.oop.methods.lab4.client;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;
import com.example.oop.methods.lab4.server.LibraryRmiServer;
import com.example.oop.methods.lab4.server.ServerRunner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static final String URL = "//127.0.0.1:" + ServerRunner.SERVER_PORT + "/Library";

    private Scanner scanner;
    private LibraryRmiServer library;

    public void connect() throws MalformedURLException, NotBoundException, RemoteException {
        this.library = (LibraryRmiServer) Naming.lookup(URL);
        this.scanner = new Scanner(System.in);
        System.out.println("RMI object connected ...");
        System.out.println("=== LIST OF AVAILABLE OPERATIONS ===");
        System.out.println("[1] Add new author");
        System.out.println("[2] Delete an author");
        System.out.println("[3] Add new book");
        System.out.println("[4] Delete a book");
        System.out.println("[5] Update book info");
        System.out.println("[6] Book count");
        System.out.println("[7] Get books by author's full name");
        System.out.println("[8] Get books by author's ID");
        System.out.println("[9] List all authors");
        System.out.println("[0] Exit client");
        int userInput;
        do {
            userInput = Integer.parseInt(this.input("Operation code: "));
            this.invokeMethod(userInput);
        } while (userInput != 0);
    }

    public void disconnect() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }

    public String input(String prompt) {
        System.out.print(prompt);
        String userInput = this.scanner.nextLine();
        System.out.println(">>> " + userInput);
        return userInput;
    }

    public void invokeMethod(int operationCode) throws RemoteException {
        System.out.println("******");
        switch (operationCode) {
            case 1: {
                String fullName = this.input("Full name: ");
                String birthday = this.input("Date of birth (in format 'yyyy-MM-dd'): ");
                String lang = this.input("Lang: ");
                String country = this.input("Country: ");
                long id = this.library.createAuthor(fullName, birthday, lang, country);
                if (id != -1L) {
                    System.out.println("Author created: ID = " + id);
                } else {
                    System.out.println("Author not created: exception occurred");
                }
                break;
            }
            case 2: {
                long id = Long.parseLong(this.input("Author ID: "));
                boolean success = this.library.deleteAuthor(id);
                System.out.println("Author deleted: " + success);
                break;
            }
            case 3: {
                String title = this.input("Title: ");
                String genre = this.input("Genre: ");
                int year = Integer.parseInt(this.input("Year: "));
                long authorId = Long.parseLong(this.input("Author ID: "));
                long id = this.library.createBook(title, genre, year, authorId);
                if (id != -1L) {
                    System.out.println("Book created: ID = " + id);
                } else {
                    System.out.println("Book not created: exception occurred");
                }
                break;
            }
            case 4: {
                long id = Long.parseLong(this.input("Book ID: "));
                boolean success = this.library.deleteBook(id);
                System.out.println("Book deleted: " + success);
                break;
            }
            case 5: {
                long bookId = Long.parseLong(this.input("Book ID: "));
                String title = this.input("Title: ");
                String genre = this.input("Genre: ");
                int year = Integer.parseInt(this.input("Year: "));
                long authorId = Long.parseLong(this.input("Author ID: "));
                boolean success = this.library.updateBook(bookId, title, genre, year, authorId);
                System.out.println("Update success: " + success);
                break;
            }
            case 6: {
                int count = this.library.countBooks();
                System.out.println("Books in library: " + count);
                break;
            }
            case 7: {
                String fullName = this.input("Author's full name: ");
                List<Book> books = this.library.getBooksByAuthorFullName(fullName);
                for (Book b: books) {
                    System.out.println("> " + b.toString());
                }
                break;
            }
            case 8: {
                long id = Long.parseLong(this.input("Author's ID: "));
                List<Book> books = this.library.getBooksByAuthorId(id);
                for (Book b: books) {
                    System.out.println("> " + b.toString());
                }
                break;
            }
            case 9: {
                List<Author> authors = this.library.listAuthors();
                for (Author a: authors) {
                    System.out.println("> " + a.toString());
                }
                break;
            }
        }
        System.out.println("******");
    }
}
