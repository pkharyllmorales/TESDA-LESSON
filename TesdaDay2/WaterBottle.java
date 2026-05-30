


public class LargestNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter three numbers: ");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        int num3 = scanner.nextInt();

        // Check if all numbers are equal first
        if (num1 == num2 && num2 == num3) {
            System.out.println("All numbers are equal");
        } else {
            // Assume the first number is the largest initially
            int largest = num1;

            if (num2 > largest) {
                largest = num2;
            }
            if (num3 > largest) {
                largest = num3;
            }

            System.out.println("The largest number is " + largest);
        }

        scanner.close();
    }
}