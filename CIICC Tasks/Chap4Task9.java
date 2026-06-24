import static java.lang.Math.addExact;
import static java.lang.Math.floorDiv;
import static java.lang.Math.multiplyExact;
import static java.lang.Math.subtractExact;

public class Chap4Task9 {

    public static int add(int a, int b) {
        // Using static import for Math.addExact()
        return addExact(a, b);
    }

    public static int subtract(int a, int b) {
        // Using static import for Math.subtractExact()
        return subtractExact(a, b);
    }

    public static int multiply(int a, int b) {
        return multiplyExact(a, b);
        // Using static import for Math.multiplyExact()
    }

    public static float divide(int a, int b) {
        return (float) floorDiv(a, b);
        // Using static import for Math.floorDiv()
        // Cast to float to match the method's required return type
    }

    public static void main(String[] args) {
        int num1 = 20;
        int num2 = 6;

        System.out.println("--- Math Operations with Static Import ---");
        System.out.println("Values used: " + num1 + " and " + num2 + "\n");

        System.out.println("Addition Result:       " + add(num1, num2));
        System.out.println("Subtraction Result:    " + subtract(num1, num2));
        System.out.println("Multiplication Result: " + multiply(num1, num2));
        
        if (num2 != 0) {
            System.out.println("Floor Division Result: " + divide(num1, num2));
        } else {
            System.out.println("Floor Division Result: Error (Cannot divide by zero)");
        }
    }
}