import java.util.Scanner;

abstract class abstractClass {
    abstract void abstractMethod();
    
    void concreteMethod() {
        System.out.println("This is a concrete method.");
    }
}

class B extends abstractClass {
    @Override
    void abstractMethod() {
        System.out.println("B's implementation of abstractMethod.");
    }
}

class C extends abstractClass {
    @Override
    void abstractMethod() {
        System.out.println("C's implementation of abstractMethod.");
    }
}

public class Chap5Task14 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        abstractClass selectedObject = null;

        System.out.println("--- Abstract Class & Polymorphism Demo ---");
        System.out.println("Which subclass would you like to instantiate?");
        System.out.println("[1] Class B");
        System.out.println("[2] Class C");
        
        while (selectedObject == null) {
            System.out.print("Enter your choice (1 or 2): ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    selectedObject = new B();
                } else if (choice == 2) {
                    selectedObject = new C();
                } else {
                    System.out.println("Invalid selection. Choose 1 or 2.");
                }
            } else {
                System.out.println("Please enter a numeric choice.");
                scanner.next();
            }
        }

        System.out.println("\n--- Executing Methods ---");
        selectedObject.abstractMethod(); 
        selectedObject.concreteMethod();

        scanner.close();
    }
}