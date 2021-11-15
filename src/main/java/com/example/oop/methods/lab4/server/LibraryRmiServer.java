package com.example.oop.methods.lab4.server;

import com.example.oop.methods.commons.model.Author;
import com.example.oop.methods.commons.model.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryRmiServer extends Remote {
    long createAuthor(String fullName, String birthday, String lang, String country) throws RemoteException;
    boolean deleteAuthor(long id) throws RemoteException;
    long createBook(String title, String genre, int year, long authorId) throws RemoteException;
    boolean deleteBook(long id) throws RemoteException;
    boolean updateBook(long id, String title, String genre, int year, long authorId) throws RemoteException;
    int countBooks() throws RemoteException;
    List<Book> getBooksByAuthorFullName(String fullName) throws RemoteException;
    List<Book> getBooksByAuthorId(long id) throws RemoteException;
    List<Author> listAuthors() throws RemoteException;
}
