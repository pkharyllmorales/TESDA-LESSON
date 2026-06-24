import java.util.Scanner;

// 1. Base class representing a generic Vehicle
class Vehicle {
    protected String make;
    protected String model;
    protected int year;

    // Base class constructor
    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }
    
    // Generic action method
    public void startEngine() {
        System.out.println("The vehicle's engine starts.");
    }
}

// 2. Derived class inheriting from Vehicle
class Car extends Vehicle {
    private int numberOfDoors;

    // Constructor leveraging super() to initialize parent fields
    public Car(String make, String model, int year, int numberOfDoors) {
        super(make, model, year); 
        this.numberOfDoors = numberOfDoors;
    }

    // Overriding the action method for specific behavior
    @Override
    public void startEngine() {
        System.out.println("The " + make + " " + model + " purrs to life!");
    }

    // Method to display all collected attributes
    public void displayDetails() {
        System.out.println("\n=======================");
        System.out.println("     CAR DETAILS       ");
        System.out.println("=======================");
        System.out.println("Make:            " + make);
        System.out.println("Model:           " + model);
        System.out.println("Year:            " + year);
        System.out.println("Number of Doors: " + numberOfDoors);
        System.out.println("=======================");
    }
}

// 3. Main Application Class
public class Chap5Task13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Vehicle Registration System ---\n");

        // Capturing Strings
        System.out.print("Enter Car Make (e.g., Toyota): ");
        String userMake = scanner.nextLine();

        System.out.print("Enter Car Model (e.g., Camry): ");
        String userModel = scanner.nextLine();

        // Input Validation Loop for Year
        int userYear = 0;
        while (true) {
            System.out.print("Enter Manufacturing Year (1886 - 2026): ");
            if (scanner.hasNextInt()) {
                userYear = scanner.nextInt();
                if (userYear >= 1886 && userYear <= 2026) {
                    break; 
                }
            } else {
                scanner.next(); // Clear invalid non-integer input
            }
            System.out.println("Error: Please enter a valid historical year.");
        }

        // Input Validation Loop for Doors
        int userDoors = 0;
        while (true) {
            System.out.print("Enter Number of Doors (1 - 6): ");
            if (scanner.hasNextInt()) {
                userDoors = scanner.nextInt();
                if (userDoors >= 1 && userDoors <= 6) {
                    break;
                }
            } else {
                scanner.next(); 
            }
            System.out.println("Error: Please enter a valid number of doors.");
        }

        // Object Instantiation
        Car myCar = new Car(userMake, userModel, userYear, userDoors);

        // Output results and trigger dynamic behavior
        myCar.displayDetails();
        myCar.startEngine();

        // Close scanner resource
        scanner.close();
    }
}