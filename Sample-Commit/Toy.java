public class Toy {
    String name;
    String brand;
    double price;
    int quantity;
    char size;



    void Setprice(double price){
        this.price = price;
    }
}   

class Inventory {
    public static void main(String[] args) {
        Toy toy1 = new Toy();
        toy1.name = "Teddy Bear";
        toy1.brand = "Build-A-Bear";
        toy1.price = 29.99;
        toy1.quantity = 10;
        toy1.size = 'M';

        System.out.println("Toy Name: " + toy1.name);
        System.out.println("Brand: " + toy1.brand);
        System.out.println("Price: $" + toy1.price);
        System.out.println("Quantity: " + toy1.quantity);
        System.out.println("Size: " + toy1.size);
    }
}