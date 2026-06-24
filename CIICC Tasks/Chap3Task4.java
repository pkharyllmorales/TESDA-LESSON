import java.util.Scanner;
public class Chap3Task4 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a string: ");
        
        String input = scanner.nextLine();
        StringBuilder sb = new StringBuilder(input);
        sb.reverse();
       
        System.out.println(sb);

        if (input.equals(sb.toString())) {
            System.out.println("The string is a palindrome.");
        } else {
            System.out.println("The string is not a palindrome.");
        }
        scanner.close();

    }
}