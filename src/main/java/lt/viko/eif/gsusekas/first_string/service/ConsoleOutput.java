package lt.viko.eif.gsusekas.first_string.service;

import org.springframework.stereotype.Service;

import lt.viko.eif.gsusekas.first_string.model.Book;
import lt.viko.eif.gsusekas.first_string.model.Library;

@Service
public class ConsoleOutput {

    public void consoleOutput(Library library) {
        System.out.println("\n--- LIBRARY DATA ---");
        System.out.println("Library ID: " + library.getId());
        System.out.println("Library Name: " + library.getName());
        System.out.println("City: " + library.getCity());
        System.out.println("Open: " + library.isOpen());
        System.out.println("Yearly Budget: " + library.getYearlyBudget());

        System.out.println("\n--- BOOKS ---");
        for (Book book : library.getBooks()) {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Pages: " + book.getPages());
            System.out.println("Price: " + book.getPrice());
            System.out.println("Available: " + book.isAvailable());
            System.out.println("-------------------------");
        }
    }
}
    

