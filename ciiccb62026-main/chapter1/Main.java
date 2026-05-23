package chapter1;

import java.util.*; // built-in classes
import otherFolder.a.Makahiya; // from other folder
import otherFolder.SunFlower;

class Main{
    public static void main(String[] args) {
        System.out.println("Hello World");//codespace
        System.out.println("Hello World2");//vscode local pc
        System.out.println("Hello World3");//codespace
        Toy toy1 = new Toy();
        toy1.name = "Rage Pink";
        toy1.brand = "Lab Vuvu";
        toy1.price = 4500;
        toy1.quantity = 12;
        toy1.setPrice(toy1.price*0.5);//this set the price to 50% for the event sale 6-6-2026
        System.out.println(toy1.price);
        Toy toy2 = new Toy();
        System.out.println("end of program");
        Random r = new Random();
        Makahiya ma1 = new Makahiya();
        System.out.println(r.nextInt(10));

    }
}
