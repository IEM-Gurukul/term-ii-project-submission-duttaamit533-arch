package service;
import java.io.*;   // ⭐ ADDED (for file handling)

import model.Book;
import java.util.ArrayList;

public class LibraryService {

    private ArrayList<Book> books = new ArrayList<>();

    // ⭐ ADDED CONSTRUCTOR (loads data when program starts)
    public LibraryService() {
        loadFromFile();
    }

    // Add book
    public void addBook(Book book) {
        books.add(book);
        saveToFile();   // ⭐ ADDED (save after adding)
        System.out.println("Book added successfully");
    }

    // Display all books
    public void displayBooks() {
        for (Book b : books) {
            b.displayBook();
        }
    }

    // Issue book
    public void issueBook(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                if (!b.isIssued()) {
                    b.setIssued(true);
                    saveToFile();   // ⭐ ADDED (save after issuing)
                    System.out.println("Book issued successfully");
                    return;
                } else {
                    System.out.println("Book already issued");
                    return;
                }
            }
        }
        System.out.println("Book not found");
    }

    // Return book
    public void returnBook(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                if (b.isIssued()) {
                    b.setIssued(false);
                    saveToFile();   // ⭐ ADDED (save after return)
                    System.out.println("Book returned successfully");
                    return;
                } else {
                    System.out.println("Book was not issued");
                    return;
                }
            }
        }
        System.out.println("Book not found");
    }

    // ⭐ ADDED METHOD (SAVE DATA TO FILE)
    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("books.txt");

            for (Book b : books) {
                fw.write(b.getBookId() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.isIssued() + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    // ⭐ ADDED METHOD (LOAD DATA FROM FILE)
    public void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Book b = new Book(data[0], data[1], data[2]);

                if (data[3].equals("true")) {
                    b.setIssued(true);
                }

                books.add(b);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("No previous data found");
        }
    }
}