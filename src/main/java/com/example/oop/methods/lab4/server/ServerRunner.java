package com.example.oop.methods.lab4.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRunner {

    public static final int SERVER_PORT = 7777;

    public static void main(String[] args) {
        try {
            LibraryRmiServer library = new LibraryRmiServerImpl();
            Registry registry = LocateRegistry.createRegistry(SERVER_PORT);
            registry.rebind("Library", library);
            System.out.println("Server started");
            System.out.println("Listening to the port " + SERVER_PORT + " ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
