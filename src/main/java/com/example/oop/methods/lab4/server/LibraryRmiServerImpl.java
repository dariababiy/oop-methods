package com.example.oop.methods.lab4.server;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;
import com.example.oop.methods.lab2.AuthorRepository;
import com.example.oop.methods.lab2.BookRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryRmiServerImpl extends UnicastRemoteObject implements LibraryRmiServer {

//    private final Library library;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
//    private long maxAuthorId;
//    private long maxBookId;

    protected LibraryRmiServerImpl() throws RemoteException {
        super();
//        this.library = new Library();
        this.authorRepository = new AuthorRepository();
        this.bookRepository = new BookRepository();
//        this.maxAuthorId = 0L;
//        this.maxBookId = 0L;
    }

    @Override
    public long createAuthor(String fullName, String birthday, String lang, String country) {
        try {
//            Author author = new Author();
//            author.setId(++this.maxAuthorId);
//            author.setFullName(fullName);
//            author.setBirthDay(birthday);
//            author.setLang(lang);
//            author.setCountry(country);
//            this.library.getAuthors().add(author);
//            return author.getId();
            return this.authorRepository.createAuthor(fullName, birthday, lang, country);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public boolean deleteAuthor(long id) {
//        return this.library.getAuthors().removeIf(a -> a.getId().equals(id));
        return this.authorRepository.deleteAuthor(id);
    }

    @Override
    public long createBook(String title, String genre, int year, long authorId) {
        try {
//            Book book = new Book();
//            book.setId(++this.maxBookId);
//            book.setTitle(title);
//            book.setGenre(genre);
//            book.setYear(year);
//            book.setAuthor(this.getAuthorById(authorId));
//            this.library.getBooks().add(book);
//            return book.getId();
            return this.bookRepository.createBook(
                    this.authorRepository.getAuthorById(authorId).getFullName(),
                    title, genre, year
            );
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    private Author getAuthorById(long id) throws Exception {
//        return this.library.getAuthors().stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new Exception("Author with ID " + id + " not found"));
        return this.authorRepository.getAuthorById(id);
    }

    @Override
    public boolean deleteBook(long id) {
//        return this.library.getBooks().removeIf(b -> b.getId().equals(id));
        return this.bookRepository.deleteBook(id);
    }

    @Override
    public boolean updateBook(long id, String title, String genre, int year, long authorId) {
        try {
//            Book book = this.findBookById(id);
//            book.setTitle(title);
//            book.setGenre(genre);
//            book.setYear(year);
//            book.setAuthor(this.getAuthorById(authorId));
//            return true;
            int rowsAffected = this.bookRepository.updateBook(
                    id,
                    this.authorRepository.getAuthorById(authorId).getFullName(),
                    title, genre, year
            );
            return rowsAffected != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Book findBookById(long id) {
//        return this.library.getBooks().stream()
//                .filter(b -> b.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new Exception("Book with ID " + id + " not found"));
        return this.bookRepository.getBookById(id);
    }

    @Override
    public int countBooks() {
//        return this.library.getBooks().size();
        return this.bookRepository.getAllBooks().size();
    }

    @Override
    public List<Book> getBooksByAuthorFullName(String fullName) {
//        return this.library.getBooks().stream()
//                .filter(b -> b.getAuthor().getFullName().equals(fullName))
//                .collect(Collectors.toList());
        return this.bookRepository.getAllBooks().stream()
                .filter(b -> b.getAuthor().getFullName().equals(fullName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByAuthorId(long id) {
//        return this.library.getBooks().stream()
//                .filter(b -> b.getAuthor().getId().equals(id))
//                .collect(Collectors.toList());
        return this.bookRepository.getAllBooks().stream()
                .filter(b -> b.getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> listAuthors() {
//        return this.library.getAuthors();
        return this.authorRepository.getAllAuthors();
    }
}
