package controller;

public class Main {
    public static void main(String[] args) {
        Application app = new ApplicationImpl(args);
        app.runApp();
    }
}