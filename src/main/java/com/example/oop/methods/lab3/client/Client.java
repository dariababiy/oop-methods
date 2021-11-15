package com.example.oop.methods.lab3.client;

import com.example.oop.methods.lab3.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;

    public void connect(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.in = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.scanner = new Scanner(System.in);
        if (this.socket.isConnected()) {
            System.out.println("Connected to server on " + hostname + ":" + port);
        }
    }

    public void sendRequest(int operationCode) throws IOException {
        switch (operationCode) {
            case 1: {
                this.out.writeInt(operationCode);
                this.sendAuthorInfoToSave();
                break;
            }
            case 2: {
                this.out.writeInt(operationCode);
                this.sendAuthorInfoToDelete();
                break;
            }
            case 3: {
                this.out.writeInt(operationCode);
                this.sendBookInfoToSave();
                break;
            }
            case 4: {
                this.out.writeInt(operationCode);
                this.sendBookInfoToDelete();
                break;
            }
            case 5: {
                this.out.writeInt(operationCode);
                this.sendBookInfoToUpdate();
                break;
            }
            case 6: {
                this.out.writeInt(operationCode);
                this.printTotalAmountOfBooks();
                break;
            }
            case 7: {
                this.out.writeInt(operationCode);
                this.printAllBooksByAuthorName();
                break;
            }
            case 8: {
                this.out.writeInt(operationCode);
                this.printAllBooksByAuthorId();
                break;
            }
            case 9: {
                this.out.writeInt(operationCode);
                this.printAllAuthors();
                break;
            }
        }
    }

    private void printAllAuthors() throws IOException {
        System.out.println("All authors in the library:");
        int size = this.in.readInt();
        System.out.println("Receiving " + size + " packages ...");
        for (int i = 0; i < size; i++) {
            long authorId = this.in.readLong();
            String fullName = this.in.readUTF();
            String birthDay = this.in.readUTF();
            String lang = this.in.readUTF();
            String country = this.in.readUTF();
            System.out.printf(">>> Author { id = %d, full_name = '%s', birthday = '%s', lang = '%s', country = '%s' }",
                    authorId, fullName, birthDay, lang, country);
        }
        System.out.println("Done");
    }

    private void printAllBooksByAuthorId() throws IOException {
        System.out.println("Insert author's ID:");
        long authorId = Long.parseLong(this.input("Author's ID: "));
        System.out.println("Ok, sending data to the server ...");
        this.out.writeLong(authorId);
        System.out.println("Sent! Waiting for the response ...");
        int size = this.in.readInt();
        System.out.println("Receiving " + size + " packages ...");
        for (int i = 0; i < size; i++) {
            long bookId = this.in.readLong();
            String title = this.in.readUTF();
            String genre = this.in.readUTF();
            int year = this.in.readInt();
            String authorFullName = this.in.readUTF();
            System.out.printf(">>> Book { id = %d, title = '%s', genre = '%s', year = %d, author_name = '%s' }",
                    bookId, title, genre, year, authorFullName);
        }
        System.out.println("Done");
    }

    private void printAllBooksByAuthorName() throws IOException {
        System.out.println("Insert author's full name:");
        String fullName = this.input("Author's full name: ");
        System.out.println("Ok, sending data to the server ...");
        this.out.writeUTF(fullName);
        System.out.println("Sent! Waiting for the response ...");
        int size = this.in.readInt();
        System.out.println("Receiving " + size + " packages ...");
        for (int i = 0; i < size; i++) {
            long bookId = this.in.readLong();
            String title = this.in.readUTF();
            String genre = this.in.readUTF();
            int year = this.in.readInt();
            String authorFullName = this.in.readUTF();
            System.out.printf(">>> Book { id = %d, title = '%s', genre = '%s', year = %d, author_name = '%s' }",
                    bookId, title, genre, year, authorFullName);
        }
        System.out.println("Done");
    }

    private void printTotalAmountOfBooks() throws IOException {
        System.out.println("Total amount of books in the library:");
        System.out.println(this.in.readInt());
    }

    private void sendBookInfoToUpdate() throws IOException {
        System.out.println("Insert book's info to update:");
        long bookIdToUpdate = Long.parseLong(this.input("Book ID: "));
        String title = this.input("Title: ");
        String genre = this.input("Genre: ");
        int year = Integer.parseInt(this.input("Year: "));
        long authorId = Long.parseLong(this.input("Author ID: "));
        System.out.println("Ok, sending data to the server ...");
        this.out.writeLong(bookIdToUpdate);
        this.out.writeUTF(title);
        this.out.writeUTF(genre);
        this.out.writeInt(year);
        this.out.writeLong(authorId);
        System.out.println("Sent! Waiting for the response ...");
        boolean success = this.in.readBoolean();
        System.out.println("Update success: " + success);
    }

    private void sendBookInfoToDelete() throws IOException {
        System.out.println("Insert book ID to delete:");
        long bookIdToDelete = Long.parseLong(this.input("ID: "));
        System.out.println("Ok, sending data to the server ...");
        this.out.writeLong(bookIdToDelete);
        System.out.println("Sent! Waiting for the response ...");
        boolean success = this.in.readBoolean();
        System.out.println("Delete success: " + success);
    }

    private void sendBookInfoToSave() throws IOException {
        System.out.println("Insert book's info:");
        String title = this.input("Title: ");
        String genre = this.input("Genre: ");
        int year = Integer.parseInt(this.input("Year: "));
        long authorId = Long.parseLong(this.input("Author ID: "));
        System.out.println("Ok, sending data to the server ...");
        this.out.writeUTF(title);
        this.out.writeUTF(genre);
        this.out.writeInt(year);
        this.out.writeLong(authorId);
        System.out.println("Sent! Waiting for the response ...");
        long bookId = this.in.readLong();
        System.out.println("Book with ID " + bookId + " saved to the library");
    }

    private void sendAuthorInfoToDelete() throws IOException {
        System.out.println("Insert author ID to delete:");
        long authorIdToDelete = Long.parseLong(this.input("ID: "));
        System.out.println("Ok, sending data to the server ...");
        this.out.writeLong(authorIdToDelete);
        System.out.println("Sent! Waiting for the response ...");
        boolean success = this.in.readBoolean();
        System.out.println("Delete success: " + success);
    }

    private void sendAuthorInfoToSave() throws IOException {
        System.out.println("Insert author's info:");
        String fullName = this.input("Full name: ");
        String birthDay = this.input("Date of birth (in format of yyyy-MM-dd): ");
        String lang = this.input("Lang: ");
        String country = this.input("Country: ");
        System.out.println("Ok, sending data to the server ...");
        this.out.writeUTF(fullName);
        this.out.writeUTF(birthDay);
        this.out.writeUTF(lang);
        this.out.writeUTF(country);
        System.out.println("Sent! Waiting for the response ...");
        long authorId = this.in.readLong();
        System.out.println("Author with ID " + authorId + " saved to the library");
    }

    private String input(String prompt) {
        System.out.print(prompt);
        String userInput = this.scanner.nextLine();
        System.out.println(">>> " + userInput);
        return userInput;
    }

    public void disconnect() throws IOException {
        if (this.socket == null || this.in == null || this.out == null || this.scanner == null) {
            throw new IllegalStateException("Cannot disconnect() from the server without connect()");
        }
        this.socket.close();
        this.in.close();
        this.out.close();
        this.scanner.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connect(Server.IP_ADDRESS, Server.SERVER_PORT);

            System.out.println("Input 1-9 to perform operations");
            System.out.println("Input -1 to close client and finish work");

            int userInput;
            do {
                userInput = Integer.parseInt(client.input("Code: "));
                client.sendRequest(userInput);
            } while (userInput != -1);

            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
