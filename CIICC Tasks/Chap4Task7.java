import java.util.Scanner;

public class Chap4Task7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first number: ");
        double num1 = scanner.nextDouble();


        System.out.print("Enter the second number: ");
        double num2 = scanner.nextDouble();

        System.out.println("\nRESULTS:");
    
        System.out.println("Addition (" + num1 + " + " + num2 + "): " + add(num1, num2));
        System.out.println("Subtraction (" + num1 + " - " + num2 + "): " + subtract(num1, num2));
        System.out.println("Multiplication (" + num1 + " * " + num2 + "): " + multiply(num1, num2));

        if (num2 != 0) {
            System.out.println("Division (" + num1 + " / " + num2 + "): " + divide(num1, num2));
        } else {
            System.out.println("Division (" + num1 + " / " + num2 + "): Error (Cannot divide by zero)");
        }

        scanner.close();
    }

    public static double add(double a, double b) {return a + b;}
    public static double subtract(double a, double b) {return a - b;}
    public static double multiply(double a, double b) {return a * b;}
    public static double divide(double a, double b) {return a / b;}
}
