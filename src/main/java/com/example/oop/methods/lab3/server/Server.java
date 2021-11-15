package com.example.oop.methods.lab3.server;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;
import com.example.oop.methods.commons.model.Library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class Server {

    public static final int SERVER_PORT = 8888;
    public static final String IP_ADDRESS = "localhost";

    private int port;
    private Library library;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private long authorIdCounter;
    private long bookIdCounter;

    public Server(int port) {
        this.port = port;
        this.library = new Library();
        this.authorIdCounter = 0L;
        this.bookIdCounter = 0L;
    }

    public void start() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Server is listening to the port " + this.port);
        this.socket = this.serverSocket.accept();
        this.in = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
        while (true) {
            int operationCode = this.in.readInt();
            boolean success = this.processRequest(operationCode);
            System.out.println("Operation: " + success);
        }
    }

    public void stop() throws IOException {
        if (this.serverSocket == null || this.socket == null || this.in == null || this.out == null) {
            throw new IllegalStateException("Cannot call stop() before calling start() method");
        }
        this.serverSocket.close();
        this.socket.close();
        this.in.close();
        this.out.close();
    }

    /**
     * Processing a single request from the client
     * @param operationCode - integer mapped to an operation, must be one of the following:
     *                      1 - add new author
     *                      2 - delete an author
     *                      3 - add new book of the author
     *                      4 - delete a book
     *                      5 - edit book info
     *                      6 - count total amount of books
     *                      7 - get books by author full name
     *                      8 - get books by author id
     *                      9 - get all authors
     * @return true if the operation successful, otherwise - false
     */
    private boolean processRequest(int operationCode) {
        try {
            switch (operationCode) {
                case 1: {
                    String fullName = this.in.readUTF();
                    String birthDay = this.in.readUTF();
                    String lang = this.in.readUTF();
                    String country = this.in.readUTF();
                    long authorId = this.createNewAuthor(fullName, birthDay, lang, country);
                    if (authorId != -1L) {
                        System.out.println("Successfully created an author");
                        System.out.println("Writing author ID in output stream ...");
                    } else {
                        System.out.println("Couldn't create an author because an exception occurred");
                        System.out.println("Writing code -1 in output stream ...");
                    }
                    this.out.writeLong(authorId);
                    break;
                }
                case 2: {
                    long authorIdToDelete = this.in.readLong();
                    boolean success = this.deleteAuthorById(authorIdToDelete);
                    System.out.println("Delete success? " + success);
                    System.out.println("Writing success code in output stream ...");
                    this.out.writeBoolean(success);
                    break;
                }
                case 3: {
                    String title = this.in.readUTF();
                    String genre = this.in.readUTF();
                    int year = this.in.readInt();
                    long authorId = this.in.readLong();
                    long bookId = this.createNewBook(title, genre, year, authorId);
                    if (bookId != -1L) {
                        System.out.println("Successfully created a book");
                        System.out.println("Writing book ID in output stream ...");
                    } else {
                        System.out.println("Couldn't create a book because an exception occurred");
                        System.out.println("Writing code -1 in output stream ...");
                    }
                    this.out.writeLong(bookId);
                    break;
                }
                case 4: {
                    long bookIdToDelete = this.in.readLong();
                    boolean success = this.deleteBookById(bookIdToDelete);
                    System.out.println("Delete success? " + success);
                    System.out.println("Writing success code in output stream ...");
                    this.out.writeBoolean(success);
                    break;
                }
                case 5: {
                    long bookIdToUpdate = this.in.readLong();
                    String title = this.in.readUTF();
                    String genre = this.in.readUTF();
                    int year = this.in.readInt();
                    long authorId = this.in.readLong();
                    boolean success = this.updateBookInfoById(bookIdToUpdate, title, genre, year, authorId);
                    System.out.println("Update success? " + success);
                    System.out.println("Writing success code in output stream ...");
                    this.out.writeBoolean(success);
                    break;
                }
                case 6: {
                    int totalBooksAmount = this.library.getBooks().size();
                    System.out.println("Writing total books amount to the output stream ...");
                    this.out.writeInt(totalBooksAmount);
                    break;
                }
                case 7: {
                    String authorFullName = this.in.readUTF();
                    List<Book> booksByAuthor = this.getAllBooksByAuthorFullName(authorFullName);
                    this.out.writeInt(booksByAuthor.size());
                    for (Book b: booksByAuthor) {
                        this.out.writeLong(b.getId());
                        this.out.writeUTF(b.getTitle());
                        this.out.writeUTF(b.getGenre());
                        this.out.writeInt(b.getYear());
                        this.out.writeUTF(b.getAuthor().getFullName());
                    }
                    break;
                }
                case 8: {
                    long authorId = this.in.readLong();
                    List<Book> booksByAuthor = this.getAllBooksByAuthorId(authorId);
                    this.out.writeInt(booksByAuthor.size());
                    for (Book b: booksByAuthor) {
                        this.out.writeLong(b.getId());
                        this.out.writeUTF(b.getTitle());
                        this.out.writeUTF(b.getGenre());
                        this.out.writeInt(b.getYear());
                        this.out.writeUTF(b.getAuthor().getFullName());
                    }
                    break;
                }
                case 9: {
                    List<Author> authors = this.library.getAuthors();
                    this.out.writeInt(authors.size());
                    for (Author author: authors) {
                        this.out.writeLong(author.getId());
                        this.out.writeUTF(author.getFullName());
                        this.out.writeUTF(author.getBirthDay());
                        this.out.writeUTF(author.getLang());
                        this.out.writeUTF(author.getCountry());
                    }
                    break;
                }
                default: {
                    throw new Exception("No operation associated with code " + operationCode);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Book> getAllBooksByAuthorId(long authorId) {
        return this.library.getBooks().stream()
                .filter(b -> b.getAuthor().getId().equals(authorId))
                .collect(Collectors.toList());
    }

    private List<Book> getAllBooksByAuthorFullName(String authorFullName) {
        return this.library.getBooks().stream()
                .filter(b -> b.getAuthor().getFullName().equals(authorFullName))
                .collect(Collectors.toList());
    }

    private boolean updateBookInfoById(long bookIdToUpdate, String title, String genre, int year, long authorId) {
        try {
            Book bookToUpdate = this.findBookById(bookIdToUpdate);
            bookToUpdate.setTitle(title);
            bookToUpdate.setGenre(genre);
            bookToUpdate.setYear(year);
            bookToUpdate.setAuthor(this.findAuthorById(authorId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Book findBookById(long bookId) throws Exception {
        return this.library.getBooks().stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new Exception("No book with ID " + bookId + " found"));
    }

    private boolean deleteBookById(long bookIdToDelete) {
        return this.library.getBooks().removeIf(b -> b.getId().equals(bookIdToDelete));
    }

    private long createNewBook(String title, String genre, int year, long authorId) {
        try {
            Book book = new Book();
            book.setId(++this.bookIdCounter);
            book.setTitle(title);
            book.setGenre(genre);
            book.setYear(year);
            book.setAuthor(this.findAuthorById(authorId));
            this.library.getBooks().add(book);
            return book.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    private Author findAuthorById(long authorId) throws Exception {
        return this.library.getAuthors().stream()
                .filter(a -> a.getId().equals(authorId))
                .findFirst().orElseThrow(() -> new Exception("No author with ID " + authorId + " found"));
    }

    private boolean deleteAuthorById(long authorIdToDelete) {
        return this.library.getAuthors().removeIf(a -> a.getId().equals(authorIdToDelete));
    }

    private long createNewAuthor(String fullName, String birthDay, String lang, String country) {
        try {
            Author author = new Author();
            author.setId(++this.authorIdCounter);
            author.setFullName(fullName);
            author.setBirthDay(birthDay);
            author.setLang(lang);
            author.setCountry(country);
            this.library.getAuthors().add(author);
            return author.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(SERVER_PORT);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
