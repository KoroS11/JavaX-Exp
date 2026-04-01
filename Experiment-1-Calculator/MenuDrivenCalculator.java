import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuDrivenCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Menu-Driven Calculator ===");

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice (1-6): ");

            switch (choice) {
                case 1: {
                    double a = readDouble(scanner, "Enter first number: ");
                    double b = readDouble(scanner, "Enter second number: ");
                    System.out.println("Result: " + add(a, b));
                    break;
                }
                case 2: {
                    double a = readDouble(scanner, "Enter first number: ");
                    double b = readDouble(scanner, "Enter second number: ");
                    System.out.println("Result: " + subtract(a, b));
                    break;
                }
                case 3: {
                    double a = readDouble(scanner, "Enter first number: ");
                    double b = readDouble(scanner, "Enter second number: ");
                    System.out.println("Result: " + multiply(a, b));
                    break;
                }
                case 4: {
                    double a = readDouble(scanner, "Enter dividend: ");
                    double b = readDouble(scanner, "Enter divisor: ");
                    try {
                        System.out.println("Result: " + divide(a, b));
                    } catch (ArithmeticException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }
                case 5:
                    System.out.println("Calculator supports: +, -, *, /");
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 6.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Help");
        System.out.println("6. Exit");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
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
                return scanner.nextDouble();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid numeric input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static double add(double a, double b) {
        return a + b;
    }

    private static double subtract(double a, double b) {
        return a - b;
    }

    private static double multiply(double a, double b) {
        return a * b;
    }

    private static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
