package com.example.oop.methods.lab4.client;

public class ClientRunner {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.disconnect();
        }
    }
}
