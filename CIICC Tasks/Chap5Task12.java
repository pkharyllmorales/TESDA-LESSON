import java.util.Scanner;

public class Chap5Task12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter initial X coordinate: ");
        double userX = scanner.nextDouble();

        System.out.print("Enter initial Y coordinate: ");
        double userY = scanner.nextDouble();

        Point p = new Point(userX, userY);
        
        System.out.println("\nScaling the point 5 times:");
        for (int i = 0; i < 5; i++) {
            p.scale();
            p.print();
        }

        // 5. Close the scanner to release resources
        scanner.close();
    }
}

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("(" + x + "," + y + ")");
    }

    public void scale() {
        this.x /= 2;
        this.y /= 2;
    }
}