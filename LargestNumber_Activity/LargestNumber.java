
package LargestNumber_Activity;

import java.util.Scanner;

public class LargestNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Enter three numbers: ");
            int num1 = scanner.nextInt();
            int num2 = scanner.nextInt();
            int num3 = scanner.nextInt();

            if (num1 == num2 && num2 == num3) {
                System.out.println("All numbers are equal!");
            } else {
                int largest = num1; //num1
                if (num2 > largest) {
                    largest = num2; //num2
                }
                if (num3 > largest) {
                    largest = num3; //num3
                }

                System.out.println("The largest number isssss " + largest + "!!");
            }
            
            System.out.print("Do you want to enter more numbers? (yes/no): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Goodbye!");
                break;
            }
        }

        scanner.close();
    }
}