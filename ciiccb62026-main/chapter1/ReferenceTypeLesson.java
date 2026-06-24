package chapter1;

import java.util.Date;

public class ReferenceTypeLesson {
    Date today;
    String greeting;
    public static void main(String[] args) {
        ReferenceTypeLesson rtl1 = new ReferenceTypeLesson();
        
        rtl1.today = new Date();
        rtl1.greeting = new String();
        
        Toy toy1 = new Toy();
        Toy toy2 = new Toy();
        Toy toy3 = toy1;
        System.out.println("end of program");
        String zooName;
        int numberOfAnimals;
        String s1,s2;
        String s3 = "Hello", s4 = "World";
        // double length, width; int height;✅
        // double length, width, int height;❌

    }
}
class Ref2{
    // public static void main(String[] args) {
    //     System.out.println(s3);
    // }
}
// P ✅     P✅     I ✅    C✅
// I ✅     C✅     C ✅
// C ✅

//C ✅      C ✅
//var✅     methods✅
//methods✅ var✅

// ❌