public class Chap4Task8 {

    public static void main(String[] args) {
        System.out.println("--- Processing Parameters: (4, 5, 10) ---");
        int totalSum = processAndSum(4, 5, 10);
        
        System.out.println("\nTotal return value (Sum of all computed cumulative results): " + totalSum);
        
        System.out.println("\n--- Processing Another Example: (3, 6) ---");
        int totalSum2 = processAndSum(3, 6);
        System.out.println("\nTotal return value: " + totalSum2);
    }

    public static int processAndSum(int... numbers) {
        int totalSumOfAll = 0;
        for (int num : numbers) {
            int cumulativeSum = calculateCumulativeSum(num);
            
            StringBuilder expression = new StringBuilder();
            for (int i = 1; i <= num; i++) {
                expression.append(i);
                if (i < num) {
                    expression.append("+");
                }
            }
            
            System.out.println("Parameter: " + num + " -> Cumulative Sum: (" + expression + ") = " + cumulativeSum);
    
            totalSumOfAll += cumulativeSum;
        }

        return totalSumOfAll;
    }

    private static int calculateCumulativeSum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
}