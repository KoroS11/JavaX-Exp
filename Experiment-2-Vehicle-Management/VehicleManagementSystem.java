import java.util.InputMismatchException;
import java.util.Scanner;

class Vehicle {
    private String registrationNumber;
    private String brand;
    private String model;
    private int year;
    private double price;

    public Vehicle(String registrationNumber, String brand, String model, int year, double price) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.printf("Reg: %s | %s %s | Year: %d | Price: %.2f%n",
                registrationNumber, brand, model, year, price);
    }
}

public class VehicleManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Vehicle Management System ===");
        int count = readInt(scanner, "How many vehicles do you want to store? ", 1, 100);

        Vehicle[] vehicles = new Vehicle[count];

        for (int i = 0; i < count; i++) {
            System.out.println("\nEnter details for vehicle " + (i + 1) + ":");
            String reg = readNonEmptyString(scanner, "Registration Number: ");
            String brand = readNonEmptyString(scanner, "Brand: ");
            String model = readNonEmptyString(scanner, "Model: ");
            int year = readInt(scanner, "Year (1980-2100): ", 1980, 2100);
            double price = readDouble(scanner, "Price (> 0): ", 0.01, Double.MAX_VALUE);

            vehicles[i] = new Vehicle(reg, brand, model, year, price);
        }

        System.out.println("\n=== All Vehicle Records ===");
        for (Vehicle vehicle : vehicles) {
            vehicle.display();
        }

        printSummaryReport(vehicles);
        scanner.close();
    }

    private static void printSummaryReport(Vehicle[] vehicles) {
        double totalPrice = 0;
        double maxPrice = Double.MIN_VALUE;
        int newestYear = Integer.MIN_VALUE;

        for (Vehicle vehicle : vehicles) {
            totalPrice += vehicle.getPrice();
            if (vehicle.getPrice() > maxPrice) {
                maxPrice = vehicle.getPrice();
            }
            if (vehicle.getYear() > newestYear) {
                newestYear = vehicle.getYear();
            }
        }

        double averagePrice = totalPrice / vehicles.length;

        System.out.println("\n=== Summary Report ===");
        System.out.println("Total Vehicles: " + vehicles.length);
        System.out.printf("Average Price: %.2f%n", averagePrice);
        System.out.printf("Highest Price: %.2f%n", maxPrice);
        System.out.println("Newest Manufacturing Year: " + newestYear);
    }

    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid integer input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }
}
