package com.example.oop.methods.lab1;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;
import com.example.oop.methods.commons.model.Library;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {

    private final Library library;
    private long maxAuthorId;
    private long maxBookId;

    public LibraryService(Library library) {
        this.library = library;
        for (Author a: this.library.getAuthors()) {
            if (a.getId() > this.maxAuthorId) {
                this.maxAuthorId = a.getId();
            }
        }
        for (Book b: this.library.getBooks()) {
            if (b.getId() > this.maxBookId) {
                this.maxBookId = b.getId();
            }
        }
    }

    /**
     * AUTHORS CRUD
     */

    public boolean addAuthor(String fullName, String birthDay, String lang, String country) {
        try {
            Author author = new Author();
            author.setId(++this.maxAuthorId);
            author.setFullName(fullName);
            author.setBirthDay(birthDay);
            author.setLang(lang);
            author.setCountry(country);
            this.library.getAuthors().add(author);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Author getAuthor(Long id) {
        try {
            return this.library.getAuthors().stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Could not find author with id " + id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateAuthor(Long id, String fullName, String birthDay, String lang, String country) {
        try {
            Author author = this.getAuthor(id);
            author.setFullName(fullName);
            author.setBirthDay(birthDay);
            author.setLang(lang);
            author.setCountry(country);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAuthor(Long id) {
        return this.library.getAuthors().remove(this.getAuthor(id));
    }

    public List<Author> getAllAuthors() {
        return this.library.getAuthors();
    }

    /**
     * BOOKS CRUD
     */

    public boolean addBook(String title, String genre, Integer year, String authorsFullName) {
        try {
            Book book = new Book();
            book.setId(++this.maxBookId);
            book.setTitle(title);
            book.setGenre(genre);
            book.setYear(year);
            book.setAuthor(this.getAuthorByFullName(authorsFullName));
            this.library.getBooks().add(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Book getBook(Long id) {
        try {
            return this.library.getBooks().stream()
                    .filter(b -> b.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Could not find book with id " + id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateBook(Long id, String title, String genre, Integer year, String authorsFullName) {
        try {
            Book book = this.getBook(id);
            book.setTitle(title);
            book.setGenre(genre);
            book.setYear(year);
            book.setAuthor(this.getAuthorByFullName(authorsFullName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(Long id) {
        return this.library.getBooks().remove(this.getBook(id));
    }

    public List<Book> getAllBooks() {
        return this.library.getBooks();
    }

    /**
     * ADDITIONAL METHODS
     */

    public List<Book> getBooksByAuthorId(Long id) {
        return this.getAllBooks().stream()
                .filter(b -> b.getAuthor().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Author getAuthorByFullName(String fullName) {
        try {
            return this.library.getAuthors().stream()
                    .filter(a -> a.getFullName().equals(fullName))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Could not found author with name " + fullName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBookByTitle(String title) {
        try {
            return this.library.getBooks().stream()
                    .filter(b -> b.getTitle().equals(title))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Could not found book with title " + title));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
