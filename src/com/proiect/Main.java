package com.proiect;

/**
 * The only purpose of the class is to create the object of
 * type Database and call the starting method with the input file.
 * This is what the user sees so that he can't interact with
 * the other classes.
 */
public class Main {

    public static void main(String[] args) {
        Database db = Database.getINSTANCE();
        db.start("in/input4.txt");
    }
}
