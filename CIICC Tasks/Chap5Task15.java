import java.util.ArrayList;
import java.util.Scanner;

interface Shape {
    double calculateArea();
    double calculatePerimeter();
}

abstract class AbstractShape implements Shape {
    protected String color;
    protected double length;
    protected double width;

    public AbstractShape(String color, double length, double width) {
        this.color = color;
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return 0.0;
    }

    @Override
    public double calculatePerimeter() {
        return 0.0;
    }
    
    public String getColor() {
        return color;
    }
}

class Circle extends AbstractShape {
    private double radius;

    public Circle(String color, double radius) {
        super(color, 0, 0);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends AbstractShape {
    public Rectangle(String color, double length, double width) {
        super(color, length, width);
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

public class Chap5Task15 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<AbstractShape> shapeList = new ArrayList<>();
        
        System.out.println("=== Geometry Management System ===");
        
        boolean running = true;
        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("[1] Add a Circle");
            System.out.println("[2] Add a Rectangle");
            System.out.println("[3] View All Shapes & Exit");
            System.out.print("Choice: ");
            
            int choice = getValidIntegerInput();
            
            if (choice == 1) {
                System.out.print("Enter Circle Color: ");
                String color = scanner.next();
                System.out.print("Enter Circle Radius: ");
                double radius = getValidDoubleInput();
                
                shapeList.add(new Circle(color, radius));
                System.out.println("Circle added successfully!");
                
            } else if (choice == 2) {
                System.out.print("Enter Rectangle Color: ");
                String color = scanner.next();
                System.out.print("Enter Rectangle Length: ");
                double length = getValidDoubleInput();
                System.out.print("Enter Rectangle Width: ");
                double width = getValidDoubleInput();
                
                shapeList.add(new Rectangle(color, length, width));
                System.out.println("Rectangle added successfully!");
                
            } else if (choice == 3) {
                running = false;
            } else {
                System.out.println("Invalid selection. Try again.");
            }
        }

        System.out.println("\n==============================================");
        System.out.println("                SHAPE REPORT                  ");
        System.out.println("==============================================");
        if (shapeList.isEmpty()) {
            System.out.println("No shapes were added.");
        } else {
            for (AbstractShape shape : shapeList) {
                String typeName = shape.getClass().getSimpleName();
                System.out.printf("Type: %-10s | Color: %-8s | Area: %-8.2f | Perimeter: %.2f\n", 
                                  typeName, shape.getColor(), shape.calculateArea(), shape.calculatePerimeter());
            }
        }
        System.out.println("==============================================");
        
        scanner.close();
    }

    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid type! Enter a valid choice number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                if (value > 0) {
                    return value;
                }
            } else {
                scanner.next();
            }
            System.out.print("Invalid dimension! Enter a positive numeric value: ");
        }
    }
}