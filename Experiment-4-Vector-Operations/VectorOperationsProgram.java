import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidVectorOperationException extends Exception {
    public InvalidVectorOperationException(String message) {
        super(message);
    }
}

class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public double dot(Vector2D other) {
        return (this.x * other.x) + (this.y * other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3D subtract(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public double dot(Vector3D other) {
        return (this.x * other.x) + (this.y * other.y) + (this.z * other.z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

public class VectorOperationsProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt(scanner, "Enter choice (1-3): ");

            try {
                switch (choice) {
                    case 1:
                        handle2DOperations(scanner);
                        break;
                    case 2:
                        handle3DOperations(scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting Vector Operations Program.");
                        break;
                    default:
                        throw new InvalidVectorOperationException("Menu choice must be 1, 2, or 3.");
                }
            } catch (InvalidVectorOperationException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("=== Vector Operations Program ===");
        System.out.println("1. 2D Vector Operations");
        System.out.println("2. 3D Vector Operations");
        System.out.println("3. Exit");
    }

    private static void printOperationMenu() {
        System.out.println("Select operation:");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Dot Product");
    }

    private static void handle2DOperations(Scanner scanner) throws InvalidVectorOperationException {
        System.out.println("Enter first 2D vector:");
        Vector2D v1 = new Vector2D(
                readDouble(scanner, "x1: "),
                readDouble(scanner, "y1: "));

        System.out.println("Enter second 2D vector:");
        Vector2D v2 = new Vector2D(
                readDouble(scanner, "x2: "),
                readDouble(scanner, "y2: "));

        printOperationMenu();
        int op = readInt(scanner, "Operation (1-3): ");

        switch (op) {
            case 1:
                System.out.println("Result (v1 + v2): " + v1.add(v2));
                break;
            case 2:
                System.out.println("Result (v1 - v2): " + v1.subtract(v2));
                break;
            case 3:
                System.out.println("Result (v1 . v2): " + v1.dot(v2));
                break;
            default:
                throw new InvalidVectorOperationException("Operation must be 1, 2, or 3.");
        }
    }

    private static void handle3DOperations(Scanner scanner) throws InvalidVectorOperationException {
        System.out.println("Enter first 3D vector:");
        Vector3D v1 = new Vector3D(
                readDouble(scanner, "x1: "),
                readDouble(scanner, "y1: "),
                readDouble(scanner, "z1: "));

        System.out.println("Enter second 3D vector:");
        Vector3D v2 = new Vector3D(
                readDouble(scanner, "x2: "),
                readDouble(scanner, "y2: "),
                readDouble(scanner, "z2: "));

        printOperationMenu();
        int op = readInt(scanner, "Operation (1-3): ");

        switch (op) {
            case 1:
                System.out.println("Result (v1 + v2): " + v1.add(v2));
                break;
            case 2:
                System.out.println("Result (v1 - v2): " + v1.subtract(v2));
                break;
            case 3:
                System.out.println("Result (v1 . v2): " + v1.dot(v2));
                break;
            default:
                throw new InvalidVectorOperationException("Operation must be 1, 2, or 3.");
        }
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
}
