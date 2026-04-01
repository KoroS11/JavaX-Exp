import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidBookDataException extends Exception {
    public InvalidBookDataException(String message) {
        super(message);
    }
}

class Book {
    private String isbn;
    private String title;
    private String author;
    private int quantity;
    private double price;

    public Book(String isbn, String title, String author, int quantity, double price) throws InvalidBookDataException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidBookDataException("ISBN cannot be empty.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidBookDataException("Title cannot be empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new InvalidBookDataException("Author cannot be empty.");
        }
        if (quantity < 0) {
            throw new InvalidBookDataException("Quantity cannot be negative.");
        }
        if (price <= 0) {
            throw new InvalidBookDataException("Price must be greater than 0.");
        }

        this.isbn = isbn.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.quantity = quantity;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getInventoryValue() {
        return quantity * price;
    }

    public void addStock(int amount) throws InvalidBookDataException {
        if (amount <= 0) {
            throw new InvalidBookDataException("Stock increment must be positive.");
        }
        quantity += amount;
    }

    public void display() {
        System.out.printf("ISBN: %s | Title: %s | Author: %s | Qty: %d | Price: %.2f%n",
                isbn, title, author, quantity, price);
    }
}

public class BookInventorySystem {

    private static final ArrayList<Book> inventory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice (1-5): ");

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    updateStock(scanner);
                    break;
                case 4:
                    showSummary();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting Book Inventory System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Book Inventory System ===");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Update Stock");
        System.out.println("4. Summary Report");
        System.out.println("5. Exit");
    }

    private static void addBook(Scanner scanner) {
        try {
            String isbn = readString(scanner, "ISBN: ");
            String title = readString(scanner, "Title: ");
            String author = readString(scanner, "Author: ");
            int quantity = readInt(scanner, "Quantity: ");
            double price = readDouble(scanner, "Price: ");

            Book newBook = new Book(isbn, title, author, quantity, price);
            inventory.add(newBook);
            System.out.println("Book added successfully.");
        } catch (InvalidBookDataException ex) {
            System.out.println("Error adding book: " + ex.getMessage());
        }
    }

    private static void viewBooks() {
        if (inventory.isEmpty()) {
            System.out.println("No books in inventory.");
            return;
        }

        System.out.println("\nBooks in Inventory:");
        for (Book book : inventory) {
            book.display();
        }
    }

    private static void updateStock(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. Add books first.");
            return;
        }

        String isbn = readString(scanner, "Enter ISBN to update: ");
        Book book = findByIsbn(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        int amount = readInt(scanner, "Enter stock to add: ");
        try {
            book.addStock(amount);
            System.out.println("Stock updated successfully.");
        } catch (InvalidBookDataException ex) {
            System.out.println("Error updating stock: " + ex.getMessage());
        }
    }

    private static void showSummary() {
        if (inventory.isEmpty()) {
            System.out.println("No books in inventory.");
            return;
        }

        int totalTitles = inventory.size();
        int totalQuantity = 0;
        double totalValue = 0;

        for (Book book : inventory) {
            totalQuantity += book.getQuantity();
            totalValue += book.getInventoryValue();
        }

        System.out.println("=== Inventory Summary ===");
        System.out.println("Total Titles: " + totalTitles);
        System.out.println("Total Quantity: " + totalQuantity);
        System.out.printf("Total Inventory Value: %.2f%n", totalValue);
    }

    private static Book findByIsbn(String isbn) {
        for (Book book : inventory) {
            if (book.getIsbn().equalsIgnoreCase(isbn.trim())) {
                return book;
            }
        }
        return null;
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid integer input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (!value.trim().isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }
}
